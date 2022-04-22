package com.LMS.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LMS.model.LeaveRecords;
import com.LMS.model.LeaveType;
import com.LMS.model.Users;
import com.LMS.model.UsersYearlyLeave;
import com.LMS.model.YearlyLeaves;
import com.LMS.model.status;
import com.LMS.repo.LeaveRecordRepo;
import com.LMS.repo.LeaveTypeRepo;
import com.LMS.repo.UserRepo;
import com.LMS.repo.UsersYearlyLeaveRepo;
import com.LMS.repo.YearlyLeavesRepo;

@RestController
public class LMSRestController {

	
//	SessionListenerWithMetrics session;
	int check=0;
	@Autowired
	LeaveRecordRepo leaveRecordRepo;
	
//	@Autowrired
//	Users 
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	LeaveTypeRepo leaveTypeRepo;
	
	@Autowired
	YearlyLeavesRepo yearlyLeavesRepo;
	
	@Autowired
	UsersYearlyLeaveRepo usersYearlyLeaveRepo;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@GetMapping("/")
	public String index() {
		
		String s ="<br><center><h1>Application is running but it is only back end.</center></h1>"
				+ "<h3><u>ALL:</u></h3>"
				+ "Login via Basic Auth\r\n<br>"
				+ "@PutMapping(\"/change_pass/{pass}\") to change pass.\r\n<br>"
				+ "@RequestMapping(\"/logout\") to sign out.\r\n"
				+ "\r\n"
				+ "<h3><u>User:</u></h3>"
				+ "@PostMapping(\"/new_leave/{fromdate}/{todate}/{type}/{remarks}\") for creating and sending/requesting new leave application. Date format YYYY-MM-DD and type is string (e.g. Sick,Casual) and has to be valid (listed by admin) for creating/sending. User wont be able to submit a request if the leave balance+requested days for leave is more than allocated for that type of leave.\r\n<br>"
				+ "@PostMapping(\"/new_leave/{date}//{type}/{remarks}\") for creating and sending/requesting new leave application for single day.\r\n<br>"
				+ "@GetMapping(\"/get_balance/{leaveType}\") to showing leave balance of user. leavetype is string (e.g. Sick,Casual) and has to be valid (listed by admin) for getting proper result. -99 means the user has not taken any leave of that type. This leave balance is yearly.\r\n<br>"
				+ "@GetMapping(\"/my_leaves\") to show all leaves.\r\n<br>"
				+ "@GetMapping(\"/my_leaves/date/{fromdate}/{todate}\") to filter own leaves by date, date format YYYY-MM-DD.\r\n<br>"
				+ "@GetMapping(\"/my_leaves/type/{leaveType}\") to filter own leaves by type (e.g. Sick,Casual) and has to be valid (listed by admin).\r\n<br>"
				+ "@GetMapping(\"/my_leaves/status/{status}\") to filter own leaves by status (Pending, Approved or Rejected).\r\n"
				+ "\r\n"
				+ "<h3><u>Manager:</u></h3>"
				+ "All from Users+\r\n<br>"
				+ "@GetMapping(\"/pending_leaves\") to show a list of pending approval leaves.\r\n<br>"
				+ "@PostMapping(\"/approve/{leaveId}\") to approve a leave, with specific leave id.\r\n"
				+ "@PostMapping(\"/approve/{leaveId}/{remark}\") to approve a leave and put a remark, with specific leave id.\r\n<br>"
				+ "@PostMapping(\"/reject/{leaveId}\") to reject a leave, with specific leave id.\r\n<br>"
				+ "@PostMapping(\"/reject/{leaveId}/{remark}\") to reject a leave and put a remark, with specific leave id.\r\n<br>"
				+ "@GetMapping(\"/show_balance/{id}/{leavetype}\") to show leave balance of the User. id here is the user id. leavetype is string (e.g. Sick,Casual) and has to be valid (listed by admin) for getting proper results.\r\n<br>"
				+ "\r\n"
				+ "<h3><u>Admin:</u></h3>"
				+ "All from Users +\r\n<br>"
				+ "@PostMapping(\"/createLeaveType/{name}/{remark}\") to create a leave type.\r\n<br>"
				+ "@PostMapping(\"/setYearly_leave/{leaveType}/{year}/{maxday}\") to allocate yearly leave for each types. leaveType is string here and refers to the name during creating leave type. year is simply 4 digit number e.g. 2022. maxday is the maximum allocated leave days for each type of leaves in that year.\r\n<br>"
				+ "@PostMapping(\"/adduser/{email}/{pass}/{name}\") to register a new User\r\n<br>"
				+ "@PostMapping(\"/addmanager/{email}/{pass}/{name}\")) to register a new Manager";
		
//		String s = "<br><center><h1>Application is running but it is only back end.</center></h1>"
//				+ "<h3><u>ALL:</u></h3>"
//				+ "Use Basic Auth on postman for login. <br>"
//				+ "@PutMapping(\"/change_pass/{pass}\") while logged in to change pass. <br>"
//				+ "@GettMapping(\"/logout\") to log out."
//				+ "<h3><u>User:</u></h3>"
//				+ "Date format is YYYY-MM-DD. <br>"
//				+ "@PostMapping(\"/new_leave/{fromdate}/{todate}/{type}/{remarks}\") for creating and sending/requesting new leave application.<br>"
//			//	+ "@GetMapping(\"/my_CasualLeaves\") to get history of all casual leaves and remaingin casual leave balance. <br>"
//			//	+ "@GetMapping(\"/my_SickLeaves\") to get history of all sick leaves and remaingin sick leave balance. <br>"
//				+ "@GetMapping(\"/my_leaves\") to show all leaves with status.<br>"
//				+ "@GetMapping(\"/my_leavesByDate/{date1}/{date2}\") to filter own leaves by date, date format YYYY-MM-DD.<br>"
//				+ "@GetMapping(\"/my_leavesByStatus/{status}\") to filter own leaves by status (Pending, Approved or Rejected)."
//				+ "<h3><u>Manager:</u></h3>"
//				+ "All from Users+<br>"
//				+ "Leave types are (Pending or Approved or Rejected)<br>"
//				+ "@GetMapping(\"/pending_leaves\") to show a list of pending approval leaves.<br>"
//				+ "@PutMapping(\"/approve/{leaveId}\") to approve a leave, with specific leave id.<br>"
//				+ "@PutMapping(\"/reject/{leaveId}\") to reject a leave, with specific leave id.<br>"
//				+ "@PutMapping(\"/remark/{leaveId}/{remark}\") to edit the remark of user and put own remark/feedback."
//				+ "<h3><u>Admin:</u></h3>"
//				+ "All from Users +<br>"
//				+ "@PutMapping(\"/setMaxSickLeave/{id}/{days}\") to set maximum sick leave of an User.<br>"
//				+ "@PutMapping(\"/setMaxCasualLeave/{id}/{days}\") to set maximum casual leave of an User.<br>"
//				+ "@PostMapping(\"/addadmin/{email}/{pass}\") to register a new Admin<br>"
//				+ "@PostMapping(\"/adduser/{email}/{pass}\") to register a new User<br>"
//				+ "@PostMapping(\"/addmanager/{email}/{pass}\") to register a new Manager<br>";
		
		return s;
	}
	
