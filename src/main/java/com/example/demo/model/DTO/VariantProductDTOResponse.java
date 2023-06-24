package com.example.demo.model.DTO;

import com.example.demo.model.Product;
import com.example.demo.model.VariantProduct;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VariantProductDTOResponse {
	private Long id;
	private String name;
	private Double price;
	private String color;
	private String model_year;

}
