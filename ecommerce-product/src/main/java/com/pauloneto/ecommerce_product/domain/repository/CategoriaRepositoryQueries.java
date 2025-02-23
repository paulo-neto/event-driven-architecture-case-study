package com.pauloneto.ecommerce_product.domain.repository;

import java.util.List;

import com.pauloneto.ecommerce_product.domain.model.Categoria;

public interface CategoriaRepositoryQueries {

	public List<Categoria> findBy(String categoria, Boolean ativo);
	public Categoria findByName(String categoria);
}
