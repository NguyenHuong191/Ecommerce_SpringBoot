package com.huong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huong.model.User;

public interface UserReposity extends JpaRepository<User, Integer>{
      
	public User findByEmail(String email);
	
	public List<User> findByRole(String role);
	
	public Boolean existsByEmail(String email);
}
