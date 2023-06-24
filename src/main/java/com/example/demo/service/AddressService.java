package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.respository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;

	public List<Address> getAllAddressByUser(Long userId) {
		return this.addressRepository.findByUserId(userId);
	}

	public Address addNewAddress(Map<String, Object> newAddress, User user) {
		Address address = new Address();
		address.setStreet(newAddress.get("street").toString());
		address.setCity(newAddress.get("city").toString());
		address.setCountry(newAddress.get("country").toString());
		address.setUser(user);
		this.addressRepository.save(address);
		return address;
	}

	public Address updateAddress(long id, Map<String, Object> newAddress, Address address) {
		address.setStreet(newAddress.get("street").toString());
		address.setCity(newAddress.get("city").toString());
		address.setCountry(newAddress.get("country").toString());
		address.setId(id);
		this.addressRepository.save(address);
		return address;
	}
	
	public void deleteAddress(long id) {
		 this.addressRepository.deleteById(id);
	}
}
