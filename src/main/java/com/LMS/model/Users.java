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
@Table(name="Users")
public class Users {

	Users(){
		
	}
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int user_id;
	
	@Column(name="email", unique = true, nullable = false)
	private String email;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="role", nullable = false)
	private String role;
	
	@OneToMany(targetEntity = LeaveRecords.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "ul_id", referencedColumnName = "user_id")
	private List<LeaveRecords> records;
	
	@OneToMany(targetEntity = UsersYearlyLeave.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "uid", referencedColumnName = "user_id")
	private List<UsersYearlyLeave> leaves;
	
//	@Column(name="maxSickLeave")
//	private int maxSickLeave;
//	
//	@Column(name="maxCasualLeave")
//	private int maxcasualLeave;
	
	@Column(name="name")
	private String name;


	public Users(String email, String pass, String role, String name) {
		this.email = email;
		this.password = pass;
		this.role = role;
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

//	public int getMaxSickLeave() {
//		return maxSickLeave;
//	}
//
//	public void setMaxSickLeave(int maxSickLeave) {
//		this.maxSickLeave = maxSickLeave;
//	}
//
//	public int getMaxcasualLeave() {
//		return maxcasualLeave;
//	}
//
//	public void setMaxcasualLeave(int maxcasualLeave) {
//		this.maxcasualLeave = maxcasualLeave;
//	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Users [user_id=" + user_id + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", name=" + name + "]";
	}


	
}
