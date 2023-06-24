package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.respository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

//	public List<Product> getAllProductsByCategory(Long categoryId, Pageable pageable) {
//		return this.productRepository.findByCategoryId(categoryId, pageable);
//	}

	public List<Product> getAllProductsByCategory(Long categoryId) {
		return this.productRepository.findByCategoryId(categoryId);
	}

	public Product getProductById(Long id) {
		return this.productRepository.findById(id).orElse(null);
	}

	public Product addNewProduct(Map<String, Object> newProduct, Category category) {
		Product product = new Product();
		product.setName(newProduct.get("name").toString());
		product.setCategory(category);
		this.productRepository.save(product);
		return product;
	}

	public Product updateProduct(long id, Map<String, Object> newProduct, Product product) {
		product.setName(newProduct.get("name").toString());
		product.setId(id);
		this.productRepository.save(product);
		return product;
	}

}
