package com.example.demo.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.ResponseCode;
import com.example.demo.model.Cart;
import com.example.demo.model.CartLineItem;
import com.example.demo.model.User;
import com.example.demo.model.VariantProduct;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.UserRepository;
import com.example.demo.respository.VariantProductRepository;
import com.example.demo.service.CartLineItemService;

@RestController
@RequestMapping("/cartLineItems")
public class CartLineItemController extends BaseRestController {
	@Autowired
	private CartLineItemService cartLineItemService;
	@Autowired
	private VariantProductRepository variantProductRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@PostMapping("")

	public ResponseEntity<?> addNewCartLine(@RequestBody(required = false) Map<String, Object> newCartLine) {
		try {
			if (ObjectUtils.isEmpty(newCartLine) || ObjectUtils.isEmpty(newCartLine.get("variantProductId"))
					|| ObjectUtils.isEmpty(newCartLine.get("quantity"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			VariantProduct foundVariantProduct = this.variantProductRepository
					.findById(Long.parseLong(newCartLine.get("variantProductId").toString())).orElse(null);

			if (ObjectUtils.isEmpty(foundVariantProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}

			Cart foundCart = this.cartRepository
					.findByUserId(Long.parseLong(newCartLine.get("variantProductId").toString()));
			CartLineItem cartLineItem = this.cartLineItemService.addNewCartLineItem(newCartLine, foundCart,
					foundVariantProduct);

			return new ResponseEntity<>(newCartLine, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
