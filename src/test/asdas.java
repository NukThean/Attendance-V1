package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;
import java.time.DayOfWeek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class asdas {

  // Counter for employees supposed to work today
  static int countOfEmployeesToWorkToday = 0;

  public static void main(String[] args) {
    // Create an instance of the class containing the methods
    int hi = getTodayShiftCount();
    System.out.println("Hello " + hi);
  }

  public static boolean isEmployeeSupposedToWorkToday(int employeeId) {
    String query =
        "SELECT sunday, monday, tuesday, wednesday, thursday, friday, saturday FROM DayShift WHERE employee_id = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(query)) {

      preparedStatement.setInt(1, employeeId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
          switch (currentDay) {
            case SUNDAY:
              return resultSet.getBoolean("sunday");
            case MONDAY:
              return resultSet.getBoolean("monday");
            case TUESDAY:
              return resultSet.getBoolean("tuesday");
            case WEDNESDAY:
              return resultSet.getBoolean("wednesday");
            case THURSDAY:
              return resultSet.getBoolean("thursday");
            case FRIDAY:
              return resultSet.getBoolean("friday");
            case SATURDAY:
              return resultSet.getBoolean("saturday");
            default:
              return false;
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static List<Integer> getAllId() {
    List<Integer> ids = new ArrayList<>();

    String sql = "SELECT TOP (1000) employee_id FROM Employees";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        ids.add(rs.getInt(1));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return ids;
  }

  public static int getTodayShiftCount() {
    System.out.println(countOfEmployeesToWorkToday);
    // Get all employee IDs
    List<Integer> employeeIds = getAllId();

    // Check if each employee is supposed to work today
    for (int employeeId : employeeIds) {
      boolean shouldWork = isEmployeeSupposedToWorkToday(employeeId);

      // Output the result and increment the counter if the employee should work today
      if (shouldWork) {
        System.out.println("Employee with ID " + employeeId + " is supposed to work today.");
        countOfEmployeesToWorkToday++;
      } else {
        System.out.println("Employee with ID " + employeeId + " is not supposed to work today.");
      }
    }
    System.out.println(countOfEmployeesToWorkToday);
    // Output the total count of employees who are supposed to work today
    System.out.println(
        "Total number of employees supposed to work today: " + countOfEmployeesToWorkToday);

    return countOfEmployeesToWorkToday;
  }
}
