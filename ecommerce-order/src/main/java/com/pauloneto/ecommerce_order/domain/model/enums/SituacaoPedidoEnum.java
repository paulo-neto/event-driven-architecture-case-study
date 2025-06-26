package com.pauloneto.ecommerce_order.domain.model.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum SituacaoPedidoEnum {

	
	
	CRIADO(1),PAGO(2),ENVIADO(3),CANCELADO(4);
	
	private final Integer situacao;
	private static final Map<Integer, SituacaoPedidoEnum> enums = Collections.synchronizedMap(new HashMap<>());
	
	private SituacaoPedidoEnum(Integer s) {
		this.situacao = s;
	}
	
	static {
		for(SituacaoPedidoEnum e : SituacaoPedidoEnum.values()) {
			enums.put(e.situacao, e);
		}
	}
	
	public SituacaoPedidoEnum get(Integer situacao) {
		return enums.get(situacao);
	}
}
