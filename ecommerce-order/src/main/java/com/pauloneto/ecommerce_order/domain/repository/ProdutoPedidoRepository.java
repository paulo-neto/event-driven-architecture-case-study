package com.pauloneto.ecommerce_order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pauloneto.ecommerce_order.domain.model.ProdutoPedido;
import com.pauloneto.ecommerce_order.domain.model.ProdutoPedidoID;

@Repository
public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, ProdutoPedidoID> {


}
