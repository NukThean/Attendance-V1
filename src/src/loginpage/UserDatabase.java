package src.loginpage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import src.utils.DatabaseConnection;

public class UserDatabase {
  private static String name;
  public static String getUserName;

  public static User authenticate(int userId, String password) {

    // Retrieve the user information
    User user = null;
    String sql = "SELECT u.password, u.role, e.first_name, e.last_name " + "FROM [User] u "
        + "JOIN Employees e ON u.user_id = e.employee_id " + "WHERE u.user_id = ?";
    String sqldate = "UPDATE [User] SET last_login = GETDATE() WHERE user_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement datestmt = conn.prepareStatement(sqldate);
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String dbPassword = rs.getString("password");
        String fname = rs.getString("first_name");
        String lname = rs.getString("last_name");

        if (dbPassword == null) {
          // Password is NULL, prompt for a new password
          String newPassword = promptForPassword();
          if (newPassword == null) {
            // User cancelled the password entry
            return null;
          }
          // Hash the new password
          String hashedNewPassword = hashPassword(newPassword);
          // Update the password in the database
          updatePassword(userId, hashedNewPassword);
          dbPassword = hashedNewPassword;
        }

        // Hash the input password
        String hashedPassword = hashPassword(password);

        name = fname + " " + lname;

        // Check if the provided password matches
        if (hashedPassword.equals(dbPassword)) {
          Role role = Role.valueOf(rs.getString("role").toUpperCase());
          user = new User(userId, dbPassword, role, name);

          datestmt.setInt(1, userId);
          datestmt.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }

  private static void updatePassword(int userId, String newPassword) {
    String sql = "UPDATE [User] SET password = ? WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, newPassword);
      stmt.setInt(2, userId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static String promptForPassword() {
    return JOptionPane.showInputDialog(null, "Please enter a new password:", "Password Required",
        JOptionPane.PLAIN_MESSAGE);
  }

  private static String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(password.getBytes());
      return bytesToHex(digest);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder(2 * bytes.length);
    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString().toUpperCase();
  }
}
