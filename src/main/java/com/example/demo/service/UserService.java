package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.UserRepository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

@Service
public class UserService {
	@Autowired
	@Qualifier(value = "UserRepository")
	private UserRepository useRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUser() {
		return this.useRepository.findAll();
	}

	public User findById(Long id) {
		return this.useRepository.findById(id).orElse(null);
	}

	public User addNewUser(Map<String, Object> newUser) {
		User user = new User();
		user.setFull_name(newUser.get("full_name").toString());
		user.setName(newUser.get("name").toString());
		user.setEmail(newUser.get("email").toString());
		user.setPassword(this.passwordEncoder.encode(newUser.get("password").toString()));
		user.setPhone(newUser.get("phone").toString());

		Cart cart = new Cart();
		cart.setUser(user);
		cart = this.cartRepository.save(cart);
		user = this.useRepository.save(user);
		return user;
	}

	public User updateUser(long id, Map<String, Object> newUser) {
		User user = new User();
		user.setName(newUser.get("name").toString());
		user.setId(id);
		user = this.useRepository.save(user);
		return user;
	}

	public void remoteUser(long id) {
		this.useRepository.deleteById(id);
	}

//	public List<User> findByFullName(String full_name) {
//		return this.useRepository.findByFull_name(full_name);
//	}
}