	@PutMapping("/change_pass/{pass}")
	public String change_pass(@PathVariable String pass) {
		try {
//			if(session.getValue("sessionRole").equals("USER")||session.getValue("sessionRole").equals("MANAGER")||session.getValue("sessionRole").equals("ADMIN")) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			temp.setPassword(passwordEncoder.encode(pass));
			userRepo.save(temp);
			return "Password changed.";
			}
			catch(Exception e) {
				System.out.println(e);
			}
		return "Password change e exception.";
	}
	
	//has to be commented
//	@GetMapping("/check")
//	public Users check() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String email = auth.getName();
//		Users temp = userRepo.findByEmail(email);
//		return temp;
//	}
	
	
//	@GetMapping("/all_users")
//	public List<Users> all_users() {
//		return userRepo.findAll();
//	}
	
//	@GetMapping("/check_leave/{type}")
//	public void check_leave(@PathVariable String type) {
//		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			String email = auth.getName();
//			Users temp = userRepo.findByEmail(email);
//			int id = temp.getUser_id();
//			System.out.println(leaveTypeRepo.isLeaveTypeValid(type));
////			if(leaveTypeRepo.isLeaveTypeValid(type))
//			}
//			catch(Exception e) {
//			  System.out.println(e);
//			}
//	}
	
