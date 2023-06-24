package com.example.demo.model.DTO;

import java.util.Date;

import com.example.demo.model.Category;


import lombok.Data;
@Data
public class CategoryDTOResponse {
private Long id;
private String name;

public CategoryDTOResponse(Category category) {
	this.id = category.getId();
	this.name = category.getName();
}
}
