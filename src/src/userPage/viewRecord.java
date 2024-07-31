package src.userPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.loginpage.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import src.utils.*;

public class viewRecord extends JFrame implements ActionListener {
  // private User user;
  String[] column = { "att_id", "date", "check_in_time", "check_out_time" };

  private JTable table;
  private DefaultTableModel tableModel;
  private JScrollPane scrollpane;

  public viewRecord(User user) {
    // this.user = user;
    int userId = user.getuserId();
    setLayout(new BorderLayout());

    TableUtils tableutil = new TableUtils();
    tableModel = tableutil.getTableModel(column);
    table = tableutil.getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = tableutil.getScrollPane(table);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(scrollpane, BorderLayout.CENTER);
    add(mainPanel, BorderLayout.CENTER);

    // Set up the JFrame properties
    setTitle("User Record");
    setSize(480, 600); // Set the size of the frame
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setLocationRelativeTo(null);
    ViewOwnRecord(userId);
    TableUtils.adjustColumnWidth(table, 0, 50);
  }

  public void ViewOwnRecord(int userId) {
    tableModel.setRowCount(0);

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT ATTENDANCE_ID, DATE, CHECK_IN_TIME, CHECK_OUT_TIME FROM Attendance WHERE EMPLOYEE_ID = ?")) {

      pstmt.setInt(1, userId);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          ArrayList<Object> row = new ArrayList<>();
          row.add(rs.getInt("ATTENDANCE_ID"));
          row.add(rs.getDate("DATE")); // Assuming DATE is a date or timestamp column
          row.add(rs.getString("CHECK_IN_TIME"));
          row.add(rs.getString("CHECK_OUT_TIME"));
          tableModel.addRow(row.toArray());
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // No action needed here for now
  }
}
