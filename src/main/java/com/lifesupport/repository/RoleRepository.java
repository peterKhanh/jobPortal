package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);

	// Get active Cate
//		@Query("SELECT u FROM Category u WHERE u.categoryStatus = True")
	List<Role> findAll();

}
