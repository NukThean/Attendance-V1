package Admin.LeaveRequest;

import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import Admin.LoginUtils.Admin;
import Admin.LoginUtils.AdminDatabase;
import Admin.emp_data.Employee;
import loginpage.User;
import utils.DatabaseConnection;
import utils.TableUtils;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.sql.ResultSet;


public class LeaveReqApp extends JFrame {
  @SuppressWarnings("unused")
  private User user;
  private int empId;
  private int leaveId;
  static JLabel lblLeaveId = new JLabel("");
  static JLabel lblEmpId = new JLabel("");
  static JLabel lblName = new JLabel("");
  static JLabel lblStatus = new JLabel("");
  static JLabel lblLeaveType = new JLabel("Leave Type: ");
  static JLabel lblAppliDate = new JLabel("Application Date: ");
  static JLabel lblStartDate = new JLabel("From: ");
  static JLabel lblEndDate = new JLabel("To: ");
  static JLabel lblNoDay = new JLabel("No. of day(s): ");
  static JLabel lblBalanceDue = new JLabel("Balance Due: ");
  static JLabel lblReason = new JLabel("Reason: ");

  static JLabel txtLeaveType = new JLabel();
  static JTextField txtAppliDate = new JTextField();
  static JTextField txtStartDate = new JTextField();
  static JTextField txtEndDate = new JTextField();
  static JTextField txtNoDay = new JTextField();
  static JTextField txtBalanceDue = new JTextField();
  // JTextField txtReason = new JTextField();
  static JButton btnApprove = new JButton("Approve");
  static JButton btnReject = new JButton("Reject");

  JTextArea txtReason = new JTextArea(20, 20);

  JScrollPane scrollpane = new JScrollPane();

  private enum LeaveType {
    ANNUAL_LEAVE, SICK_LEAVE, SPECIAL_LEAVE, BLOCK_LEAVE
  }

  LeaveType State = null;

  public LeaveReqApp() {
    setLayout(new BorderLayout());
    JPanel MainLeave = new JPanel(new BorderLayout());
    JPanel inputPanel = new JPanel(new GridBagLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout());

    btnApprove.setBackground(new Color(124, 208, 70));
    btnReject.setBackground(new Color(239, 89, 64));

    GridBagConstraints err = new GridBagConstraints();
    err.insets = new Insets(10, 10, 10, 10);
    err.fill = GridBagConstraints.HORIZONTAL;
    err.weightx = 1;
    err.weighty = 1;
    err.gridx = 0;
    err.gridy = 0;

    addlabeltopanel(inputPanel, lblEmpId, err, 1, 0, null);
    addlabeltopanel(inputPanel, lblName, err, 1, 1, null);
    addlabeltopanel(inputPanel, lblLeaveId, err, 2, 0, null);
    addlabeltopanel(inputPanel, lblStatus, err, 2, 1, null);

    addlabeltopanel(inputPanel, lblAppliDate, err, 3, 0, null);
    addlabeltopanel(inputPanel, lblStartDate, err, 4, 0, null);
    addlabeltopanel(inputPanel, lblEndDate, err, 5, 0, null);
    addlabeltopanel(inputPanel, lblNoDay, err, 6, 0, null);
    addlabeltopanel(inputPanel, lblBalanceDue, err, 7, 0, null);
    addlabeltopanel(inputPanel, lblReason, err, 8, 0, null);


    addlabeltopanel(inputPanel, lblLeaveType, err, 0, 0, null);

    addlabeltopanel(inputPanel, txtLeaveType, err, 0, 1, null);
    addtxtfieldtopanel(inputPanel, txtAppliDate, err, 3, 1, null);
    addtxtfieldtopanel(inputPanel, txtStartDate, err, 4, 1, null);
    addtxtfieldtopanel(inputPanel, txtEndDate, err, 5, 1, null);
    addtxtfieldtopanel(inputPanel, txtNoDay, err, 6, 1, null);
    addtxtfieldtopanel(inputPanel, txtBalanceDue, err, 7, 1, null);

    txtReason.setLineWrap(true); // Enable line wrapping
    txtReason.setWrapStyleWord(true); // Wrap at word boundaries
    txtReason.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    txtReason.setEditable(false);

    TableUtils tableutil = new TableUtils();
    scrollpane = tableutil.getScrollPane2(txtReason);

    scrollpane.setPreferredSize(new Dimension(150, 100)); // Width and height
    scrollpane.setMinimumSize(new Dimension(150, 100)); // Width and height

    err.gridx = 1;
    err.gridy = 8;
    inputPanel.add(scrollpane, err);


    inputPanel.setPreferredSize(new Dimension(400, 460));
    buttonPanel.setPreferredSize(new Dimension(400, 40));
    buttonPanel.add(btnApprove);
    buttonPanel.add(btnReject);
    // Add the input panel to the MainLeave panel
    MainLeave.add(inputPanel, BorderLayout.CENTER);
    MainLeave.add(buttonPanel, BorderLayout.SOUTH);

    // Add MainLeave to the content pane
    add(MainLeave, BorderLayout.CENTER);

    setPreferredSize(new Dimension(400, 500));
    setBackground(Color.WHITE);
    setVisible(true);
    pack(); // This will ensure that the frame is sized correctly
    setLocationRelativeTo(null);

    btnApprove.addActionListener(e -> {
      ApproveLeave(leaveId);
      dispose();
      LeaveReq.populateLeave();
    });
    btnReject.addActionListener(e -> {
      RejectLeave(leaveId);
      dispose();
      LeaveReq.populateLeave();
    });
  }



