package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.IndustrialType;

public interface IndustrialTypeRepository extends JpaRepository<IndustrialType, Integer> {
	@Query("SELECT u FROM IndustrialType u WHERE u.status = True")
	List<IndustrialType> findAllByActive();
	// Search Cate by Name
	@Query("SELECT c FROM IndustrialType c WHERE c.name LIKE %?1%")
	List<IndustrialType> SearchIndustrialType(String keyword);

}
