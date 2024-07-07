package src.emp_data;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class empform extends JPanel {
  private JTable table;
  private JScrollPane scrollpane;

  public empform() {
    Info_input infoinput = new Info_input();
    setLayout(new BorderLayout()); // Use BorderLayout for main panel
    JPanel mainpanel = new JPanel(new BorderLayout());
    JPanel empnorth = new JPanel(new BorderLayout());
    JPanel empcenter = new JPanel(new BorderLayout());
    JPanel empsouth = new JPanel(new BorderLayout());
    JPanel empwest = new JPanel(new BorderLayout());
    JPanel empeast = new JPanel(new BorderLayout());
    JPanel northnorth = new JPanel(new GridBagLayout());
    JPanel centersouth = new JPanel(new GridBagLayout());

    mainpanel.setPreferredSize(new Dimension(864, 688));

    empnorth.setPreferredSize(new Dimension(0, 80));
    empcenter.setPreferredSize(new Dimension(664, 428));
    empsouth.setPreferredSize(new Dimension(0, 0));
    empwest.setPreferredSize(new Dimension(0, 0));
    empeast.setPreferredSize(new Dimension(0, 0));

    northnorth.setPreferredSize(new Dimension(0, 40));
    centersouth.setPreferredSize(new Dimension(0, 380));

    empnorth.setBackground(Color.GRAY);
    empcenter.setBackground(Color.GREEN);
    empsouth.setBackground(Color.WHITE);
    centersouth.setBackground(Color.WHITE);

    northnorth.setBackground(new Color(15, 41, 102));
    empnorth.setBackground(new Color(157, 160, 168));

    GridBagConstraints emm = new GridBagConstraints();
    emm.insets = new Insets(10, 10, 10, 10);
    emm.fill = GridBagConstraints.BOTH;
    emm.weightx = 1;
    emm.weighty = 1;

    // Create column headers
    String[] column =
        {"ID", "FIRST NAME", "LAST NAME", "PHONE", "EMAIL", "NID", "POSITION", "DEPARTMENT"};

    // Initialize the table with a DefaultTableModel
    DefaultTableModel tableModel = new DefaultTableModel(column, 0);
    table = new JTable(tableModel);
    table.setBackground(Color.WHITE);
    table.setForeground(Color.BLACK);
    table.setFont(new Font("Times New Roman", Font.PLAIN, 13));
    table.setGridColor(Color.BLACK);
    table.setShowGrid(true);

    // Set intercell spacing to default
    table.setIntercellSpacing(new Dimension(1, 1));

    // Set the custom cell renderer to color rows
    table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

    // Set the custom header renderer
    JTableHeader header = table.getTableHeader();
    header.setDefaultRenderer(new CustomHeaderRenderer());
    header.setFont(new Font("Arial", Font.BOLD, 14));

    // Set preferred size for JTable
    table.setPreferredScrollableViewportSize(new Dimension(400, 380));

    // Create JScrollPane with JTable
    scrollpane = new JScrollPane(table);
    scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollpane.setPreferredSize(new Dimension(200, 200)); // Set preferred size for JScrollPane
    scrollpane.getViewport().setBackground(Color.WHITE);
    scrollpane.setBackground(Color.WHITE);

    // Add scroll pane with table to JPanel (empform)
    empnorth.add(northnorth, BorderLayout.NORTH);
    centersouth.add(scrollpane, emm);
    empcenter.add(centersouth, BorderLayout.SOUTH);
    empcenter.add(infoinput, BorderLayout.CENTER);

    mainpanel.add(empcenter, BorderLayout.CENTER);
    mainpanel.add(empsouth, BorderLayout.SOUTH);
    mainpanel.add(empnorth, BorderLayout.NORTH);
    mainpanel.add(empwest, BorderLayout.WEST);
    mainpanel.add(empeast, BorderLayout.EAST);

    add(mainpanel, BorderLayout.CENTER);

    // Fetch data from database and populate the table
    populateTable(tableModel);
  }

  // Method to fetch employee information from the database and populate the table
  private void populateTable(DefaultTableModel tableModel) {
    String dbURL =
        "jdbc:sqlserver://NUKTHEAN\\DBSERVER;databaseName=ATTENDANCE_SYSTEM;encrypt=true;trustServerCertificate=true";
    String user = "sa";
    String pass = "password1";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection
      conn = DriverManager.getConnection(dbURL, user, pass);
      System.out.println("empform: Connected to the database");

      // Prepare the SQL SELECT statement
      String sql =
          "SELECT ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT FROM EMP_INFO";

      // Create the PreparedStatement
      pstmt = conn.prepareStatement(sql);

      // Execute the query
      rs = pstmt.executeQuery();

      // Iterate over the ResultSet and populate the table model
      while (rs.next()) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(rs.getInt("ID"));
        row.add(rs.getString("FIRST_NAME"));
        row.add(rs.getString("LAST_NAME"));
        row.add(rs.getString("PHONE"));
        row.add(rs.getString("EMAIL"));
        row.add(rs.getString("NID"));
        row.add(rs.getString("POSITION"));
        row.add(rs.getString("DEPARTMENT"));
        tableModel.addRow(row.toArray());
      }

      // Adjust column widths after populating the table
      adjustColumnWidths();

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

  // Method to adjust column widths
  private void adjustColumnWidths() {
    // Set column widths based on content
    TableColumnModel columnModel = table.getColumnModel();

    // Set width for ID column (index 0) smaller than other columns
    columnModel.getColumn(0).setPreferredWidth(1); // Adjust the width as needed

    // You can set preferred widths for other columns here if needed
  }

  // Custom TableCellRenderer to set row colors
  private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      Component c =
          super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (!isSelected) {
        if (row % 2 == 0) {
          c.setBackground(Color.LIGHT_GRAY);
          c.setForeground(Color.BLACK);
        } else {
          c.setBackground(Color.WHITE);
          c.setForeground(Color.BLACK);
        }
      } else {
        c.setBackground(Color.BLUE);
        c.setForeground(Color.WHITE);
      }
      return c;
    }
  }

  // Custom HeaderRenderer to set column header colors
  private static class CustomHeaderRenderer extends DefaultTableCellRenderer {
    public CustomHeaderRenderer() {
      setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      Component c =
          super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      c.setBackground(new Color(15, 41, 102));
      c.setForeground(Color.WHITE);
      return c;
    }
  }
}
