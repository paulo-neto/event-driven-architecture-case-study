package com.pauloneto.ecommerce_order.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_forma_pagamento_pedido")
public class FormaPagamentoPedido {

	@Id
	private Long id;
	
    @OneToOne
    @MapsId // Indica que o 'id' é o mapeamento da associação com formaPag
    @JoinColumn(name = "pedido_id")
	private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPag;
}
