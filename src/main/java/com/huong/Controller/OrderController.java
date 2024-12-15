package com.huong.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huong.model.ProductOrder;
import com.huong.service.OrderService;
import com.huong.service.ProductService;
import com.huong.util.OrderStatus;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {
	
	@Autowired
    private OrderService orderService;
	
	//--------cap nhat trang thai cua don hang
	@PostMapping("/update-order-status")
	public String updateOrderStt(@RequestParam Integer id, @RequestParam Integer st, RedirectAttributes attributes) {
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
		return "redirect:/admin/orders";
    }

	
	//---search-order
	@GetMapping("/search")
	public String search(@RequestParam String searchText, Model m) {
		if(searchText!=null) {
		    List<ProductOrder> orderFromSearch = orderService.getOrdersFromSearch(searchText.trim());
		    if(!ObjectUtils.isEmpty(orderFromSearch)) {
		        m.addAttribute("orders", orderFromSearch);
		    }
		    }else {
			List<ProductOrder> orders = orderService.getAllOrder();
			m.addAttribute("orders", orders);
		}
		return "/admin/orders";
	}

}
