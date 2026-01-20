package com.klef.fsad.sdp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.model.Admin;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Manager;
import com.klef.fsad.sdp.security.JWTUtilizer;
import com.klef.fsad.sdp.services.AdminService;
import com.klef.fsad.sdp.services.EmployeeService;
import com.klef.fsad.sdp.services.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth/checkapi")
@CrossOrigin("*")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AdminService adminService;
	@Autowired	
	private ManagerService managerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private JWTUtilizer jwtService;
	
	@GetMapping("/")
	public String home() {
		logger.info("Home endpoint accessed");
		return "Welcome to Employee Management System...!!!";
	}
	
	@PostMapping("/checklogin")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginrequest) {
		
		String identifier = loginrequest.getIdentifier();
		String password = loginrequest.getPassword();
		
		Admin admin = adminService.checkadminlogin(identifier, password);
		Manager manager = managerService.checkmanagerlogin(identifier, password);
		Employee employee = employeeService.checkemplogin(identifier, password);
		
		if(admin != null) {
			String token = jwtService.generateJWTToken(admin.getUsername(), "ADMIN");
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("role", "admin");
			res.put("message", "Login Successful");
			res.put("token", token);
			res.put("data", admin);
			
			return ResponseEntity.ok(res);
		}
		if(manager != null) {
			String token = jwtService.generateJWTToken(manager.getUsername(), "MANAGER");
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("role", "manager");
			res.put("message", "Login Successful");
			res.put("token", token);
			res.put("data", manager);

			return ResponseEntity.ok(res);
		}
		if(employee != null) {
			if (employee.getAccountstatus().equalsIgnoreCase("Accepted")) {
				String token = jwtService.generateJWTToken(employee.getUsername(), "EMPLOYEE");
				Map<String, Object> res = new HashMap<String, Object>();
				res.put("role", "employee");
				res.put("message", "Login Successful");
				res.put("token", token);
				res.put("data", employee);

				return ResponseEntity.ok(res);
			} 
			else {
				return ResponseEntity.status(401).body(Map.of("message", "Account Not approved yet Please Contact Administrator. Current Status: " + employee.getAccountstatus()));
			}
		}
		return ResponseEntity.status(401).body(Map.of("message", "Invalid Username/Email or Password"));
	}
	
    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Check Admin table first
        Admin admin = adminService.findAdminByEmail(email);
        if (admin != null) {
            return ResponseEntity.ok(Map.of("message", "Admin password reset not supported. Please contact system administrator."));
        }

        Optional<Manager>manager = managerService.findManagerByEmail(email);
        if (manager.isPresent()) {
            String token = managerService.generateResetToken(email);
            return ResponseEntity.ok(Map.of("message", "Reset link sent", "token", token));
        }

        Employee employee = employeeService.findEmployeeByEmail(email);
        if (employee != null) {
            String token = employeeService.generateResetToken(email);
            return ResponseEntity.ok(Map.of("message", "Reset link sent", "token", token));
        }

        return ResponseEntity.status(404).body(Map.of("message", "Email not found"));
    }

    @GetMapping("/isresetlinkexpired")
    public ResponseEntity<?> isResetLinkExpired(@RequestParam("token") String token) {
        boolean isValidManager = managerService.validateResetToken(token) && !managerService.isTokenExpired(token);
        boolean isValidEmployee = employeeService.validateResetToken(token) && !employeeService.isTokenExpired(token);

        if (isValidManager || isValidEmployee) {
            return ResponseEntity.ok(Map.of("valid", true));
        } else {
            return ResponseEntity.status(400).body(Map.of("valid", false, "message", "Link expired or invalid"));
        }
    }

    // ✅ Reset Password (using token)
    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newpassword");

        if (managerService.validateResetToken(token)) {
            if (managerService.isTokenExpired(token)) {
                return ResponseEntity.status(400).body(Map.of("message", "Token expired"));
            }
            managerService.updatePassword(token, newPassword);
            managerService.deleteResetToken(token);
            return ResponseEntity.ok(Map.of("message", "Password updated for Manager"));
        }

        if (employeeService.validateResetToken(token)) {
            if (employeeService.isTokenExpired(token)) {
                return ResponseEntity.status(400).body(Map.of("message", "Token expired"));
            }
            employeeService.updatePassword(token, newPassword);
            employeeService.deleteResetToken(token);
            return ResponseEntity.ok(Map.of("message", "Password updated for Employee"));
        }

        return ResponseEntity.status(400).body(Map.of("message", "Invalid or expired token"));
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        String role = request.get("role");
        String username = request.get("username");
        String oldPassword = request.get("oldpassword");
        String newPassword = request.get("newpassword");

        if (role.equalsIgnoreCase("MANAGER")) {
            Manager manager = managerService.findManagerByUsername(username);
            if (manager != null && managerService.changePassword(manager, oldPassword, newPassword)) {
                return ResponseEntity.ok(Map.of("message", "Password changed for Manager"));
            } else {
                return ResponseEntity.status(400).body(Map.of("message", "Old password incorrect"));
            }
        }
        else if (role.equalsIgnoreCase("EMPLOYEE")) {
            Employee employee = employeeService.findEmployeeByUsername(username);
            if (employee != null && employeeService.changePassword(employee, oldPassword, newPassword)) {
                return ResponseEntity.ok(Map.of("message", "Password changed for Employee"));
            } 
            else {
                return ResponseEntity.status(400).body(Map.of("message", "Old password incorrect"));
            }
        } 
        else {
            return ResponseEntity.status(403).body(Map.of("message", "Admins can't change password here"));
        }
    }
}
