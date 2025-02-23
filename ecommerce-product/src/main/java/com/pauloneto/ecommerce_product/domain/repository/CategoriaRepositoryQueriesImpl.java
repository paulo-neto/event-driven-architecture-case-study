package com.pauloneto.ecommerce_product.domain.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.pauloneto.ecommerce_product.domain.model.Categoria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

@Repository
public class CategoriaRepositoryQueriesImpl implements CategoriaRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Categoria> findBy(String categoria, Boolean ativo) {
		
		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Categoria.class);
		var root = criteria.from(Categoria.class);
		var criterios = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(categoria)) {
			criterios.add(builder.like(root.get("nome"), "%" + categoria + "%"));
		}
		if(ativo != null) {
			criterios.add(builder.equal(root.get("ativo"), ativo));
		}
		criteria.where(criterios.toArray(new Predicate[0]));
		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Categoria findByName(String categoria) {
		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Categoria.class);
		var root = criteria.from(Categoria.class);
		var criterios = new ArrayList<Predicate>();
		if(!StringUtils.hasText(categoria))
			throw new IllegalArgumentException("Uma categoria deve ser informada para consulta!");
		
		criterios.add(builder.equal(root.get("nome"), categoria));
		criteria.where(criterios.toArray(new Predicate[0]));
		var query = manager.createQuery(criteria);
		return query.getSingleResult();
	}

}
