package com.pauloneto.ecommerce_product.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pauloneto.ecommerce_product.domain.dto.ProdutoDTO;
import com.pauloneto.ecommerce_product.service.ProdutoService;

@RestController
public class ProdutoController {
	
	private final ProdutoService prodService;
	
	public ProdutoController(final ProdutoService produtoService) {
		this.prodService = produtoService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProdutoDTO> list(){
		
		return prodService.list();
	}
}
