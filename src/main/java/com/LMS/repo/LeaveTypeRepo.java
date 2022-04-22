package com.LMS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.LMS.model.LeaveType;
import com.LMS.model.Users;

public interface LeaveTypeRepo extends JpaRepository<LeaveType, Integer> {

	@Query("SELECT l FROM LeaveType l WHERE l.name = ?1")
	LeaveType isLeaveTypeValid(String leaveType);
	
	@Query("SELECT l.leavetype_id FROM LeaveType l WHERE l.name = ?1")
	int findLeaveType(String leaveType);
	
}
