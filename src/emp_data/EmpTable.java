package emp_data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.DatabaseConnection;
import utils.TableUtils;

public class EmpTable extends JPanel implements ActionListener {

  JButton btnadd = new JButton("Add");
  JButton btnamend = new JButton("Amend");
  JButton btnremove = new JButton("Remove");
  JButton btnsearch = new JButton("Search");
  JButton btnsort = new JButton("Sort");
  JButton btnback = new JButton("Back");

  private static JTable table;
  private DefaultTableModel tableModel;
  private JScrollPane scrollpane;

  String[] column =
      {"ID", "FIRST NAME", "LAST NAME", "SEX", "PHONE", "EMAIL", "POSITION", "DEPARTMENT"};

  public EmpTable() {
    setLayout(new BorderLayout()); // Use BorderLayout for main panel
    JPanel mainpanel = new JPanel(new BorderLayout());
    JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 6)); // Using FlowLayout
    JPanel northpanel = new JPanel(new GridBagLayout());

    northpanel.setPreferredSize(new Dimension(894, 80));
    buttonpanel.setPreferredSize(new Dimension(864, 40));
    mainpanel.setPreferredSize(new Dimension(864, 688));

    GridBagConstraints err = new GridBagConstraints();
    err.insets = new Insets(10, 10, 10, 10);
    err.fill = GridBagConstraints.NONE;
    err.weightx = 1;
    err.weighty = 1;
    err.gridx = 0;
    err.gridy = 0;

    Font customFont = new Font("Times New Roman", Font.PLAIN, 15);

    // Apply custom font to buttons
    btnadd.setFont(customFont);
    btnamend.setFont(customFont);
    btnremove.setFont(customFont);
    btnsearch.setFont(customFont);
    btnsort.setFont(customFont);

    btnadd.addActionListener(this);
    btnamend.addActionListener(this);
    btnremove.addActionListener(this);
    btnsearch.addActionListener(this);
    btnsort.addActionListener(this);
    // btconfirmamend.setEnabled(false);

    addButtonToPanel(buttonpanel, btnadd);
    addButtonToPanel(buttonpanel, btnamend);
    addButtonToPanel(buttonpanel, btnremove);
    addButtonToPanel(buttonpanel, btnsearch);
    addButtonToPanel(buttonpanel, btnsort);

    TableUtils tableutil = new TableUtils();
    tableModel = tableutil.getTableModel(column);
    table = tableutil.getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = tableutil.getScrollPane(table);

    err.insets = new Insets(10, 10, 10, 800);
    // northpanel.add(btnback, err);
    mainpanel.add(northpanel, BorderLayout.NORTH);
    mainpanel.add(buttonpanel, BorderLayout.SOUTH);
    mainpanel.add(scrollpane, BorderLayout.CENTER);
    add(mainpanel, BorderLayout.CENTER);

    // Fetch initial data
    populateTable();
    autoRefresh();

    // Adjust column widths after populating the table
    TableUtils.adjustColumnWidth(table, 0, 30);
    TableUtils.adjustColumnWidth(table, 3, 30);
    TableUtils.adjustColumnWidth(table, 4, 70);

  }

  protected void autoRefresh() {
    // Set up the auto-refresh timer
    Timer refreshTimer = new Timer(5000, e -> populateTable());
    refreshTimer.start();
  }

  public void populateTable() {
    tableModel.setRowCount(0);

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();

      // Prepare the SQL SELECT statement
      String sql =
          "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, SEX, PHONE, EMAIL, POSITION, DEPARTMENT FROM Employees ORDER BY Department";

      // Create the PreparedStatement
      pstmt = conn.prepareStatement(sql);

      // Execute the query
      rs = pstmt.executeQuery();

      // Iterate over the ResultSet and populate the table model
      while (rs.next()) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(rs.getInt("EMPLOYEE_ID"));
        row.add(rs.getString("FIRST_NAME"));
        row.add(rs.getString("LAST_NAME"));
        row.add(rs.getString("SEX"));
        row.add(rs.getString("PHONE"));
        row.add(rs.getString("EMAIL"));
        row.add(rs.getString("POSITION"));
        row.add(rs.getString("DEPARTMENT"));
        tableModel.addRow(row.toArray());
      }

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

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnadd) {
      EmpInput inputFrame = new EmpInput();
      inputFrame.setVisible(true);
    } else if (e.getSource() == btnamend) {
      EmpAmend amendFrame = new EmpAmend();
      amendFrame.showAmendDialog();
    } else if (e.getSource() == btnremove) {
      EmpDelete deleteFrame = new EmpDelete();
      deleteFrame.showDeleteDialog();
    } else if (e.getSource() == btnsearch) {
      EmpSearch searchFrame = new EmpSearch();
      searchFrame.showSearchOption();
    } else if (e.getSource() == btnsort) {
      EmpSort empsort = new EmpSort();
      empsort.showSortDialog();

    }
  }

  private void addButtonToPanel(JPanel panel, JButton button) {
    button.setPreferredSize(new Dimension(68, 25));
    button.setMinimumSize(new Dimension(68, 25));
    button.setBackground(Color.LIGHT_GRAY);
    button.setFocusPainted(false);
    button.setMargin(new Insets(0, 5, 0, 5));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    if (button == btnremove) {
      button.setBackground(Color.RED); // Exit button color set to red
    }
    panel.add(button);
  }

  public static void main(String[] args) {
    EmpTable empTable = new EmpTable();

    JFrame frame = new JFrame("Employee Table");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(900, 700); // Set frame size
    frame.add(empTable);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static int getStaffCount() {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int count = 0;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("Info_input StaffCount: Connected to the database");

      // Prepare the SQL COUNT statement
      String sql = "SELECT COUNT(*) FROM Employees";

      // Create the PreparedStatement
      pstmt = conn.prepareStatement(sql);

      // Execute the query
      rs = pstmt.executeQuery();

      // Retrieve the count from the ResultSet
      if (rs.next()) {
        count = rs.getInt(1);
      }

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
    return count;
  }
}
