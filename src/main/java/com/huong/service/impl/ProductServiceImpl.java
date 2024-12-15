package com.huong.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.huong.model.Category;
import com.huong.model.Product;
import com.huong.repository.CategoryReposity;
import com.huong.repository.ProductReposity;
import com.huong.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
	private ProductReposity productReposity;
    
    @Autowired
    private CategoryReposity categoryReposity;
    
    @Value("${uploadImg.dir}")
    private String uploadDir;
    
    //-----luu sp
	@Override
	public boolean saveProduct(Product product, MultipartFile file) throws IOException, InterruptedException {
		String fileName = file.getOriginalFilename();
        product.setImg(fileName);
        product.setDiscount(0);
        product.setDiscountPrice(product.getPrice());

		Path path = Paths.get(uploadDir + fileName);
        file.transferTo(path);
                
        Product productSaved = productReposity.save(product);
        if (ObjectUtils.isEmpty(productSaved)) {
            return false; 
        }
		return true;
	
	}

	//-----lay tat ca sp de hien thi ben view admin
	@Override
	public Page<Product> getAllProducts(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Product> page = productReposity.findAllByOrderByIsActiveDesc(pageable);
		return page;
	}

	//------delete
	@Override
	public boolean deleteProduct(Integer id) {
		Product product = productReposity.findById(id).orElse(null);
		
		if(!ObjectUtils.isEmpty(product)) {
			productReposity.deactivateProductById(id);
			return true;
		}
		return false;
	}

	@Override
	public Product getProductById(Integer id) {
		Product product = productReposity.findById(id).orElse(null);
		return product;
	}

	//---------update
	@Override
	public boolean updateProduct(Product product, MultipartFile file) throws IOException,InterruptedException {
		Product productFromDb = getProductById(product.getId());

		productFromDb.setName(product.getName());
		productFromDb.setDescription(product.getDescription());
		productFromDb.setPrice(product.getPrice());
		productFromDb.setStock(product.getStock());
		productFromDb.setDiscount(product.getDiscount());	
		productFromDb.setIsActive(product.getIsActive());
		
		Double discount = product.getPrice()*(product.getDiscount()/100.0);
		Double discountPrice = product.getPrice()- discount;
		
		productFromDb.setDiscountPrice(discountPrice);
		
		productFromDb.setCategory(product.getCategory());
		
		if(!file.isEmpty()) {
				// Nếu người dùng upload file mới
	            String newFileName = file.getOriginalFilename();
	            Path path = Paths.get(uploadDir + newFileName);
	            file.transferTo(path);
	            productFromDb.setImg(newFileName); // Cập nhật ảnh mới
		}else {
				//neu k thi giu lai anh cu
				productFromDb.setImg(productFromDb.getImg());
			}
        Product updateProduct = productReposity.save(productFromDb);
        
        if(updateProduct != null) {
        	return true;
        }
        return false;
}



	//----lay danh sach san pham co isActive la true de hien thi ben user
	@Override
	public Page<Product> getAllActiveProductPagination(Integer pageNo, Integer pageSize,String category) {
		Pageable page = PageRequest.of(pageNo,pageSize);
		Page<Product> pageProduct = null;
		
		if(ObjectUtils.isEmpty(category)) {
			pageProduct = productReposity.findByIsActiveTrueAndCategory_IsActiveTrue(page);
		}else {
			int categoryid = categoryReposity.findCategoryIdByName(category);
			pageProduct = productReposity.findByCategoryIdAndIsActiveTrue(page,categoryid);
		}
		return pageProduct;
	}

	//-----lay 5 san pham moi cap nhat
	@Override
	public List<Product> getTop10LastProduct() {
		List<Product> lastProduct = productReposity.findTop10ByOrderByIdDesc();
		return lastProduct;
	}

	//-----lay san pham theo tim kiem ben trang tim kiem cua user
	@Override
	public Page<Product> searchProductPagination(String searchText, Integer pageNo, Integer pageSize) {
	    Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> page = productReposity.findByIsActiveTrueAndNameContainingIgnoreCaseOrIsActiveTrueAndCategory_NameContainingIgnoreCase(searchText,searchText,pageable);
	    return page;
	}
	
	//-----lay san pham theo tim kiem ben trang tim kiem cua admin
		@Override
		public Page<Product> searchProduct(String searchText, Integer pageNo, Integer pageSize) {
		    Pageable pageable = PageRequest.of(pageNo, pageSize);
	        Page<Product> page = productReposity.findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCaseOrderByIsActiveDesc(searchText,searchText,pageable);
		    return page;
		}
}


