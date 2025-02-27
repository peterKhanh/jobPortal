package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.BlogTag;
@Repository
public interface BlogTagRepository extends JpaRepository<BlogTag, Integer> {
	
	List<BlogTag> findByBlogId(Integer id);
}
