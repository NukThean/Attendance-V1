package attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import loginpage.User;
import utils.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SortByDep extends JFrame implements ActionListener {
  String[] column = {"no.", "att_id", "emp_id", "date", "shift_in", "shift_out", "check_in_time",
      "check_out_time", "TimeDiff_in", "TimeDiff_out"};


  private JTable table;
  private DefaultTableModel tableModel;
  private JScrollPane scrollpane;
  private static JLabel lblid = new JLabel("");

  JLabel lbldep = new JLabel("Department");
  private static JComboBox<String> cmbdep = new JComboBox<>();
  private static JComboBox<String> cmbMonth = new JComboBox<>();
  private static JComboBox<String> cmbYear = new JComboBox<>();

  public SortByDep() {
    setLayout(new BorderLayout());
    lblid.setBackground(Color.BLACK);

    TableUtils tableutil = new TableUtils();
    tableModel = tableutil.getTableModel(column);
    table = tableutil.getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = tableutil.getScrollPane(table);

    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel namePanel = new JPanel(new BorderLayout());
    namePanel.setPreferredSize(new Dimension(894, 50));
    namePanel.add(lbldep, BorderLayout.WEST);
    mainPanel.add(scrollpane, BorderLayout.CENTER);


    add(mainPanel, BorderLayout.CENTER);
    add(namePanel, BorderLayout.NORTH);

    setLocationRelativeTo(null);
    TableUtils.adjustRowHeights(table);

    setTitle("User Record");
    setSize(894, 688); // Set the size of the frame
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(false);
  }

  private static void loadComboBox() {
    // Sample departments, replace with actual department names from your database
    // or configuration
    String[] departments = User.getDepartment();
    for (String dep : departments) {
      cmbdep.addItem(dep);
    }

    String[] month = {"January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"};
    for (String mon : month) {
      cmbMonth.addItem(mon);
    }

    int currentYear = 2024;
    for (int i = currentYear; i <= 2050; i++) {
      cmbYear.addItem(String.valueOf(i));
    }
  }

  public void handleSortDep(String department, int month, int year) {
    System.out.println("Sort requested for ID: " + department);
    SortByDepartment(department, month, year);
    lbldep.setText("Monthly Report for Department: " + department);
  }


  public void showSortDepDialog() {
    JFrame frame = new JFrame("Sort");
    JPanel panel1 = new JPanel(new GridBagLayout());
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel("Choose option to sort");
    loadComboBox();


    JButton okButton = new JButton("OK");
    Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

    label.setFont(customFont);
    cmbdep.setFont(customFont);
    cmbMonth.setFont(customFont);
    cmbYear.setFont(customFont);

    cmbdep.setPreferredSize(new Dimension(120, 20));
    cmbdep.setMinimumSize(new Dimension(120, 20));
    cmbMonth.setPreferredSize(new Dimension(120, 20));
    cmbMonth.setMinimumSize(new Dimension(120, 20));
    cmbYear.setPreferredSize(new Dimension(120, 20));
    cmbYear.setMinimumSize(new Dimension(120, 20));

    okButton.setBackground(Color.LIGHT_GRAY);
    okButton.setFocusPainted(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel1.add(label, gbc);

    gbc.gridy = 1;
    panel1.add(cmbdep, gbc);
    gbc.gridy++;
    panel1.add(cmbMonth, gbc);
    gbc.gridy++;
    panel1.add(cmbYear, gbc);


    panel1.setPreferredSize(new Dimension(250, 150));
    panel1.setBackground(Color.WHITE);

    panel2.setPreferredSize(new Dimension(250, 40));
    panel2.setBackground(new Color(15, 41, 102));
    panel2.add(okButton);
    // Define the ActionListener for okButton
    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedDep = (String) cmbdep.getSelectedItem();

        String selectedYearString = (String) cmbYear.getSelectedItem();
        int selectedYear = Integer.parseInt(selectedYearString);

        // String selectedMonthString = (String) cmbMonth.getSelectedItem();
        int selectedMonth = cmbMonth.getSelectedIndex() + 1; // Converts to month number

        // Perform the desired action with the selected item
        handleSortDep(selectedDep, selectedMonth, selectedYear);
        frame.dispose();
        setVisible(true);
      }
    });

    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attandance V1\\img\\naga.png");
    frame.setIconImage(icon);
    frame.add(panel1, BorderLayout.CENTER);
    frame.add(panel2, BorderLayout.SOUTH);
    frame.pack();
    frame.setLocationRelativeTo(null); // Center the frame after packing
    frame.setVisible(true);
  }

  public void SortByDepartment(String departmentName, int month, int year) {
    tableModel.setRowCount(0);


    LocalDate today = LocalDate.now();
    int daysInMonth = today.withMonth(month).lengthOfMonth();

    // Step 1: Fetch all employees in the department
    ArrayList<Integer> employeeIds = new ArrayList<>();
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt =
            conn.prepareStatement("SELECT EMPLOYEE_ID FROM Employees WHERE DEPARTMENT = ?")) {

      pstmt.setString(1, departmentName);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        employeeIds.add(rs.getInt("EMPLOYEE_ID"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Initialize the row counter globally
    int rowNum = 1;

    // Step 2: For each employee, fetch attendance records and populate the table
    for (int employeeId : employeeIds) {
      Map<LocalDate, ArrayList<Object>> dataMap = new HashMap<>();

      try (Connection conn = DatabaseConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(
              "SELECT A.ATTENDANCE_ID, A.EMPLOYEE_ID, A.DATE, S.START_SHIFT, S.END_SHIFT, A.CHECK_IN_TIME, A.CHECK_OUT_TIME, A.TimeDiff_in, A.TimeDiff_out "
                  + "FROM Attendance AS A INNER JOIN ShiftSchedule AS S "
                  + "ON A.EMPLOYEE_ID = S.EMPLOYEE_ID "
                  + "WHERE A.EMPLOYEE_ID = ? AND MONTH(A.DATE) = ? AND YEAR(A.DATE) = ? "
                  + "ORDER BY A.DATE ASC")) {

        pstmt.setInt(1, employeeId);
        pstmt.setInt(2, month);
        pstmt.setInt(3, year);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
          LocalDate date = rs.getDate("DATE").toLocalDate();
          ArrayList<Object> row = new ArrayList<>();
          row.add(rs.getInt("ATTENDANCE_ID")); // att_id
          row.add(rs.getInt("EMPLOYEE_ID")); // emp_id
          row.add(rs.getDate("DATE")); // date
          row.add(rs.getString("START_SHIFT")); // shift_in
          row.add(rs.getString("END_SHIFT")); // shift_out
          row.add(rs.getString("CHECK_IN_TIME")); // check_in_time
          row.add(rs.getString("CHECK_OUT_TIME")); // check_out_time
          row.add(rs.getString("TimeDiff_in")); // TimeDiff_in
          row.add(rs.getString("TimeDiff_out")); // TimeDiff_out
          dataMap.put(date, row);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

      for (int day = 1; day <= daysInMonth; day++) {
        LocalDate date = LocalDate.of(year, month, day);
        ArrayList<Object> rowData =
            dataMap.getOrDefault(date, getDefaultRowData(employeeId, departmentName, date));

        // Ensure the "emp_id" is set if it's null
        if (rowData.get(1) == null) {
          rowData.set(1, employeeId); // Set the emp_id column correctly
        }

        rowData.add(0, rowNum++); // Add row number as the first element
        tableModel.addRow(rowData.toArray());
      }
    }
  }

  // Helper method to get a default row with the date, emp_id, department, and null values for other
  // columns
  private static ArrayList<Object> getDefaultRowData(int employeeId, String departmentName,
      LocalDate date) {
    ArrayList<Object> row = new ArrayList<>();
    row.add(null); // no.
    row.add(employeeId); // emp_id (set this correctly initially)
    row.add(Date.valueOf(date)); // date
    row.add(null); // shift_in
    row.add(null); // shift_out
    row.add(null); // check_in_time
    row.add(null); // check_out_time
    row.add(null); // TimeDiff_in
    row.add(null); // TimeDiff_out
    return row;
  }



  @Override
  public void actionPerformed(ActionEvent e) {
    // No action needed here for now
  }
}
