package Admin.LeaveRequest;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.DatabaseConnection;
import utils.TableUtils;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaveReq extends JPanel implements ActionListener {
  String column[] = {"leave_id", "emp_id", "first name", "last name", "leave_type", "apply_date",
      "from_date", "to_date", "status", "reason"};
  private static JTable table;
  private static DefaultTableModel tableModel;
  private JScrollPane scrollpane;

  private JButton btnView = new JButton("View");
  private JButton btnApprove = new JButton("Approve");
  private JButton btnReject = new JButton("Reject");

  int selectedRow;
  int emp_id;
  int leave_id;

  public LeaveReq() {
    setLayout(new BorderLayout());
    JPanel headerPanel = new JPanel(new GridBagLayout());
    headerPanel.setPreferredSize(new Dimension(894, 80));

    GridBagConstraints err = new GridBagConstraints();
    err.insets = new Insets(10, 10, 10, 10);
    err.fill = GridBagConstraints.NONE;
    err.weightx = 1;
    err.weighty = 1;
    err.gridx = 0;
    err.gridy = 0;

    TableUtils tableutil = new TableUtils();
    tableModel = tableutil.getTableModel(column);
    table = tableutil.getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = tableutil.getScrollPane(table);

    TableUtils.adjustColumnWidth(table, 0, 50);
    TableUtils.adjustColumnWidth(table, 1, 40);
    TableUtils.adjustColumnWidth(table, 2, 70);
    TableUtils.adjustColumnWidth(table, 3, 130);
    TableUtils.adjustColumnWidth(table, 4, 80);
    TableUtils.adjustColumnWidth(table, 5, 70);
    TableUtils.adjustColumnWidth(table, 6, 70);
    TableUtils.adjustColumnWidth(table, 7, 70);
    TableUtils.adjustColumnWidth(table, 8, 120);

    btnView.addActionListener(this);
    btnApprove.addActionListener(this);
    btnReject.addActionListener(this);

    btnView.setBackground(new Color(254, 227, 86));
    btnApprove.setBackground(new Color(124, 208, 70));
    btnReject.setBackground(new Color(239, 89, 64));
    addButtonToPanel(headerPanel, btnView, err, 0, 0);
    err.insets = new Insets(10, 100, 10, 10);
    addButtonToPanel(headerPanel, btnApprove, err, 0, 0);
    err.insets = new Insets(10, 190, 10, 10);
    addButtonToPanel(headerPanel, btnReject, err, 0, 0);
    add(scrollpane, BorderLayout.CENTER);
    add(headerPanel, BorderLayout.NORTH);


    populateLeave();
    setPreferredSize(new Dimension(894, 688)); // Set the size of the left panel
    setBackground(Color.WHITE);


    // Add ListSelectionListener to print the ID of the selected row
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
          selectedRow = table.getSelectedRow();
          if (selectedRow != -1) {
            Object id = table.getValueAt(selectedRow, 0); // Assuming ID is in the first column
            emp_id = (int) id;
            Object Leaveid = table.getValueAt(selectedRow, 1); // Assuming ID is in the first column
            leave_id = (int) Leaveid;
            System.out.println("Selected Employee ID: " + id + " Selected Leave ID: " + leave_id);
          }
        }
      }
    });

  }

  public static void populateLeave() {
    tableModel.setRowCount(0);
    String sql =
        "SELECT E.first_name, E.last_name, L.employee_id, L.leave_id, L.leave_type, L.application_date, L.start_date, L.end_date, L.status, L.reason "
            + "FROM LEAVES AS L INNER JOIN Employees AS E ON L.employee_id = E.employee_id WHERE status = 'Pending for approve' ORDER BY leave_id DESC";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      System.out.println("LeaveReq: Connected to database");

      ResultSet rs = pstmt.executeQuery();

      // Process the result set here
      while (rs.next()) {
        ArrayList<Object> row = new ArrayList<>();

        row.add(rs.getInt("employee_id"));
        row.add(rs.getInt("leave_id"));
        row.add(rs.getString("first_name"));
        row.add(rs.getString("last_name"));
        row.add(rs.getString("leave_type"));
        row.add(rs.getDate("application_date"));
        row.add(rs.getDate("start_date"));
        row.add(rs.getDate("end_date"));
        row.add(rs.getString("status"));
        row.add(rs.getString("reason"));

        tableModel.addRow(row.toArray());



        // System.out.println("Added row: " + row);

      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
  }

  private void addButtonToPanel(JPanel panel, JButton button, GridBagConstraints gbc, int gridy,
      int gridx) {
    gbc.gridy = gridy;
    gbc.gridx = gridx;
    gbc.anchor = GridBagConstraints.SOUTHWEST;
    button.setPreferredSize(new Dimension(80, 30));
    button.setMinimumSize(new Dimension(80, 30));

    button.setFocusPainted(false);
    button.setMargin(new Insets(0, 5, 0, 5));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    panel.add(button, gbc);
  }

  public static void main(String[] args) {

    JFrame frame = new JFrame("Leave Request");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    LeaveReq leavereq = new LeaveReq();
    frame.setContentPane(leavereq);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    LeaveReqApp leaveReqApp = new LeaveReqApp();
    if (e.getSource() == btnView) {
      leaveReqApp.ViewLeave(leave_id);
    } else if (e.getSource() == btnApprove) {
      // leaveReqApp.setVisible(false);
      // leaveReqApp.ApproveLeave(leave_id);
    } else if (e.getSource() == btnReject) {
      leaveReqApp.setVisible(false);
      leaveReqApp.RejectLeave(leave_id);
    }
  }
}
