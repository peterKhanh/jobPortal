package com.lifesupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifesupport.models.User;
import com.lifesupport.models.UserRole;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserName(String userName);
	User findTop1ByEmail(String email);

	
	// Search User by Name
	@Query("SELECT c FROM User c WHERE c.fullName LIKE %?1%")
	List<User> SearchUser(String keyword);

	// Search SystemAdmin and UserAdmin by Name
	@Query(value = "SELECT u.id, u.fullname, u.userName, u.address , u.email, u.password , u.telephone , u.gender , u.enabled, u.avata   from users u , users_roles ur WHERE u.id = ur.user_id AND (ur.role_id = ?1 or ur.role_id = ?2) AND  u.fullName LIKE %?1%",  nativeQuery = true)
	List<User> SearchSystemUserAndUserAdmin(String keyword, Long roleIdSystemAdmin, Long roleIdUserAdmin);


	// Search Candidate
	@Query(value = "SELECT u.id, u.fullname, u.userName, u.address , u.email, u.password , u.telephone , u.gender , u.enabled, u.avata   from users u , users_roles ur WHERE u.id = ur.user_id AND (ur.role_id = ?1) AND  u.fullName LIKE %?1%",  nativeQuery = true)
	List<User> SearchCandidate(String keyword, Long roleIdCandidate);
	

	// Xem danh sach User với vai trò là SystemUser or Admin User
	@Query(value = "SELECT u.id, u.fullname, u.userName, u.address , u.email, u.password , u.telephone , u.gender , u.enabled, u.avata   from users u , users_roles ur WHERE u.id = ur.user_id AND (ur.role_id = ?1 or ur.role_id = ?2)",  nativeQuery = true)
	Page<User> findAdminUserAndSystemAdmin(Pageable pageable, Long roleIdSystemAdmin, Long roleIdUserAdmin);
	
	
	// Xem danh sach User theo nhom quyen
	@Query(value = "SELECT u.id, u.fullname, u.userName, u.address , u.email, u.password , u.telephone , u.gender , u.enabled, u.avata   from users u , users_roles ur WHERE u.id = ur.user_id AND ur.role_id = ?1",  nativeQuery = true)
	Page<User> findByUserRoles(Pageable pageable, Long roleId );

	
	// Lay danh sach User moi ung tuyn
	@Query(value = "SELECT u.id, u.fullname, u.userName, u.address , u.avata, u.email, u.password , u.telephone , u.gender , u.enabled  from users u , job j, jobapply ja WHERE u.id = ja.user_id AND j.id = ja.job_id ORDER BY ja.apply_date DESC"
			+ "",  nativeQuery = true)
	Page<User> findRecentApplyCandidate(Pageable pageable);

	// Lay danh sach ứng viên đã ứng tuyển vào 01 công ty
	@Query(value = "SELECT u.id, u.fullname, u.userName, u.address , u.avata, u.email, u.password , u.telephone , u.gender , u.enabled  from users u , job j, jobapply ja WHERE u.id = ja.user_id AND j.id = ja.job_id AND j.enterprise_id = ?1 ORDER BY ja.apply_date DESC"
			+ "",  nativeQuery = true)
	Page<User> GetAllCandidateApplyToEnterprise(Pageable pageable, Long enterpriseId);


}
