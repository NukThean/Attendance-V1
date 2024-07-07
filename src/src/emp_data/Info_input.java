package src.emp_data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Info_input extends JPanel implements ActionListener {
  JButton btnadd = new JButton("Add");
  JButton btnedit = new JButton("Edit");
  JButton btnremove = new JButton("Remove");
  JButton btnsearch = new JButton("Search");
  JButton btnsort = new JButton("Sort"); // sort for the best attendance and sort by department
  JLabel lblname1 = new JLabel("Enter First Name: ");
  JLabel lblname2 = new JLabel("Enter Last Name: ");
  JLabel lblphone = new JLabel("Enter Phone: ");
  JLabel lblemail = new JLabel("Enter Email: ");
  JLabel lblnid = new JLabel("Enter NID: ");
  JLabel lblposition = new JLabel("Enter Position: ");
  JLabel lbldep = new JLabel("Enter Department: ");


  JTextField txtname1 = new JTextField();
  JTextField txtname2 = new JTextField();
  JTextField txtphone = new JTextField();
  JTextField txtemail = new JTextField();
  JTextField txtnid = new JTextField();
  JTextField txtposition = new JTextField();
  JTextField txtdep = new JTextField();

  public Info_input() {
    setLayout(new BorderLayout());
    JPanel mainpanel = new JPanel(new BorderLayout());
    JPanel center = new JPanel(new BorderLayout());
    JPanel ccenter = new JPanel(new GridBagLayout());
    JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Using FlowLayout
    buttonpanel.setPreferredSize(new Dimension(864, 25));

    Font customFont = new Font("Times New Roman", Font.PLAIN, 15);
    ccenter.setBackground(Color.WHITE);

    GridBagConstraints err = new GridBagConstraints();
    err.insets = new Insets(10, 10, 10, 10);
    err.fill = GridBagConstraints.NONE;
    err.weightx = 1;
    err.weighty = 1;
    err.gridx = 0;
    err.gridy = 0;

    GridBagConstraints nghz = new GridBagConstraints();
    nghz.insets = new Insets(10, 10, 10, 10);
    nghz.fill = GridBagConstraints.NONE;
    nghz.weightx = 1;
    nghz.weighty = 1;
    nghz.gridx = 0;
    nghz.gridy = 0;

    // Apply custom font to buttons
    btnadd.setFont(customFont);
    btnedit.setFont(customFont);
    btnremove.setFont(customFont);
    btnsearch.setFont(customFont);
    btnsort.setFont(customFont);

    btnadd.addActionListener(this);
    btnedit.addActionListener(this);
    btnremove.addActionListener(this);
    btnsearch.addActionListener(this);
    btnsort.addActionListener(this);

    addButtonToPanel(buttonpanel, btnadd);
    addButtonToPanel(buttonpanel, btnedit);
    addButtonToPanel(buttonpanel, btnremove);
    addButtonToPanel(buttonpanel, btnsearch);
    addButtonToPanel(buttonpanel, btnsort);

    customFont = new Font("Times New Roman", Font.PLAIN, 16);

    lblname1.setFont(customFont);
    lblname2.setFont(customFont);
    lblphone.setFont(customFont);
    lblemail.setFont(customFont);
    lblnid.setFont(customFont);
    lblposition.setFont(customFont);
    lbldep.setFont(customFont);

    txtname1.setFont(customFont);
    txtname2.setFont(customFont);
    txtphone.setFont(customFont);
    txtemail.setFont(customFont);
    txtnid.setFont(customFont);
    txtposition.setFont(customFont);
    txtdep.setFont(customFont);

    err.insets = new Insets(5, 15, 0, 310);
    addlabeltopanel(ccenter, lblname1, err, 0, 0);
    err.insets = new Insets(10, 15, 5, 310);
    addlabeltopanel(ccenter, lblname2, err, 1, 0);
    addlabeltopanel(ccenter, lblphone, err, 2, 0);
    addlabeltopanel(ccenter, lblemail, err, 3, 0);
    addlabeltopanel(ccenter, lblnid, err, 4, 0);
    err.insets = new Insets(5, 0, 0, 310);
    addlabeltopanel(ccenter, lbldep, err, 0, 1);
    addlabeltopanel(ccenter, lblposition, err, 1, 1);



    nghz.insets = new Insets(10, 120, 5, 0);
    addtxtfieldtopanel(ccenter, txtname1, nghz, 0, 0);
    addtxtfieldtopanel(ccenter, txtname2, nghz, 1, 0);
    addtxtfieldtopanel(ccenter, txtphone, nghz, 2, 0);
    addtxtfieldtopanel(ccenter, txtemail, nghz, 3, 0);
    addtxtfieldtopanel(ccenter, txtnid, nghz, 4, 0);
    nghz.insets = new Insets(10, 110, 5, 0);
    addtxtfieldtopanel(ccenter, txtdep, nghz, 0, 1);
    addtxtfieldtopanel(ccenter, txtposition, nghz, 1, 1);



    buttonpanel.setBackground(Color.WHITE);
    center.setBackground(Color.WHITE);

    center.add(buttonpanel, BorderLayout.SOUTH);
    center.add(ccenter, BorderLayout.CENTER);
    mainpanel.add(center, BorderLayout.CENTER);
    add(mainpanel, BorderLayout.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnadd) {
      // Gather data from text fields
      String firstName = txtname1.getText();
      String lastName = txtname2.getText();
      String phone = txtphone.getText();
      String email = txtemail.getText();
      String nid = txtnid.getText();
      String position = txtposition.getText();
      String department = txtdep.getText();

      // Create an emp_info object
      emp_info staff = new emp_info(0, firstName, lastName, Integer.parseInt(phone), email,
          Integer.parseInt(nid), position, department);

      // Insert the data into the database
      ArrayList<emp_info> staffList = new ArrayList<>();
      staffList.add(staff);
      insertStaffInfo(staffList);

      // Clear text fields after insertion
      txtname1.setText("");
      txtname2.setText("");
      txtphone.setText("");
      txtemail.setText("");
      txtnid.setText("");
      txtposition.setText("");
      txtdep.setText("");
    }
    // Implement other button actions if needed
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

  private void addlabeltopanel(JPanel panell, JLabel Label, GridBagConstraints err, int gridy,
      int gridx) {
    err.gridy = gridy;
    err.gridx = gridx;
    Label.setPreferredSize(new Dimension(120, 30));
    Label.setMinimumSize(new Dimension(120, 30));
    Label.setBackground(Color.WHITE);
    Label.setOpaque(true);
    panell.add(Label, err);
  }

  private void addtxtfieldtopanel(JPanel panelll, JTextField field, GridBagConstraints nghz,
      int gridy, int gridx) {
    nghz.gridx = gridx;
    nghz.gridy = gridy;
    field.setPreferredSize(new Dimension(290, 30));
    field.setMinimumSize(new Dimension(290, 30));
    field.setBackground(Color.white);
    field.setOpaque(true);
    field.setForeground(Color.BLACK);
    panelll.add(field, nghz);
  }

  public static int getStaffCount() {
    String dbURL =
        "jdbc:sqlserver://NUKTHEAN\\DBSERVER;databaseName=ATTENDANCE_SYSTEM;encrypt=true;trustServerCertificate=true";
    String user = "sa";
    String pass = "password1";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int count = 0;

    try {
      // Establish the connection
      conn = DriverManager.getConnection(dbURL, user, pass);
      System.out.println("Info_input: Connected to the database");

      // Prepare the SQL COUNT statement
      String sql = "SELECT COUNT(*) FROM EMP_INFO";

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


  public static void insertStaffInfo(ArrayList<emp_info> staffList) {
    String dbURL =
        "jdbc:sqlserver://NUKTHEAN\\DBSERVER;databaseName=ATTENDANCE_SYSTEM;encrypt=true;trustServerCertificate=true";
    String user = "sa";
    String pass = "password1";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      // Establish the connection
      conn = DriverManager.getConnection(dbURL, user, pass);
      System.out.println("Info_input_insert: Connected to the database");

      // Prepare the SQL INSERT statement
      String sql =
          "INSERT INTO EMP_INFO (FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT) VALUES (?, ?, ?, ?, ?, ?, ?)";

      // Create the PreparedStatement
      pstmt = conn.prepareStatement(sql);

      // Iterate over the ArrayList and insert data
      for (emp_info staff : staffList) {
        pstmt.setString(1, staff.getFirstName());
        pstmt.setString(2, staff.getLastName());
        pstmt.setInt(3, staff.getPhone());
        pstmt.setString(4, staff.getEmail());
        pstmt.setInt(5, staff.getNid());
        pstmt.setString(6, staff.getPosition());
        pstmt.setString(7, staff.getDepartment());

        // Execute the statement
        pstmt.executeUpdate();
      }

      System.out.println("All staff information inserted successfully!");

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

}
