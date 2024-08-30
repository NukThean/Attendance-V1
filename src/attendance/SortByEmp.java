package attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class SortByEmp extends JFrame implements ActionListener {
  String[] column = { "att_id", "date", "shift_in", "shift_out", "check_in_time",
      "check_out_time", "TimeDiff_in", "TimeDiff_out" };

  private JTable table;
  private DefaultTableModel tableModel;
  private JScrollPane scrollpane;

  public SortByEmp() {
    setLayout(new BorderLayout());

    TableUtils tableutil = new TableUtils();
    tableModel = tableutil.getTableModel(column);
    table = tableutil.getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = tableutil.getScrollPane(table);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(scrollpane, BorderLayout.CENTER);
    add(mainPanel, BorderLayout.CENTER);

    setLocationRelativeTo(null);
    // TableUtils.adjustColumnWidth(table, 0, 60);
    // TableUtils.adjustColumnWidth(table, 1, 30);
    // TableUtils.adjustColumnWidth(table, 4, 30);
    // TableUtils.adjustColumnWidth(table, 5, 70);

    TableUtils.adjustRowHeights(table);

    setTitle("User Record");
    setSize(480, 600); // Set the size of the frame
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public static void HandleSortByEmpID(int userId) {
    // Create an instance of SortByEmp
    SortByEmp sortByEmp = new SortByEmp();
    sortByEmp.SortByEmpID(userId);
    // new SortByEmp();
  }

  public static void showSearchIDDialog() {
    DialogUtils.createDialog("Attendance System V1", "Sort by ID: ", new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JTextField textField = (JTextField) ((JPanel) ((JButton) e.getSource()).getParent()
            .getParent().getComponent(0)).getComponent(1);
        String idForSearch = textField.getText();
        try {
          int idSort = Integer.parseInt(idForSearch.trim());
          SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close the dialog
          HandleSortByEmpID(idSort);
          // handleDeletion(idDelete); // Call the deletion handling method
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  public void SortByEmpID(int userId) {
    tableModel.setRowCount(0);

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT A.ATTENDANCE_ID, A.EMPLOYEE_ID, A.DATE, S.START_SHIFT, S.END_SHIFT, A.CHECK_IN_TIME, A.CHECK_OUT_TIME, A.TimeDiff_in, A.TimeDiff_out "
                + "FROM Attendance AS A INNER JOIN ShiftSchedule AS S "
                + "ON A.EMPLOYEE_ID = S.EMPLOYEE_ID "
                + "WHERE A.EMPLOYEE_ID = ? "
                + "ORDER BY EMPLOYEE_ID DESC")) {

      pstmt.setInt(1, userId);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          ArrayList<Object> row = new ArrayList<>();
          row.add(rs.getInt("ATTENDANCE_ID"));
          // row.add(rs.getInt("EMPLOYEE_ID"));
          row.add(rs.getDate("DATE")); // Assuming DATE is a date or timestamp column
          row.add(rs.getString("START_SHIFT"));
          row.add(rs.getString("END_SHIFT"));
          row.add(rs.getString("CHECK_IN_TIME"));
          row.add(rs.getString("CHECK_OUT_TIME"));
          row.add(rs.getString("TimeDiff_in"));
          row.add(rs.getString("TimeDiff_out"));
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
