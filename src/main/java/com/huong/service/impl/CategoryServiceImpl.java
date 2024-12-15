package com.huong.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.huong.model.Category;
import com.huong.model.Product;
import com.huong.repository.CategoryReposity;
import com.huong.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
	private CategoryReposity categoryReposity;
	
    @Value("${uploadCategory.dir}")
    private String categoryUpload;
    
    //------luu category
    
	@Override
	public Category saveCategory(Category category, MultipartFile img) throws IOException, InterruptedException {
		if(!img.isEmpty() && img != null) {
	    	String originalFilename = img.getOriginalFilename();
	        String fileExtension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf('.') + 1);
	        
	        //kiểm tra định dạng file
	        if (!List.of("jpg", "jpeg", "png").contains(fileExtension.toLowerCase())) {
	            throw new RuntimeException("Invalid file type. Only JPG, JPEG, PNG are allowed.");
	        }

	        category.setImg(originalFilename);
	        
	        try {
	            Path path = Paths.get(categoryUpload + originalFilename);
	            img.transferTo(path);  
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error while saving the file");
	        }
	    }
                
        Category categorySaved = categoryReposity.save(category);
		
		return categorySaved;
	}

	//-------lay tat ca category con hdong
	@Override
	public List<Category> getAllActiveCategory() {
        List<Category> categoryActive = categoryReposity.findByIsActiveTrue();
		return categoryActive;
	}

	//----kiem tra ton tai
	@Override
	public Boolean existCategory(String name) {
	    return categoryReposity.existsByName(name);
	}

	@Override
	public boolean deleteCategories(int id) {
        Category category = categoryReposity.findById(id).orElse(null);
		
		if(!ObjectUtils.isEmpty(category)) {
			categoryReposity.deactivateCategoryById(id);;
			return true;
		}
		return false;
	}

	
	@Override
	public Category getCategoryById(int id) {
		Category category = categoryReposity.findById(id).orElse(null);
		return category;
	}

	//----------update
	@Override
	public boolean updateCategory(Category category, MultipartFile img) throws InterruptedException, IOException {
		Category category2 = getCategoryById(category.getId());

		if(!img.isEmpty()) {
	    	String originalFilename = img.getOriginalFilename();
	        String fileExtension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf('.') + 1);
	        
	        if (!List.of("jpg", "jpeg", "png").contains(fileExtension.toLowerCase())) {
	            throw new RuntimeException("Invalid file type. Only JPG, JPEG, PNG are allowed.");
	        }

	        category2.setImg(originalFilename);
	        
	        try {
	            Path path = Paths.get(categoryUpload + originalFilename);
	            img.transferTo(path);  
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error while saving the file");
	        }
	    }
		
		if( !ObjectUtils.isEmpty(category2)) {
			category2.setName(category.getName());
			category2.setIsActive(category.getIsActive());
			
			Category updateCategory = categoryReposity.save(category2);
			if(!ObjectUtils.isEmpty(updateCategory)) {
	               return true;
			}
		}
		return false;
	}

	@Override
	public Page<Category> getAllCategoryPage(Integer pageNo, Integer pageSize) {
		
		Pageable page = PageRequest.of(pageNo, pageSize);
		return categoryReposity.findAllByOrderByIsActiveDesc(page);
	}
}
