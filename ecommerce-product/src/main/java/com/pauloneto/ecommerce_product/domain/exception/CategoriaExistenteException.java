package com.pauloneto.ecommerce_product.domain.exception;

public class CategoriaExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_CAT_EXIT = "Existe uma Categoria com esse nome: %s";

	public CategoriaExistenteException(String catNova) {
		super(String.format(MSG_CAT_EXIT, catNova));
	}
	
	public CategoriaExistenteException(String catNova, Throwable cause) {
		super(String.format(MSG_CAT_EXIT, catNova), cause);
	}

}
