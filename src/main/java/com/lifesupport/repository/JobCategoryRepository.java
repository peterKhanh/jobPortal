package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.JobCategory;


public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {
		// Get active Cate
		@Query("SELECT u FROM JobCategory u WHERE u.status = True")
		List<JobCategory> findAllByActive();

		// Get active Cate
		@Query("SELECT u FROM JobCategory u WHERE u.status = True AND u.popular = True" )
		List<JobCategory> findAllPopularCate();

		
		// Search Cate by Name
		@Query("SELECT c FROM JobCategory c WHERE c.name LIKE %?1%")
		List<JobCategory> SearchJobCategory(String keyword);

	
}
