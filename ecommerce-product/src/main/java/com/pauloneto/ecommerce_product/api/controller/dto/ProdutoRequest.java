package com.pauloneto.ecommerce_product.api.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "O nome do produto é obrigatório!")
	private String nome;
	
	@NotBlank(message = "Uma descrição para o produto é obrigatório!")
	private String descricao;
	
	@Positive(message = "Um preço para o produto é obrigatório!")
	private BigDecimal preco;
	
	@NotNull(message = "Um ID de uma categoria deve se informado!")
	private Long categoriaId;

}
