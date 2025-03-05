package com.pauloneto.ecommerce_product.api.controller;

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
import com.pauloneto.ecommerce_product.api.exceptionhandler.Erro;
import com.pauloneto.ecommerce_product.domain.dto.CategoriaDTO;
import com.pauloneto.ecommerce_product.domain.model.Categoria;
import com.pauloneto.ecommerce_product.domain.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Tag(name = "categorias", description = "API para ações no recurso Categoria")
@RestController
@RequestMapping("categorias")
public class CategoriaController {

	private final CategoriaService service;
	
	public CategoriaController(final CategoriaService c) {
		this.service = c;
	}
	
	@Operation(summary = "Cria uma nova Categoria", description = "Cria uma nova Categoria, caso ela não exista", tags = "categorias")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "201",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = CategoriaDTO.class)) }),
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
	public CategoriaDTO create(
			@Valid @RequestBody @NotBlank(message = "O nome da nova Categoria não pode ser vazio!") String novaCategoria) {
		var dto = service.create(novaCategoria);
		return dto;
	}
	
	@Operation(summary = "Inativa uma Categoria", description = "Inativa uma Categoria, caso ela exista", tags = "categorias")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "204"), 
			@ApiResponse(
					description = "Categoria não encontrada", responseCode = "404",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }),
			@ApiResponse(
					description = "Ocorreu um erro interno inesperado na API", responseCode = "500",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }) 
			}
	)
	@DeleteMapping("/{categoriaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable("categoriaId") Long categoriaId) {
		service.inativate(categoriaId);
	}
	
	@Operation(summary = "Lista Categorias", description = "Lista Categorias, pelos critérios informados", tags = "categorias")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "200",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = CategoriaDTO[].class)) }) ,
			@ApiResponse(
					description = "Ocorreu um erro interno inesperado na API", responseCode = "500",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }) 
			}
	)
	@GetMapping("/")
	public List<CategoriaDTO> findBy(
			@RequestParam(required = false) String categoria,
			@RequestParam(required = false) Boolean ativo){
		
		return service.findBy(categoria, ativo);
	}
	
	@Operation(summary = "Atualiza uma Categoria", description = "Atualiza uma Categoria, caso ela exista com os valores informados", tags = "categorias")
	@ApiResponses(value = { 
			@ApiResponse(
					description = "Ação realizada com sucesso", responseCode = "200",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = CategoriaDTO.class)) }),
			@ApiResponse(
					description = "Categoria exitente", responseCode = "409",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }),
			@ApiResponse(
					description = "Categoria não encontrada", responseCode = "404",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }),
			@ApiResponse(
					description = "Ocorreu um erro interno inesperado na API", responseCode = "500",
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Erro.class)) }) 
			}
	)
	@PatchMapping(value = "/{categoriaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoriaDTO update(
			@PathVariable("categoriaId")Long categoriaId,
			@Valid @RequestBody @NotEmpty(message = "É esperado algum campo para alteração!") Map<String, Object> campos, HttpServletRequest request) {
		var produtoAtual = service.findOrFail(categoriaId);
		merge(campos, produtoAtual, request);
		var dto = service.update(categoriaId, produtoAtual);
		return dto;
	}

	private void merge(Map<String, Object> dadosOrigem, Categoria categoriaDestino, HttpServletRequest request) {
		var serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			var objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			var produtoOrigem = objectMapper.convertValue(dadosOrigem, Categoria.class);

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				var field = ReflectionUtils.findField(Categoria.class, nomePropriedade);
				field.setAccessible(true);

				var novoValor = ReflectionUtils.getField(field, produtoOrigem);

				ReflectionUtils.setField(field, categoriaDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
}
