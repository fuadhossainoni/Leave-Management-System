package com.LMS.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.LMS.model.LeaveRecords;
import com.LMS.model.Users;
import com.LMS.model.YearlyLeaves;

public interface YearlyLeavesRepo extends JpaRepository<YearlyLeaves, Integer> {

//	@Query(value="SELECT maxday FROM yearly_leave WHERE (leavetype_id = ?1 and year = YEAR(CURDATE()))", nativeQuery=true)
//	int findmaxById(int id);
	
	@Query(value="SELECT maxday FROM yearly_leave WHERE (leavetype_id = ?1 and year = YEAR(CURDATE()))", nativeQuery=true)
	int maxleaves(int leave_id);
	
//	@Query(value="SELECT useddays FROM yearly_leave WHERE (uy_id=?1 and leavetype_id = ?2 and year = YEAR(CURDATE()))", nativeQuery=true)
//	int usedleaves(int user_id,int leave_id);
	
//	SELECT l FROM YearlyLeaves l WHERE l.uy_id = ?1 and l.leavetype_id = ?2 and year = YEAR(CURDATE()))
	@Query(value="SELECT * FROM yearly_leave WHERE (uy_id=?1 and leavetype_id = ?2 and year = DATE_FORMAT(NOW(), '%Y'))", nativeQuery=true)
	YearlyLeaves findYearlyleaves(int user_id,int leave_id);
	
//	SELECT DATEDIFF(day, '2017/08/25', '2017/08/26') AS DateDiff;
	@Query(value="SELECT DATEDIFF(?2, ?1)", nativeQuery=true)
	int datediff(Date start, Date end);
	
	@Query("SELECT u FROM YearlyLeaves u WHERE u.leavetype_id = ?1")
	YearlyLeaves findByleavetypeid(int leavetypeid);
	
}
