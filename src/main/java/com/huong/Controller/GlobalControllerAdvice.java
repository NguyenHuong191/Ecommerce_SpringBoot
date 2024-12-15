package com.huong.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.huong.model.Category;
import com.huong.model.User;
import com.huong.service.CartService;
import com.huong.service.CategoryService;
import com.huong.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
	private CartService cartService;

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if(p!=null && !m.containsAttribute("user")) {
			String email = p.getName();
			
			User userDetails = userService.getUserByEmail(email);
			
			m.addAttribute("userLogin", userDetails);
			
			//lay so luong sp trong cart
			Integer countCartByUserId = cartService.getCountCart(userDetails.getId());
			m.addAttribute("countCart", countCartByUserId);
		}
	    if (!m.containsAttribute("category")) {
		   List<Category> categories = categoryService.getAllActiveCategory();
		   m.addAttribute("categories", categories);
	}
	}
}
