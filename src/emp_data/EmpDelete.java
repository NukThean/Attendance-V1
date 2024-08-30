package emp_data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import utils.*;
import java.awt.*;

public class EmpDelete extends EmpInput {
  public EmpDelete() {}

  public void showDeleteDialog() {
    DialogUtils.createDialog("Attendance System V1", "Delete by ID: ", new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JTextField textField = (JTextField) ((JPanel) ((JButton) e.getSource()).getParent()
            .getParent().getComponent(0)).getComponent(1);
        String idForDelete = textField.getText();
        try {
          int idDelete = Integer.parseInt(idForDelete.trim());
          SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close the dialog
          showConfirmationDialog(idDelete); // Show confirmation dialog
          // handleDeletion(idDelete); // Call the deletion handling method
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  private void handleDeletion(int id) {
    // Implement the logic to handle the deletion using the provided ID
    System.out.println("Delete requested for ID: " + id);
    // You can call updateStaffInfo or any other logic here
    deleteStaffInfo(id);
    // updateButtonState();
  }

  private void showConfirmationDialog(int id) {
    int staffId = id;
    String staffName = getStaffNameById(staffId);
    JFrame frame = new JFrame("Attendance");
    JPanel panel1 = new JPanel(new GridBagLayout());
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel("<html>Are you sure that you want to delete?<br>ID: " + id
        + "<br> Name: " + staffName + "</html>");
    // JLabel label2 = new JLabel(labelText2);
    // JTextField textField = new JTextField();
    JButton yesButton = new JButton("Yes");
    JButton noButton = new JButton("No");
    Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

    label.setFont(customFont);
    // label2.setFont(customFont);
    // textField.setPreferredSize(new Dimension(120, 20));
    // textField.setMinimumSize(new Dimension(120, 20));
    yesButton.setBackground(Color.RED);
    yesButton.setFocusPainted(false);
    noButton.setBackground(Color.LIGHT_GRAY);
    noButton.setFocusPainted(false);

    yesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Yes clicked");
        handleDeletion(id);
        frame.dispose();
      }
    });

    noButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel1.add(label, gbc);

    gbc.gridx = 1;
    // panel1.add(textField, gbc);

    panel1.setPreferredSize(new Dimension(250, 80));
    panel1.setBackground(Color.WHITE);

    panel2.setPreferredSize(new Dimension(250, 40));
    panel2.setBackground(new Color(15, 41, 102));
    panel2.add(yesButton);
    panel2.add(Box.createRigidArea(new Dimension(20, 0))); // 20-pixel horizontal gap
    panel2.add(noButton);

    frame.setResizable(false);

    yesButton.addActionListener(this);
    noButton.addActionListener(this);

    frame.setLocationRelativeTo(null);
    Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attandance V1\\img\\naga.png");
    frame.setIconImage(icon);
    frame.add(panel1, BorderLayout.CENTER);
    frame.add(panel2, BorderLayout.SOUTH);
    frame.pack();
    frame.setLocationRelativeTo(null); // Center the frame after packing
    frame.setVisible(true);
  }

  public String getStaffNameById(int id) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String empname = null;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("EmpDelete.getStaffNameById: Connected to the database");

      // Prepare the SQL INSERT statement
      String sql = "SELECT FIRST_NAME, LAST_NAME FROM Employees WHERE EMPLOYEE_ID = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id); // Set the ID parameter
      rs = pstmt.executeQuery();
      // Check if a result is returned
      if (rs.next()) {
        // int fetchid = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");

        // Execute the statement
        empname = firstName + " " + lastName;
      }

      // System.out.println("All staff information inserted successfully!");

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close the resources
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return empname;
  }

  public static void deleteStaffInfo(int id) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement pstmt3 = null;
    PreparedStatement pstmt4 = null;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("EmpDelete.deleteStaffInfo: Connected to the database");

      // Prepare the SQL DELETE statement
      String sql = "DELETE FROM Employees WHERE EMPLOYEE_ID = ?";
      String sql2 = "DELETE FROM [User] WHERE USER_ID = ?";
      String sql3 = "DELETE FROM ShiftSchedule WHERE EMPLOYEE_ID = ?";
      String sql4 = "DELETE FROM Attendance WHERE EMPLOYEE_ID = ?";

      // Create the PreparedStatement
      pstmt4 = conn.prepareStatement(sql4);
      pstmt3 = conn.prepareStatement(sql3);
      pstmt2 = conn.prepareStatement(sql2);
      pstmt = conn.prepareStatement(sql);

      // Set the parameter for the delete
      pstmt4.setInt(1, id);
      pstmt3.setInt(1, id);
      pstmt2.setInt(1, id);
      pstmt.setInt(1, id);

      // Execute the delete
      pstmt4.executeUpdate();
      pstmt3.executeUpdate();
      pstmt2.executeUpdate();
      pstmt.executeUpdate();

      System.out.println("Staff with ID " + id + " deleted successfully!");

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close the resources
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
