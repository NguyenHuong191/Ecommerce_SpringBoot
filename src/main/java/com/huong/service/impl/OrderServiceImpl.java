package com.huong.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.huong.model.Cart;
import com.huong.model.OrderAddress;
import com.huong.model.OrderRequest;
import com.huong.model.ProductOrder;
import com.huong.repository.CartRepository;
import com.huong.repository.OrderAddressRepository;
import com.huong.repository.ProductOrderRepository;
import com.huong.service.CartService;
import com.huong.service.OrderService;
import com.huong.util.OrderStatus;

import jakarta.transaction.Transactional;


@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private ProductOrderRepository productOrderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
		 
	 @Autowired
	 private OrderAddressRepository addressRepository;
	
	@Override
	@Transactional
	public void saveOrder(Integer userId, OrderRequest orderRequest) {
		List<Cart> carts = cartRepository.findByUserId(userId);
		
		// Kiểm tra xem địa chỉ đã tồn tại chưa
		   OrderAddress orderAddress = addressRepository.findByAddressAndCityAndStateAndMobilePhone(
	            orderRequest.getAddress(), 
	            orderRequest.getCity(), 
	            orderRequest.getState(), 
	            orderRequest.getMobilePhone()
	        );
	        
	        if(ObjectUtils.isEmpty(orderAddress)) {
		   //luu dia chi
			     orderAddress = new OrderAddress();

		         orderAddress.setName(orderRequest.getName());
		         orderAddress.setMobilePhone(orderRequest.getMobilePhone());
		         orderAddress.setAddress(orderRequest.getAddress());
		         orderAddress.setCity(orderRequest.getCity());
	       	     orderAddress.setState(orderRequest.getState());
	        
	             addressRepository.save(orderAddress);
	       }
		
		for(Cart cart : carts) {
		   ProductOrder order = new ProductOrder();
		   order.setOrderId(UUID.randomUUID().toString());
		   order.setOrderDate(LocalDate.now());
		   
		   order.setProduct(cart.getProduct());
		   
		   order.setPrice(cart.getProduct().getDiscountPrice());
		   order.setQuantity(cart.getQuantity());
		   order.setUser(cart.getUser());
		   
		   order.setStatus(OrderStatus.IN_PROGRESS.getName());
		   order.setPaymentType(orderRequest.getPaymentType());
		
		   
		   order.setOrderAddress(orderAddress);
		   
		   productOrderRepository.save(order);
		   
		}
		   cartRepository.deleteAllByUserId(userId);

	}

	@Override
	public List<ProductOrder> getOrderByuser(Integer userId) {
		List<ProductOrder> orders= productOrderRepository.findByUserIdOrderByOrderDateDesc(userId);
		return orders;
	}

	@Override
	public Boolean UpdateOrderStatus(Integer id, String status) {
		Optional<ProductOrder> findById= productOrderRepository.findById(id);
		
		if(findById.isPresent()) {
			ProductOrder productOrder= findById.get();
			productOrder.setStatus(status);
			productOrderRepository.save(productOrder);
			return true;
		}
		
		return false;
	}

	@Override
	public List<ProductOrder> getAllOrder() {
		return productOrderRepository.findAll();
	}

	@Override
	public List<ProductOrder> getOrdersFromSearch(String searchText) {
		List<ProductOrder> orderOfSearch = productOrderRepository.findByOrderIdContainingIgnoreCaseOrProduct_NameContainingIgnoreCaseOrOrderAddress_NameContainingIgnoreCaseOrOrderAddress_MobilePhoneContainingIgnoreCaseOrOrderAddress_AddressContainingIgnoreCase(searchText, searchText, searchText, searchText,searchText);
		return orderOfSearch;
	}

	

}
