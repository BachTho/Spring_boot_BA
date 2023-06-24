package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cart;


@Repository(value = "CartRepository")
public interface CartRepository  extends JpaRepository<Cart, Long>  {
Cart findByUserId(Long userId);
}
