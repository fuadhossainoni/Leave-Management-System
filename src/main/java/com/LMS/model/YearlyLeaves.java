package com.LMS.model;

import java.time.Year;
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
@Table(name="YearlyLeave")
public class YearlyLeaves {
	

	public YearlyLeaves() {
	}

	@Id
	@Column(name="year_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int year_id;
	
	@Column(name="year")
	private int year;
	
	@Column(name="maxday")
	private int maxDay;
	
	@Column(name="leavetype_id")
	private int leavetype_id;
	
//	@Column(name="uy_id")
//	private int uy_id;
	
//	@Column(name="useddays")
//	private int useddays;

	@OneToMany(targetEntity = UsersYearlyLeave.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "yl_id", referencedColumnName = "year_id")
	private List<UsersYearlyLeave> Yearlyleaves;
	
	public int getMaxDay() {
		return maxDay;
	}

	public int getYear_id() {
		return year_id;
	}

	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}

	public YearlyLeaves(int year2, int maxDay, int leavetype_id) {
		super();
		this.year = year2;
		this.maxDay = maxDay;
		this.leavetype_id = leavetype_id;
	}

//	public int getUseddays() {
//		return useddays;
//	}
//
//	public void setUseddays(int useddays) {
//		this.useddays = useddays;
//	}
}
