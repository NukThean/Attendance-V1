package emp_data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import loginpage.User;
import utils.DatabaseConnection;
import java.awt.*;

public class EmpInput extends JFrame implements ActionListener {

  JButton btconfirmamend = new JButton("Confirm");
  JLabel lblname1 = new JLabel("First Name: ");
  JLabel lblname2 = new JLabel("Last Name: ");
  JLabel lblsex = new JLabel("Sex");
  JLabel lblphone = new JLabel("Phone: ");
  JLabel lblemail = new JLabel("Email: ");
  JLabel lblnid = new JLabel("NID: ");
  JLabel lblposition = new JLabel("Position: ");
  JLabel lbldep = new JLabel("Department: ");
  JLabel lblid = new JLabel("ID: ");


  JButton btnmore = new JButton("Add more");
  JButton btnfinish = new JButton("Finish");

  JTextField txtname1 = new JTextField();
  JTextField txtname2 = new JTextField();
  JTextField txtsex = new JTextField();
  JTextField txtphone = new JTextField();
  JTextField txtemail = new JTextField();
  JTextField txtnid = new JTextField();
  JTextField txtposition = new JTextField();
  JComboBox<String> cmbdep = new JComboBox<>();
  JTextField txtid = new JTextField();

  public EmpInput() {

    setLayout(new BorderLayout());
    JPanel main = new JPanel(new BorderLayout());
    JPanel ccenter = new JPanel(new GridBagLayout());
    JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

    main.setPreferredSize(new Dimension(864, 688));
    south.setPreferredSize(new Dimension(0, 40));

    Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

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

    lblname1.setFont(customFont);
    lblname2.setFont(customFont);
    lblsex.setFont(customFont);
    lblphone.setFont(customFont);
    lblemail.setFont(customFont);
    lblnid.setFont(customFont);
    lblposition.setFont(customFont);
    lbldep.setFont(customFont);
    lblid.setFont(customFont);

    txtname1.setFont(customFont);
    txtname2.setFont(customFont);
    txtsex.setFont(customFont);
    txtphone.setFont(customFont);
    txtemail.setFont(customFont);
    txtnid.setFont(customFont);
    txtposition.setFont(customFont);
    cmbdep.setFont(customFont);
    txtid.setFont(customFont);
    txtid.setEditable(false);
    txtid.setBackground(Color.GRAY);

    btnfinish.setFont(customFont);
    btnmore.setFont(customFont);

    btnmore.addActionListener(this);
    btnfinish.addActionListener(this);

    err.insets = new Insets(5, 15, 0, 310);
    addlabeltopanel(ccenter, lblname1, err, 0, 0);
    err.insets = new Insets(10, 15, 5, 310);
    addlabeltopanel(ccenter, lblname2, err, 1, 0);
    addlabeltopanel(ccenter, lblsex, err, 2, 0);
    addlabeltopanel(ccenter, lblphone, err, 3, 0);
    addlabeltopanel(ccenter, lblemail, err, 4, 0);
    err.insets = new Insets(5, 0, 0, 310);
    addlabeltopanel(ccenter, lblnid, err, 0, 1);
    addlabeltopanel(ccenter, lbldep, err, 1, 1);
    addlabeltopanel(ccenter, lblposition, err, 2, 1);
    addlabeltopanel(ccenter, lblid, err, 3, 1); // Add lblid to the panel

    nghz.insets = new Insets(10, 120, 5, 0);
    addtxtfieldtopanel(ccenter, txtname1, nghz, 0, 0);
    addtxtfieldtopanel(ccenter, txtname2, nghz, 1, 0);
    addtxtfieldtopanel(ccenter, txtsex, nghz, 2, 0);
    addtxtfieldtopanel(ccenter, txtphone, nghz, 3, 0);
    addtxtfieldtopanel(ccenter, txtemail, nghz, 4, 0);
    nghz.insets = new Insets(10, 110, 5, 0);
    addtxtfieldtopanel(ccenter, txtnid, nghz, 0, 1);
    addcomboboxtopanel(ccenter, cmbdep, nghz, 1, 1);
    addtxtfieldtopanel(ccenter, txtposition, nghz, 2, 1);
    addtxtfieldtopanel(ccenter, txtid, nghz, 3, 1); // Add txtid to the panel

    addButtonToPanel(south, btnmore);
    addButtonToPanel(south, btnfinish);

    setTitle("Employee Input");
    setSize(864, 688); // Adjust the size as needed
    setLocationRelativeTo(null); // Center the frame on the screen
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    main.add(south, BorderLayout.SOUTH);
    main.add(ccenter, BorderLayout.CENTER);
    add(main, BorderLayout.CENTER);

    loadDepartments();

    txtname1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtname2.requestFocus();
      }
    });

    txtname2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtsex.requestFocus();
      }
    });

    txtsex.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtphone.requestFocus();
      }
    });

    txtphone.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtemail.requestFocus();
      }
    });

    txtemail.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtnid.requestFocus();
      }
    });

    txtnid.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cmbdep.requestFocus();
      }
    });

    cmbdep.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtposition.requestFocus();
      }
    });

  }

  private void loadDepartments() {
    // Sample departments, replace with actual department names from your database
    // or configuration
    String[] departments = User.getDepartment();
    for (String dep : departments) {
      cmbdep.addItem(dep);
    }
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

  private void addButtonToPanel(JPanel panel, JButton button) {
    button.setPreferredSize(new Dimension(85, 25));
    button.setMinimumSize(new Dimension(85, 25));
    button.setBackground(Color.LIGHT_GRAY);
    button.setFocusPainted(false);
    button.setMargin(new Insets(0, 5, 0, 5));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    panel.add(button);
  }

  private void addcomboboxtopanel(JPanel panelll, JComboBox<String> comboBox,
      GridBagConstraints nghz, int gridy, int gridx) {
    nghz.gridx = gridx;
    nghz.gridy = gridy;
    comboBox.setPreferredSize(new Dimension(290, 30));
    comboBox.setMinimumSize(new Dimension(290, 30));
    comboBox.setBackground(Color.white);
    comboBox.setOpaque(true);
    comboBox.setForeground(Color.BLACK);
    panelll.add(comboBox, nghz);
  }

  private void clearTextFields() {
    txtname1.setText("");
    txtname2.setText("");
    txtsex.setText("");
    txtphone.setText("");
    txtemail.setText("");
    txtnid.setText("");
    txtposition.setText("");
    cmbdep.setSelectedIndex(0);
    txtid.setText("");
  }

  public static void insertStaffInfo(ArrayList<emp_info> staffList) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection
      conn = DatabaseConnection.getConnection();
      System.out.println("Info_input_insert: Connected to the database");

      // Prepare the SQL INSERT statement for Employees
      String sql =
          "INSERT INTO Employees (FIRST_NAME, LAST_NAME, SEX, PHONE, EMAIL, NID, POSITION, DEPARTMENT) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

      // Create the PreparedStatement
      pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

      // Iterate over the ArrayList and insert data
      for (emp_info staff : staffList) {
        pstmt.setString(1, staff.getFirstName());
        pstmt.setString(2, staff.getLastName());
        pstmt.setString(3, staff.getSex());
        pstmt.setInt(4, staff.getPhone());
        pstmt.setString(5, staff.getEmail());
        pstmt.setInt(6, staff.getNid());
        pstmt.setString(7, staff.getPosition());
        pstmt.setString(8, staff.getDepartment());

        // Execute the insert statement
        pstmt.executeUpdate();

        // Retrieve the generated keys (i.e., the newly inserted employee ID)
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          int employeeId = rs.getInt(1); // Retrieve the ID

          // Insert corresponding user role into the User table
          String userSql = "INSERT INTO [User] (role, user_id) " + "VALUES (?, ?)";
          try (PreparedStatement userPstmt = conn.prepareStatement(userSql)) {
            // userPstmt.setString(1, "1"); // Set the password
            userPstmt.setString(1, "user"); // Set the role
            userPstmt.setInt(2, employeeId); // Set the employee ID

            userPstmt.executeUpdate(); // Execute the insert for the User table
          }
        }
      }

      System.out.println("All staff information inserted successfully!");

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
    if (e.getSource() == btnmore) {
      // Gather data from text fields
      String firstName = txtname1.getText();
      String lastName = txtname2.getText();
      String sex = txtsex.getText();
      String phone = txtphone.getText();
      String email = txtemail.getText();
      String nid = txtnid.getText();
      String position = txtposition.getText();
      String department = (String) cmbdep.getSelectedItem();

      // Create an emp_info object
      emp_info staff = new emp_info(0, firstName, lastName, sex, Integer.parseInt(phone), email,
          Integer.parseInt(nid), position, department);

      // Insert the data into the database
      ArrayList<emp_info> staffList = new ArrayList<>();
      staffList.add(staff);
      insertStaffInfo(staffList);

      // Clear text fields after insertion
      clearTextFields();
    } else if (e.getSource() == btnfinish) {
      // Gather data from text fields
      String firstName = txtname1.getText();
      String lastName = txtname2.getText();
      String sex = txtsex.getText();
      String phone = txtphone.getText();
      String email = txtemail.getText();
      String nid = txtnid.getText();
      String position = txtposition.getText();
      String department = (String) cmbdep.getSelectedItem();

      // Create an emp_info object
      emp_info staff = new emp_info(0, firstName, lastName, sex, Integer.parseInt(phone), email,
          Integer.parseInt(nid), position, department);

      // Insert the data into the database
      ArrayList<emp_info> staffList = new ArrayList<>();
      staffList.add(staff);
      insertStaffInfo(staffList);

      dispose();
    }

  }
}
