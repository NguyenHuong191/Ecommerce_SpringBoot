package com.huong.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.huong.model.Product;

import jakarta.transaction.Transactional;

public interface ProductReposity extends JpaRepository<Product, Integer>{

	List<Product> findByIsActiveTrue();
	
	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.isActive = false WHERE p.id = :id")
	void deactivateProductById(@Param("id") int id);
	
	Page<Product> findByIsActiveTrueAndNameContainingIgnoreCaseOrIsActiveTrueAndCategory_NameContainingIgnoreCase(String searchProduct, String searchCategory,Pageable pageable);

	Page<Product> findByIsActiveTrueAndCategory_IsActiveTrue(Pageable page);

	Page<Product> findByCategoryIdAndIsActiveTrue(Pageable page, int category);
	
    List<Product> findTop10ByOrderByIdDesc();

	Page<Product> findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCaseOrderByIsActiveDesc(String searchProduct,
			String searchCategory, Pageable pageable);

	Page<Product> findAllByOrderByIsActiveDesc(Pageable pageable);

}
