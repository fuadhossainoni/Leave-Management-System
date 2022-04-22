package com.LMS.model;


import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="LeaveRecords")
public class LeaveRecords {
	
	LeaveRecords() {
		
	}

	@Id
	@Column(name="leave_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leave_id;
	
	@Column(name="fromdate")
	private Date fromdate;
	
	@Column(name="todate")
	private Date todate;
	
	@Column(name="leavetype_id")
	private int leavetype_id;
	
	@Column(name="userremarks")
	private String userremarks;
	
	@Column(name="status")
	private status status;
	
	@Column(name="managerremarks")
	private String managerremarks;
	
	//user leave id
	@Column(name="ul_id")
	private int ul_id;

//	//leave type id on leave application
//	@Column(name="ll_id")
//	private int ll_id;
	
	public int getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(int leave_id) {
		this.leave_id = leave_id;
	}

	public int getul_id() {
		return ul_id;
	}

	public void setul_id(int ul_id) {
		this.ul_id = ul_id;
	}


	public LeaveRecords(int ul_id, Date fromdate, Date todate, int leavetype_id, String userremarks) {
		super();
		this.ul_id = ul_id;
		this.fromdate = fromdate;
		this.todate = todate;
		this.leavetype_id = leavetype_id;
		this.userremarks = userremarks;
		this.status = status.Pending;
	}

	public int getLeavetype_id() {
		return leavetype_id;
	}

	public void setLeave_type(int leavetype_id) {
		this.leavetype_id = leavetype_id;
	}

	public String getUserRemarks() {
		return userremarks;
	}

	public void setUserRemarks(String userremarks) {
		this.userremarks = userremarks;
	}
	
	public String getManagerRemarks() {
		return managerremarks;
	}

	public void setManagerRemarks(String managerremarks) {
		this.managerremarks = managerremarks;
	}

	public status getStatus() {
		return status;
	}

	public void setStatus(status status) {
		this.status = status;
	}

	public Date getFromndate() {
		return fromdate;
	}

	public void setFromndate(Date fromndate) {
		this.fromdate = fromndate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	
	
}