	@PostMapping("/new_leave/{date}/{type}/{remarks}")
	public String new_leave(@PathVariable Date date, @PathVariable String type, @PathVariable String remarks) {
		try {
			Date fromdate = date;
			Date todate = date;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			int id = temp.getUser_id();
			LeaveType leaveType = leaveTypeRepo.isLeaveTypeValid(type);
			int leave_id = leaveTypeRepo.findLeaveType(type);
			if(leaveType==null) {
				return "Leave type thik na.";
			}
			else if(leaveType!=null) {
				System.out.println("leave type thik ase");
				YearlyLeaves tempyleave = yearlyLeavesRepo.findByleavetypeid(leave_id);
				int yearlyleavpk = tempyleave.getYear_id();
//				System.out.println(usersYearlyLeaveRepo.findByuidyid(id, leave_id));
				if(usersYearlyLeaveRepo.findByuidyid(id, yearlyleavpk)!=null){
					System.out.println("Null na.");
					if(usersYearlyLeaveRepo.balance(id, yearlyleavpk)+yearlyLeavesRepo.datediff(fromdate, todate)<=yearlyLeavesRepo.maxleaves(leave_id)) {
						LeaveRecords leave = new LeaveRecords(id,fromdate,todate,leave_id,remarks);
						leaveRecordRepo.save(leave);
						return "New Leave Requested.";
					}
					else {
						return "No leaves left.";
					}
				}
				else {
//					System.out.println(yearlyLeavesRepo.maxleaves(leave_id));
					if(yearlyLeavesRepo.datediff(fromdate, todate) <= yearlyLeavesRepo.maxleaves(leave_id)) {
						LeaveRecords leave = new LeaveRecords(id,fromdate,todate,leave_id,remarks);
						leaveRecordRepo.save(leave);
						return "New Leave Requested.";
					}
				}
				
			}
//			System.out.println(leave);;
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		return "Leave Request Exception.";
	}
	
	@PostMapping("/new_leave/{fromdate}/{todate}/{type}/{remarks}")
	public String new_leave(@PathVariable Date fromdate, @PathVariable Date todate, @PathVariable String type, @PathVariable String remarks) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			int id = temp.getUser_id();
			LeaveType leaveType = leaveTypeRepo.isLeaveTypeValid(type);
			int leave_id = leaveTypeRepo.findLeaveType(type);
			if(leaveType==null) {
				return "Leave type thik na.";
			}
			else if(leaveType!=null) {
				System.out.println("leave type thik ase");
				YearlyLeaves tempyleave = yearlyLeavesRepo.findByleavetypeid(leave_id);
				int yearlyleavpk = tempyleave.getYear_id();
//				System.out.println(usersYearlyLeaveRepo.findByuidyid(id, leave_id));
				if(usersYearlyLeaveRepo.findByuidyid(id, yearlyleavpk)!=null){
					System.out.println("Null na.");
					if(usersYearlyLeaveRepo.balance(id, yearlyleavpk)+yearlyLeavesRepo.datediff(fromdate, todate)<=yearlyLeavesRepo.maxleaves(leave_id)) {
						LeaveRecords leave = new LeaveRecords(id,fromdate,todate,leave_id,remarks);
						leaveRecordRepo.save(leave);
						return "New Leave Requested.";
					}
					else {
						return "No leaves left.";
					}
				}
				else {
//					System.out.println(yearlyLeavesRepo.maxleaves(leave_id));
					if(yearlyLeavesRepo.datediff(fromdate, todate) <= yearlyLeavesRepo.maxleaves(leave_id)) {
						LeaveRecords leave = new LeaveRecords(id,fromdate,todate,leave_id,remarks);
						leaveRecordRepo.save(leave);
						return "New Leave Requested.";
					}
				}
				
			}
//			System.out.println(leave);;
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		return "Leave Request Exception.";
	}
	
