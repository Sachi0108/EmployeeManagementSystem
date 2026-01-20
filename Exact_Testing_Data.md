# EXACT TESTING DATA - Employee Management System

## Server: http://localhost:2029

---

## 1. AUTHENTICATION ENDPOINTS

### Home Endpoint
**GET** `http://localhost:2029/auth/checkapi/`
**Headers**: None
**Response**: "Employee Management System Backend Project is Running"

### Login
**POST** `http://localhost:2029/auth/checkapi/checklogin`
**Headers**: 
```
Content-Type: application/json
```
**Body**:
```json
{
  "identifier": "admin",
  "password": "admin123"
}
```
**Expected Response**:
```json
{
  "role": "admin",
  "message": "Login Successful",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "data": {...}
}
```

### Forgot Password
**POST** `http://localhost:2029/auth/checkapi/forgotpassword`
**Headers**: 
```
Content-Type: application/json
```
**Body**:
```json
{
  "email": "manager1@company.com"
}
```

### Check Reset Link Validity
**GET** `http://localhost:2029/auth/checkapi/isresetlinkexpired?token=reset_token_here`

### Reset Password
**POST** `http://localhost:2029/auth/checkapi/resetpassword`
**Headers**: 
```
Content-Type: application/json
```
**Body**:
```json
{
  "token": "reset_token_here",
  "newpassword": "newPassword123"
}
```

### Change Password
**POST** `http://localhost:2029/auth/checkapi/changepassword`
**Headers**: 
```
Content-Type: application/json
```
**Body**:
```json
{
  "role": "MANAGER",
  "username": "manager1",
  "oldpassword": "password123",
  "newpassword": "newPassword123"
}
```

---

## 2. ADMIN ENDPOINTS

### Add Manager
**POST** `http://localhost:2029/admin/addmanager`
**Headers**: 
```
Content-Type: application/json
Authorization: Bearer ADMIN_TOKEN_HERE
```
**Body**:
```json
{
  "name": "John Manager",
  "email": "manager1@company.com",
  "password": "password123",
  "contact": "9876543210",
  "department": "IT",
  "location": "Pune"
}
```

### View All Managers
**GET** `http://localhost:2029/admin/viewallmanagers`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### View All Employees
**GET** `http://localhost:2029/admin/viewallemployees`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### Assign Duty to Manager
**PUT** `http://localhost:2029/admin/assigndutytomanager?managerid=1`
**Headers**: 
```
Content-Type: application/json
Authorization: Bearer ADMIN_TOKEN_HERE
```
**Body**:
```json
{
  "title": "System Maintenance",
  "description": "Perform monthly system maintenance",
  "deadline": "2024-02-15",
  "status": "PENDING"
}
```

### Assign Duty to Employee
**PUT** `http://localhost:2029/admin/assigndutytoemployee?empid=1`
**Headers**: 
```
Content-Type: application/json
Authorization: Bearer ADMIN_TOKEN_HERE
```
**Body**:
```json
{
  "title": "Code Review",
  "description": "Review pull requests",
  "deadline": "2024-02-10",
  "status": "PENDING"
}
```

### Update Employee Account Status
**PUT** `http://localhost:2029/admin/updateempaccstatus?empid=1&status=ACCEPTED`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### View All Leave Applications
**GET** `http://localhost:2029/admin/viewallapplications`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### Delete Employee
**DELETE** `http://localhost:2029/admin/deleteemployee?eid=1`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### Delete Manager
**DELETE** `http://localhost:2029/admin/deletemanager?mid=1`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### View Employee Duties
**GET** `http://localhost:2029/admin/viewemployeeduties?eid=1`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### View Duties Assigned by Manager
**GET** `http://localhost:2029/admin/viewassingeddutiesbymanager?mid=1`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### View Duties Assigned by Admin
**GET** `http://localhost:2029/admin/viewassingeddutiesbyadmin?id=1`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### Get Manager Count
**GET** `http://localhost:2029/admin/managercount`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

### Get Employee Count
**GET** `http://localhost:2029/admin/employeecount`
**Headers**: 
```
Authorization: Bearer ADMIN_TOKEN_HERE
```

---

## 3. EMPLOYEE ENDPOINTS

### Register Employee
**POST** `http://localhost:2029/employee/addemployee`
**Headers**: 
```
Content-Type: application/json
```
**Body**:
```json
{
  "name": "Jane Employee",
  "email": "employee1@company.com",
  "password": "emp123",
  "contact": "9876543211",
  "department": "Development",
  "location": "Mumbai",
  "experience": "3",
  "gender": "FEMALE"
}
```

### View Profile
**GET** `http://localhost:2029/employee/viewprofile?empid=1`
**Headers**: 
```
Authorization: Bearer EMPLOYEE_TOKEN_HERE
```

### View Assigned Duties
**GET** `http://localhost:2029/employee/viewduties?empid=1`
**Headers**: 
```
Authorization: Bearer EMPLOYEE_TOKEN_HERE
```

