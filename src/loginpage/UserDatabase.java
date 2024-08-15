package loginpage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import utils.DatabaseConnection;

public class UserDatabase {
  private static final String PEPPER = System.getenv("ATTDANCE_SYSTEM_USER_PEPPERING_PW");
  private static String name;
  public static String getUserName;

  public static boolean CheckuserIdExist(int userId) {
    String sql = "SELECT COUNT(*) FROM [User] WHERE user_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getInt(1) > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean CheckuserpwExist(int userId) {
    String sql = "SELECT password FROM [User] WHERE user_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        // Check if the password is not null and not empty
        String password = rs.getString("password");
        return password != null && !password.isEmpty();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean CheckuserpwTrue(int userId, String providedPassword) {
    String sql = "SELECT password, salt FROM [User] WHERE user_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String storedPassword = rs.getString("password");
        String salt = rs.getString("salt");

        // Hash the provided password with the retrieved salt and pepper
        String hashedProvidedPassword = hashPassword(providedPassword, salt, PEPPER);

        // Compare the hashed provided password with the stored password
        return hashedProvidedPassword.equals(storedPassword);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }



  public static User authenticate(int userId, String password) {
    // Retrieve the user information
    User user = null;
    String sql = "SELECT u.password, u.salt, u.role, e.first_name, e.last_name " + "FROM [User] u "
        + "JOIN Employees e ON u.user_id = e.employee_id " + "WHERE u.user_id = ?";
    String sqldate = "UPDATE [User] SET last_login = GETDATE() WHERE user_id = ?";
    // System.out.println("PEPPER: " + PEPPER);
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement datestmt = conn.prepareStatement(sqldate);
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String dbPassword = rs.getString("password");
        String salt = rs.getString("salt");
        String fname = rs.getString("first_name");
        String lname = rs.getString("last_name");

        // Hash the input password with the stored salt and pepper
        String hashedPassword = hashPassword(password, salt, PEPPER);

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

  private static void updatePassword(int userId, String newPassword, String salt) {
    String sql = "UPDATE [User] SET password = ?, salt = ? WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, newPassword);
      stmt.setString(2, salt);
      stmt.setInt(3, userId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static String promptForPassword() {
    return JOptionPane.showInputDialog(null, "Please enter a new password:", "Password Required",
        JOptionPane.PLAIN_MESSAGE);
  }

  private static String hashPassword(String password, String salt, String pepper) {
    if (pepper == null) {
      throw new IllegalStateException("Pepper value is not set.");
    }
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt.getBytes()); // Add salt to the hash
      md.update(pepper.getBytes()); // Add pepper to the hash
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

  private static String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return bytesToHex(salt);
  }

  public static String handleNullPassword(int userId) {
    // Prompt the user for a new password
    String newPassword = promptForPassword();
    if (newPassword == null) {
      // User cancelled the password entry
      return null;
    }
    // Generate a new salt
    String salt = generateSalt();
    // Hash the new password with the salt and pepper
    String hashedNewPassword = hashPassword(newPassword, salt, PEPPER);
    // Update the password and salt in the database
    updatePassword(userId, hashedNewPassword, salt);
    return hashedNewPassword;
  }

  public static void fail_login(int userId) {
    int fail_attm = 0;
    String selectSql = "SELECT failed_attempts FROM [User] WHERE user_id = ?";
    String updateSql = "UPDATE [User] SET failed_attempts = ? WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

      // Retrieve the current failed_attempts value
      selectStmt.setInt(1, userId);
      ResultSet rs = selectStmt.executeQuery();

      if (rs.next()) {
        fail_attm = rs.getInt("failed_attempts");
      }

      // Increment the failed_attempts value
      fail_attm += 1;

      // Update the failed_attempts value in the database
      updateStmt.setInt(1, fail_attm);
      updateStmt.setInt(2, userId);
      updateStmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void reset_fail_attempts(int userId) {
    String resetSql = "UPDATE [User] SET failed_attempts = 0 WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement resetStmt = conn.prepareStatement(resetSql)) {

      // Reset the failed_attempts value in the database
      resetStmt.setInt(1, userId);
      resetStmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void unblock_login(int userId) {
    String resetSql = "UPDATE [User] SET failed_attempts = 0 WHERE user_id = ?";
    String resetStat = "UPDATE [User] SET account_satus = ? WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement resetStmt = conn.prepareStatement(resetSql);
        PreparedStatement resetStmtt = conn.prepareStatement(resetStat)) {

      // Reset the failed_attempts value in the database
      resetStmt.setInt(1, userId);
      resetStmt.setString(2, "active");
      resetStmt.setInt(3, userId);
      resetStmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void set_status(int userId) {
    String resetSql = "UPDATE [User] SET account_status = ? WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement resetStmt = conn.prepareStatement(resetSql)) {

      // Reset the failed_attempts value in the database
      resetStmt.setString(1, "Blocked");
      resetStmt.setInt(2, userId);
      resetStmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static int get_failed_attempts(int userId) {
    int fail_attm = -1; // Initialize with -1 to indicate an error if the user is not found
    String sql = "SELECT failed_attempts FROM [User] WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      // Set the user_id parameter in the SQL query
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      // If a result is returned, retrieve the failed_attempts value
      if (rs.next()) {
        fail_attm = rs.getInt("failed_attempts");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return fail_attm;
  }



}
