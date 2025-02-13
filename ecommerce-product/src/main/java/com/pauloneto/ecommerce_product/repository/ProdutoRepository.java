package com.pauloneto.ecommerce_product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pauloneto.ecommerce_product.domain.model.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

}
