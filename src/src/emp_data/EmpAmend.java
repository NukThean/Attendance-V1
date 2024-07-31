package src.emp_data;

import src.utils.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

import java.awt.*;

public class EmpAmend extends EmpInput {
  private boolean isDialogOpen = false; // Flag to track dialog state

  public EmpAmend() {
    btnmore.setText("More");

    btnmore.addActionListener(this);
    btnfinish.addActionListener(this);
  }

  private void handleAmendment(int id) {
    System.out.println("Amendment requested for ID: " + id);
    EmpAmend amendFrame = new EmpAmend();
    amendFrame.setVisible(true);

    getStaffInfo(id, amendFrame.txtid, amendFrame.txtname1, amendFrame.txtname2,
        amendFrame.txtphone, amendFrame.txtemail, amendFrame.txtnid, amendFrame.txtposition,
        amendFrame.cmbdep);
  }

  public void showAmendDialog() {
    if (!isDialogOpen) {
      isDialogOpen = true;
      DialogUtils.createDialog("Attendance System V1", "Amend by ID: ", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JTextField textField = (JTextField) ((JPanel) ((JButton) e.getSource()).getParent()
              .getParent().getComponent(0)).getComponent(1);
          String idForAmend = textField.getText();
          try {
            int idAmend = Integer.parseInt(idForAmend.trim());
            SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
            handleAmendment(idAmend);
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
          } finally {
            isDialogOpen = false;
          }
        }
      });
    }
  }

  public static void getStaffInfo(int id, JTextField txtid, JTextField txtname1,
      JTextField txtname2, JTextField txtphone, JTextField txtemail, JTextField txtnid,
      JTextField txtposition, JComboBox<String> cmbdep) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = DatabaseConnection.getConnection();
      System.out.println("Info_input_getStaffInfo: Connected to the database");

      String sql = "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT FROM Employees WHERE EMPLOYEE_ID = ?";

      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      if (rs.next()) {
        int fetchid = rs.getInt("EMPLOYEE_ID");
        String firstName = rs.getString("FIRST_NAME");
        String lastName = rs.getString("LAST_NAME");
        String phone = rs.getString("PHONE");
        String email = rs.getString("EMAIL");
        String nid = rs.getString("NID");
        String position = rs.getString("POSITION");
        String department = rs.getString("DEPARTMENT");

        txtid.setText(String.valueOf(fetchid));
        txtname1.setText(firstName);
        txtname2.setText(lastName);
        txtphone.setText(phone);
        txtemail.setText(email);
        txtnid.setText(nid);
        txtposition.setText(position);
        cmbdep.setSelectedItem(department);

        System.out.println("Staff information fetched for Amend successfully!");
      } else {
        System.out.println("No staff found with ID: " + id);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
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

  public static void updateStaffInfo(emp_info staff) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = DatabaseConnection.getConnection();
      System.out.println("Info_input_update: Connected to the database");

      String sql = "UPDATE Employees SET FIRST_NAME = ?, LAST_NAME = ?, PHONE = ?, EMAIL = ?, NID = ?, POSITION = ?, DEPARTMENT = ? WHERE EMPLOYEE_ID = ?";

      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, staff.getFirstName());
      pstmt.setString(2, staff.getLastName());
      pstmt.setInt(3, staff.getPhone());
      pstmt.setString(4, staff.getEmail());
      pstmt.setInt(5, staff.getNid());
      pstmt.setString(6, staff.getPosition());
      pstmt.setString(7, staff.getDepartment());
      pstmt.setInt(8, staff.getId());

      pstmt.executeUpdate();
      System.out.println("Staff information updated successfully!");

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
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

  @Override
  public void actionPerformed(ActionEvent e) {
    int id = Integer.parseInt(txtid.getText());
    String firstName = txtname1.getText();
    String lastName = txtname2.getText();
    String phone = txtphone.getText();
    String email = txtemail.getText();
    String nid = txtnid.getText();
    String position = txtposition.getText();
    String department = (String) cmbdep.getSelectedItem();

    emp_info staff = new emp_info(id, firstName, lastName, Integer.parseInt(phone), email,
        Integer.parseInt(nid), position, department);

    if (e.getSource() == btnmore) {
      System.out.println("Preparing to amend Staff information");
      updateStaffInfo(staff);
      dispose();
      showAmendDialog();

    } else if (e.getSource() == btnfinish) {
      System.out.println("Preparing to amend Staff information");
      updateStaffInfo(staff);
      dispose();
    }
  }
}
