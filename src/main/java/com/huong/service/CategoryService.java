package com.huong.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.huong.model.Category;

public interface CategoryService {

	public Category saveCategory(Category category, MultipartFile file) throws IOException, InterruptedException;
	
	public Boolean existCategory(String name);
		
	public boolean deleteCategories(int id);

	public Category getCategoryById(int id);
	
	public boolean updateCategory(Category category, MultipartFile file) throws InterruptedException, IllegalStateException, IOException;

	public List<Category> getAllActiveCategory();

//	public List<Category> getAllInactiveCategory();
	
	Page<Category> getAllCategoryPage(Integer pageNo, Integer pageSize);
}
