package com.huong.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class OrderRequest {

	private String name;
	private String mobilePhone;
	private String address;
	private String city;
	private String state;
	
	private String paymentType;

}
