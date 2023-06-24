package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.ResponseCode;
import com.example.demo.model.DTO.AuthenDTORequet;
import com.example.demo.model.DTO.AuthenDTOResponse;
import com.example.demo.util.JwtUtils;

@RestController
@RequestMapping("/login")
public class AuthenController extends BaseRestController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping
public ResponseEntity<?> login(@RequestBody AuthenDTORequet authen){
	try {
//		String encodedPassword = this.passwordEncoder.encode(authen.getPassword());
		this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authen.getName(),authen.getPassword()));
		String token =JwtUtils.generateToken(authen.getName());
		AuthenDTOResponse authenDTOResponse= new AuthenDTOResponse(token,"Đăng nhập thành công");
		return success(authenDTOResponse);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return error(ResponseCode.NO_CONTENT.getCode(),ResponseCode.NO_CONTENT.getMessage());
}
}
