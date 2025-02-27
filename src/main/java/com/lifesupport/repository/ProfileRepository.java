package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.Profile;
import com.lifesupport.models.User;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	// 
	@Query("SELECT c FROM Profile c WHERE c.user = %?1%")
	List<Profile> findAllByUser(User user);

}
