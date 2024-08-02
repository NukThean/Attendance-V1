package src.attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.utils.DatabaseConnection;
import src.utils.TableUtils;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class attform extends JPanel {
  private static JTable table;
  private DefaultTableModel tableModel;
  private JScrollPane scrollpane;

  String[] column = {"att_id", "emp_id", "date", "check_in_time", "check_out_time"};

  public attform() {
    setLayout(new BorderLayout());
    JPanel main = new JPanel(new BorderLayout());
    TableUtils tableutil = new TableUtils();
    tableModel = tableutil.getTableModel(column);
    table = tableutil.getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = tableutil.getScrollPane(table);

    main.add(scrollpane, BorderLayout.CENTER);
    add(main, BorderLayout.CENTER);
    setPreferredSize(new Dimension(894, 688)); // Set the size of the left panel
    setBackground(Color.WHITE);
    setVisible(true);

    getAttnew();
    autoRefresh();

  }

  protected void autoRefresh() {
    // Set up the auto-refresh timer
    Timer refreshTimer = new Timer(5000, e -> getAttnew());
    refreshTimer.start();
  }

  public void getAttnew() {
    tableModel.setRowCount(0);

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("attform: load populateTable ");

      // Prepare the SQL SELECT statement
      String sql =
          "SELECT ATTENDANCE_ID, EMPLOYEE_ID, DATE, CHECK_IN_TIME, CHECK_OUT_TIME FROM Attendance ORDER BY attendance_id DESC";

      // Create the PreparedStatement
      pstmt = conn.prepareStatement(sql);

      // Execute the query
      rs = pstmt.executeQuery();

      // Iterate over the ResultSet and populate the table model
      while (rs.next()) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(rs.getInt("ATTENDANCE_ID"));
        row.add(rs.getInt("EMPLOYEE_ID"));
        row.add(rs.getDate("DATE")); // Assuming DATE is a date or timestamp column
        row.add(rs.getString("CHECK_IN_TIME"));
        row.add(rs.getString("CHECK_OUT_TIME"));
        tableModel.addRow(row.toArray());
      }

      // Adjust column widths after populating the table
      // adjustColumnWidths();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close the resources
      try {
        if (rs != null) {
          rs.close();
        }
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
