package com.example.demo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.respository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.constants.ResponseCode;
import jakarta.websocket.server.PathParam;

//import com.example.model.User;

@RestController
@RequestMapping("/users")
public class UserController extends BaseRestController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = this.userService.getAllUser();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

//c1
	@GetMapping("/getById")
	public ResponseEntity<User> getById(@RequestParam(name = "id", required = false, defaultValue = "1") long id) {
		User foundUser = this.userService.findById(id);
		if (ObjectUtils.isEmpty(foundUser)) {
			return new ResponseEntity<>(foundUser, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(foundUser, HttpStatus.OK);
	}

//c2
	@GetMapping("/{id}")
	public ResponseEntity<User> getByIdPath(@PathVariable long id) {
		User foundUser = this.userService.findById(id);
		if (ObjectUtils.isEmpty(foundUser)) {
			return new ResponseEntity<>(foundUser, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(foundUser, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> addUser(@RequestBody(required = false) Map<String, Object> newUser) {
		try {
			if (ObjectUtils.isEmpty(newUser)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			User foundUser = this.userRepository.findByName(newUser.get("name").toString()).orElse(null);
			if (!ObjectUtils.isEmpty(foundUser)) {
				return super.error(ResponseCode.DATA_EXISTS.getCode(), ResponseCode.DATA_EXISTS.getMessage());
			}
			this.userService.addNewUser(newUser);
			return super.success(newUser);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable long id,
			@RequestBody(required = false) Map<String, Object> newUser) {
		return new ResponseEntity<>(this.userService.updateUser(id, newUser), HttpStatus.OK);
	}

//	@GetMapping("/getByName")
//	public ResponseEntity<?> getUserByFullName(@RequestParam String full_name) {
//		return new ResponseEntity<>(this.userService.findByFullName(full_name), HttpStatus.OK);
//	}
}
