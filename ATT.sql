-- Create the database
CREATE DATABASE Attendance_Management;
GO

-- Use the created database
USE Attendance_Management;
GO

-- Create the Employees table
CREATE TABLE Employees (
    Emp_Img VARBINARY(MAX),
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
	salt VARCHAR(32) NULL, --Store salt value
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
	salt VARCHAR(32) NULL, --Store salt value
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
    leave_type VARCHAR(20) NOT NULL,
    application_date DATE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    reason VARCHAR(250),
    status VARCHAR(30),
    approver VARCHAR(50),
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id),
);
GO

CREATE TABLE LeavesBalance (
    employee_id INT PRIMARY KEY NOT NULL,
    annual_leave FLOAT NOT NULL,
    sick_leave FLOAT NOT NULL,
    special_leave FLOAT NOT NULL,
    block_leave FLOAT NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id),
);
GO

CREATE TABLE Attendance (
	attendance_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	employee_id INT NOT NULL,
	date DATE NOT NULL,
    check_in_time TIME(0),
    check_out_time TIME(0),
    TimeDiff_in INT,
    TimeDiff_out INT,
	Late_in BIT,
	Early_out BIT,
    status VARCHAR(20) DEFAULT 'active',
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id)
);
GO

CREATE TABLE ShiftSchedule (
    employee_id INT PRIMARY KEY,
    start_shift TIME(0),
    end_shift TIME(0),
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id)
);

CREATE TABLE DayShift (
    employee_id INT PRIMARY KEY,
    sunday BIT,
    monday BIT,
    tuesday BIT,
    wednesday BIT,
    thursday BIT,
    friday BIT,
    saturday BIT,
    sunday BIT
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id)
);



-- Insert an employee for the admin role
INSERT INTO Employees (first_name, last_name, sex, department, position, email, phone, nid)
VALUES ('CHEA', 'NUKTHEAN','M', 'CEO', 'CEO', 'admin@example.com1', '1234567890', '123456789');

-- Get the employee_id of the newly inserted admin employee
DECLARE @admin_employee_id INT = SCOPE_IDENTITY();


-- Get the user_id of the newly inserted admin role
DECLARE @admin_user_id INT = SCOPE_IDENTITY();

-- Insert the corresponding admin role into the Admin table
INSERT INTO Admin (admin_id, role)
VALUES (@admin_user_id, 'admin');

INSERT INTO [USER] (user_id, role)
VALUES (@admin_user_id, 'user');

INSERT INTO ShiftSchedule (employee_id, start_shift, end_shift)
VALUES (@admin_user_id, '09:00:00', '17:00:00');

INSERT INTO DayShift (employee_id, sunday, monday, tuesday, wednesday, thursday, friday, saturday)
VALUES (1, 0, 1, 1, 1, 1, 1, 1);


/*UPDATE Attendance
SET lateTimeInMin = DATEDIFF(minute, start_shift, check_in_time)
FROM Attendance AS A
INNER JOIN ShiftSchedule AS S
ON A.employee_id = S.employee_id;
*/