	@GetMapping("/pending_leaves")
	public List<LeaveRecords> pending_leaves() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			if(temp.getRole().equals("MANAGER")) {
				return leaveRecordRepo.pendingLeaves();
				}
		}
			catch(Exception e) {
				System.out.println(e);
			}
		List<LeaveRecords> myList = new ArrayList();
		 return myList;
	}
	
	@PostMapping("/approve/{leaveId}")
	public String approve(@PathVariable int leaveId) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			if(temp.getRole().equals("MANAGER")) {
				LeaveRecords tempLeave = leaveRecordRepo.findByLeaveId(leaveId);
				if(tempLeave.getStatus().equals(status.Approved)) {
					return "Already approved.";
				}
				int leavetypeid = tempLeave.getLeavetype_id();
//				int uid = tempLeave.getul_id();
				tempLeave.setStatus(status.Approved);
				
//				YearlyLeaves tempyearlyleave = yearlyLeavesRepo.findByleavetypeid(leavetypeid);
//				int ylid = tempyearlyleave.getYear_id();
//				System.out.println(tempLeave.getul_id());
//				System.out.println(ylid);
//				if(usersYearlyLeaveRepo.findByuidyid(tempLeave.getul_id(), ylid)==null) {
//					UsersYearlyLeave temp1 = new UsersYearlyLeave(tempLeave.getul_id(), ylid,0);
//					usersYearlyLeaveRepo.save(temp1);
//				}
//				int newuseddays = usersYearlyLeaveRepo.usedleaves(tempLeave.getul_id(), leavetypeid)+yearlyLeavesRepo.datediff(tempLeave.getFromndate(), tempLeave.getTodate());
//				System.out.println(newuseddays);
				
//				YearlyLeaves tempyearlyleave = yearlyLeavesRepo.findYearlyleaves(tempLeave.getul_id(), leavetypeid);
//				tempyearlyleave.setUseddays(newuseddays);
//				yearlyLeavesRepo.save(tempyearlyleave);
				
				YearlyLeaves tempyleave = yearlyLeavesRepo.findByleavetypeid(leavetypeid);
				int yearlyleavpk = tempyleave.getYear_id();
				System.out.println(tempLeave.getul_id());
				System.out.println(yearlyleavpk);
				if(usersYearlyLeaveRepo.findByuidyid(tempLeave.getul_id(), yearlyleavpk)!=null && usersYearlyLeaveRepo.balance(tempLeave.getul_id(), yearlyleavpk)>=0) {
					UsersYearlyLeave tempUsersYearlyLeave = usersYearlyLeaveRepo.findByuidyid(tempLeave.getul_id(), yearlyleavpk);
					System.out.println(tempUsersYearlyLeave);
					int newuseddays = usersYearlyLeaveRepo.balance(tempLeave.getul_id(), yearlyleavpk)+yearlyLeavesRepo.datediff(tempLeave.getFromndate(), tempLeave.getTodate())+1;
					System.out.println(newuseddays);
					tempUsersYearlyLeave.setUsedday(newuseddays);
					usersYearlyLeaveRepo.save(tempUsersYearlyLeave);
					leaveRecordRepo.save(tempLeave);
				}
				else {
					usersYearlyLeaveRepo.save(new UsersYearlyLeave(tempLeave.getul_id(), yearlyleavpk,yearlyLeavesRepo.datediff(tempLeave.getFromndate(), tempLeave.getTodate())+1));
					leaveRecordRepo.save(tempLeave);
				}
				
				
				return tempLeave+"<br><b>Approved.</b>";
				}
				else {
					return "Manager not logged in.";
				}
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		return "Leave Approval e exception";
	}
	
	@PostMapping("/approve/{leaveId}/{remark}")
	public String approve(@PathVariable int leaveId,@PathVariable String remark) {
		try {
			int newuseddays=1;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			if(temp.getRole().equals("MANAGER")) {
				LeaveRecords tempLeave = leaveRecordRepo.findByLeaveId(leaveId);
				if(tempLeave.getStatus().equals(status.Approved)) {
					return "Already approved.";
				}
				int leavetypeid = tempLeave.getLeavetype_id();
				tempLeave.setStatus(status.Approved);
				tempLeave.setManagerRemarks(remark);
				YearlyLeaves tempyleave = yearlyLeavesRepo.findByleavetypeid(leavetypeid);
				int yearlyleavpk = tempyleave.getYear_id();
				System.out.println(tempLeave.getul_id());
				System.out.println(yearlyleavpk);
				if(usersYearlyLeaveRepo.findByuidyid(tempLeave.getul_id(), yearlyleavpk)!=null && usersYearlyLeaveRepo.balance(tempLeave.getul_id(), yearlyleavpk)>=0) {
					UsersYearlyLeave tempUsersYearlyLeave = usersYearlyLeaveRepo.findByuidyid(tempLeave.getul_id(), yearlyleavpk);
					System.out.println(tempUsersYearlyLeave);
					newuseddays = usersYearlyLeaveRepo.balance(tempLeave.getul_id(), yearlyleavpk)+yearlyLeavesRepo.datediff(tempLeave.getFromndate(), tempLeave.getTodate());
					newuseddays++;
					System.out.println(newuseddays);
					tempUsersYearlyLeave.setUsedday(newuseddays);
					usersYearlyLeaveRepo.save(tempUsersYearlyLeave);
					leaveRecordRepo.save(tempLeave);
				}
				else {
					int useddays=yearlyLeavesRepo.datediff(tempLeave.getFromndate(), tempLeave.getTodate());
					useddays++;
					usersYearlyLeaveRepo.save(new UsersYearlyLeave(tempLeave.getul_id(), yearlyleavpk,useddays));
					leaveRecordRepo.save(tempLeave);
				}
				
				
				return tempLeave+"<br><b>Approved.</b>";
				}
				else {
					return "Manager not logged in.";
				}
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		return "Leave Approval e exception";
	}
	
	@PostMapping("/reject/{leaveId}/{remark}")
	public String reject(@PathVariable int leaveId,@PathVariable String remark) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			if(temp.getRole().equals("MANAGER")) {
				LeaveRecords tempLeave = leaveRecordRepo.findByLeaveId(leaveId);
				tempLeave.setStatus(status.Rejected);
				tempLeave.setManagerRemarks(remark);
				leaveRecordRepo.save(tempLeave);
				return tempLeave+"<br><b>Rejected.</b>";
				}
				else {
					return "Manager not logged in.";
				}
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		return "Leave Rejection e exception.";
	}
	
	@PostMapping("/reject/{leaveId}")
	public String reject(@PathVariable int leaveId) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			if(temp.getRole().equals("MANAGER")) {
				LeaveRecords tempLeave = leaveRecordRepo.findByLeaveId(leaveId);
				tempLeave.setStatus(status.Rejected);
				leaveRecordRepo.save(tempLeave);
				return tempLeave+"<br><b>Rejected.</b>";
				}
				else {
					return "Manager not logged in.";
				}
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		return "Leave Rejection e exception.";
	}
	
