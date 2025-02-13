package com.pauloneto.ecommerce_product.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.pauloneto.ecommerce_product.domain.dto.ProdutoDTO;
import com.pauloneto.ecommerce_product.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository repository;
	private final ModelMapper mapper;
	
	public ProdutoService(final ProdutoRepository repository, final ModelMapper m) {
		this.repository = repository;
		this.mapper = m;
	}
	
	public List<ProdutoDTO> list(){
		//mapper.ma
		return null;//repository.findAll();
		
	}
}
