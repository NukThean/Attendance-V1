package src.loginpage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import src.utils.DatabaseConnection;

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



}