### Apply Leave
**POST** `http://localhost:2029/employee/applyleave?empid=1`
**Headers**: 
```
Content-Type: application/json
Authorization: Bearer EMPLOYEE_TOKEN_HERE
```
**Body**:
```json
{
  "reason": "Medical appointment",
  "fromdate": "2024-02-15",
  "todate": "2024-02-16",
  "leavetype": "SICK_LEAVE"
}
```

### View Own Leaves
**GET** `http://localhost:2029/employee/viewownleaves?empid=1`
**Headers**: 
```
Authorization: Bearer EMPLOYEE_TOKEN_HERE
```

---

## 4. MANAGER ENDPOINTS

### View All Employees
**GET** `http://localhost:2029/manager/viewallemployees`
**Headers**: 
```
Authorization: Bearer MANAGER_TOKEN_HERE
```

### Update Employee Account Status
**PUT** `http://localhost:2029/manager/updateempaccstatus?empid=1&status=ACCEPTED`
**Headers**: 
```
Authorization: Bearer MANAGER_TOKEN_HERE
```

### Apply Leave (Manager)
**POST** `http://localhost:2029/manager/applyleave?managerid=1`
**Headers**: 
```
Content-Type: application/json
Authorization: Bearer MANAGER_TOKEN_HERE
```
**Body**:
```json
{
  "reason": "Family vacation",
  "fromdate": "2024-02-20",
  "todate": "2024-02-25",
  "leavetype": "CASUAL_LEAVE"
}
```

### View Own Leaves (Manager)
**GET** `http://localhost:2029/manager/viewownleaves?managerid=1`
**Headers**: 
```
Authorization: Bearer MANAGER_TOKEN_HERE
```

### Update Employee Leave Status
**PUT** `http://localhost:2029/manager/updateleavestatus?leaveid=1&status=APPROVED`
**Headers**: 
```
Authorization: Bearer MANAGER_TOKEN_HERE
```

### Assign Duty to Employee (Manager)
**POST** `http://localhost:2029/manager/assigndutytoemp?managerid=1&empid=1`
**Headers**: 
```
Content-Type: application/json
Authorization: Bearer MANAGER_TOKEN_HERE
```
**Body**:
```json
{
  "title": "Bug Fix",
  "description": "Fix critical bug in authentication",
  "deadline": "2024-02-12",
  "status": "PENDING"
}
```

### View Assigned Duties (Manager)
**GET** `http://localhost:2029/manager/viewAssingedDuties?managerid=1`
**Headers**: 
```
Authorization: Bearer MANAGER_TOKEN_HERE
```

---

## 5. TESTING SEQUENCE

### Step 1: Start Application
```bash
cd EmployeeManagementSystem
mvn spring-boot:run
```

### Step 2: Admin Login
**POST** `http://localhost:2029/auth/checkapi/checklogin`
```json
{"identifier": "admin", "password": "admin123"}
```
Copy the token from response.

### Step 3: Create Manager
**POST** `http://localhost:2029/admin/addmanager`
Headers: `Authorization: Bearer ADMIN_TOKEN`
```json
{
  "name": "John Manager",
  "email": "manager1@company.com",
  "password": "password123",
  "contact": "9876543210",
  "department": "IT",
  "location": "Pune"
}
```

### Step 4: Manager Login
**POST** `http://localhost:2029/auth/checkapi/checklogin`
```json
{"identifier": "manager1@company.com", "password": "password123"}
```
Copy the manager token.

### Step 5: Register Employee
**POST** `http://localhost:2029/employee/addemployee`
```json
{
  "name": "Jane Employee",
  "email": "employee1@company.com",
  "password": "emp123",
  "contact": "9876543211",
  "department": "Development",
  "location": "Mumbai",
  "experience": "3",
  "gender": "FEMALE"
}
```

### Step 6: Accept Employee Account
**PUT** `http://localhost:2029/admin/updateempaccstatus?empid=1&status=ACCEPTED`
Headers: `Authorization: Bearer ADMIN_TOKEN`

### Step 7: Employee Login
**POST** `http://localhost:2029/auth/checkapi/checklogin`
```json
{"identifier": "employee1@company.com", "password": "emp123"}
```
Copy the employee token.

### Step 8: Test All Endpoints
Now you have all 3 tokens and can test any endpoint.

---

## 6. IMPORTANT NOTES

### Authentication
- All endpoints require `Authorization: Bearer TOKEN` header
- Login endpoint: `/auth/checkapi/checklogin`
- Role validation is case-sensitive: "admin", "manager", "employee"

### Employee Registration
- Endpoint: `/employee/addemployee` (JSON, not multipart)
- Employee account must be "ACCEPTED" before login
- Status check: Employee needs "Accepted" status (case-sensitive)

### Parameter Names
- Employee ID parameter: `empid`
- Manager ID parameter: `managerid`
- Leave ID parameter: `leaveid`

### Common Issues
- Employee login fails if account status is not "Accepted"
- Token format: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
- All dates in YYYY-MM-DD format

### Response Format
- Login returns: role, message, token, data
- Success operations return success messages
- Errors return HTTP status codes with error messages

This is the EXACT structure of your application endpoints with proper URLs, parameters, and request formats.
