package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.CartLineItem;
import com.example.demo.model.VariantProduct;
import com.example.demo.respository.CartLineItemRepository;

@Service
public class CartLineItemService {
	@Autowired
	private CartLineItemRepository cartLineItemRepository;

	public CartLineItem addNewCartLineItem(Map<String, Object> newCartLine, Cart cart, VariantProduct variantProduct) {
		CartLineItem cartLineItem = new CartLineItem();
		cartLineItem.setDeleted(false);
		cartLineItem.setQuantity(Integer.parseInt(newCartLine.get("quantity").toString()));
		cartLineItem.setCart(cart);
		cartLineItem.setVariantProduct(variantProduct);
		this.cartLineItemRepository.save(cartLineItem);
		return cartLineItem;
	}
}
