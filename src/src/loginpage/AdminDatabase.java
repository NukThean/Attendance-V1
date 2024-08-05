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

public class AdminDatabase {
  private static final String PEPPER = System.getenv("ATTDANCE_SYSTEM_ADMIN_PEPPERING_PW");
  private static String name;

  public static boolean CheckAdminIdExist(int adminId) {
    String sql = "SELECT COUNT(*) FROM [Admin] WHERE admin_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, adminId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getInt(1) > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean CheckAdminPwExist(int adminId) {
    String sql = "SELECT password FROM [Admin] WHERE admin_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, adminId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        String password = rs.getString("password");
        return password != null && !password.isEmpty();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean CheckAdminPwTrue(int adminId, String providedPassword) {
    String sql = "SELECT password, salt FROM [Admin] WHERE admin_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, adminId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String storedPassword = rs.getString("password");
        String salt = rs.getString("salt");

        String hashedProvidedPassword = hashPassword(providedPassword, salt, PEPPER);
        return hashedProvidedPassword.equals(storedPassword);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static Admin AdminAuthenticate(int adminId, String password) {
    Admin user = null;
    String sql = "SELECT password, salt, role FROM [Admin] WHERE admin_id = ?";
    String sqldate = "UPDATE [Admin] SET last_login = GETDATE() WHERE admin_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement datestmt = conn.prepareStatement(sqldate);
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, adminId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String dbPassword = rs.getString("password");
        String salt = rs.getString("salt");

        if (dbPassword == null) {
          String newPassword = promptForPassword();
          if (newPassword == null) {
            return null;
          }
          salt = generateSalt();
          String hashedNewPassword = hashPassword(newPassword, salt, PEPPER);
          updatePassword(adminId, hashedNewPassword, salt);
          dbPassword = hashedNewPassword;
        }

        String hashedPassword = hashPassword(password, salt, PEPPER);

        if (hashedPassword.equals(dbPassword)) {
          Role role = Role.valueOf(rs.getString("role").toUpperCase());
          user = new Admin(adminId, dbPassword, role, name);

          datestmt.setInt(1, adminId);
          datestmt.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }

  private static void updatePassword(int adminId, String newPassword, String salt) {
    String sql = "UPDATE [Admin] SET password = ?, salt = ? WHERE admin_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, newPassword);
      stmt.setString(2, salt);
      stmt.setInt(3, adminId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static String promptForPassword() {
    return JOptionPane.showInputDialog(null, "Please enter a new password:", "Password Required",
        JOptionPane.PLAIN_MESSAGE);
  }

  private static String hashPassword(String password, String salt, String pepper) {
    if (pepper == null) {
      throw new IllegalStateException("Pepper value is not set.");
    }
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt.getBytes());
      md.update(pepper.getBytes());
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
