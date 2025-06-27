package com.pauloneto.ecommerce_order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pauloneto.ecommerce_order.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	//public Pedido consultaExitentePor
}
