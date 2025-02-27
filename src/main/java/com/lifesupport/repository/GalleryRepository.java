package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Gallery;


public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
		// Get active 
//		@Query("SELECT u FROM Category u WHERE u.categoryStatus = True")
//		List<Category> findAllByActive();
//		
//		 Search Slide by Title
		@Query("SELECT c FROM Gallery c WHERE c.title LIKE %?1%")
		List<Gallery> SearchGallery(String keyword);

		@Query("SELECT c FROM Gallery c WHERE c.type = %?1%")
		List<Gallery> SearchByType(String type);

	
}
