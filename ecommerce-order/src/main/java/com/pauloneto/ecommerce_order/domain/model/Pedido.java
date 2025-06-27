package com.pauloneto.ecommerce_order.domain.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.pauloneto.ecommerce_order.domain.model.enums.SituacaoPedidoEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pedido")
public class Pedido {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "dt_criacao")
	private OffsetDateTime dataCriacao;
	
	@Enumerated(EnumType.ORDINAL)
	private SituacaoPedidoEnum situacao;
	
	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private FormaPagamentoPedido formPagPed;
	
	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private ClientePedido cliente;
	
	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private EnderecoPedido endereco;
	
	
}
