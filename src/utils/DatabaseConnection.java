package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  private static final String URL =
      "jdbc:sqlserver://NUKTHEAN\\DBSERVER;databaseName=Attendance_Management;encrypt=true;trustServerCertificate=true";
  private static final String USER = "sa";
  private static final String PASSWORD = "password1";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

}
