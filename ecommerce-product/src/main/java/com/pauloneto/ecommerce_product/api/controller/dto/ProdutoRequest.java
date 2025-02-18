package com.pauloneto.ecommerce_product.api.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private String descricao;
	
	private BigDecimal preco;
	
	private Long categoriaId;

}
