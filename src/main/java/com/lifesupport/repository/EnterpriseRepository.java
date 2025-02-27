package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.Enterprise;
import com.lifesupport.models.IndustrialType;
@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
	// Search Enterprise by Name
	@Query("SELECT c FROM Enterprise c WHERE c.name LIKE %?1%")
	List<Enterprise> SearchEnterprise(String keyword);
	
	// Search enterprise by industrialType
	@Query("SELECT c FROM Enterprise c WHERE c.industrialType = %?1%")
	List<Enterprise> SearchByIndustrialType(IndustrialType industrialType);

	// Search enterprise by industrialType
	@Query("SELECT c FROM Enterprise  c")
	List<Enterprise> getAllEnterprise();
	
	Page<Enterprise> findByIndustrialType(IndustrialType industrialType, Pageable pageable);

//	List<Enterprise> findTop1ByCategory(Category category);
	
//	Get All active Enterprise to Display in Homepage
	@Query("SELECT c FROM Enterprise c")
	List<Enterprise> getAllEnterpriseForHomePage();
	

}
