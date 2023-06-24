package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.VariantProduct;
import com.example.demo.respository.VariantProductRepository;

@Service
public class VarianProductService {
	@Autowired
	private VariantProductRepository variantProductRepository;

	public List<VariantProduct> getAllVariantProductByProduct(Long productId) {
		return this.variantProductRepository.findByProductId(productId);
	}

	public VariantProduct getVariantProductById(Long id) {
		return this.variantProductRepository.findById(id).orElse(null);
	}

	public VariantProduct addNewVariant(Map<String, Object> newVariant, Product product) {
		VariantProduct variantProduct = new VariantProduct();
		variantProduct.setName(newVariant.get("name").toString());
		variantProduct.setModel_year(newVariant.get("model_year").toString());
		variantProduct.setPrice(Double.parseDouble(newVariant.get("price").toString()));
		variantProduct.setColor(newVariant.get("color").toString());
		variantProduct.setProduct(product);
		this.variantProductRepository.save(variantProduct);
		return variantProduct;
	}

	public VariantProduct updateVariant(long id, Map<String, Object> newVariant, VariantProduct variantProduct) {
		variantProduct.setName(newVariant.get("name").toString());
		variantProduct.setModel_year(newVariant.get("model_year").toString());
		variantProduct.setPrice(Double.parseDouble(newVariant.get("price").toString()));
		variantProduct.setColor(newVariant.get("color").toString());
		variantProduct.setId(id);
		this.variantProductRepository.save(variantProduct);
		return variantProduct;
	}

}
