package Admin.Admindashboard;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;
import java.time.DayOfWeek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShiftCount {

  // Counter for employees supposed to work today
  static int countOfEmployeesToWorkToday = 0;
  static int countOfLeaveToday = 0;

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
    System.out.println("Total number of employees work today: "
        + ((countOfEmployeesToWorkToday) - countOfLeaveToday));

    return countOfEmployeesToWorkToday - countOfLeaveToday;
  }


  public static List<Integer> getAllLeave() {
    List<Integer> leavesId = new ArrayList<>();

    String sql = "SELECT leave_id FROM Leaves WHERE status = 'approved' "
        + "AND ? BETWEEN start_date AND end_date";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          leavesId.add(rs.getInt(1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return leavesId;
  }

  public static int getTodayLeaveCount() {

    // Get all leave IDs where the leave starts today
    List<Integer> leaveIds = getAllLeave();

    // Check if each employee is supposed to work today
    for (int leaveId : leaveIds) {
      boolean isLeaveToday = isLeaveToday(leaveId);

      // Output the result and increment the counter if the employee should work today
      if (!isLeaveToday) {
        System.out.println("Employee with ID " + leaveId + " is not on leave today.");

      } else {
        System.out.println("Employee with ID " + leaveId + " is on leave today.");
        countOfLeaveToday++;
      }
    }

    // Output the total count of employees who are supposed to work today
    System.out.println("Total number of employees supposed to work today: " + countOfLeaveToday);

    return countOfLeaveToday;
  }



  public static boolean isLeaveToday(int leaveId) {
    String query =
        "SELECT start_date, end_date FROM Leaves WHERE leave_id = ? AND status = 'approved'";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(query)) {

      preparedStatement.setInt(1, leaveId);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
          LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

          // Calculate the date difference excluding the end date
          // long dateDifference = ChronoUnit.DAYS.between(startDate, endDate);

          // Check if today is within the range from start date to the day before end date
          return LocalDate.now().isAfter(startDate.minusDays(1))
              && LocalDate.now().isBefore(endDate);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }


}
