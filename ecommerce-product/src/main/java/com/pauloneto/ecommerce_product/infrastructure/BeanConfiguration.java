package com.pauloneto.ecommerce_product.infrastructure;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class BeanConfiguration {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
