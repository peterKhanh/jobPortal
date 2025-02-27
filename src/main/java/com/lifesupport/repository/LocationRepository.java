package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	@Query("SELECT u FROM Location u WHERE u.status = True")
	List<Location> findAllByActive();
	// Search Rank by Name
	
	@Query("SELECT c FROM Location c WHERE c.name LIKE %?1%")
	List<Location> SearchLocation(String keyword);

}
