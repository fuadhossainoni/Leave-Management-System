package com.LMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="UsersYearlyLeave")
public class UsersYearlyLeave {
	@Id
	@Column(name="uyl_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int user_id;
	
	public UsersYearlyLeave() {
	}

	@Column(name="uid")
	private int uid;
	
	@Column(name="yl_id")
	private int yl_id;
	
	@Column(name="usedday")
	private int usedday;

	public int getUsedday() {
		return usedday;
	}

	public void setUsedday(int usedday) {
		this.usedday = usedday;
	}

	public UsersYearlyLeave(int uid, int yl_id, int usedday) {
		super();
		this.uid = uid;
		this.yl_id = yl_id;
		this.usedday = usedday;
	}

	@Override
	public String toString() {
		return "UsersYearlyLeave [user_id=" + user_id + ", uid=" + uid + ", yl_id=" + yl_id + ", usedday=" + usedday
				+ "]";
	}
	
	
}
