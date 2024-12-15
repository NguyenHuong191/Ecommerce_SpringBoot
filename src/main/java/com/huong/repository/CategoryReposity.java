package com.huong.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.huong.model.Category;

import jakarta.transaction.Transactional;

public interface CategoryReposity extends JpaRepository<Category, Integer> {
      
	public Boolean existsByName(String name);
	
	@Modifying
	@Transactional
	@Query("UPDATE Category c SET c.isActive = false WHERE c.id = :id")
	void deactivateCategoryById(@Param("id") int id);

	public List<Category> findByIsActiveTrue();
		
	@Query("SELECT c.id FROM Category c WHERE c.name = :name")
	int findCategoryIdByName(String name);

	public Page<Category> findAllByOrderByIsActiveDesc(Pageable page);


}