//	@PutMapping("/remark/{leaveId}/{remark}")
//	public String remark(@PathVariable int leaveId, @PathVariable String remark) {
//		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			String email = auth.getName();
//			Users temp = userRepo.findByEmail(email);
//			if(temp.getRole().equals("MANAGER")) {
//				LeaveRecords tempLeave = leaveRecordRepo.findByLeaveId(leaveId);
//				tempLeave.setRemarks(remark);
//				leaveRecordRepo.save(tempLeave);
//				return tempLeave+"<br><b>Remark changed to: "+remark+"</b>";
//				}
//				else {
//					return "Manager not logged in.";
//				}
//			}
//			catch(Exception e) {
//			  return "Remarking e exception.";
//			}
//	}
	
//	@GetMapping("/all_leaves")
//	public String all_leaves() {
//		if(user!=null) {
//		return leaveRecordRepo.findAll().toString();
//		}
//		else {
//			return "User not logged in.";
//		}
//	}
	
	@GetMapping("/show_balance/{id}/{leavetype}")
	public int get_balance(@PathVariable int id,@PathVariable String leavetype) {
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email1 = auth.getName();
			Users temp1 = userRepo.findByEmail(email1);
			if(temp1.getRole().equals("MANAGER")) {
				int leavetype_id = leaveTypeRepo.findLeaveType(leavetype);
				YearlyLeaves temp = yearlyLeavesRepo.findByleavetypeid(leavetype_id);
				int leavetype_id1 = temp.getYear_id();
//				System.out.println(email);
				System.out.println(yearlyLeavesRepo.maxleaves(leavetype_id));	//max
				System.out.println(usersYearlyLeaveRepo.balance(id, leavetype_id1));
				if(yearlyLeavesRepo.maxleaves(leavetype_id)>0 && usersYearlyLeaveRepo.balance(id, leavetype_id1)>0) {
					return yearlyLeavesRepo.maxleaves(leavetype_id)-usersYearlyLeaveRepo.balance(id, leavetype_id1);
				}
			}
				else {
					return -99;
				}
			}
			catch (Exception E){
				System.out.println(E);
		}
		return -99;
	}
	
	
	@GetMapping("/get_balance/{leaveType}")
	public int get_balance(@PathVariable String leaveType) {
		try {
			int leavetype_id = leaveTypeRepo.findLeaveType(leaveType);
			YearlyLeaves temp1 = yearlyLeavesRepo.findByleavetypeid(leavetype_id);
			int leavetype_id1 = temp1.getYear_id();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
//			System.out.println(email);
			Users temp = userRepo.findByEmail(email);
			int id = temp.getUser_id();
			System.out.println(id);
			System.out.println(leavetype_id1);
			System.out.println(yearlyLeavesRepo.maxleaves(leavetype_id));	//max
			System.out.println(usersYearlyLeaveRepo.balance(id, leavetype_id1));
			if(yearlyLeavesRepo.maxleaves(leavetype_id)>0 && usersYearlyLeaveRepo.balance(id, leavetype_id1)>0) {
				return yearlyLeavesRepo.maxleaves(leavetype_id)-usersYearlyLeaveRepo.balance(id, leavetype_id1);
			}
			
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		return -99;
	}
	
	@GetMapping("/my_leaves")
	public List<LeaveRecords> my_leaves() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users user = userRepo.findByEmail(email);
			int userid = user.getUser_id();
				return (leaveRecordRepo.findAllById(userid));
			}
			catch(Exception e) {
				System.out.println(e);
			}
		List<LeaveRecords> myList = new ArrayList();
		 return myList;
	}
	
	@GetMapping("/my_leaves/date/{fromdate}/{todate}")
	public List<LeaveRecords> my_leaves(@PathVariable Date fromdate, @PathVariable Date todate) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users user = userRepo.findByEmail(email);
			int userid = user.getUser_id();
				return (leaveRecordRepo.findAllinDate(userid,fromdate,todate));
			}
			catch(Exception e) {
				System.out.println(e);
			}
		List<LeaveRecords> myList = new ArrayList();
		 return myList;
	}
	
	@GetMapping("/my_leaves/type/{leaveType}")
	public List<LeaveRecords> my_leavesbytpe(@PathVariable String leaveType) {
		try {
			int leave_id = leaveTypeRepo.findLeaveType(leaveType);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			int id = temp.getUser_id();
			return leaveRecordRepo.leavesbytype(id, leave_id);
			}
			catch(Exception e) {
			  System.out.println(e);
			}
		List<LeaveRecords> myList = new ArrayList();
		 return myList;
	}
	
	@GetMapping("/my_leaves/status/{status}")
	public List<LeaveRecords> my_leaves(@PathVariable String status) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users user = userRepo.findByEmail(email);
			int userid = user.getUser_id();
			if(status.equals("Approved")) {
				return leaveRecordRepo.leavesbystatus(userid, 0);
			}
			else if(status.equals("Rejected")) {
				return leaveRecordRepo.leavesbystatus(userid, 1);
			}
			else if(status.equals("Pending")) {
				return leaveRecordRepo.leavesbystatus(userid, 2);
			}
			}
			catch(Exception e) {
				System.out.println(e);
			}
		List<LeaveRecords> myList = new ArrayList();
		 return myList;
	}
	
