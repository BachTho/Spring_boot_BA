package com.example.demo.model.DTO;

import com.example.demo.model.Product;

import lombok.Data;

@Data
public class ProductDTOResponse {
	private Long id;
	private String name;

	public ProductDTOResponse(Product product) {
		this.id = product.getId();
		this.name = product.getName();
	}
}
