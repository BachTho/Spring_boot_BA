package com.example.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.example.demo.model.DTO.ProductDTOResponse;
import com.example.demo.respository.AddressRepository;
import com.example.demo.respository.UserRepository;
import com.example.demo.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseRestController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;

	@GetMapping("")
	public ResponseEntity<?> getAllAddressByUser(
			@RequestParam(name = "userId", required = false, defaultValue = "1") long userId)

	{
		List<Address> addresses = this.addressService.getAllAddressByUser(userId);
//		List<ProductDTOResponse> dtoResponses = addresses.stream().map(ProductDTOResponse::new).toList();

		return new ResponseEntity<>(addresses, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> addAddress(@RequestBody(required = false) Map<String, Object> newAddress) {
		try {
			if (ObjectUtils.isEmpty(newAddress)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			User foundUser = this.userRepository.findById(Long.parseLong(newAddress.get("userId").toString()))
					.orElse(null);
			if (!ObjectUtils.isEmpty(foundUser)) {
				this.addressService.addNewAddress(newAddress, foundUser);
				return super.success(newAddress);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateAddress(@PathVariable long id,
			@RequestBody(required = false) Map<String, Object> newAddress) {
		try {
			if (ObjectUtils.isEmpty(newAddress) || ObjectUtils.isEmpty(id)
					|| ObjectUtils.isEmpty(newAddress.get("street")) || ObjectUtils.isEmpty(newAddress.get("country"))
					|| ObjectUtils.isEmpty(newAddress.get("city"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Address foundAddress = this.addressRepository.findById(id).orElse(null);
			if (!ObjectUtils.isEmpty(foundAddress)) {
				Address address = this.addressService.updateAddress(id, newAddress, foundAddress);
				return new ResponseEntity<>(address, HttpStatus.OK);
			}
			return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAddress(@PathVariable long id) {
		try {
			if (ObjectUtils.isEmpty(id)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Address foundAddress = this.addressRepository.findById(id).orElse(null);
			if (ObjectUtils.isEmpty(foundAddress)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			this.addressService.deleteAddress(id);
			return new ResponseEntity<>(foundAddress, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

}
