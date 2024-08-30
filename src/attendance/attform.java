package attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.DatabaseConnection;
import utils.TableUtils;
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

  String[] column = {"att_id", "emp_id", "date", "shift_in", "shift_out", "check_in_time",
      "check_out_time", "TimeDiff_in", "TimeDiff_out"};

  public attform() {
    setLayout(new BorderLayout());
    JPanel main = new JPanel(new BorderLayout());
    TableUtils tableutil = new TableUtils();
    tableModel = tableutil.getTableModel(column);
    table = tableutil.getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = tableutil.getScrollPane(table);

    HeaderAtt headerAtt = new HeaderAtt();
    main.add(headerAtt, BorderLayout.NORTH);
    main.add(scrollpane, BorderLayout.CENTER);

    add(main, BorderLayout.CENTER);
    setPreferredSize(new Dimension(894, 688)); // Set the size of the left panel
    setBackground(Color.WHITE);
    setVisible(true);

    getAttnew();
    TableUtils.adjustColumnWidth(table, 0, 35);
    TableUtils.adjustColumnWidth(table, 1, 50);
    TableUtils.adjustColumnWidth(table, 2, 70);
    TableUtils.adjustRowHeights(table);
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
      // System.out.println("attform: load populateTable ");

      // Prepare the SQL SELECT statement
      String sql =
          "SELECT A.ATTENDANCE_ID, A.EMPLOYEE_ID, A.DATE, S.START_SHIFT, S.END_SHIFT, A.CHECK_IN_TIME, A.CHECK_OUT_TIME, A.TimeDiff_in, A.TimeDiff_out "
              + "FROM Attendance AS A INNER JOIN ShiftSchedule AS S "
              + "ON A.EMPLOYEE_ID = S.EMPLOYEE_ID ORDER BY attendance_id DESC";

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
        row.add(rs.getString("START_SHIFT"));
        row.add(rs.getString("END_SHIFT"));
        row.add(rs.getString("CHECK_IN_TIME"));
        row.add(rs.getString("CHECK_OUT_TIME"));
        row.add(rs.getString("TimeDiff_in"));
        row.add(rs.getString("TimeDiff_out"));
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
