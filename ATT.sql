-- Create the database
CREATE DATABASE Attendance_Management;
GO

-- Use the created database
USE Attendance_Management;
GO

-- Create the Employees table
CREATE TABLE Employees (
    employee_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    sex VARCHAR(1) NOT NULL,
    department VARCHAR(35),
    position VARCHAR(35),
    email VARCHAR(50),
    phone VARCHAR(15),
    nid VARCHAR(15),
    CONSTRAINT UQ_Employee_Email UNIQUE (email),
    CONSTRAINT UQ_Employee_NID UNIQUE (nid)
);
GO

-- Create the Admin table
CREATE TABLE Admin (
    admin_id INT PRIMARY KEY,
    password VARCHAR(64) NULL, -- Store hashed passwords
    last_login DATETIME DEFAULT CURRENT_TIMESTAMP,
    failed_attempts INT DEFAULT 0,
    account_status VARCHAR(20) DEFAULT 'active',
    role VARCHAR(15) NOT NULL, -- Add role column
    CONSTRAINT FK_Admin_UserRoles FOREIGN KEY (admin_id) REFERENCES Employees(employee_id)
);
GO

-- Create the User table
CREATE TABLE [User] (
    user_id INT PRIMARY KEY,
    password VARCHAR(64) NULL, -- Store hashed passwords
    last_login DATETIME DEFAULT CURRENT_TIMESTAMP,
    failed_attempts INT DEFAULT 0,
    account_status VARCHAR(20) DEFAULT 'active',
    role VARCHAR(15) NOT NULL, -- Add role column
    CONSTRAINT FK_User_UserRoles FOREIGN KEY (user_id) REFERENCES Employees(employee_id)
);
GO

-- Create the Leaves table
CREATE TABLE Leaves (
    leave_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    employee_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(70),
    status VARCHAR(10),
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id)
);
GO

CREATE TABLE Attendance (
	attendance_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	employee_id INT NOT NULL,
	date DATE NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    status VARCHAR(20) DEFAULT 'active',
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id)
);
GO

-- Insert an employee for the admin role
INSERT INTO Employees (first_name, last_name, sex, department, position, email, phone, nid)
VALUES ('CHEA', 'NUKTHEAN','M', 'CEO', 'CEO', 'admin@example.com1', '1234567890', '123456789');

-- Get the employee_id of the newly inserted admin employee
DECLARE @admin_employee_id INT = SCOPE_IDENTITY();


-- Get the user_id of the newly inserted admin role
DECLARE @admin_user_id INT = SCOPE_IDENTITY();

-- Insert the corresponding admin role into the Admin table
INSERT INTO Admin (admin_id, password, role)
VALUES (@admin_user_id, CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', '1'), 2), 'admin');
INSERT INTO [User] (user_id, password, role)
VALUES (@admin_user_id, CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', '1'), 2), 'user');

-- Insert an employee for the user role
INSERT INTO Employees (first_name, last_name,sex, department, position, email, phone, nid)
VALUES ('BERN', 'ERN','M', 'Development', 'Developer', 'user@example.com1', '0987654321', '987654321');

-- Get the employee_id of the newly inserted user employee
DECLARE @user_employee_id INT = SCOPE_IDENTITY();


-- Get the user_id of the newly inserted user role
DECLARE @user_user_id INT = SCOPE_IDENTITY();

-- Insert the corresponding user role into the User table
INSERT INTO [User] (user_id, password, role)
VALUES (@user_user_id, CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', '1'), 2), 'user');
