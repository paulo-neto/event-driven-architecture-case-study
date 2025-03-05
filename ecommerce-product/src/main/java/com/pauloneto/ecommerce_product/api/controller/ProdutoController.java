package com.pauloneto.ecommerce_product.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pauloneto.ecommerce_product.api.controller.dto.ProdutoRequest;
import com.pauloneto.ecommerce_product.api.exceptionhandler.Erro;
import com.pauloneto.ecommerce_product.domain.dto.ProdutoDTO;
import com.pauloneto.ecommerce_product.domain.model.Produto;
import com.pauloneto.ecommerce_product.domain.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Tag(name = "produtos", description = "API para ações no recurso Produto")
@RestController
@RequestMapping(value = "produtos")
public class ProdutoController {
	
	private final ProdutoService prodService;
	
	public ProdutoController(final ProdutoService produtoService) {
		this.prodService = produtoService;
	}
	
	@Operation(summary = "Lista Produtos", description = "Lista Produtos, pelos critérios informados", tags = "produtos")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "200",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = ProdutoDTO[].class)) }) ,
			@ApiResponse(
					description = "Ocorreu um erro interno inesperado na API", responseCode = "500",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }) 
			}
	)
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProdutoDTO> findBy(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) BigDecimal precoInicio,
			@RequestParam(required = false) BigDecimal precoFim,
			@RequestParam(required = false) String categoria){
		
		return prodService.findBy(nome, precoInicio, precoFim, categoria);
	}
	
	@Operation(summary = "Cria um novo Produto", description = "Cria um novo Produto, caso ele não exista", tags = "produtos")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "201",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Produto.class)) }) ,
			@ApiResponse(
					description = "Categoria exitente", responseCode = "409",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }),
			@ApiResponse(
					description = "Ocorreu um erro interno inesperado na API", responseCode = "500",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }) 
			}
	)
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO create(@Valid @RequestBody ProdutoRequest produto) {
		var dto = prodService.create(produto);
		return dto;
	}
	
	@Operation(summary = "Atualiza um Produto", description = "Atualiza um Produto, caso ele exista com os valores informados", tags = "produtos")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "200",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Produto.class)) }) ,
			@ApiResponse(
					description = "Categoria não encontrada", responseCode = "409",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }),
			@ApiResponse(
					description = "Produto não encontrado", responseCode = "404",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }),
			@ApiResponse(
					description = "Ocorreu um erro interno inesperado na API", responseCode = "500",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }) 
			}
	)
	@PatchMapping(value = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoDTO update(@PathVariable("produtoId") Long produtoId, 
			@Valid @NotEmpty(message = "É esperado algum campo para alteração!") @RequestBody Map<String, Object> campos,
			HttpServletRequest request) {
		
		var produtoAtual = prodService.findOrFail(produtoId);
		merge(campos, produtoAtual, request);
		var dto = prodService.update(produtoId, produtoAtual);
		return dto;
	}
	
	@Operation(summary = "Inativa um Produto", description = "Inativa um Produto, caso ele exista", tags = "produtos")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "204") ,
			@ApiResponse(
					description = "Produto não encontrado", responseCode = "404",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }),
			@ApiResponse(
					description = "Ocorreu um erro interno inesperado na API", responseCode = "500",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }) 
			}
	)
	@DeleteMapping(value = "/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativate(@PathVariable("produtoId") Long produtoId) {
		prodService.inativate(produtoId);
	}
	
	private void merge(Map<String, Object> dadosOrigem, Produto produtoDestino, HttpServletRequest request) {
		var serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			var objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			var produtoOrigem = objectMapper.convertValue(dadosOrigem, Produto.class);

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				var field = ReflectionUtils.findField(Produto.class, nomePropriedade);
				field.setAccessible(true);

				var novoValor = ReflectionUtils.getField(field, produtoOrigem);

				ReflectionUtils.setField(field, produtoDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
}
