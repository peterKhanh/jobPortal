package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Category;
import com.lifesupport.models.Tastimonial;


public interface TastimonialRepository extends JpaRepository<Tastimonial, Integer> {

	
//		 Search Tastimonial by Title
		@Query("SELECT c FROM Tastimonial c WHERE c.title LIKE %?1%")
		List<Tastimonial> SearchTastimonial(String keyword);

		// Get active Tastimonial
		@Query("SELECT u FROM Tastimonial u WHERE u.status = True")
		List<Tastimonial> findAllByActive();

		List<Tastimonial> findByStatusTrue();

}
