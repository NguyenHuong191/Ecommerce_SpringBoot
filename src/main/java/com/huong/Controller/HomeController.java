package com.huong.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.huong.model.Category;
import com.huong.model.Product;
import com.huong.service.CategoryService;
import com.huong.service.ProductService;
import com.huong.service.UserService;

import io.micrometer.common.util.StringUtils;

@Controller
public class HomeController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String index(Model m) {
		List<Product> products = productService.getTop10LastProduct();
	    List<List<Product>> productGroups = partitionListProduct(products, 3);
		m.addAttribute("productGroups", productGroups);
		
	    List<Category> categories = categoryService.getAllActiveCategory();
	    List<List<Category>> categoryGroups = partitionListCategory(categories, 5);
        m.addAttribute("categoryGroups", categoryGroups);

		return "index";
	}
	
	
	//----navbar controller
	@GetMapping("/signin")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	 //hien thi list san pham con hdong dua tren category
	@GetMapping("/products")
	public String products(Model m, @RequestParam(value="category",defaultValue="")String category,
			               @RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			               @RequestParam(name = "pageSize",defaultValue = "8")Integer pageSize,
			               @RequestParam(defaultValue = "") String searchText) {
		
		m.addAttribute("paramValue",category);
		
		Page<Product> page = null;
		
		if(StringUtils.isEmpty(searchText)) {
			page = productService.getAllActiveProductPagination(pageNo, pageSize,category);
		}else {
			page = productService.searchProductPagination(searchText, pageNo, pageSize);
		    m.addAttribute("searchText", searchText);
		}
		
		List<Product> products = page.getContent();		
		
		m.addAttribute("productActive", products);
		m.addAttribute("productSize", products.size());
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElement", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
	    
	    return "product";
	}	
	 
	//---ket thuc navbar controller
	
	//----detail product
	@GetMapping("/product_details/{id}")
	public String product_details(Model m ,@PathVariable int id) {
		Product productById = productService.getProductById(id);
		m.addAttribute("product_detail", productById);
		return "product_details";
	}
	
    private List<List<Category>> partitionListCategory(List<Category> list, int size) {
        List<List<Category>> partitionedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitionedList.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitionedList;
    }
    
    private List<List<Product>> partitionListProduct(List<Product> list, int size) {
        List<List<Product>> partitionedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitionedList.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitionedList;
    }
}
