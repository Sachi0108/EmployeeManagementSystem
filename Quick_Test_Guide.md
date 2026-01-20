# Quick Start Testing Guide - Exact Data

## Server Setup
**URL**: http://localhost:2029
**Port**: 2029

---

## STEP 1: Start Application
```bash
cd EmployeeManagementSystem
mvn spring-boot:run
```

---

## STEP 2: Admin Login (Get Token)

**POST** `http://localhost:2029/login`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "identifier": "admin",
  "password": "admin123"
}
```

**Expected Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "ADMIN"
}
```

**Copy the token value - you'll need it for all admin requests**

---

## STEP 3: Create Manager

**POST** `http://localhost:2029/admin/addmanager`

**Headers**:
```
Content-Type: application/json
Authorization:Bearer PASTE_ADMIN_TOKEN_HERE
```

**Body** (raw JSON):
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

**Expected Response**:
```
Manager Added Successfully with ID: 1
```

---

## STEP 4: Manager Login

**POST** `http://localhost:2029/login`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "identifier": "manager1@company.com",
  "password": "password123"
}
```

**Expected Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "MANAGER"
}
```

**Copy the manager token**

---

## STEP 5: Register Employee

**POST** `http://localhost:2029/employee/register`

**Headers**:
```
Content-Type: multipart/form-data
```

**Body** (form-data):
- `name`: `Jane Employee`
- `email`: `employee1@company.com`
- `password`: `emp123`
- `contact`: `9876543211`
- `department`: `Development`
- `location`: `Mumbai`
- `experience`: `3`
- `gender`: `FEMALE`
- `photo`: [Upload any image file]

**Expected Response**:
```
Employee Registered Successfully
```

---

## STEP 6: Employee Login

**POST** `http://localhost:2029/login`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "identifier": "employee1@company.com",
  "password": "emp123"
}
```

**Expected Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "EMPLOYEE"
}
```

**Copy the employee token**

---

## STEP 7: Accept Employee Account (Admin)

**PUT** `http://localhost:2029/admin/updateempaccstatus?empid=1&status=ACCEPTED`

**Headers**:
```
Authorization: Bearer PASTE_ADMIN_TOKEN_HERE
```

**Expected Response**:
```
Employee account status updated successfully
```

---

## STEP 8: Test Key Endpoints

### View All Employees (Admin)
**GET** `http://localhost:2029/admin/viewallemployees`
**Headers**: `Authorization: Bearer ADMIN_TOKEN`

### Assign Duty to Employee (Admin)
**PUT** `http://localhost:2029/admin/assigndutytoemployee?empid=1`
**Headers**: 
```
Content-Type: application/json
Authorization: Bearer ADMIN_TOKEN
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

### View Employee Duties (Admin)
**GET** `http://localhost:2029/admin/viewemployeeduties?eid=1`
**Headers**: `Authorization: Bearer ADMIN_TOKEN`

### View Assigned Duties (Employee)
**GET** `http://localhost:2029/employee/viewassignedduties`
**Headers**: `Authorization: Bearer EMPLOYEE_TOKEN`

### Apply Leave (Employee)
**POST** `http://localhost:2029/employee/applyleave`
**Headers**:
```
Content-Type: application/json
Authorization: Bearer EMPLOYEE_TOKEN
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

### View All Leave Applications (Admin)
**GET** `http://localhost:2029/admin/viewallapplications`
**Headers**: `Authorization: Bearer ADMIN_TOKEN`

---

## TOKENS YOU NEED:
1. **Admin Token** - From Step 2
2. **Manager Token** - From Step 4  
3. **Employee Token** - From Step 6

Replace `PASTE_XXX_TOKEN_HERE` with actual tokens from responses.

---

## COMMON ERRORS & SOLUTIONS:

### 401 Unauthorized
- Check if token is copied correctly (remove extra spaces)
- Ensure token hasn't expired
- Verify login credentials

### 403 Forbidden
- Check user role (admin/manager/employee)
- Ensure correct endpoint for user role

### 405 Method Not Allowed
- Check HTTP method (GET/POST/PUT/DELETE)
- Verify URL spelling

### Employee Login Failed
- Make sure employee account status is "ACCEPTED" (Step 7)
- Check email and password exactly as registered

---

## QUICK COPY-PASTE VALUES:

**Admin Login**:
```json
{"identifier": "admin", "password": "admin123"}
```

**Manager Login**:
```json
{"identifier": "manager1@company.com", "password": "password123"}
```

**Employee Login**:
```json
{"identifier": "employee1@company.com", "password": "emp123"}
```

**Manager Creation**:
```json
{"name": "John Manager", "email": "manager1@company.com", "password": "password123", "contact": "9876543210", "department": "IT", "location": "Pune"}
```

**Duty Assignment**:
```json
{"title": "Code Review", "description": "Review pull requests", "deadline": "2024-02-10", "status": "PENDING"}
```

**Leave Application**:
```json
{"reason": "Medical appointment", "fromdate": "2024-02-15", "todate": "2024-02-16", "leavetype": "SICK_LEAVE"}
```

Start with Step 1 and follow in order. All data is exact and ready to use!
