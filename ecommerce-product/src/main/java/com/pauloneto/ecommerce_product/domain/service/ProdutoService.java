package com.pauloneto.ecommerce_product.domain.service;

import static com.pauloneto.ecommerce_product.domain.service.CategoriaService.MSG_CAT_NAO_ENCONTRADA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pauloneto.ecommerce_product.api.controller.dto.ProdutoRequest;
import com.pauloneto.ecommerce_product.domain.dto.ProdutoDTO;
import com.pauloneto.ecommerce_product.domain.exception.EntidadeNaoEncontradaException;
import com.pauloneto.ecommerce_product.domain.model.Produto;
import com.pauloneto.ecommerce_product.domain.repository.CategoriaRepository;
import com.pauloneto.ecommerce_product.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository repository;
	private final CategoriaRepository categoriaRepository;
	
	private static final String MSG_PROD_NAO_ENCONTRADO = "Produto com ID %d não encontrado";
	
	public ProdutoService(final ProdutoRepository repository, CategoriaRepository cr) {
		this.repository = repository;
		this.categoriaRepository = cr;
	}
	
	public List<ProdutoDTO> list(){
		var all = repository.findAll();
		var retorno = produtosToProdutosDTO(all);
		return retorno;
		
	}


	public List<ProdutoDTO> findBy(String nome, BigDecimal precoInicio, BigDecimal precoFim, String categoria) {
		var produtos = repository.findBy(nome, precoInicio, precoFim, categoria);
		var retorno = produtosToProdutosDTO(produtos);
		return retorno;
	}
	
	public Produto findOrFail(Long produtoId) {
		return repository.findById(produtoId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_PROD_NAO_ENCONTRADO, produtoId)));
	}
	
	@Transactional
	public ProdutoDTO create(ProdutoRequest request) {
		var opCatg = categoriaRepository.findById(request.getCategoriaId());
		var cat = opCatg.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_CAT_NAO_ENCONTRADA, request.getCategoriaId())));
		var p = Produto.builder().ativo(true).categoria(cat).descricao(request.getDescricao())
				.nome(request.getNome()).preco(request.getPreco()).build();
		p = repository.save(p);
		var pdto = produtoToProdutoDTO(p);
		return pdto;
	}

	private List<ProdutoDTO> produtosToProdutosDTO(List<Produto> produtos) {
		List<ProdutoDTO> retorno = new ArrayList<ProdutoDTO>();
		if(produtos.isEmpty())
			return retorno;
		
		produtos.forEach(p -> {
			var pdto = produtoToProdutoDTO(p);
			retorno.add(pdto);
		});
		return retorno;
	}

	private ProdutoDTO produtoToProdutoDTO(Produto p) {
		var pdto = new ProdutoDTO();
		BeanUtils.copyProperties(p, pdto);
		pdto.setCategoria(p.getCategoria().getNome());
		return pdto;
	}

	public ProdutoDTO update(Long produtoId, Produto produto) {
		var produtoAtual = findOrFail(produtoId);
		BeanUtils.copyProperties(produto, produtoAtual, "id");
		produtoAtual = repository.save(produtoAtual);
		var pdto = produtoToProdutoDTO(produtoAtual);
		return pdto;
	}

	@Transactional
	public void inativate(Long produtoId) {
		var produto = findOrFail(produtoId);
		produto.setAtivo(false);
	}
}
