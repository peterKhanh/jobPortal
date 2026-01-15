package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.BlogCate;

public interface BlogCateRepository extends JpaRepository<BlogCate, Integer> {
	@Query("SELECT u FROM BlogCate u WHERE u.status = True")
	List<BlogCate> findAllByActive();
	// Search Rank by Name
	
	@Query("SELECT c FROM BlogCate c WHERE c.name LIKE %?1%")
	List<BlogCate> SearchBlogCate(String keyword);

}