  private void addlabeltopanel(JPanel panell, JLabel Label, GridBagConstraints err, int gridy,
      int gridx, Dimension size) {
    err.gridy = gridy;
    err.gridx = gridx;
    if (size != null) {
      Label.setPreferredSize(size);
      Label.setMinimumSize(size);
      Label.setMaximumSize(size);
    } else {
      Label.setPreferredSize(new Dimension(65, 30));
      Label.setMinimumSize(new Dimension(65, 30));
    }
    Label.setBackground(Color.WHITE);
    Label.setOpaque(true);
    panell.add(Label, err);

  }

  private void addtxtfieldtopanel(JPanel panelll, JTextField field, GridBagConstraints nghz,
      int gridy, int gridx, Dimension size) {
    nghz.gridx = gridx;
    nghz.gridy = gridy;
    if (size != null) {
      field.setPreferredSize(size);
      field.setMinimumSize(size);
      field.setMaximumSize(size);
    } else {
      field.setPreferredSize(new Dimension(120, 30));
      field.setMinimumSize(new Dimension(120, 30));
    }
    field.setBackground(Color.white);
    field.setOpaque(true);
    field.setForeground(Color.BLACK);
    field.setEditable(false);
    field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    panelll.add(field, nghz);
  }


  public void ViewLeave(int LeaveId) {
    leaveId = LeaveId;
    String sql =
        "SELECT L.employee_id, E.first_name, E.last_name, L.leave_type,  L.application_date,  L.start_date,  L.end_date,  L.reason,  L.status "
            + ", LB.remain_annual_leave, LB.remain_sick_leave, LB.remain_special_leave, LB.remain_block_leave "
            + "FROM LEAVES AS L INNER JOIN Employees AS E " + "ON L.employee_id = E.employee_id "
            + "INNER JOIN LeavesBalance AS LB " + "ON L.employee_id = LB.employee_id "
            + "WHERE L.leave_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, LeaveId); // Set the user ID parameter
      System.out.println("LeaveReqApp: Connected to database");
      System.out.println("Request to view leave: " + LeaveId);

      ResultSet rs = pstmt.executeQuery();

      // Process the result set here
      while (rs.next()) {
        empId = rs.getInt("employee_id");
        String leaveType = rs.getString("leave_type");
        Date applicationDate = rs.getDate("application_date");
        Date startDate = rs.getDate("start_date");
        Date endDate = rs.getDate("end_date");
        String reason = rs.getString("reason");
        String status = rs.getString("status");

        float remain_annaul_leave = rs.getFloat("remain_annual_leave");
        float remain_sick_leave = rs.getFloat("remain_sick_leave");
        float remain_special_leave = rs.getFloat("remain_special_leave");
        float remain_block_leave = rs.getFloat("remain_block_leave");

        lblLeaveId.setText("Leave ID: " + LeaveId);
        lblName.setText(
            "Employee Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
        lblEmpId.setText("Employee ID: " + String.valueOf(empId));
        txtLeaveType.setText(leaveType);
        txtAppliDate.setText(String.valueOf(applicationDate));
        txtStartDate.setText(String.valueOf(startDate));
        txtEndDate.setText(String.valueOf(endDate));

        System.out.println("fool");


        txtReason.setText(reason);
        lblStatus.setText("Status: " + status);

        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate endLocalDate = endDate.toLocalDate();
        float daysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        txtNoDay.setText(daysBetween + " Days");

        // if (leaveType.equalsIgnoreCase("Annual Leave")) {
        // txtBalanceDue.setText(String.valueOf(remain_annaul_leave - daysBetween) + " Days");
        // } else if (leaveType.equalsIgnoreCase("Sick Leave")) {
        // txtBalanceDue.setText(String.valueOf(remain_sick_leave - daysBetween) + " Days");
        // } else if (leaveType.equalsIgnoreCase("Special Leave")) {
        // txtBalanceDue.setText(String.valueOf(remain_special_leave - daysBetween) + " Days");
        // } else if (leaveType.equalsIgnoreCase("Block Leave")) {
        // txtBalanceDue.setText(String.valueOf(remain_block_leave - daysBetween) + " Days");
        // }

        float balanceDue;

        switch (leaveType.toLowerCase()) {
          case "annual leave" -> {
            balanceDue = remain_annaul_leave - daysBetween;
            State = LeaveType.ANNUAL_LEAVE;
          }
          case "sick leave" -> {
            balanceDue = remain_sick_leave - daysBetween;
            State = LeaveType.SICK_LEAVE;
          }
          case "special leave" -> {
            balanceDue = remain_special_leave - daysBetween;
            State = LeaveType.SPECIAL_LEAVE;
          }
          case "block leave" -> {
            balanceDue = remain_block_leave - daysBetween;
            State = LeaveType.BLOCK_LEAVE;
          }
          default -> {
            balanceDue = 0.0f; // Handle unknown leave types
            State = null; // Or handle it with a specific default state if applicable
          }
        }

        txtBalanceDue.setText(balanceDue + " Days");



      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
  }

  public void ApproveLeave(int LEAVE_ID) {
    Connection conn = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;

    try {
      conn = DatabaseConnection.getConnection();
      System.out.println("Info_input_update: Connected to the database");


      String LeaveType = null;
      switch (State) {
        case ANNUAL_LEAVE -> {
          LeaveType = "remain_annual_leave";
        }
        case SICK_LEAVE -> {
          LeaveType = "remain_sick_leave";
        }
        case SPECIAL_LEAVE -> {
          LeaveType = "remain_special_leave";
        }
        case BLOCK_LEAVE -> {
          LeaveType = "remain_block_leave";
        }
        default -> {
          LeaveType = null;
        }
      }
      String sql = "UPDATE Leaves SET status = 'Approved', approver= ? WHERE LEAVE_ID = ?";
      String sql2 = "UPDATE LeavesBalance SET " + LeaveType + " = " + LeaveType
          + " - ? WHERE employee_id = ?";


      pstmt1 = conn.prepareStatement(sql);
      pstmt2 = conn.prepareStatement(sql2);


      pstmt1.setString(1, AdminDatabase.getAdminName(Admin.getuserId()));
      pstmt1.setInt(2, LEAVE_ID);
      System.out.println("Approve leave by: " + AdminDatabase.getAdminName(Admin.getuserId()));

      pstmt2.setFloat(1, Float.parseFloat(txtNoDay.getText().replaceAll("[^\\d.]", "")));
      pstmt2.setInt(2, empId);


      pstmt2.executeUpdate();
      pstmt1.executeUpdate();

      System.out.println("Approve leave successfully!");
      JOptionPane.showMessageDialog(this, "Leave has been approved", "Success",
          JOptionPane.INFORMATION_MESSAGE);

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

  public void RejectLeave(int LEAVE_ID) {
    Connection conn = null;
    PreparedStatement pstmt1 = null;

    try {
      conn = DatabaseConnection.getConnection();
      System.out.println("LeaveReqApp.RejectLeave: Connected to the database");

      String sql = "UPDATE Leaves SET status = 'Rejected', approver= ? WHERE LEAVE_ID = ?";


      pstmt1 = conn.prepareStatement(sql);


      pstmt1.setString(1, AdminDatabase.getAdminName(Admin.getuserId()));
      pstmt1.setInt(2, LEAVE_ID);
      System.out.println("Reject leave by: " + AdminDatabase.getAdminName(Admin.getuserId()));



      pstmt1.executeUpdate();

      System.out.println("Reject leave successfully!");
      JOptionPane.showMessageDialog(this, "Leave has been rejected", "Success",
          JOptionPane.INFORMATION_MESSAGE);

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


  public static void main(String[] args) {
    new LeaveReqApp();
  }

}

