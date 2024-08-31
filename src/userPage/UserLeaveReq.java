package userPage;

import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import loginpage.User;
import utils.DatabaseConnection;


public class UserLeaveReq extends JFrame {
  @SuppressWarnings("unused")
  private User user;
  private int userId;
  JLabel lblLeaveType = new JLabel("Leave Type: ");
  JLabel lblAppliDate = new JLabel("Application Date: ");
  JLabel lblStartDate = new JLabel("From: ");
  JLabel lblEndDate = new JLabel("To: ");
  JLabel lblNoDay = new JLabel("No. of day(s): ");
  JLabel lblBalanceDue = new JLabel("Balance Due: ");
  JLabel lblReason = new JLabel("Reason: ");

  JComboBox<String> cmbLeaveType = new JComboBox<>();
  JTextField txtAppliDate = new JTextField();
  static JTextField txtStartDate = new JTextField();
  static JTextField txtEndDate = new JTextField();
  JTextField txtNoDay = new JTextField();
  JTextField txtBalanceDue = new JTextField();
  JTextField txtReason = new JTextField();
  JButton btnSubmit = new JButton("Submit");

  public UserLeaveReq(User user) {
    this.user = user;
    this.userId = user.getuserId(); // Initialize userId
    setLayout(new BorderLayout());
    JPanel MainLeave = new JPanel(new BorderLayout());
    JPanel inputPanel = new JPanel(new GridBagLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout());

    GridBagConstraints err = new GridBagConstraints();
    err.insets = new Insets(10, 10, 10, 10);
    err.fill = GridBagConstraints.HORIZONTAL;
    err.weightx = 1;
    err.weighty = 1;
    err.gridx = 0;
    err.gridy = 0;

    addcomboboxtopanel(inputPanel, cmbLeaveType, err, 0, 1, null);
    // addlabeltopanel(inputPanel, lblAppliDate, err, 1, 0, null);
    addlabeltopanel(inputPanel, lblStartDate, err, 2, 0, null);
    addlabeltopanel(inputPanel, lblEndDate, err, 3, 0, null);
    addlabeltopanel(inputPanel, lblNoDay, err, 4, 0, null);
    addlabeltopanel(inputPanel, lblBalanceDue, err, 5, 0, null);
    addlabeltopanel(inputPanel, lblReason, err, 6, 0, null);


    addlabeltopanel(inputPanel, lblLeaveType, err, 0, 0, null);
    // addtxtfieldtopanel(inputPanel, txtAppliDate, err, 1, 1, null);
    addtxtfieldtopanel(inputPanel, txtStartDate, err, 2, 1, null);
    addtxtfieldtopanel(inputPanel, txtEndDate, err, 3, 1, null);
    addtxtfieldtopanel(inputPanel, txtNoDay, err, 4, 1, null);
    addtxtfieldtopanel(inputPanel, txtBalanceDue, err, 5, 1, null);
    addtxtfieldtopanel(inputPanel, txtReason, err, 6, 1, null);


    inputPanel.setPreferredSize(new Dimension(450, 360));
    buttonPanel.setPreferredSize(new Dimension(450, 40));
    buttonPanel.add(btnSubmit);
    // Add the input panel to the MainLeave panel
    MainLeave.add(inputPanel, BorderLayout.CENTER);
    MainLeave.add(buttonPanel, BorderLayout.SOUTH);

    // Add MainLeave to the content pane
    add(MainLeave, BorderLayout.CENTER);

    setPreferredSize(new Dimension(450, 400));
    setBackground(Color.WHITE);
    setVisible(true);
    pack(); // This will ensure that the frame is sized correctly
    setLocationRelativeTo(null);
    populateLeaveType();

    btnSubmit.addActionListener(e -> handleInsertLeave());
  }



  public void populateLeaveType() {
    String[] LeaveType = {"Annual Leave", "Sick Leave", "Special Leave", "Block Leave"};

    for (String type : LeaveType) {
      cmbLeaveType.addItem(type);
    }
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
      Label.setPreferredSize(new Dimension(82, 30));
      Label.setMinimumSize(new Dimension(82, 30));
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
      field.setPreferredSize(new Dimension(150, 30));
      field.setMinimumSize(new Dimension(150, 30));
    }
    field.setBackground(Color.white);
    field.setOpaque(true);
    field.setForeground(Color.BLACK);
    panelll.add(field, nghz);
  }

  private void addcomboboxtopanel(JPanel panelll, JComboBox<String> comboBox,
      GridBagConstraints nghz, int gridy, int gridx, Dimension size) {
    nghz.gridx = gridx;
    nghz.gridy = gridy;
    if (size != null) {
      comboBox.setPreferredSize(size);
      comboBox.setMinimumSize(size);
      comboBox.setMaximumSize(size);
    } else {
      comboBox.setPreferredSize(new Dimension(150, 30));
      comboBox.setMinimumSize(new Dimension(150, 30));
    }
    comboBox.setBackground(Color.white);
    comboBox.setForeground(Color.BLACK);
    panelll.add(comboBox, nghz);
  }

  private void InsertLeave(int userId) {
    String sql =
        "INSERT INTO LEAVES (employee_id, leave_type, application_date, start_date, end_date, reason, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      LocalDate currentDate = LocalDate.now();
      LocalDate startDate = parseDate(txtStartDate.getText());
      LocalDate endDate = parseDate(txtEndDate.getText());

      if (startDate == null || endDate == null) {
        JOptionPane.showMessageDialog(this, "Please enter the dates in the format yyyy-MM-dd",
            "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
        return;
      }

      stmt.setInt(1, userId);
      stmt.setString(2, cmbLeaveType.getSelectedItem().toString());
      stmt.setDate(3, Date.valueOf(currentDate)); // Application Date
      stmt.setDate(4, Date.valueOf(startDate)); // Start Date
      stmt.setDate(5, Date.valueOf(endDate)); // End Date
      stmt.setString(6, txtReason.getText());
      stmt.setString(7, "Pending for approve");

      stmt.executeUpdate();

      JOptionPane.showMessageDialog(this, "Leave request submitted successfully!", "Success",
          JOptionPane.INFORMATION_MESSAGE);
      this.dispose();
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
  }

  // Utility method to parse date and handle different formats
  private LocalDate parseDate(String dateString) {
    try {
      // Attempt to parse the date assuming it is in the correct format
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      return LocalDate.parse(dateString, formatter);
    } catch (Exception e) {
      return null; // Return null if parsing fails
    }
  }

  private void handleInsertLeave() {
    InsertLeave(userId);
  }


}
