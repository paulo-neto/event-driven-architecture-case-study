package com.pauloneto.ecommerce_order.domain;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.pauloneto.ecommerce_order.domain.model.Pedido;
import com.pauloneto.ecommerce_order.domain.model.ProdutoPedido;
import com.pauloneto.ecommerce_order.domain.repository.PedidoRepository;
import com.pauloneto.ecommerce_order.domain.repository.ProdutoPedidoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PedidoService {

	
	private final PedidoRepository repository;
	private final ProdutoPedidoRepository ppRepository;
	
	@Transactional
	public Pedido criar(Pedido pedido, Set<ProdutoPedido> produtos) {
		
		return null;
	}
	
}
