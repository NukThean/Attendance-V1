package src.userPage;

import javax.swing.*;

import src.loginpage.User;
import src.utils.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class userPage extends JFrame {
  private User user;
  private JLabel lblName;
  private JButton btnCheckin;
  private JButton btnCheckout;
  private JButton btnviewrec;

  public userPage(User user) {
    this.user = user;
    int userId = user.getuserId();

    setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel northPanel = new JPanel(new GridBagLayout());
    northPanel.setPreferredSize(new Dimension(0, 50));
    northPanel.setMinimumSize(new Dimension(0, 50));
    northPanel.setBackground(Color.BLUE);

    // Initialize components
    lblName = new JLabel("Welcome, " + user.getName() + "!");
    btnCheckin = new JButton("Check in");
    btnCheckout = new JButton("Check out");
    btnviewrec = new JButton("View own Record");

    lblName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblName.setForeground(Color.WHITE);

    GridBagConstraints us = new GridBagConstraints();
    us.insets = new Insets(10, 10, 10, 10);
    us.fill = GridBagConstraints.HORIZONTAL;
    us.gridx = 0;
    us.gridy = 0;
    us.weightx = 1;
    us.weighty = 1;

    btnCheckin.addActionListener(this::handleCheckin);
    btnCheckout.addActionListener(this::handleCheckout);
    btnviewrec.addActionListener(this::handleViewRecord);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(btnCheckin);
    buttonPanel.add(btnCheckout);
    buttonPanel.add(btnviewrec);

    northPanel.add(lblName, us);

    // Set up the JFrame properties
    setTitle("User Page");
    setSize(480, 600); // Set the size of the frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    mainPanel.add(northPanel, BorderLayout.NORTH);
    mainPanel.add(buttonPanel, BorderLayout.CENTER);

    add(mainPanel, BorderLayout.CENTER);

    // Check the status and disable buttons accordingly
    if (isStatusActive(userId)) {
      btnCheckin.setEnabled(false);
      btnCheckout.setEnabled(true);
    } else {
      btnCheckin.setEnabled(true);
      btnCheckout.setEnabled(false);
    }
  }

  private void handleCheckin(ActionEvent e) {
    int userId = user.getuserId();
    checkIn(userId);
    btnCheckin.setEnabled(false);
    btnCheckout.setEnabled(true);
  }

  private void handleCheckout(ActionEvent e) {
    int userId = user.getuserId();
    checkOut(userId);
    btnCheckin.setEnabled(true);
    btnCheckout.setEnabled(false);
  }

  private void handleViewRecord(ActionEvent e) {
    new viewRecord(user).setVisible(true);
  }

  private void checkIn(int userId) {
    String sql = "INSERT INTO Attendance (employee_id, date, check_in_time, status) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      // Get the current date and time
      LocalDate currentDate = LocalDate.now();
      LocalTime currentTime = LocalTime.now();

      stmt.setInt(1, userId);
      stmt.setDate(2, Date.valueOf(currentDate));
      stmt.setTime(3, Time.valueOf(currentTime));
      stmt.setString(4, "Active");
      stmt.executeUpdate();

      JOptionPane.showMessageDialog(this, "Checked in successfully!", "Success",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void checkOut(int userId) {
    String selectSql = "SELECT TOP 1 attendance_id FROM Attendance WHERE employee_id = ? ORDER BY attendance_id DESC";
    String updateSql = "UPDATE Attendance SET check_out_time = ?, status = ? WHERE attendance_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

      // Retrieve the latest attendance_id
      selectStmt.setInt(1, userId);
      try (ResultSet rs = selectStmt.executeQuery()) {
        if (rs.next()) {
          int attendanceId = rs.getInt("attendance_id");

          // Set the check_out_time to the current time
          LocalTime currentTime = LocalTime.now();
          updateStmt.setTime(1, Time.valueOf(currentTime));
          updateStmt.setString(2, "Inactive");
          updateStmt.setInt(3, attendanceId);

          // Execute the update
          int rowsUpdated = updateStmt.executeUpdate();
          if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Check-out time updated successfully.", "Info",
                JOptionPane.INFORMATION_MESSAGE);
          }
        }
      }
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private boolean isStatusActive(int userId) {
    String sql = "SELECT TOP 1 status FROM Attendance WHERE employee_id = ? ORDER BY attendance_id DESC";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return "Active".equals(rs.getString("status"));
        } else {
          return false;
        }
      }
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }
}
