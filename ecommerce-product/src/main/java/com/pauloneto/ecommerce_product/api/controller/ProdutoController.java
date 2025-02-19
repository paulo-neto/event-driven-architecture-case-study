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
import com.pauloneto.ecommerce_product.domain.dto.ProdutoDTO;
import com.pauloneto.ecommerce_product.domain.model.Produto;
import com.pauloneto.ecommerce_product.domain.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "produtos")
public class ProdutoController {
	
	private final ProdutoService prodService;
	
	public ProdutoController(final ProdutoService produtoService) {
		this.prodService = produtoService;
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProdutoDTO> findBy(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) BigDecimal precoInicio,
			@RequestParam(required = false) BigDecimal precoFim,
			@RequestParam(required = false) String categoria){
		
		return prodService.findBy(nome, precoInicio, precoFim, categoria);
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO create(@RequestBody ProdutoRequest produto) {
		var dto = prodService.create(produto);
		return dto;
	}
	
	@PatchMapping(value = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoDTO update(@PathVariable("produtoId") Long produtoId, 
			@RequestBody Map<String, Object> campos, HttpServletRequest request) {
		
		var produtoAtual = prodService.findOrFail(produtoId);
		merge(campos, produtoAtual, request);
		var dto = prodService.update(produtoId, produtoAtual);
		return dto;
	}
	
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
