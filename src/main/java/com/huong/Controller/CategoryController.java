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
import com.huong.service.CategoryService;

@Controller
@RequestMapping("/admin/category")    
public class CategoryController {
     @Autowired
     private CategoryService categoryService;
     
     
     @PostMapping("/save")      //tự động ánh xạ dữ liệu form vào category
 	public String saveCategory(@ModelAttribute Category category,@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, InterruptedException { 		
 		if(categoryService.existCategory(category.getName())) {
 	        redirectAttributes.addFlashAttribute("errorMsg", "Category already exists!");
 		}else {
 			if(!file.isEmpty()) {
 				category.setImg(file.getOriginalFilename());
 			}
 			
 			Category saveCategory = categoryService.saveCategory(category, file);
 			if(!ObjectUtils.isEmpty(saveCategory)) {
 				redirectAttributes.addFlashAttribute("succMsg", "Save successfully!");
 			}else {
 				redirectAttributes.addFlashAttribute("errorMsg", "Save failed!");
 			}
 		}
 		//redirect link
 		return "redirect:/admin/category";
 		
 	}
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable int id, RedirectAttributes redirectAttributes ) {
		boolean isDelete = categoryService.deleteCategories(id);
		
		if(isDelete) {
			redirectAttributes.addFlashAttribute("succMsg", "Delete successfuly!");
		}else {
			redirectAttributes.addFlashAttribute("errorMsg", "Delete failed!");
		}
		return "redirect:/admin/category";
	}
	
	@GetMapping("/edit/{id}")
	public String loadEditCategory(@PathVariable int id, Model m,
			@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = "10") Integer pageSize) {
		m.addAttribute("cate", categoryService.getCategoryById(id));
		Page<Category> page = categoryService.getAllCategoryPage(pageNo, pageSize);
        List<Category> categorys = page.getContent();
		
		m.addAttribute("categorys", categorys);
	    m.addAttribute("pageNo", page.getNumber());
	    m.addAttribute("pageSize", pageSize);
	    m.addAttribute("totalElement", page.getTotalElements());
	    m.addAttribute("totalPages", page.getTotalPages());
	    m.addAttribute("isFirst", page.isFirst());
	    m.addAttribute("isLast", page.isLast());
		return "/admin/edit_category";
	}
	
	
	@PostMapping("/update")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, InterruptedException { 
   	 
		boolean isUpdate = categoryService.updateCategory(category, file);
	     if(isUpdate) {
			redirectAttributes.addFlashAttribute("succMsg","Update successfully!");
		}else {
			redirectAttributes.addFlashAttribute("errorMsg","Update failed!");			
		}
		
		return "redirect:/admin/category";
		
	}
	
}
