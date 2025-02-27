package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
		// Get active Cate
		@Query("SELECT u FROM Category u WHERE u.categoryStatus = True")
		List<Category> findAllByActive();
		
		// Search Cate by Name
		@Query("SELECT c FROM Category c WHERE c.categoryName LIKE %?1%")
		List<Category> SearchCategory(String keyword);

	
}
