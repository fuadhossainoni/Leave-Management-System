package com.LMS.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="LeaveType")
public class LeaveType {
	LeaveType(){
		
	}
	
	@Id
	@Column(name="leavetype_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leavetype_id;
	
	@Column(name="name")
	private String name;

	@Column(name="remark")
	private String remark;
	
	public LeaveType(String name, String remark) {
		super();
		this.name = name;
		this.remark = remark;
	}

	@OneToMany(targetEntity = LeaveRecords.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "leavetype_id", referencedColumnName = "leavetype_id")
	private List<LeaveRecords> leaveRecords;
	
	@OneToMany(targetEntity = YearlyLeaves.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "leavetype_id", referencedColumnName = "leavetype_id")
	private List<YearlyLeaves> yearlyLeaves;
	

	public int getLeavetype_id() {
		return leavetype_id;
	}

	public void setLeavetype_id(int leavetype_id) {
		this.leavetype_id = leavetype_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
