package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.Blog;
import com.lifesupport.models.Category;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
	// Search Blog by Name
	@Query("SELECT c FROM Blog c WHERE c.title LIKE %?1%")
	List<Blog> SearchBlog(String keyword);
	
	// Search Blog by CategoryID
	@Query("SELECT c FROM Blog c WHERE c.category = %?1%")
	List<Blog> SearchByCategory(Category category);
	
	Page<Blog> findByCategory(Category category, Pageable pageable);

	@Query("SELECT c FROM Blog c WHERE c.category = %?1% Order By c.createAt DESC ")
	List<Blog> findTop4ByCategory(Category category);


}
