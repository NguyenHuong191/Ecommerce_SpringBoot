package com.huong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huong.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

	List<ProductOrder> findByUserIdOrderByOrderDateDesc(Integer userId);

	List<ProductOrder> findByOrderIdContainingIgnoreCaseOrProduct_NameContainingIgnoreCaseOrOrderAddress_NameContainingIgnoreCaseOrOrderAddress_MobilePhoneContainingIgnoreCaseOrOrderAddress_AddressContainingIgnoreCase(String orderId, String productName, String userName, String mobilePhone,String address);
}