package com.huong.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.huong.model.Category;
import com.huong.model.Product;
import com.huong.model.ProductOrder;
import com.huong.model.User;
import com.huong.service.CategoryService;
import com.huong.service.OrderService;
import com.huong.service.UserService;
import com.huong.util.OrderStatus;

@Controller
@RequestMapping("/admin")
public class AdminController {
     @Autowired
     private CategoryService categoryService;
     
     @Autowired
     private UserService userService;
     
     @Autowired
     private OrderService orderService;
     
	@GetMapping("/")
	public String index() {
		return "admin/index";
	}
	
	@GetMapping("/product/add")
	public String addProduct(Model m) {
		return "admin/add_product";
	}
	
	@GetMapping("/category")
	public String addCategory(Model m,
			@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = "10") Integer pageSize) {
		
		Page<Category> page = categoryService.getAllCategoryPage(pageNo, pageSize);
        List<Category> categorys = page.getContent();
		
		m.addAttribute("categorys", categorys);
	    m.addAttribute("pageNo", page.getNumber());
	    m.addAttribute("pageSize", pageSize);
	    m.addAttribute("totalElement", page.getTotalElements());
	    m.addAttribute("totalPages", page.getTotalPages());
	    m.addAttribute("isFirst", page.isFirst());
	    m.addAttribute("isLast", page.isLast());
		
		
		
		//return về file add_category.html trong thư mục admin
		return "admin/add_category";
	}
	
	@GetMapping("/users")
	public String manageUser(Model m,@RequestParam Integer type) {
	    List<User> users = null;
		
		if(type==1) {
		    users = userService.getUsers("ROLE_USER");
		}else {
			users = userService.getUsers("ROLE_ADMIN");
		}
	    m.addAttribute("users", users);
        m.addAttribute("userType", type);
		return "/admin/manage_user";

	}


	@GetMapping("/orders")
	public String getAllOrders(Model m) {
		List<ProductOrder> orders = orderService.getAllOrder();
		m.addAttribute("orders", orders);
		return "/admin/orders";
	}
	
	@GetMapping("/add-admin")
	public String addAdmin(Model m) {
		
		return "/admin/add_admin";
	}
	
	//----------profile
	@GetMapping("/profile")
	public String userProfile() {
		return "/admin/profile";
	}

}
