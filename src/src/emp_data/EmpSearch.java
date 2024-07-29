package src.emp_data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.DatabaseConnection;
import src.utils.DialogUtils;
import src.utils.TableUtils;

public class EmpSearch extends JFrame implements ActionListener {
  public static DefaultTableModel tableModelSearch;
  public static JTable tableResult;
  public static JScrollPane paneResult;
  private static EmpSearch empsearch;
  private static TableUtils tableutil = new TableUtils(); // Create an instance of EmpTable

  private static String[] column =
      {"ID", "FIRST NAME", "LAST NAME", "PHONE", "EMAIL", "NID", "POSITION", "DEPARTMENT"};

  public EmpSearch() {
    empsearch = this;



    tableModelSearch = tableutil.getTableModel(column);
    tableResult = tableutil.getTable(tableModelSearch);
    paneResult = tableutil.getScrollPane(tableResult);

    setLayout(new BorderLayout()); // Use BorderLayout for main panel
    JPanel mainpanel = new JPanel(new BorderLayout());


    mainpanel.setPreferredSize(new Dimension(864, 688));
    mainpanel.add(paneResult, BorderLayout.CENTER);
    add(mainpanel, BorderLayout.CENTER);

    Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attandance V1\\img\\naga.png");
    setIconImage(icon);
    setSize(864, 688);
    setLocationRelativeTo(null);
    setResizable(true);
    setVisible(false);

    // Adjust column widths after populating the table
    TableUtils.adjustColumnWidth(tableResult, 0, 30);
    TableUtils.adjustColumnWidth(tableResult, 3, 65);


  }


  public static void searchByName(String name) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("SearchByName: Connected to the database");

      // Prepare the SQL INSERT statement
      // Prepare the SQL SELECT statement with LIKE clause
      String sql =
          "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT "
              + "FROM Employees " + "WHERE FIRST_NAME LIKE ? OR LAST_NAME LIKE ?";


      pstmt = conn.prepareStatement(sql);
      String searchPattern = "%" + name + "%"; // Pattern for partial match
      pstmt.setString(1, searchPattern);
      pstmt.setString(2, searchPattern);

      rs = pstmt.executeQuery();
      // Iterate over the ResultSet and populate the table model

      // Clear the current rows in the table model

      tableModelSearch.setRowCount(0);

      while (rs.next()) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(rs.getInt("EMPLOYEE_ID"));
        row.add(rs.getString("FIRST_NAME"));
        row.add(rs.getString("LAST_NAME"));
        row.add(rs.getString("PHONE"));
        row.add(rs.getString("EMAIL"));
        row.add(rs.getString("NID"));
        row.add(rs.getString("POSITION"));
        row.add(rs.getString("DEPARTMENT"));
        tableModelSearch.addRow(row.toArray());
      }

      // Adjust column widths after populating the table
      // empTable.adjustColumnWidths();
      empsearch.setVisible(true);

      // System.out.println("All staff information inserted successfully!");

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close the resources
      try {
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

  public static void searchByID(int id) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("SearchByID: Connected to the database");

      // Prepare the SQL INSERT statement
      String sql =
          "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT "
              + "FROM Employees WHERE EMPLOYEE_ID = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id); // Set the ID parameter
      rs = pstmt.executeQuery();
      // Iterate over the ResultSet and populate the table model

      while (rs.next()) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(rs.getInt("EMPLOYEE_ID"));
        row.add(rs.getString("FIRST_NAME"));
        row.add(rs.getString("LAST_NAME"));
        row.add(rs.getString("PHONE"));
        row.add(rs.getString("EMAIL"));
        row.add(rs.getString("NID"));
        row.add(rs.getString("POSITION"));
        row.add(rs.getString("DEPARTMENT"));
        tableModelSearch.addRow(row.toArray());
      }

      empsearch.setVisible(true);

      System.out.println("Sucessful Seach Employee with ID " + id);


    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close the resources
      try {
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

  public void showSearchIDDialog() {
    DialogUtils.createDialog("Attendance System V1", "Search by ID: ", new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JTextField textField = (JTextField) ((JPanel) ((JButton) e.getSource()).getParent()
            .getParent().getComponent(0)).getComponent(1);
        String idForSearch = textField.getText();
        try {
          int idSearch = Integer.parseInt(idForSearch.trim());
          SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close the dialog
          HandlesearchByID(idSearch);
          // handleDeletion(idDelete); // Call the deletion handling method
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  public void showSearchNameDialog() {
    DialogUtils.createDialog("Attendance System V1", "Search by Name: ", new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JTextField textField = (JTextField) ((JPanel) ((JButton) e.getSource()).getParent()
            .getParent().getComponent(0)).getComponent(1);
        String NameForSearch = textField.getText();
        try {
          String nameSearch = NameForSearch;
          SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close the dialog
          HandlesearchByName(nameSearch);
          // handleDeletion(idDelete); // Call the deletion handling method
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  private void HandlesearchByID(int id) {

    // Implement the logic to handle the deletion using the provided ID
    System.out.println("Search requested for ID: " + id);
    // You can call updateStaffInfo or any other logic here
    searchByID(id);
    // updateButtonState();
  }

  private void HandlesearchByName(String name) {

    // Implement the logic to handle the deletion using the provided ID
    System.out.println("Search requested for Name: " + name);
    // You can call updateStaffInfo or any other logic here
    searchByName(name);
    // updateButtonState();
  }

  public void showSearchOption() {

    JFrame frame = new JFrame("Attendance");
    JPanel panel1 = new JPanel(new GridBagLayout());
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel("Choose Option to Search");

    JButton byIDButton = new JButton("By ID");
    JButton byNameButton = new JButton("By Name");
    Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

    label.setFont(customFont);
    byIDButton.setBackground(Color.LIGHT_GRAY);
    byIDButton.setFocusPainted(false);
    byNameButton.setBackground(Color.LIGHT_GRAY);
    byNameButton.setFocusPainted(false);

    byIDButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showSearchIDDialog();
        frame.dispose();
      }
    });

    byNameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showSearchNameDialog();
        frame.dispose();
      }
    });

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel1.add(label, gbc);

    panel1.setPreferredSize(new Dimension(250, 80));
    panel1.setBackground(Color.WHITE);

    panel2.setPreferredSize(new Dimension(250, 40));
    panel2.setBackground(new Color(15, 41, 102));
    panel2.add(byIDButton);
    panel2.add(Box.createRigidArea(new Dimension(20, 0))); // 20-pixel horizontal gap
    panel2.add(byNameButton);

    frame.setResizable(false);

    byIDButton.addActionListener(this);
    byNameButton.addActionListener(this);

    frame.setLocationRelativeTo(null);
    Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attandance V1\\img\\naga.png");
    frame.setIconImage(icon);
    frame.add(panel1, BorderLayout.CENTER);
    frame.add(panel2, BorderLayout.SOUTH);
    frame.pack();
    frame.setLocationRelativeTo(null); // Center the frame after packing
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
