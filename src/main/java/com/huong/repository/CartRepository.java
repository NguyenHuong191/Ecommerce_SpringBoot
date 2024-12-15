package com.huong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huong.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

	public Cart findByProductIdAndUserId(Integer pid, Integer uid);

	public Integer countByUserId(Integer userId);

	public List<Cart> findByUserId(Integer userId);

	public void deleteAllByUserId(Integer userId);
}