//	@GetMapping("/my_SickLeaves")
//	public String my_SickLeaves() {
////			try {
////				if(session.getValue("sessionRole").equals("USER")||session.getValue("sessionRole").equals("MANAGER")||session.getValue("sessionRole").equals("ADMIN")) {
//		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			String email = auth.getName();
//			Users user = userRepo.findByEmail(email);			
//					int sickBalance=user.getMaxSickLeave() - leaveRecordRepo.approvedSickLeaveCount(user.getUser_id());
//					return (leaveRecordRepo.findAllByType(user.getUser_id(), "Sick").toString()+"<br><h1>Sick Leave Balance = "+sickBalance+"</h1>");
//				}
//			catch(Exception e) {
//				  System.out.println(e);
//				}
//		return "My sick leaves e exception.";
//	}
//	
//	@GetMapping("/my_CasualLeaves")
//	public String my_CasualLeaves() {
////			try {
////				if(session.getValue("sessionRole").equals("USER")||session.getValue("sessionRole").equals("MANAGER")||session.getValue("sessionRole").equals("ADMIN")) {
////					Users user = userRepo.findByid((int) session.getAttribute("user_id"));
//		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			String email = auth.getName();
//			Users user = userRepo.findByEmail(email);
//					int casualBalance=user.getMaxcasualLeave() - leaveRecordRepo.approvedCasualLeaveCount(user.getUser_id());
//					return (leaveRecordRepo.findAllByType(user.getUser_id(), "Casual").toString()+"<br><h1>Casual Leave Balance = "+casualBalance+"</h1>");
//				}
//				catch(Exception e) {
//				  System.out.println(e);
//				}
//		return "My casual leaves e exception.";
//	}
	
