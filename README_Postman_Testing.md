# Employee Management System - Postman Testing Guide

## Files Created

1. **Postman_Collection.json** - Complete API collection with all endpoints
2. **Postman_Environment.json** - Environment variables for testing
3. **README_Postman_Testing.md** - This setup guide

## Setup Instructions

### 1. Import Collection & Environment

1. Open Postman
2. Click **Import** → Select **Postman_Collection.json**
3. Click **Import** again → Select **Postman_Environment.json**
4. Select the "Employee Management System Environment" from the dropdown

### 2. Start the Application

```bash
cd EmployeeManagementSystem
mvn spring-boot:run
```

### 3. Testing Workflow

#### Step 1: Admin Login
1. Go to **Authentication** → **Login**
2. Update environment variables:
   - `login_identifier`: `admin` (or your admin username)
   - `login_password`: `admin123` (or your admin password)
3. Send request - this will automatically set `admin_token`

#### Step 2: Create Manager
1. Go to **Admin Endpoints** → **Add Manager**
2. Update manager details if needed
3. Send request using admin token

#### Step 3: Manager Login
1. Update environment variables:
   - `login_identifier`: `manager1` (or created manager username)
   - `login_password`: `password123`
2. Send login request - this will set `manager_token`

#### Step 4: Register Employee
1. Go to **Employee Endpoints** → **Register Employee**
2. Update employee details
3. Upload a photo file
4. Send request

#### Step 5: Employee Login
1. Update environment variables:
   - `login_identifier`: `employee1` (or registered employee username)
   - `login_password`: employee password
2. Send login request - this will set `employee_token`

#### Step 6: Test All Endpoints
Now you can test all endpoints with proper authentication tokens.

## Environment Variables

### Authentication Tokens (Auto-set after login)
- `admin_token` - JWT token for admin operations
- `manager_token` - JWT token for manager operations  
- `employee_token` - JWT token for employee operations

### User IDs
- `admin_id` - Admin user ID (default: 1)
- `manager_id` - Manager user ID (default: 1)
- `employee_id` - Employee user ID (default: 1)

### Login Credentials
- `login_identifier` - Username/email for login
- `login_password` - Password for login

### Test Data
- `manager_*` - Manager creation data
- `employee_*` - Employee registration data
- `duty_*` - Duty assignment data
- `leave_*` - Leave application data

## Collection Structure

### Authentication
- Home Endpoint
- Login (with auto token saving)
- Forgot Password
- Check Reset Link Validity
- Reset Password
- Change Password

### Admin Endpoints
- Add Manager
- View All Managers
- View All Employees
- Assign Duty to Manager
- Assign Duty to Employee
- Update Employee Account Status
- View All Leave Applications
- Delete Employee
- Delete Manager
- View Employee Duties
- View Duties Assigned by Manager
- View Duties Assigned by Admin
- Get Manager Count
- Get Employee Count

### Manager Endpoints
- View All Employees
- Update Employee Account Status
- Apply Leave (Manager)
- View Own Leaves (Manager)
- Update Employee Leave Status
- Assign Duty to Employee
- View Assigned Duties

### Employee Endpoints
- Register Employee
- View Profile
- View Assigned Duties
- Apply Leave (Employee)
- View Own Leaves (Employee)

## Important Notes

1. **Authentication**: All protected endpoints require valid JWT tokens
2. **Auto Token Management**: Login requests automatically save tokens to environment
3. **File Upload**: Employee registration uses multipart/form-data for photo upload
4. **Date Format**: Use YYYY-MM-DD format for leave dates
5. **Account Status**: Employees need "ACCEPTED" status to login successfully
6. **Base URL**: Default is `http://localhost:2028` (change if needed)

## Common Test Scenarios

### 1. Complete User Flow
1. Admin login → Create manager → Manager login → Register employee → Employee login

### 2. Duty Management
1. Admin assigns duty to manager → Manager assigns duty to employee → Employee views duties

### 3. Leave Management
1. Employee applies for leave → Manager approves/rejects → Admin views all applications

### 4. Error Testing
1. Invalid credentials → 401 error
2. Missing tokens → 403 error
3. Wrong HTTP method → 405 error

## Troubleshooting

### Connection Issues
- Ensure application is running on port 2028
- Check database connection
- Verify firewall settings

### Authentication Issues
- Check token validity (JWT expires)
- Verify role-based access
- Ensure proper Authorization header format

### Data Issues
- Clear database if needed
- Check unique constraints
- Verify data types and formats

## Export/Import

To share this collection:
1. Export collection: Right-click collection → Export
2. Export environment: Click gear icon → Export
3. Share both JSON files with team members

This comprehensive setup provides everything needed for thorough API testing of the Employee Management System.
