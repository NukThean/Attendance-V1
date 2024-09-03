package Admin.attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Admin.emp_data.EmpDelete;
import utils.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SortByEmp extends JFrame implements ActionListener {
  String[] column = {"no.", "att_id", "date", "shift_in", "shift_out", "check_in", "check_out",
      "TimeDiff_in", "TimeDiff_out"};

  private JTable table;
  private static DefaultTableModel tableModel;
  private JScrollPane scrollpane;
  private static JLabel lblid = new JLabel("");

  JLabel lbldep = new JLabel("Department");
  private static JComboBox<String> cmbMonth = new JComboBox<>();
  private static JComboBox<String> cmbYear = new JComboBox<>();

  public SortByEmp() {
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
    namePanel.add(lblid, BorderLayout.WEST);
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

    TableUtils.adjustColumnWidth(table, 0, 30);
    TableUtils.adjustColumnWidth(table, 1, 40);
    TableUtils.adjustColumnWidth(table, 2, 70);
    TableUtils.adjustColumnWidth(table, 3, 60);
    TableUtils.adjustColumnWidth(table, 4, 60);
    TableUtils.adjustColumnWidth(table, 5, 60);
    TableUtils.adjustColumnWidth(table, 6, 60);
  }

  public static void HandleSortByEmpID(int userId, int month, int year) {
    // Create an instance of SortByEmp
    String empName = EmpDelete.getStaffNameById(userId);
    SortByEmp.SortByEmpID(userId, month, year);
    lblid.setText("<html>ID: " + userId + "<br>Name: " + empName + "</html>");
  }


  private static void loadComboBox() {

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

  public void showSortIdDialog() {
    JFrame frame = new JFrame("Sort");
    JPanel panel1 = new JPanel(new GridBagLayout());
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel("Choose option to sort");
    JTextField txtId = new JTextField();
    loadComboBox();


    JButton okButton = new JButton("OK");
    Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

    label.setFont(customFont);
    cmbMonth.setFont(customFont);
    cmbYear.setFont(customFont);
    txtId.setFont(customFont);

    cmbMonth.setPreferredSize(new Dimension(120, 20));
    cmbMonth.setMinimumSize(new Dimension(120, 20));
    cmbYear.setPreferredSize(new Dimension(120, 20));
    cmbYear.setMinimumSize(new Dimension(120, 20));
    txtId.setPreferredSize(new Dimension(120, 20));
    txtId.setMinimumSize(new Dimension(120, 20));


    okButton.setBackground(Color.LIGHT_GRAY);
    okButton.setFocusPainted(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel1.add(label, gbc);

    gbc.gridy = 1;
    panel1.add(txtId, gbc);
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
        String selectedId = txtId.getText();
        int selectedID = Integer.parseInt(selectedId);

        String selectedYearString = (String) cmbYear.getSelectedItem();
        int selectedYear = Integer.parseInt(selectedYearString);

        // String selectedMonthString = (String) cmbMonth.getSelectedItem();
        int selectedMonth = cmbMonth.getSelectedIndex() + 1; // Converts to month number

        System.out.println("Selected ID: " + selectedID);
        System.out.println("Selected Month: " + selectedMonth);
        System.out.println("Selected Year: " + selectedYear);

        // Perform the desired action with the selected item
        HandleSortByEmpID(selectedID, selectedMonth, selectedYear);
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



  private static void SortByEmpID(int userId, int month, int year) {
    tableModel.setRowCount(0);

    LocalDate today = LocalDate.now();
    int daysInMonth = today.withMonth(month).lengthOfMonth();

    // Map to store the data fetched from the database with date as key
    Map<LocalDate, ArrayList<Object>> dataMap = new HashMap<>();

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT A.ATTENDANCE_ID, A.DATE, S.START_SHIFT, S.END_SHIFT, A.CHECK_IN_TIME, A.CHECK_OUT_TIME, A.TimeDiff_in, A.TimeDiff_out "
                + "FROM Attendance AS A INNER JOIN ShiftSchedule AS S "
                + "ON A.EMPLOYEE_ID = S.EMPLOYEE_ID " + "WHERE A.EMPLOYEE_ID = ? "
                + "AND MONTH(A.DATE) = ? AND YEAR(A.DATE) = ? " + "ORDER BY A.DATE ASC")) {

      pstmt.setInt(1, userId);
      pstmt.setInt(2, month);
      pstmt.setInt(3, year);

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        LocalDate date = rs.getDate("DATE").toLocalDate();
        ArrayList<Object> row = new ArrayList<>();
        row.add(rs.getInt("ATTENDANCE_ID"));
        row.add(rs.getDate("DATE"));
        row.add(rs.getString("START_SHIFT"));
        row.add(rs.getString("END_SHIFT"));
        row.add(rs.getString("CHECK_IN_TIME"));
        row.add(rs.getString("CHECK_OUT_TIME"));
        row.add(rs.getString("TimeDiff_in"));
        row.add(rs.getString("TimeDiff_out"));
        dataMap.put(date, row);


      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Fill the table with data, one row per day of the month
    // LocalDate currentDate = LocalDate.of(year, month, 1);
    int rowNum = 1;
    for (int day = 1; day <= daysInMonth; day++) {
      LocalDate date = LocalDate.of(year, month, day);
      ArrayList<Object> rowData = dataMap.getOrDefault(date, getDefaultRowData(date));
      rowData.add(0, rowNum++);
      tableModel.addRow(rowData.toArray());
    }
  }

  // Helper method to get a default row with the date and null values for other columns
  private static ArrayList<Object> getDefaultRowData(LocalDate date) {
    ArrayList<Object> row = new ArrayList<>();
    row.add(null); // att_id
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
