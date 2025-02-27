package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Category;
import com.lifesupport.models.IndustrialType;
import com.lifesupport.models.WorkingModel;

public interface WorkingModelRepository extends JpaRepository<WorkingModel, Integer> {
	@Query("SELECT u FROM WorkingModel u WHERE u.status = True")
	List<WorkingModel> findAllByActive();
	// Search Cate by Name
	
	@Query("SELECT c FROM WorkingModel c WHERE c.name LIKE %?1%")
	List<WorkingModel> SearchWorkingModel(String keyword);

}
