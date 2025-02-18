package com.pauloneto.ecommerce_product.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.pauloneto.ecommerce_product.domain.model.Produto;

public interface ProdutoRepositoryQueries {

	public List<Produto> findBy(String nome, BigDecimal precoInicio, BigDecimal precoFim, String categoria);
}
