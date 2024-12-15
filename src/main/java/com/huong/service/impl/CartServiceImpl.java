package com.huong.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.huong.model.Cart;
import com.huong.model.Product;
import com.huong.model.User;
import com.huong.repository.CartRepository;
import com.huong.repository.ProductReposity;
import com.huong.repository.UserReposity;
import com.huong.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserReposity userReposity;
	
	@Autowired
	private ProductReposity productReposity;
	
	@Override
	public Cart saveCart(Integer productId, Integer userId) {
        User userDetails = userReposity.findById(userId).get();
        Product product = productReposity.findById(productId).get();
        
        Cart cartStatus = cartRepository.findByProductIdAndUserId(productId, userId);
        
        Cart cart = null;
        
        if(ObjectUtils.isEmpty(cartStatus)) {
        	//tao moi neu chua ton tai
            cart = new Cart();
            cart.setUser(userDetails);
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setTotalPrice(1*product.getDiscountPrice());
        }else {
        	cart = cartStatus;
        	cart.setQuantity(cart.getQuantity()+1);
        	cart.setTotalPrice(cart.getQuantity()*cart.getProduct().getDiscountPrice());
        }
        Cart saveCart = cartRepository.save(cart);
        
		return saveCart;
	}

	@Override
	public List<Cart> getCartByUser(Integer userId) {
		List<Cart> carts = cartRepository.findByUserId(userId);
		
		BigDecimal totalCartPrice= BigDecimal.ZERO;
		
		List<Cart> updateCarts = new ArrayList<>();
		
		for(Cart c:carts) {
			
			BigDecimal totalPrice = new BigDecimal(c.getProduct().getDiscountPrice())
	                .multiply(new BigDecimal(c.getQuantity()))
	                .setScale(2, RoundingMode.HALF_UP);
		    
			c.setTotalPrice(totalPrice.doubleValue());
		    
		    totalCartPrice = totalCartPrice.add(totalPrice)
	                .setScale(2, RoundingMode.HALF_UP);;
		    
	        c.setTotalCartPrice(totalCartPrice.doubleValue());
		    
		    updateCarts.add(c);
		}		
		return carts;
	}

	//lay so luong spham trong gio hang
	@Override
	public Integer getCountCart(Integer userId) {
		Integer countByUserId = cartRepository.countByUserId(userId);		
		return countByUserId;
	}

	//update sluong
	@Override
	public void updateQuantity(String sy, Integer cid) {
		int updateQuantity;
		Cart updateCart;
		
		Cart cart = cartRepository.findById(cid).get();
		
		//neu la nhan nut tru
		if(sy.equalsIgnoreCase("de")) {
			updateQuantity = cart.getQuantity() - 1;
			
			if(updateQuantity == 0 ) {
				cartRepository.delete(cart);
			}else {
				cart.setQuantity(updateQuantity);
				updateCart = cartRepository.save(cart);
			}
		}else {
			updateQuantity = cart.getQuantity() + 1;
			cart.setQuantity(updateQuantity);
			updateCart = cartRepository.save(cart);
		}
	}

	//------xoa 
	@Override
	public boolean deleteFromCart(Integer cid) {
		Cart cart = cartRepository.findById(cid).orElse(null);
		
		if(!ObjectUtils.isEmpty(cart)) {
			cartRepository.delete(cart);
			return true;
		}
		return false;
	}
}
