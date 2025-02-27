package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.Blog;
import com.lifesupport.models.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// Search Blog by CategoryID
//	@Query("SELECT c FROM Blog c WHERE c.category = %?1%")
//	List<Blog> SearchByCategory(Category category);
	
//	Page<Blog> findByCategory(Category category, Pageable pageable);

	List<Comment> findByBlogOrderByCreateAtDesc(Blog  blog);
	
//	List<Comment> findByStatusTrueOrderByCreateAtDesc(Blog  blog);


}
