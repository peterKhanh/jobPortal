package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Slide;
import com.lifesupport.models.Tastimonial;


public interface SlideRepository extends JpaRepository<Slide, Integer> {
	
//		 Search Slide by Title
		@Query("SELECT c FROM Slide c WHERE c.title LIKE %?1%")
		List<Slide> SearchSlide(String keyword);

		List<Slide> findByStatusTrue();

}
