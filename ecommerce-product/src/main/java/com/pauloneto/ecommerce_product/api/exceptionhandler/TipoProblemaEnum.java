package com.pauloneto.ecommerce_product.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TipoProblemaEnum {

	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	TipoProblemaEnum(String path, String title) {
		this.uri = "https://api.produtos.com.br" + path;
		this.title = title;
	}
	
}