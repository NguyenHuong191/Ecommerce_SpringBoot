package com.huong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huong.model.OrderAddress;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Integer>{

	public OrderAddress findByAddressAndCityAndStateAndMobilePhone(String address, String city,
			String state, String mobilePhone);
     
	
}
