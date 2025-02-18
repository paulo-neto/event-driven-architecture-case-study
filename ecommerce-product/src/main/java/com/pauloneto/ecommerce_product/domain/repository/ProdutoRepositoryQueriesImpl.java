package com.pauloneto.ecommerce_product.domain.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.pauloneto.ecommerce_product.domain.model.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

@Repository
public class ProdutoRepositoryQueriesImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Produto> findBy(String nome, BigDecimal precoInicio, BigDecimal precoFim, String categoria) {
		
		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Produto.class);
		var root = criteria.from(Produto.class);
		var criterios = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			criterios.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		if(StringUtils.hasText(categoria)) {
			criterios.add(builder.like(root.get("categoria").get("nome"), "%" + categoria + "%"));
		}
		if(precoInicio != null) {
			criterios.add(builder.greaterThanOrEqualTo(root.get("preco"), precoInicio));
		}
		if(precoFim != null) {
			criterios.add(builder.lessThanOrEqualTo(root.get("preco"), precoFim));
		}
		criteria.where(criterios.toArray(new Predicate[0]));
		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

}
