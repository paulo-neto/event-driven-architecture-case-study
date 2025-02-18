package com.pauloneto.ecommerce_product.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pauloneto.ecommerce_product.api.controller.dto.ProdutoRequest;
import com.pauloneto.ecommerce_product.domain.dto.ProdutoDTO;
import com.pauloneto.ecommerce_product.domain.service.ProdutoService;

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
	public ProdutoDTO criar(@RequestBody ProdutoRequest produto) {
		var dto = prodService.criar(produto);
		return dto;
	}
}
