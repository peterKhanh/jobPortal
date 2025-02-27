package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.Job;
import com.lifesupport.models.JobApply;
import com.lifesupport.models.User;
@Repository
public interface JobApplyRepository extends JpaRepository<JobApply, Long> {
	
	List<JobApply> findByUserId(Long id);
	@Query("SELECT c FROM JobApply c WHERE c.user = %?1% Order By c.applyDate DESC") 
	List<JobApply> findByUser(User user);

	@Query("SELECT c FROM JobApply c WHERE c.user = %?1% Order By c.applyDate DESC") 
	Page<JobApply> findByUser(User user, Pageable pageable );
	
	@Query("SELECT c FROM JobApply c WHERE c.job = %?1% Order By c.applyDate DESC") 
	Page<JobApply> findByJob(Job job, Pageable pageable );

	@Query(value = "SELECT ja.user_id, ja.job_id, ja.apply_date from jobapply ja ORDER BY ja.apply_date DESC",  nativeQuery = true)
	Page<JobApply> findRecentApplyCandidate(Pageable pageable);

}
