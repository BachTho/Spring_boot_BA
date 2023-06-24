package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.VariantProduct;

@Repository
public interface VariantProductRepository extends JpaRepository<VariantProduct, Long> {
	
	List<VariantProduct> findByProductId(Long productId);

}
