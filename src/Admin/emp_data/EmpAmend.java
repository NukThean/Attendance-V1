package Admin.emp_data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import utils.*;
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

    getStaffInfo(id, amendFrame.txtid, amendFrame.txtname1, amendFrame.txtname2, amendFrame.txtsex,
        amendFrame.txtphone, amendFrame.txtemail, amendFrame.txtnid, amendFrame.txtposition,
        amendFrame.cmbdep, amendFrame.txtEshift1, amendFrame.txtEshift2, amendFrame.txtSshift1,
        amendFrame.txtSshift2, amendFrame.txtblank1, amendFrame.txtblank2, amendFrame.cmbTime1,
        amendFrame.cmbTime2, amendFrame.txtFileLocation, amendFrame.chkSunday, amendFrame.chkMonday,
        amendFrame.chkTuesday, amendFrame.chkWednesday, amendFrame.chkThursday,
        amendFrame.chkFriday, amendFrame.chkSaturday, amendFrame.txtAnnualLeave,
        amendFrame.txtSickLeave, amendFrame.txtSpecialLeave, amendFrame.txtBlockLeave);
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
      JTextField txtname2, JTextField txtsex, JTextField txtphone, JTextField txtemail,
      JTextField txtnid, JTextField txtposition, JComboBox<String> cmbdep, JTextField txtEshift1,
      JTextField txtEshift2, JTextField txtSshift1, JTextField txtSshift2, JTextField txtblank1,
      JTextField txtblank2, JComboBox<String> cmbTime1, JComboBox<String> cmbTime2,
      JTextField txtFileLocation, JCheckBox day1, JCheckBox day2, JCheckBox day3, JCheckBox day4,
      JCheckBox day5, JCheckBox day6, JCheckBox day7, JTextField txtAnnualLeave,
      JTextField txtSickLeave, JTextField txtSpecialLeave, JTextField txtBlockLeave) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      System.out.println("Info_input_getStaffInfo: Connected to the database");

      String sql =
          "SELECT E.EMP_IMG, E.EMPLOYEE_ID, E.FIRST_NAME, E.LAST_NAME, E.SEX, E.PHONE, E.EMAIL, E.NID, E.POSITION, E.DEPARTMENT, S.start_shift, S.end_shift, L.annual_leave, L.sick_leave, L.special_leave, L.block_leave,"
              + "D.monday, D.tuesday, D.wednesday, D.thursday, D.friday, D.saturday, D.sunday "
              + "FROM Employees AS E INNER JOIN ShiftSchedule AS S "
              + "ON E.employee_id = S.employee_id " + "INNER JOIN DayShift AS D "
              + "ON E.employee_id = D.employee_id " + "INNER JOIN LeavesBalance AS L "
              + "ON E.employee_id = L.employee_id " + "WHERE E.EMPLOYEE_ID = ? ";


      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      if (rs.next()) {
        int fetchid = rs.getInt("EMPLOYEE_ID");
        String firstName = rs.getString("FIRST_NAME");
        String lastName = rs.getString("LAST_NAME");
        String sex = rs.getString("SEX");
        String phone = rs.getString("PHONE");
        String email = rs.getString("EMAIL");
        String nid = rs.getString("NID");
        String position = rs.getString("POSITION");
        String department = rs.getString("DEPARTMENT");
        String startShift24h = rs.getString("start_shift");
        String endShift24h = rs.getString("end_shift");
        boolean monday = rs.getBoolean("monday");
        boolean tuesday = rs.getBoolean("tuesday");
        boolean wednesday = rs.getBoolean("wednesday");
        boolean thursday = rs.getBoolean("thursday");
        boolean friday = rs.getBoolean("Friday");
        boolean saturday = rs.getBoolean("saturday");
        boolean sunday = rs.getBoolean("sunday");

        float annaul_leave = rs.getFloat("annual_leave");
        float sick_leave = rs.getFloat("sick_leave");
        float special_leave = rs.getFloat("special_leave");
        float block_leave = rs.getFloat("block_leave");


        String startShift12h = ConvertTimeFormat.ConvertTo12h(startShift24h);
        String endShift12h = ConvertTimeFormat.ConvertTo12h(endShift24h);

        String startShift1 = startShift12h.substring(0, 2);
        String startShift2 = startShift12h.substring(3, 5);

        String endShift1 = endShift12h.substring(0, 2);
        String endShift2 = endShift12h.substring(3, 5);

        String AmPm1 = startShift12h.substring(9, 11);
        String AmPm2 = endShift12h.substring(9, 11);

        if (AmPm1.equals("AM")) {
          System.out.println("you fool");
          cmbTime1.setSelectedIndex(0);
        } else if (AmPm1.equals("PM")) {
          cmbTime1.setSelectedIndex(1);
        }

        if (AmPm2.equals("AM")) {
          cmbTime2.setSelectedIndex(0);
        } else if (AmPm2.equals("PM")) {
          cmbTime2.setSelectedIndex(1);
        }

        txtid.setText(String.valueOf(fetchid));
        txtname1.setText(firstName);
        txtname2.setText(lastName);
        txtsex.setText(sex);
        txtphone.setText(phone);
        txtemail.setText(email);
        txtnid.setText(nid);
        txtposition.setText(position);
        cmbdep.setSelectedItem(department);
        txtSshift1.setText(startShift1);
        txtSshift2.setText(startShift2);
        txtEshift1.setText(endShift1);
        txtEshift2.setText(endShift2);
        chkMonday.setSelected(monday);
        chkTuesday.setSelected(tuesday);
        chkWednesday.setSelected(wednesday);
        chkThursday.setSelected(thursday);
        chkFriday.setSelected(friday);
        chkSaturday.setSelected(saturday);
        chkSunday.setSelected(sunday);
        txtFileLocation.setText("Drop or Select Pic");

        txtAnnualLeave.setText(String.valueOf(annaul_leave));
        txtSickLeave.setText(String.valueOf(sick_leave));
        txtSpecialLeave.setText(String.valueOf(special_leave));
        txtBlockLeave.setText(String.valueOf(block_leave));

        txtblank1.setText(":");
        txtblank2.setText(":");

        System.out.println("cmbTime1 item count: " + cmbTime1.getItemCount());
        System.out.println("cmbTime2 item count: " + cmbTime2.getItemCount());
        System.out.println("cmbTime1 selected index: " + cmbTime1.getSelectedIndex());
        System.out.println("cmbTime2 selected index: " + cmbTime2.getSelectedIndex());

        System.out.println("Staff information fetched for Amend successfully!");
      } else {
        System.out.println("ERROR Fetching data with ID: " + id);
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

  public static void updateStaffInfo(Employee staff) {
    Connection conn = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement pstmt3 = null;

    try {
      conn = DatabaseConnection.getConnection();
      System.out.println("Info_input_update: Connected to the database");

      String sql =
          "UPDATE Employees SET FIRST_NAME = ?, LAST_NAME = ?, SEX = ?, PHONE = ?, EMAIL = ?, NID = ?, POSITION = ?, DEPARTMENT = ? WHERE EMPLOYEE_ID = ?";
      String sql2 =
          "UPDATE ShiftSchedule SET start_shift = ?, end_shift = ? WHERE EMPLOYEE_ID = ? ";
      String sql3 =
          "UPDATE LeavesBalance SET annual_leave = ?, sick_leave = ?, special_leave = ?, block_leave = ? WHERE EMPLOYEE_ID = ? ";

      pstmt1 = conn.prepareStatement(sql);
      pstmt2 = conn.prepareStatement(sql2);
      pstmt3 = conn.prepareStatement(sql3);


      pstmt1.setString(1, staff.getFirstName());
      pstmt1.setString(2, staff.getLastName());
      pstmt1.setString(3, staff.getSex());
      pstmt1.setInt(4, staff.getPhone());
      pstmt1.setString(5, staff.getEmail());
      pstmt1.setInt(6, staff.getNid());
      pstmt1.setString(7, staff.getPosition());
      pstmt1.setString(8, staff.getDepartment());
      pstmt1.setInt(9, staff.getId());



      pstmt2.setString(1, staff.getStartshift());
      pstmt2.setString(2, staff.getEndshift());
      pstmt2.setInt(3, staff.getId());


      pstmt3.setFloat(1, staff.getAnnaul_leave());
      pstmt3.setFloat(2, staff.getSick_leave());
      pstmt3.setFloat(3, staff.getSpecial_leave());
      pstmt3.setFloat(4, staff.getBlock_leave());
      pstmt3.setInt(5, staff.getId());



      pstmt1.executeUpdate();
      pstmt2.executeUpdate();
      pstmt3.executeUpdate();
      System.out.println("Staff information updated successfully!");

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (pstmt1 != null) {
          pstmt1.close();
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
    String sex = txtsex.getText();
    String phone = txtphone.getText();
    String email = txtemail.getText();
    String nid = txtnid.getText();
    String position = txtposition.getText();
    String department = (String) cmbdep.getSelectedItem();
    String filelocation = txtFileLocation.getText();


    String startShift = ConvertTimeFormat.ConvertTo24h(txtSshift1.getText() + txtblank1.getText()
        + txtSshift2.getText() + ":00 " + (String) cmbTime1.getSelectedItem());
    String endShift = ConvertTimeFormat.ConvertTo24h(txtEshift1.getText() + txtblank2.getText()
        + txtEshift2.getText() + ":00 " + (String) cmbTime2.getSelectedItem());


    float annaul_leave = Float.parseFloat(txtAnnualLeave.getText());
    float sick_leave = Float.parseFloat(txtSickLeave.getText());
    float special_leave = Float.parseFloat(txtSpecialLeave.getText());
    float block_leave = Float.parseFloat(txtBlockLeave.getText());

    Employee staff = new Employee(filelocation, id, firstName, lastName, sex,
        Integer.parseInt(phone), email, Integer.parseInt(nid), position, department, startShift,
        endShift, annaul_leave, sick_leave, special_leave, block_leave);

    if (e.getSource() == btnmore) {
      System.out.println("Preparing to amend Staff information");
      updateStaffInfo(staff);
      dispose();
      showAmendDialog();
      if (filelocation != null && !filelocation.isEmpty()
          && !filelocation.equals("Drop or Select Pic")) {
        uploadImage(filelocation, id);
      }

    } else if (e.getSource() == btnfinish) {
      System.out.println("Preparing to amend Staff information");
      updateStaffInfo(staff);
      dispose();
      if (filelocation != null && !filelocation.isEmpty()
          && !filelocation.equals("Drop or Select Pic")) {
        uploadImage(filelocation, id);
      }
    }
  }
}