//	@GetMapping("/my_leavesByDate/{date1}/{date2}")
//	public String my_leavesByDate(@PathVariable Date date1,@PathVariable Date date2) {
//		
//		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			String email = auth.getName();
//			Users temp = userRepo.findByEmail(email);
//				return leaveRecordRepo.findAllinDate(temp.getUser_id(),date1, date2).toString();
//		}
//				catch(Exception e) {
//				  System.out.println(e);
//				}
//		return "My leaves by date e exception.";
//	}
		
//	@GetMapping("/my_leavesByType/{type}")
//	public String my_leavesByType(@PathVariable String type) {
//		if(user!=null) {
//			try {
//				return leaveRecordRepo.findAllByType(user.getUser_id(),type).toString();
//				}
//				catch(Exception e) {
//				  System.out.println(e);
//				  return "my leaves by type e error";
//				}
//			}
//		else {
//			return "No User Logged In";
//		}
//	}
	
//	@GetMapping("/my_leavesByStatus/{status}")
//	public String my_leavesByStatus(@PathVariable String status) {
//		try {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String email = auth.getName();
//		Users temp = userRepo.findByEmail(email);
//				return leaveRecordRepo.findAllByStatus(temp.getUser_id(), status).toString();
//			}
//				catch(Exception e) {
//				  System.out.println(e);
//				}
//			return "My leaves by status e exception.";
//	}
	
//	@RequestMapping("/auth/{email}/{pass}")
//	public String auth(@PathVariable String email, @PathVariable String pass, HttpServletRequest request, HttpServletResponse response) {
//		
//		try {
//    		Users temp = userRepo.findByEmail(email);
//    		String hashedpass = temp.getPass();
//    		if(pwd.matches(pass, hashedpass)) {
//    			session = request.getSession();
//    			session.setAttribute("sessionRole", temp.getRole());
//    			session.setAttribute("user_id", temp.getUser_id());
//    			return "Logged in.<br>Role = "+session.getAttribute("sessionRole");
//    			}
//    		else {
//    			return "Login Error.";
//    		}
//   			}
//    		catch(Exception e) {
//    		  System.out.println(e);
//    		}
//    	return "Authorization e exception.";
//    }
	
//	@RequestMapping("/signout")
//	public String signout(HttpServletRequest request, HttpServletResponse response) {
//		try{
////			session = request.getSession(false);
//			if(session == null){
//				return "No user logged in.";
//			}
//			else {
//				session.invalidate();
//				session = null;
//				return "Signed Out";
//			}
//		}
//		catch (Exception E){
//			System.out.println(E);
//			return "Sign out e exception";
//		}
//	}
	
	
//	@GetMapping("/check")
//	public String check() {
//		try{
//			if(session == null){
//				return "Session nai";
//			}
//			else {
//				return ("Session ase, id: "+session.getId());
//			}
//		}
//		catch (Exception E){
//			System.out.println(E);
//			return "Session nai from exception";
//		}
//	}
	
