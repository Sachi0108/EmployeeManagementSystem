package com.klef.fsad.sdp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.model.Admin;
import com.klef.fsad.sdp.model.Duty;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Leave;
import com.klef.fsad.sdp.model.Manager;
import com.klef.fsad.sdp.security.JWTUtilizer;
import com.klef.fsad.sdp.services.AdminService;
import com.klef.fsad.sdp.services.DutyService;
import com.klef.fsad.sdp.services.ManagerService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private DutyService dutyService;
	@Autowired
	private JWTUtilizer jwtService;
	
	@PostMapping("/addmanager")
	public ResponseEntity<String> addManager(@RequestBody Manager manager,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body("Access Denied! Admin privileges required");
		}
		
		adminService.addManager(manager);
		return ResponseEntity.ok("Manager Added Successfully with ID: " + manager.getId());
	}
	
	@GetMapping("/viewallmanagers")
	public ResponseEntity<List<Manager>> viewAllManagers(@RequestHeader("Authorization") String authHeader) {
		
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		
		return ResponseEntity.ok(adminService.viewAllManagers());
	}
	
	@GetMapping("/viewallemployees")
	public ResponseEntity<List<Employee>> viewAllEmployees(@RequestHeader("Authorization") String authHeader) {
		
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		
		return ResponseEntity.ok(adminService.viewAllEmployees());
	}
	
	@PutMapping("/assigndutytomanager") 
	public ResponseEntity<String> assignDutyToManager(@RequestBody Duty duty, @RequestParam Long managerid, @RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body("Access Denied! Admin privileges required");
		}
		Admin admin = adminService.findAdminById(1); // Get current admin ID from token if needed
		dutyService.assignDutyByAdminToManager(duty, managerid, admin.getId());
		return ResponseEntity.ok("Duty Assinged to Manager Successfully");
	}
	
	@PutMapping("/assigndutytoemployee") 
	public ResponseEntity<String> assignDutyToEmployee(@RequestBody Duty duty, @RequestParam Long empid, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.substring(7);
			Map<String, String> tokenClaims = jwtService.validateToken(token);
			if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
				return ResponseEntity.status(403).body("Access Denied! Admin privileges required");
			}
			Admin admin = adminService.findAdminById(1); // Get current admin ID from token if needed
			dutyService.assignDutyByAdminToEmployee(duty, empid, admin.getId());
			return ResponseEntity.ok("Duty Assinged to Employee Successfully");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error: " + e.getMessage());
		}
	}
	
	@PutMapping("/updateempaccstatus") 
	public ResponseEntity<String> updateEmployeeAccountStatus(@RequestParam Long empid, @RequestParam String status,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body("Access Denied! Admin privileges required");
		}
		
		String message = managerService.updateEmployeeAccountStatus(empid, status.toUpperCase());
		return ResponseEntity.ok(message);
	}
	
	@PutMapping("/approveallpendingemployees")
	public ResponseEntity<String> approveAllPendingEmployees(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body("Access Denied! Admin privileges required");
		}
		
		try {
			List<Employee> pendingEmployees = adminService.viewAllEmployees();
			int approvedCount = 0;
			
			for(Employee emp : pendingEmployees) {
				if(emp.getAccountstatus() != null && emp.getAccountstatus().equalsIgnoreCase("PENDING")) {
					managerService.updateEmployeeAccountStatus(emp.getId(), "ACCEPTED");
					approvedCount++;
				}
			}
			
			return ResponseEntity.ok("Successfully approved " + approvedCount + " pending employees");
		} catch(Exception e) {
			return ResponseEntity.status(500).body("Error approving employees: " + e.getMessage());
		}
	}
	
	@GetMapping("/viewallapplications")
	public ResponseEntity<List<Leave>> viewAllLeaveApplications(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		
		List<Leave> leaves = adminService.viewAllLeaveApplications();
		return ResponseEntity.ok(leaves);
	}
	
	@DeleteMapping("/deleteemployee")
	public ResponseEntity<String> deleteEmployee(@RequestParam Long eid,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body("Access Denied! Admin privileges required");
		}
		
		String res = adminService.deleteEmployee(eid);
		return ResponseEntity.ok(res);
	}
	
	@DeleteMapping("/deletemanager")
	public ResponseEntity<String> deleteManager(@RequestParam Long mid,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body("Access Denied! Admin privileges required");
		}
		
		String res = adminService.deleteManager(mid);
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/viewemployeeduties")
	public ResponseEntity<List<Duty>> viewEmployeeAssingedDuties(@RequestParam Long eid,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		
		List<Duty> duties = dutyService.viewAllDutiesofEmployee(eid);
		return ResponseEntity.ok(duties);
	}
	
	@GetMapping("/viewassingeddutiesbymanager")
	public ResponseEntity<List<Duty>> getDutiesAssingedByManager(@RequestParam Long mid,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		List<Duty> duties = dutyService.viewDutiesAssignedByManager(mid);
		return ResponseEntity.ok(duties);
	}
	
	@GetMapping("/viewassingeddutiesbyadmin")
	public ResponseEntity<List<Duty>> getDutiesAssingedByAdmin(@RequestParam int id,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		List<Duty> duties = dutyService.viewDutiesAssignedByAdmin(id);
		return ResponseEntity.ok(duties);
	}
	
	@GetMapping("/managercount")
	public ResponseEntity<Long> getManagerCount(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		
		return ResponseEntity.ok(adminService.managercount());
	}
	
	@GetMapping("/employeecount")
	public ResponseEntity<Long> getEmployeeCount(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		Map<String, String> tokenClaims = jwtService.validateToken(token);
		if(tokenClaims == null || tokenClaims.get("role") == null || !tokenClaims.get("role").equals("ADMIN")) {
			return ResponseEntity.status(403).body(null);
		}
		
		return ResponseEntity.ok(adminService.employeecount());
	}
}