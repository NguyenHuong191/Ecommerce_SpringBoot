package com.huong.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.huong.model.Product;

public interface ProductService {
	
	List<Product> getTop10LastProduct();
	
	public boolean saveProduct(Product product, MultipartFile file) throws IOException, InterruptedException;

	public Page<Product> getAllProducts(Integer pageNo, Integer pageSize);
	
	public boolean deleteProduct(Integer id);
	
	public Product getProductById(Integer id);

	public boolean updateProduct(Product product, MultipartFile file) throws IOException, InterruptedException;
	
	Page<Product> getAllActiveProductPagination(Integer pageNo, Integer pageSize, String category);

	Page<Product> searchProductPagination(String searchText, Integer pageNo, Integer pageSize);

	Page<Product> searchProduct(String searchText, Integer pageNo, Integer pageSize);
	
	

}
