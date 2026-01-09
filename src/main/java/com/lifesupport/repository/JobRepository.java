package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifesupport.models.JobCategory;
import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;
import com.lifesupport.models.Location;
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	// Search Job by Title
	@Query("SELECT c FROM Job c WHERE (c.title LIKE %?1% or c.workingAddress LIKE %?1% or c.description LIKE %?1%) AND c.expiredDate >  DATE(NOW()) AND c.status ='APPROVED' ORDER BY c.updateAt DESC, c.createAt DESC" )
	List<Job> SearchJob(String keyword);
	
	// Find All Approved Job by Job Category
	@Query("SELECT c FROM Job c WHERE c.jobCategory = ?1 AND c.expiredDate >  DATE(NOW()) AND c.status ='APPROVED' ORDER BY c.createAt DESC")
	Page<Job> findApprovedJobByCategory(JobCategory jobCategory, Pageable pageable);


	// Find All Approved Job by Job Location
	@Query("SELECT c FROM Job c WHERE c.location = ?1 AND c.expiredDate >  DATE(NOW()) AND c.status ='APPROVED' ORDER BY c.createAt DESC")
	Page<Job> findApprovedJobByLocation(Location location, Pageable pageable);

	
	Page<Job> findByLocation(Location location, Pageable pageable);

	
	// Danh sach Jon theo Doanh nghiep
	@Query("SELECT c FROM Job c WHERE c.enterprise = ?1  ORDER BY c.createAt DESC")
	List<Job> GetAllActiveJobByEnterprise(Enterprise enterprise);

	// Danh sach Jon theo Doanh nghiep
	// Theo trang thai STATUS
	@Query("SELECT c FROM Job c WHERE c.enterprise = ?1 AND c.status = ?2 ORDER BY c.createAt DESC")
	List<Job> GetAllJobOfEnterpriseByStatus(Enterprise enterprise, String status);

	@Query("SELECT c FROM Job c WHERE c.expiredDate >  DATE(NOW()) AND c.status ='APPROVED'")
	Page<Job> findAllJobForHomePage(Pageable pageable);
	
	// Danh sach Cong viec het han theo Doanh nghiep
	@Query("SELECT c FROM Job c WHERE c.enterprise = ?1 AND  c.expiredDate <  DATE(NOW()) AND c.status ='APPROVED' ORDER BY c.createAt DESC")
	List<Job> GetAllExpiredJobByEnterprise(Enterprise enterprise);
	
	// Lấy danh sách 10 công việc mới đăng
	@Query("SELECT c FROM Job c WHERE c.expiredDate >  DATE(NOW()) AND c.status ='APPROVED' ORDER BY c.createAt DESC Limit 5 ")
	List<Job> findTop10ByOrderByCreateAtDesc();

	// Lấy danh sách 10 công việc mới đăng theo Lĩnh vực
//	@Query("SELECT * FROM Job c WHERE c.jobCategory = '?1' AND c.expiredDate >  DATE(NOW()) AND c.status ='APPROVED' ORDER BY c.createAt DESC")
	@Query("SELECT c FROM Job c WHERE c.jobCategory = ?1 AND c.expiredDate >  DATE(NOW()) AND c.status ='APPROVED' ORDER BY c.createAt DESC Limit 10 ")
	List<Job> findTop10ByJobCategoryOrderByCreateAtDesc(JobCategory jobCategory);

	
	
}
