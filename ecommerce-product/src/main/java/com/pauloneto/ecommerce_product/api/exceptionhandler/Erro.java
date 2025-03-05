package com.pauloneto.ecommerce_product.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Erro {

	private Integer status;
	private LocalDateTime timestamp;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
	private List<Campo> campos;
	@Getter
	@Builder
	public static class Campo{
		
		private String nome;
		private String mensagem;
	}
}