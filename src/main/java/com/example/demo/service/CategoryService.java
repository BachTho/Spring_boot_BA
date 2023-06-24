package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.respository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	@Qualifier(value = "CategoryRepository")
	private CategoryRepository categoryRepository;

	public List<Category> getAllCategory(){
		return this.categoryRepository.findAll(); 
	}
	
	public Category findById(Long id) {
		return this.categoryRepository.findById(id).orElse(null);
	}
}

