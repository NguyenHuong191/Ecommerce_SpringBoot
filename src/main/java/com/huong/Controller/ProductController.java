package com.huong.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huong.model.Category;
import com.huong.model.Product;
import com.huong.service.CategoryService;
import com.huong.service.ProductService;

@Controller
@RequestMapping("/admin/product")    
public class ProductController {
	@Autowired
    private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	 @Value("${uploadImg.dir}")
     private String uploadDir;
	
	 //add product
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile img, RedirectAttributes attributes) throws IllegalStateException, IOException, InterruptedException {
		
		boolean isSaved = productService.saveProduct(product, img);
		
		if(isSaved) {
			attributes.addFlashAttribute("succMsg", "Product saved success!");
		}
		else {
			attributes.addFlashAttribute("errorMsg", "Product saved failed!");
		}
		
		return "redirect:/admin/product/add";
	}
	

	//view product va search
	@GetMapping("/")
	public String viewProduct(Model m, @RequestParam(name="searchText",defaultValue = "") String searchText,
			@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = "10") Integer pageSize) {
		
		Page<Product> page = null;
		if(searchText!=null && searchText.length()>0) {
			page = productService.searchProduct(searchText, pageNo, pageSize);
		}else {
			page = productService.getAllProducts(pageNo, pageSize);
		}
		List<Product> products = page.getContent();
		
		m.addAttribute("products", products);
	    m.addAttribute("productSize", products.size());
	    m.addAttribute("pageNo", page.getNumber());
	    m.addAttribute("pageSize", pageSize);
	    m.addAttribute("totalElement", page.getTotalElements());
	    m.addAttribute("totalPages", page.getTotalPages());
	    m.addAttribute("isFirst", page.isFirst());
	    m.addAttribute("isLast", page.isLast());
	    
	    m.addAttribute("searchText", searchText);

		return "admin/view_product";
	}
	
	//xoa sp
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
		Boolean isDelete = productService.deleteProduct(id);
		
		if(isDelete) {
			redirectAttributes.addFlashAttribute("succMsg", "Delete product success!");
		}else {
			redirectAttributes.addFlashAttribute("errorMsg", "Delete product failed!");			
		}
		return "redirect:/admin/product/";
	}
	
	// get du lieu id tu duong dan va tra ve edit_product
	@GetMapping("/edit/{id}")
	public String editProduct(@PathVariable int id,Model m) {
		m.addAttribute("product", productService.getProductById(id));
		m.addAttribute("categories", categoryService.getAllActiveCategory());
		return "admin/edit_product";
	}
	
	@PostMapping("/update")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, InterruptedException{
		Category category = categoryService.getCategoryById(product.getCategory().getId());
		if(category != null) {
				product.setCategory(category);
			
        if(product.getDiscount()<0 || product.getDiscount()>100) {
			redirectAttributes.addFlashAttribute("errorMsg", "Invalid discount! Discount must be in 1-100%!");
        }else {
		
		boolean isUpdate = productService.updateProduct(product, file);
        
        if(isUpdate) {
			redirectAttributes.addFlashAttribute("succMsg", "Update product success!");
        }else {
			redirectAttributes.addFlashAttribute("errorMsg", "Update product failed!");
        }
        }
		}else {
			redirectAttributes.addFlashAttribute("errorMsg", "Something is wrong!");
		}
		return "redirect:/admin/product/edit/" + product.getId() ;
		
	}

}
