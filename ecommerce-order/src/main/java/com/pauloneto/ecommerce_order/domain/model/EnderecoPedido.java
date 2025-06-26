package com.pauloneto.ecommerce_order.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "tb_endereco_pedido")
public class EnderecoPedido {

	@Id
	@ManyToOne
	@JoinColumn(name = "pedido_id") 
	private Pedido pedido;
	
	private Integer cep;
	
	private String uf;
	
	private String municipio;
	
	private String logradouro;
	
	private String bairro;
	
	@Column(name = "num_logradouro")
	private Integer numero;
}
