package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Category;
import com.lifesupport.models.OurService;


public interface OurServiceRepository extends JpaRepository<OurService, Integer> {
		// Get active OurService
//		@Query("SELECT u FROM Category u WHERE u.categoryStatus = True")
//		List<Category> findAllByActive();
//		
//		 Search Slide by Title
		@Query("SELECT c FROM OurService c WHERE c.title LIKE %?1%")
		List<OurService> SearchOurService(String keyword);

}
