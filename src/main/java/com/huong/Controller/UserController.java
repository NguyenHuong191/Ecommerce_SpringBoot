package com.huong.Controller;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.plaf.multi.MultiButtonUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huong.model.Cart;
import com.huong.model.Category;
import com.huong.model.OrderRequest;
import com.huong.model.ProductOrder;
import com.huong.model.User;
import com.huong.service.CartService;
import com.huong.service.CategoryService;
import com.huong.service.OrderService;
import com.huong.service.UserService;
import com.huong.util.OrderStatus;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;

	//---- them vao gio
	@GetMapping("/addToCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, RedirectAttributes redirectAttributes) {
		Cart saveCart = cartService.saveCart(pid, uid);
		
		if(!ObjectUtils.isEmpty(saveCart)) {
			redirectAttributes.addFlashAttribute("succMsg","Add to cart success!");
		}else {
			redirectAttributes.addFlashAttribute("errorMsg","Add to cart failed!");
		}
		
		//redirect to home control
		return "redirect:/product_details/"+ pid; 
	}
	
	//-----load gio hang
	@GetMapping("/cart")
	public String loadCartPage(Principal p ,Model m) {
		User user = getLoggedInUserDetails(p);
		
		List<Cart> carts = cartService.getCartByUser(user.getId());
		m.addAttribute("carts", carts);
		
		if(carts.size() > 0) {
		Double totalCartPrice = carts.get(carts.size()-1).getTotalCartPrice();
	    m.addAttribute("totalCartPrice",totalCartPrice);
		}
	    return "/user/cart_page";
	}
	
	//----update so luong
	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid, RedirectAttributes redirectAttributes) {
		cartService.updateQuantity(sy,cid);
		return "redirect:/user/cart";
	}
	
	//----xoa 
	@GetMapping("/deleteFromCart")
	public String deleteFromCart(@RequestParam Integer cid, RedirectAttributes redirectAttributes) {
		boolean isDelete = cartService.deleteFromCart(cid);
		
		if(isDelete) {
			redirectAttributes.addFlashAttribute("succMsg", "Delete success!");
		}else {
			redirectAttributes.addFlashAttribute("errorMsg", "Delete failed!");			
		}
		
		return "redirect:/user/cart";
	}

	//-----lay user login
	private User getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		User user = userService.getUserByEmail(email);
		return user;
	}
	
	//------trang order
	@GetMapping("/orders")
	public String orderPage(Principal p ,Model m) {
        User user = getLoggedInUserDetails(p);
		
		List<Cart> carts = cartService.getCartByUser(user.getId());
		m.addAttribute("carts", carts);
		
		if(carts.size() > 0) {
           Double orderPrice = carts.get(carts.size()-1).getTotalCartPrice();
           m.addAttribute("orderPrice",orderPrice);
           
		   Double totalOderPrice = carts.get(carts.size()-1).getTotalCartPrice() + 40;//+40 la phi ship
	       m.addAttribute("totalOrderPrice",totalOderPrice);
	       
	       int totalQuantity = 0;
	       for(Cart cart: carts) {
	    	   totalQuantity += cart.getQuantity();
	       }
	       m.addAttribute("totalQuantity", totalQuantity);
		}
		
		return "/user/order_page";
	}
	
	//------luu order
	@PostMapping("/save-orders")
	public String saveOrder(Model m,@ModelAttribute OrderRequest request, Principal p) {
        User user = getLoggedInUserDetails(p);
        m.addAttribute("user", user);
		orderService.saveOrder(user.getId(), request);
		
		return "/user/success";
	}
	
	//--------trang dat hang thanh cong
	@GetMapping("/success")
	public String getMethodName() {
		return "/user/success";
	}
	
	
	//--------xem tat ca don hang
	@GetMapping("/my-orders")
	public String myOrder(Model m, Principal p) {
		User loggedInUserDetails = getLoggedInUserDetails(p);
		List<ProductOrder> orders  = orderService.getOrderByuser(loggedInUserDetails.getId());
		m.addAttribute("orders", orders);
		return "/user/my_orders";
	}
	
	//--------cap nhat trang thai cua don hang
	@GetMapping("/update-status")
	public String updateStt(@RequestParam Integer id, @RequestParam Integer st, RedirectAttributes attributes) {
		OrderStatus[] values = OrderStatus.values();
		
		for(OrderStatus orderStatus : values) {
			if(orderStatus.getId().equals(st)) {
				String status = orderStatus.getName();
				Boolean updateOrder = orderService.UpdateOrderStatus(id, status);
				if(updateOrder) {
					attributes.addFlashAttribute("succMsg","Update status success!");
				}else {
					attributes.addFlashAttribute("errorMsg","Update status failed!");				
				}
			}
		}
		return "redirect:/user/my-orders";
	}
	
	//----------profile
	@GetMapping("/profile")
	public String userProfile() {
		return "/user/profile";
	}
}
