package com.LMS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.LMS.model.Users;
import com.LMS.model.UsersYearlyLeave;

public interface UsersYearlyLeaveRepo extends JpaRepository<UsersYearlyLeave, Integer> {

	@Query("SELECT u FROM UsersYearlyLeave u WHERE u.uid = ?1 and u.yl_id = ?2")
	UsersYearlyLeave findByuidyid(int uid, int year_id);
	
	@Query(value="SELECT * FROM users_yearly_leave WHERE (uid=?1 and yl_id = ?2)", nativeQuery=true)
	int usedleaves(int user_id,int year_id);
	
	@Query(value="SELECT usedday FROM users_yearly_leave WHERE (uid=?1 and yl_id = ?2)", nativeQuery=true)
	int balance(int user_id,int year_id);
	
}
