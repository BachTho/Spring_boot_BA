package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Category;

@Repository(value = "CategoryRepository")
public interface CategoryRepository  extends JpaRepository<Category, Long>{

}
