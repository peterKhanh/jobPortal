package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.Blog;
import com.lifesupport.models.Category;
import com.lifesupport.models.BlogCate;


@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
	// Search Blog by Name
	@Query("SELECT c FROM Blog c WHERE c.title LIKE %?1%")
	List<Blog> SearchBlog(String keyword);
	
	// Search Blog by CategoryID
	@Query("SELECT c FROM Blog c WHERE c.category = ?1")
	List<Blog> SearchByCategory(Category category);

	// Search Blog by BlogCate
	@Query("SELECT c FROM Blog c WHERE c.blogcate = ?1 Order By c.createAt DESC ")
	List<Blog> SearchByBlogCate(BlogCate blogcate);
	
	Page<Blog> findByCategory(Category category, Pageable pageable);

	Page<Blog> findByBlogcate(BlogCate blogcate, Pageable pageable);



	@Query("SELECT c FROM Blog c WHERE c.blogcate = ?1 Order By c.createAt DESC ")
	List<Blog> findTop4ByBlogCate(BlogCate blogcate);


}
