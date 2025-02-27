package com.lifesupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifesupport.models.Role;
import com.lifesupport.models.User;
import com.lifesupport.models.UserRole;



public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	UserRole findByUser(User user);
	UserRole findByRole(Role role);
}
