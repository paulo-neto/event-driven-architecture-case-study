package com.pauloneto.ecommerce_order.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_produto_pedido")
public class ProdutoPedido {

	@EmbeddedId
	private ProdutoPedidoID ppid;
	
	private BigDecimal valor;
	
	private Integer quantidade;
}
