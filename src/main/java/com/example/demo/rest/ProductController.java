package com.example.demo.rest;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.ResponseCode;
import com.example.demo.model.Address;
import com.example.demo.model.Category;
import com.example.demo.model.Product;

import com.example.demo.model.DTO.CategoryDTOResponse;
import com.example.demo.model.DTO.ProductDTOResponse;
import com.example.demo.respository.CategoryRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order.Direction;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseRestController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductService productService;

	@GetMapping("")
	public ResponseEntity<?> getAllProductByCategory(
			@RequestParam(name = "categoryId", required = false, defaultValue = "1") long categoryId)

	{
		List<Product> products = this.productService.getAllProductsByCategory(categoryId);
		List<ProductDTOResponse> dtoResponses = products.stream().map(ProductDTOResponse::new).toList();

		return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
	}

	@GetMapping("/getById")
	public ResponseEntity<?> getProductById(@RequestParam(name = "id", required = false, defaultValue = "1") long id) {
		try {
			Product foundProduct = this.productService.getProductById(id);
			if (ObjectUtils.isEmpty(foundProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			return super.success(foundProduct);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PostMapping("")
	public ResponseEntity<?> addProduct(@RequestBody(required = false) Map<String, Object> newProduct) {
		try {
			if (ObjectUtils.isEmpty(newProduct)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Category foundCategory = this.categoryRepository
					.findById(Long.parseLong(newProduct.get("categoryId").toString())).orElse(null);
			if (!ObjectUtils.isEmpty(foundCategory)) {
				this.productService.addNewProduct(newProduct, foundCategory);
				return super.success(newProduct);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable long id,
			@RequestBody(required = false) Map<String, Object> newProduct) {
		try {
			if (ObjectUtils.isEmpty(newProduct) || ObjectUtils.isEmpty(id)
					|| ObjectUtils.isEmpty(newProduct.get("name"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Product foundProduct = this.productRepository.findById(id).orElse(null);
			if (!ObjectUtils.isEmpty(foundProduct)) {
				Product product = this.productService.updateProduct(id, newProduct, foundProduct);
				return new ResponseEntity<>(product, HttpStatus.OK);
			}
			return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
