package com.pauloneto.ecommerce_product.domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pauloneto.ecommerce_product.domain.dto.CategoriaDTO;
import com.pauloneto.ecommerce_product.domain.exception.CategoriaExistenteException;
import com.pauloneto.ecommerce_product.domain.exception.EntidadeNaoEncontradaException;
import com.pauloneto.ecommerce_product.domain.model.Categoria;
import com.pauloneto.ecommerce_product.domain.repository.CategoriaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoriaService {

	public static final String MSG_CAT_NAO_ENCONTRADA = "Categoria com ID %d não encontrada";
	private final CategoriaRepository repository;

	public CategoriaService(final CategoriaRepository r) {
		this.repository = r;
	}

	public List<CategoriaDTO> findBy(String categoria, Boolean ativo) {
		var categorias = repository.findBy(categoria, ativo);
		var retorno = categoriasToCategoriasDTO(categorias);
		return retorno;
	}

	public Categoria findOrFail(Long categoriaId) {
		return repository.findById(categoriaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_CAT_NAO_ENCONTRADA, categoriaId)));
	}
	
	@Transactional
	public CategoriaDTO create(String novaCategoria) {
		try {
			var catEncontrada = repository.findByName(novaCategoria);
			if (catEncontrada != null)
				throw new CategoriaExistenteException(novaCategoria);			
		}catch (EmptyResultDataAccessException e) {
			log.error(e.getMessage());
		}

		var cat = Categoria.builder().dtCriacao(LocalDateTime.now()).ativo(true).nome(novaCategoria).build();
		cat = repository.save(cat);
		var dto = categoriaToCategoriaDTO(cat);
		return dto;
	}

	@Transactional
	public void inativate(Long categoriaId) {
		var cat = findOrFail(categoriaId);
		cat.setAtivo(false);
		
	}
	
	public CategoriaDTO update(Long categoriaId, Categoria cat) {
		var categoriaAtual = findOrFail(categoriaId);
		BeanUtils.copyProperties(cat, categoriaAtual, "id");
		categoriaAtual = repository.save(categoriaAtual);
		var dto = categoriaToCategoriaDTO(categoriaAtual);
		return dto;
	}
	
	private List<CategoriaDTO> categoriasToCategoriasDTO(List<Categoria> categorias) {
		var retorno = new ArrayList<CategoriaDTO>();
		if (categorias.isEmpty())
			return retorno;

		categorias.forEach(c -> {
			var cdto = categoriaToCategoriaDTO(c);
			retorno.add(cdto);
		});
		return retorno;
	}

	private CategoriaDTO categoriaToCategoriaDTO(Categoria c) {
		var cdto = new CategoriaDTO();
		BeanUtils.copyProperties(c, cdto);
		return cdto;
	}

}
