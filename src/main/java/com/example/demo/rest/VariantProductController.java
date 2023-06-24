package com.example.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.VariantProduct;
import com.example.demo.model.DTO.ProductDTOResponse;
import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.VariantProductRepository;
import com.example.demo.service.VarianProductService;

@RestController
@RequestMapping("/variantProducts")
public class VariantProductController extends BaseRestController {

	@Autowired
	private VarianProductService varianProductService;
	@Autowired
	private VariantProductRepository variantProductRepository;
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("")
	public ResponseEntity<?> getAllVariantProductByProduct(
			@RequestParam(name = "productId", required = false, defaultValue = "1") long productId) {
		List<VariantProduct> variantProducts = this.varianProductService.getAllVariantProductByProduct(productId);
		return new ResponseEntity<>(variantProducts, HttpStatus.OK);
	}

	@GetMapping("/getById")
	public ResponseEntity<?> getProductById(@RequestParam(name = "id", required = false, defaultValue = "1") long id) {
		try {
			VariantProduct foundVariantProduct = this.varianProductService.getVariantProductById(id);
			if (ObjectUtils.isEmpty(foundVariantProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			return super.success(foundVariantProduct);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PostMapping("")
	public ResponseEntity<?> addVariantProduct(@RequestBody(required = false) Map<String, Object> newVariantProduct) {
		try {
			if (ObjectUtils.isEmpty(newVariantProduct)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Product foundProduct = this.productRepository
					.findById(Long.parseLong(newVariantProduct.get("productId").toString())).orElse(null);
			if (!ObjectUtils.isEmpty(foundProduct)) {
				this.varianProductService.addNewVariant(newVariantProduct, foundProduct);
				return super.success(newVariantProduct);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateVariantProduct(@PathVariable long id,
			@RequestBody(required = false) Map<String, Object> newVariantProduct) {
		try {
			if (ObjectUtils.isEmpty(newVariantProduct) || ObjectUtils.isEmpty(id)
					|| ObjectUtils.isEmpty(newVariantProduct.get("name"))
					|| ObjectUtils.isEmpty(newVariantProduct.get("price"))
					|| ObjectUtils.isEmpty(newVariantProduct.get("color"))
					|| ObjectUtils.isEmpty(newVariantProduct.get("model_year"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			VariantProduct foundVariantProduct = this.variantProductRepository.findById(id).orElse(null);
			if (!ObjectUtils.isEmpty(foundVariantProduct)) {
				VariantProduct variantProduct = this.varianProductService.updateVariant(id, newVariantProduct,
						foundVariantProduct);
				return new ResponseEntity<>(variantProduct, HttpStatus.OK);
			}
			return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
