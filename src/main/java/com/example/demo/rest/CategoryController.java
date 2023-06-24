package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.ResponseCode;
import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.model.DTO.CategoryDTOResponse;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/categories")
public class CategoryController extends BaseRestController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public ResponseEntity<?> getAllCategory() {
		try {

			List<Category> categories = this.categoryService.getAllCategory();
			List<CategoryDTOResponse> categoryDTOResponses = categories.stream().map(CategoryDTOResponse::new).toList();

//		return new ResponseEntity<>(categories, HttpStatus.OK);
			return super.success(categoryDTOResponses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@GetMapping("/getById")
	public ResponseEntity<Category> getById(@RequestParam(name = "id", required = false, defaultValue = "1") long id) {
		Category foundCategory = this.categoryService.findById(id);

		return new ResponseEntity<>(foundCategory, HttpStatus.OK);
	}
}
