package com.LMS.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.LMS.model.LeaveRecords;

public interface LeaveRecordRepo extends JpaRepository<LeaveRecords, Integer> {
	
	@Query("SELECT l FROM LeaveRecords l WHERE l.ul_id = ?1")
	List<LeaveRecords> findAllById(int id);
	
	@Query(value="select count(*) from leave_records where (ul_id= ?1 and status=0 and leavetype_id=?2)", nativeQuery=true)
	int approvedLeaveCount(int userId, int type);
	
	@Query(value="select * from leave_records where (ul_id= ?1 and ((fromdate >=?2 and fromdate <=?3) or (todate >=?2 and todate <=?3)));\r\n"
			+ "", nativeQuery=true)
	List<LeaveRecords> findAllinDate(int userId, Date start, Date end);
	
	@Query(value="select * from leave_records where (ul_id= ?1 and leavetype_id=?2)", nativeQuery=true)
	List<LeaveRecords> leavesbytype(int userId, int type);
	
	@Query(value="select * from leave_records where (ul_id= ?1 and status=?2)", nativeQuery=true)
	List<LeaveRecords> leavesbystatus(int userId, int status);
	
	@Query(value="select * from leave_records where status=2", nativeQuery=true)
	List<LeaveRecords> pendingLeaves();
	
	@Query(value="select * from leave_records where leave_id= ?1", nativeQuery=true)
	LeaveRecords findByLeaveId(int leave_id);
	
	//
	@Query(value="select count(*) from leave_records where (user_id= ?1 and status='Approved'  and leave_type='Casual')", nativeQuery=true)
	int approvedCasualLeaveCount(int userId);
	
	
}
