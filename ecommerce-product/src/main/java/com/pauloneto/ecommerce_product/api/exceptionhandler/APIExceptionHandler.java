package com.pauloneto.ecommerce_product.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.pauloneto.ecommerce_product.domain.exception.CategoriaExistenteException;
import com.pauloneto.ecommerce_product.domain.exception.EntidadeNaoEncontradaException;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado na API. Tente novamente caso "
			+ "o problema persista, entre em contato com o administrador da API.";
	
	public static final String MSG_ERRO_CAMPO_INVALIDO = "Um ou mais campos estão inválidos, faça o preenchimento correto e tente novamente!";
	
	@Override
	@Nullable
	protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		TipoErroEnum tipoErro = TipoErroEnum.PARAMETRO_INVALIDO;
				
		List<Erro.Campo> problemObjects = ex.getAllErrors().stream()
				.map(oe -> {
					String msg = oe.getDefaultMessage();
					return Erro.Campo.builder().mensagem(msg).build();
				}).collect(Collectors.toList());
		Erro e = createErroBuilder(status, tipoErro, MSG_ERRO_CAMPO_INVALIDO).userMessage(MSG_ERRO_CAMPO_INVALIDO)
				.campos(problemObjects).build();
		return handleExceptionInternal(ex, e, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		TipoErroEnum tipoErro = TipoErroEnum.PARAMETRO_INVALIDO;
		BindingResult bindingResult = ex.getBindingResult();
		List<Erro.Campo> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			String msg = objectError.getDefaultMessage();
			String name = objectError.getObjectName();
			return Erro.Campo.builder().nome(name).mensagem(msg).build();
		}).collect(Collectors.toList());
		Erro e = createErroBuilder(status, tipoErro, MSG_ERRO_CAMPO_INVALIDO).userMessage(MSG_ERRO_CAMPO_INVALIDO)
				.campos(problemObjects).build();
		return handleExceptionInternal(ex, e, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		TipoErroEnum problemType = TipoErroEnum.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

		ex.printStackTrace();

		Erro problem = createErroBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		TipoErroEnum problemType = TipoErroEnum.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Erro problem = createErroBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(CategoriaExistenteException.class)
	public ResponseEntity<Object> handleCategoriaExistenteException(CategoriaExistenteException e, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		TipoErroEnum problemType = TipoErroEnum.ERRO_NEGOCIO;
		String detail = e.getMessage();
		Erro problem = createErroBuilder(status, problemType, detail).userMessage(detail).build();
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request); 
		}
		
		TipoErroEnum problemType = TipoErroEnum.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Erro problem = createErroBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		TipoErroEnum problemType = TipoErroEnum.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);

		Erro problem = createErroBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		String path = joinPath(ex.getPath());
		
		TipoErroEnum problemType = TipoErroEnum.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Erro problem = createErroBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Erro.builder()
				.timestamp(LocalDateTime.now())
				.title(status.getReasonPhrase())
				.status(status.value())
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		} else if (body instanceof String) {
			body = Erro.builder()
				.timestamp(LocalDateTime.now())
				.title((String) body)
				.status(status.value())
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
	private Erro.ErroBuilder createErroBuilder(HttpStatusCode status, TipoErroEnum problemType,
			String detail) {

		return Erro.builder().timestamp(LocalDateTime.now()).status(status.value()).type(problemType.getUri())
				.title(problemType.getTitle()).detail(detail);
	}
}
