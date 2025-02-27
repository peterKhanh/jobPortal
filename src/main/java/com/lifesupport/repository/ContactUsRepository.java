package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.ContactUs;


public interface ContactUsRepository extends JpaRepository<ContactUs, Integer> {
		// Get active Cate
		@Query("SELECT u FROM ContactUs u WHERE u.status = True")
		List<ContactUs> findAllByActive();
		
		// Search Cate by Name
		@Query("SELECT c FROM ContactUs c WHERE c.title LIKE %?1%")
		List<ContactUs> SearchContactUs(String keyword);
	
}
