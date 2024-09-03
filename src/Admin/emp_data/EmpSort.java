package Admin.emp_data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import loginpage.User;
import utils.DatabaseConnection;
import utils.DialogUtils;
import utils.TableUtils;
import utils.ImgUtils.ImageDisplayWithResize;

public class EmpSort extends JFrame implements ActionListener {

  public static DefaultTableModel tableModelSort;
  public static JTable tableResult;
  public static JScrollPane paneResult;
  private static EmpSort empsort;
  private static TableUtils tableutil = new TableUtils(); // Create an instance of EmpTabl

  JLabel lbldep = new JLabel("Department");
  JComboBox<String> cmbdep = new JComboBox<>();

  private static String[] column = {"PROFILE", "ID", "FIRST NAME", "LAST NAME", "PHONE", "EMAIL",
      "NID", "POSITION", "DEPARTMENT"};

  public EmpSort() {

    empsort = this;

    tableModelSort = tableutil.getTableModel(column);
    tableResult = tableutil.getTable(tableModelSort);
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
    // Adjust column widths after populating the table
    TableUtils.adjustColumnWidth(tableResult, 0, 60);
    TableUtils.adjustColumnWidth(tableResult, 1, 30);
    TableUtils.adjustColumnWidth(tableResult, 4, 30);
    TableUtils.adjustColumnWidth(tableResult, 5, 70);

    TableUtils.adjustRowHeights(tableResult);
    loadDepartments();

  }

  private void loadDepartments() {
    // Sample departments, replace with actual department names from your database
    // or configuration
    String[] departments = User.getDepartment();
    for (String dep : departments) {
      cmbdep.addItem(dep);
    }
  }

  public void handleSortDep(String department) {
    System.out.println("Sort requested for ID: " + department);
    sortbyDepartment(department);
  }

  public void showSortDialog() {
    JFrame frame = new JFrame("Sort");
    JPanel panel1 = new JPanel(new GridBagLayout());
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel("Choose option to sort");


    JButton okButton = new JButton("OK");
    Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

    label.setFont(customFont);
    cmbdep.setFont(customFont);

    cmbdep.setPreferredSize(new Dimension(120, 20));
    cmbdep.setMinimumSize(new Dimension(120, 20));

    okButton.setBackground(Color.LIGHT_GRAY);
    okButton.setFocusPainted(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel1.add(label, gbc);

    gbc.gridy = 1;
    panel1.add(cmbdep, gbc);


    panel1.setPreferredSize(new Dimension(250, 80));
    panel1.setBackground(Color.WHITE);

    panel2.setPreferredSize(new Dimension(250, 40));
    panel2.setBackground(new Color(15, 41, 102));
    panel2.add(okButton);
    // Define the ActionListener for okButton
    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedItem = (String) cmbdep.getSelectedItem();
        // Perform the desired action with the selected item
        handleSortDep(selectedItem);
        frame.dispose();
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


  public static void sortbyDepartment(String department) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    tableModelSort.setRowCount(0);

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("SearchByID: Connected to the database");

      // Prepare the SQL INSERT statement
      String sql =
          "SELECT EMP_IMG, EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT "
              + "FROM Employees WHERE department = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, department); // Set the ID parameter
      rs = pstmt.executeQuery();
      // Iterate over the ResultSet and populate the table model

      while (rs.next()) {
        ArrayList<Object> row = new ArrayList<>();

        Blob blob = rs.getBlob("Emp_Img");
        ImageIcon imageIcon = null;
        if (blob != null) {
          byte[] imageBytes = blob.getBytes(1, (int) blob.length());
          ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

          // Convert byte array to BufferedImage
          BufferedImage originalImage = null;
          try {
            originalImage = ImageIO.read(bis);
          } catch (IOException e) {
            e.printStackTrace();
          }

          if (originalImage != null) {
            // Resize the image while maintaining aspect ratio
            int maxWidth = 60;
            BufferedImage resizedImage =
                ImageDisplayWithResize.resizeImageMultiStep(originalImage, maxWidth);
            imageIcon = new ImageIcon(resizedImage);
          } else {
            imageIcon = new ImageIcon(); // Empty icon for missing images
          }
        } else {
          imageIcon = new ImageIcon(); // Empty icon for null blobs
        }

        row.add(imageIcon);


        row.add(rs.getInt("EMPLOYEE_ID"));
        row.add(rs.getString("FIRST_NAME"));
        row.add(rs.getString("LAST_NAME"));
        row.add(rs.getString("PHONE"));
        row.add(rs.getString("EMAIL"));
        row.add(rs.getString("NID"));
        row.add(rs.getString("POSITION"));
        row.add(rs.getString("DEPARTMENT"));
        tableModelSort.addRow(row.toArray());
      }

      empsort.setVisible(true);

      System.out.println("Sucessful Sort by department: " + department);

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

  @Override
  public void actionPerformed(ActionEvent e) {

  }

}