//	@PutMapping("/setMaxSickLeave/{id}/{days}")
//	public String setMaxSickLeave(@PathVariable int id, @PathVariable int days) {
//		
//		try{
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			String email1 = auth.getName();
//			Users temp1 = userRepo.findByEmail(email1);
//			if(temp1.getRole().equals("ADMIN")) {
//				Users temp = (Users) userRepo.findByid(id);
//				temp.setMaxSickLeave(days);
//				userRepo.save(temp);
//				return ("Max Sick Leave for \""+temp.getName()+"\" is set to "+days+" days.");
//				}
//				else {
//					return "Admin chara ar keu max sick leave set korte parbe na.";
//				}
//			}
//			catch (Exception E){
//				System.out.println(E);
//			}
//			return "Sick leave set e exception";
//	}
	
	@PostMapping("/createLeaveType/{name}/{remark}")
	public String createLeaveLeave(@PathVariable String name, @PathVariable String remark) {
		
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email1 = auth.getName();
			Users temp1 = userRepo.findByEmail(email1);
			if(temp1.getRole().equals("ADMIN")) {
				leaveTypeRepo.save(new LeaveType(name,remark));
				return "New leave type added.";
					}
				else {
					return "Admin chara ar keu leave type add korte parbe na.";
				}
			}
			catch (Exception E){
				System.out.println(E);
		}
		return "Leave type create e exception.";
	}
	
	@PostMapping("/setYearly_leave/{leaveType}/{year}/{maxday}")
	public String setYearlyLeave(@PathVariable String leaveType,@PathVariable int year, @PathVariable int maxday) {
		
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email1 = auth.getName();
			Users temp1 = userRepo.findByEmail(email1);
			if(temp1.getRole().equals("ADMIN")) {
				if(leaveTypeRepo.isLeaveTypeValid(leaveType)==null) {
					return "Wrong Leave Type.";
				}
//				Year iyear = Year.of(year);
//				YearlyLeaves tempyearlyleaves = yearlyLeavesRepo.findByleavetypeid(leavetypeid);
				LeaveType temp = leaveTypeRepo.isLeaveTypeValid(leaveType);
				yearlyLeavesRepo.save(new YearlyLeaves(year,maxday,temp.getLeavetype_id()));
				return "Yearly leave for leave type id: "+temp.getLeavetype_id()+" is allocated";
					}
				else {
					return "Admin chara ar keu yearly leave allocate korte parbe na.";
				}
			}
			catch (Exception E){
				System.out.println(E);
		}
		return "Yearly leave allocation e exception.";
	}
	
//	//private
//	@PostMapping("/addtheadmin/{email}/{pass}")
//	public String addadmin(@PathVariable String email, @PathVariable String pass) {
//		
//			try{
//				if(session.getValue("sessionRole").equals("ADMIN")) {
//				Users u1 = new Users(email,pwd.encode(pass), "ADMIN");
//				userRepo.save(u1);
//				return ("Admin added <br>"+u1.toString());
//				}
//				else {
//					return "Admin chara ar keu admin add korte parbe na.";
//				}
//			}
//			catch (Exception E){
//				System.out.println(E);
//				return "Admin Add exception";
//			}
//	}
	
	@PostMapping("/adduser/{email}/{pass}/{name}")
	public String adduser(@PathVariable String email, @PathVariable String pass,@PathVariable String name) {
			try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email1 = auth.getName();
			Users temp = userRepo.findByEmail(email1);
			if(temp.getRole().equals("ADMIN")) {
				Users u1 = new Users(email,passwordEncoder.encode(pass),"USER",name);
				userRepo.save(u1);
				return ("User added <br>"+u1.toString());
				}
				else {
					return "Admin chara ar keu user add korte parbe na.";
				}
			}
			catch (Exception E){
				System.out.println(E);
			}
			return "User Add exception";
	}
	
	@PostMapping("/addmanager/{email}/{pass}/{name}")
	public String addmanager(@PathVariable String email, @PathVariable String pass,@PathVariable String name) {
		
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email1 = auth.getName();
			Users temp = userRepo.findByEmail(email1);
			if(temp.getRole().equals("ADMIN")) {
				Users u1 = new Users(email,passwordEncoder.encode(pass),"MANAGER",name);
				userRepo.save(u1);
				return ("Manager added <br>"+u1.toString());
				}
				else {
					return "Admin chara ar keu manager add korte parbe na.";
				}
			}
			catch (Exception E){
				System.out.println(E);
			}
		return "Manager Add exception";
	}
	
	@PostMapping("/addoni")
	public String addoni() {
		Users temp = new Users("oni@test.com",passwordEncoder.encode("pass1"),"ADMIN", "Oni");
		userRepo.save(temp);
		return "Oni added";
	}
	
}
