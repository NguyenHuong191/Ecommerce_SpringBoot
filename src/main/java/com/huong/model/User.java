package com.huong.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor //tạo constructor với tso
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    private String name;
    private String img;
    
    private String email;
    private String password;
   
    private String role;
    
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;
    
    
    private Boolean isEnable;
    private Boolean accountNonLocked;
    private Integer failedAttempt;
    private Date lockTime;
    
    
	

}
