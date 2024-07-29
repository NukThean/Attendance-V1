// package src.emp_data;

// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import javax.swing.*;
// import javax.swing.table.DefaultTableCellRenderer;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.JTableHeader;
// import javax.swing.table.TableColumn;
// import javax.swing.table.TableColumnModel;
// import src.DatabaseConnection;
// import java.awt.*;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import javax.swing.plaf.basic.BasicScrollBarUI;



// public class EmpFormData extends JPanel implements ActionListener {

//   JButton btnadd = new JButton("Add");
//   JButton btnamend = new JButton("Amend");
//   JButton btnremove = new JButton("Remove");
//   JButton btnsearch = new JButton("Search");
//   JButton btnsort = new JButton("Sort"); // sort for the best attendance and sort by department
//   JButton btconfirmamend = new JButton("Confirm");
//   JLabel lblname1 = new JLabel("Enter First Name: ");
//   JLabel lblname2 = new JLabel("Enter Last Name: ");
//   JLabel lblphone = new JLabel("Enter Phone: ");
//   JLabel lblemail = new JLabel("Enter Email: ");
//   JLabel lblnid = new JLabel("Enter NID: ");
//   JLabel lblposition = new JLabel("Enter Position: ");
//   JLabel lbldep = new JLabel("Enter Department: ");
//   JLabel lblid = new JLabel("ID: ");

//   JTextField txtname1 = new JTextField();
//   JTextField txtname2 = new JTextField();
//   JTextField txtphone = new JTextField();
//   JTextField txtemail = new JTextField();
//   JTextField txtnid = new JTextField();
//   JTextField txtposition = new JTextField();
//   JTextField txtdep = new JTextField();
//   JTextField txtid = new JTextField();

//   public JTable table;
//   private DefaultTableModel tableModel;
//   private JScrollPane scrollpane;
//   private Timer refreshTimer;
//   int i = 0; // for count time refresh every 5sec


//   public EmpFormData() {
//     setLayout(new BorderLayout()); // Use BorderLayout for main panel
//     JPanel mainpanel = new JPanel(new BorderLayout());
//     JPanel empnorth = new JPanel(new BorderLayout());
//     JPanel empcenter = new JPanel(new BorderLayout());
//     JPanel empsouth = new JPanel(new BorderLayout());
//     JPanel empwest = new JPanel(new BorderLayout());
//     JPanel empeast = new JPanel(new BorderLayout());
//     JPanel northnorth = new JPanel(new GridBagLayout());
//     JPanel centersouth = new JPanel(new GridBagLayout());

//     JPanel maincennorth = new JPanel(new BorderLayout());
//     JPanel center = new JPanel(new BorderLayout());
//     JPanel ccenter = new JPanel(new GridBagLayout());
//     JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Using FlowLayout
//     buttonpanel.setPreferredSize(new Dimension(864, 25));

//     mainpanel.setPreferredSize(new Dimension(864, 688));

//     empnorth.setPreferredSize(new Dimension(0, 80));
//     empcenter.setPreferredSize(new Dimension(664, 428));
//     empsouth.setPreferredSize(new Dimension(0, 0));
//     empwest.setPreferredSize(new Dimension(0, 0));
//     empeast.setPreferredSize(new Dimension(0, 0));

//     northnorth.setPreferredSize(new Dimension(0, 40));
//     centersouth.setPreferredSize(new Dimension(0, 380));

//     empnorth.setBackground(Color.GRAY);
//     empcenter.setBackground(Color.GREEN);
//     empsouth.setBackground(Color.white);
//     centersouth.setBackground(Color.white);
//     buttonpanel.setBackground(Color.GRAY);
//     center.setBackground(Color.CYAN);

//     northnorth.setBackground(new Color(15, 41, 102));
//     empnorth.setBackground(new Color(157, 160, 168));

//     GridBagConstraints emm = new GridBagConstraints();
//     emm.insets = new Insets(10, 10, 10, 10);
//     emm.fill = GridBagConstraints.BOTH;
//     emm.weightx = 1;
//     emm.weighty = 1;

//     Font customFont = new Font("Times New Roman", Font.PLAIN, 15);
//     ccenter.setBackground(Color.cyan);

//     GridBagConstraints err = new GridBagConstraints();
//     err.insets = new Insets(10, 10, 10, 10);
//     err.fill = GridBagConstraints.NONE;
//     err.weightx = 1;
//     err.weighty = 1;
//     err.gridx = 0;
//     err.gridy = 0;

//     GridBagConstraints nghz = new GridBagConstraints();
//     nghz.insets = new Insets(10, 10, 10, 10);
//     nghz.fill = GridBagConstraints.NONE;
//     nghz.weightx = 1;
//     nghz.weighty = 1;
//     nghz.gridx = 0;
//     nghz.gridy = 0;

//     // Apply custom font to buttons
//     btnadd.setFont(customFont);
//     btnamend.setFont(customFont);
//     btnremove.setFont(customFont);
//     btnsearch.setFont(customFont);
//     btnsort.setFont(customFont);
//     btconfirmamend.setFont(customFont);

//     btnadd.addActionListener(this);
//     btnamend.addActionListener(this);
//     btnremove.addActionListener(this);
//     btnsearch.addActionListener(this);
//     btnsort.addActionListener(this);
//     btconfirmamend.addActionListener(this);
//     // btconfirmamend.setEnabled(false);

//     addButtonToPanel(buttonpanel, btnadd);
//     addButtonToPanel(buttonpanel, btnamend);
//     addButtonToPanel(buttonpanel, btnremove);
//     addButtonToPanel(buttonpanel, btnsearch);
//     addButtonToPanel(buttonpanel, btnsort);
//     addButtonToPanel(buttonpanel, btconfirmamend);
//     btnamend.addActionListener(this);

//     customFont = new Font("Times New Roman", Font.PLAIN, 16);

//     lblname1.setFont(customFont);
//     lblname2.setFont(customFont);
//     lblphone.setFont(customFont);
//     lblemail.setFont(customFont);
//     lblnid.setFont(customFont);
//     lblposition.setFont(customFont);
//     lbldep.setFont(customFont);
//     lblid.setFont(customFont);

//     txtname1.setFont(customFont);
//     txtname2.setFont(customFont);
//     txtphone.setFont(customFont);
//     txtemail.setFont(customFont);
//     txtnid.setFont(customFont);
//     txtposition.setFont(customFont);
//     txtdep.setFont(customFont);
//     txtid.setFont(customFont);
//     txtid.setEditable(false);
//     txtid.setBackground(Color.GRAY);

//     err.insets = new Insets(5, 15, 0, 310);
//     addlabeltopanel(ccenter, lblname1, err, 0, 0);
//     err.insets = new Insets(10, 15, 5, 310);
//     addlabeltopanel(ccenter, lblname2, err, 1, 0);
//     addlabeltopanel(ccenter, lblphone, err, 2, 0);
//     addlabeltopanel(ccenter, lblemail, err, 3, 0);
//     addlabeltopanel(ccenter, lblnid, err, 4, 0);
//     err.insets = new Insets(5, 0, 0, 310);
//     addlabeltopanel(ccenter, lbldep, err, 0, 1);
//     addlabeltopanel(ccenter, lblposition, err, 1, 1);
//     addlabeltopanel(ccenter, lblid, err, 2, 1); // Add lblid to the panel



//     nghz.insets = new Insets(10, 120, 5, 0);
//     addtxtfieldtopanel(ccenter, txtname1, nghz, 0, 0);
//     addtxtfieldtopanel(ccenter, txtname2, nghz, 1, 0);
//     addtxtfieldtopanel(ccenter, txtphone, nghz, 2, 0);
//     addtxtfieldtopanel(ccenter, txtemail, nghz, 3, 0);
//     addtxtfieldtopanel(ccenter, txtnid, nghz, 4, 0);
//     nghz.insets = new Insets(10, 110, 5, 0);
//     addtxtfieldtopanel(ccenter, txtdep, nghz, 0, 1);
//     addtxtfieldtopanel(ccenter, txtposition, nghz, 1, 1);
//     addtxtfieldtopanel(ccenter, txtid, nghz, 2, 1); // Add txtid to the panel


//     txtname1.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         txtname2.requestFocus();
//       }
//     });

//     txtname2.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         txtphone.requestFocus();
//       }
//     });


//     txtphone.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         txtemail.requestFocus();
//       }
//     });

//     txtemail.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         txtnid.requestFocus();
//       }
//     });

//     txtnid.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         txtdep.requestFocus();
//       }
//     });

//     txtdep.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         txtposition.requestFocus();
//       }
//     });


//     // Create column headers
//     String[] column =
//         {"ID", "FIRST NAME", "LAST NAME", "PHONE", "EMAIL", "NID", "POSITION", "DEPARTMENT"};

//     // Initialize the table with a DefaultTableModel
//     tableModel = new DefaultTableModel(column, 0);
//     table = new JTable(tableModel);
//     table.setBackground(Color.WHITE);
//     table.setForeground(Color.BLACK);
//     table.setFont(new Font("Times New Roman", Font.PLAIN, 13));
//     table.setGridColor(Color.BLACK);
//     table.setShowGrid(true);

//     // Set intercell spacing to default
//     table.setIntercellSpacing(new Dimension(1, 1));
//     table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


//     // Set the custom cell renderer to color rows
//     table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

//     // Set the custom header renderer
//     JTableHeader header = table.getTableHeader();
//     header.setDefaultRenderer(new CustomHeaderRenderer());
//     header.setFont(new Font("Arial", Font.BOLD, 14));

//     // Set preferred size for JTable
//     table.setPreferredScrollableViewportSize(new Dimension(400, 380));

//     // Create JScrollPane with JTable
//     scrollpane = new JScrollPane(table);
//     scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//     scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//     scrollpane.getVerticalScrollBar().setBackground(Color.LIGHT_GRAY);
//     scrollpane.getHorizontalScrollBar().setBackground(Color.LIGHT_GRAY);
//     scrollpane.setPreferredSize(new Dimension(200, 100)); // Set preferred size for JScrollPane
//     scrollpane.getViewport().setBackground(Color.WHITE);
//     scrollpane.setBackground(Color.WHITE);



//     // Set custom components in the corners
//     scrollpane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, createCornerLabel());
//     scrollpane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, createCornerLabel());

//     // Customize the UI of the vertical scroll bar to hide arrow buttons
//     scrollpane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
//       @Override
//       protected void configureScrollBarColors() {
//         this.thumbColor = new Color(11, 57, 163); // Set thumb color
//       }

//       @Override
//       protected JButton createDecreaseButton(int orientation) {
//         return createZeroButton(); // Hide the decrease button
//       }

//       @Override
//       protected JButton createIncreaseButton(int orientation) {
//         return createZeroButton(); // Hide the increase button
//       }

//       private JButton createZeroButton() {
//         JButton button = new JButton();
//         Dimension zeroDim = new Dimension(0, 0);
//         button.setPreferredSize(zeroDim);
//         button.setMinimumSize(zeroDim);
//         button.setMaximumSize(zeroDim);
//         return button;
//       }
//     });

//     // Customize the UI of the horizontal scroll bar to hide arrow buttons
//     scrollpane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
//       @Override
//       protected void configureScrollBarColors() {
//         this.thumbColor = new Color(11, 57, 163); // Set thumb color
//       }

//       @Override
//       protected JButton createDecreaseButton(int orientation) {
//         return createZeroButton(); // Hide the decrease button
//       }

//       @Override
//       protected JButton createIncreaseButton(int orientation) {
//         return createZeroButton(); // Hide the increase button
//       }

//       private JButton createZeroButton() {
//         JButton button = new JButton();
//         Dimension zeroDim = new Dimension(0, 0);
//         button.setPreferredSize(zeroDim);
//         button.setMinimumSize(zeroDim);
//         button.setMaximumSize(zeroDim);
//         return button;
//       }
//     });


//     center.add(buttonpanel, BorderLayout.SOUTH);
//     center.add(ccenter, BorderLayout.CENTER);
//     maincennorth.add(center, BorderLayout.CENTER);

//     // Add scroll pane with table to JPanel (empform)
//     empnorth.add(northnorth, BorderLayout.NORTH);
//     centersouth.add(scrollpane, emm);
//     empcenter.add(centersouth, BorderLayout.SOUTH);
//     empcenter.add(maincennorth, BorderLayout.CENTER);

//     mainpanel.add(empcenter, BorderLayout.CENTER);
//     mainpanel.add(empsouth, BorderLayout.SOUTH);
//     mainpanel.add(empnorth, BorderLayout.NORTH);
//     mainpanel.add(empwest, BorderLayout.WEST);
//     mainpanel.add(empeast, BorderLayout.EAST);

//     add(mainpanel, BorderLayout.CENTER);

//     // Set up the auto-refresh timer
//     refreshTimer = new Timer(5000, e -> populateTable());
//     refreshTimer.start();

//     // Fetch initial data
//     populateTable();

//     updateButtonState();

//     btnamend.addMouseListener(new java.awt.event.MouseAdapter() {
//       public void mouseClicked(java.awt.event.MouseEvent evt) {
//         showAmendDialog();
//       }
//     });

//     btnremove.addMouseListener(new java.awt.event.MouseAdapter() {
//       public void mouseClicked(java.awt.event.MouseEvent evt) {
//         showDeleteDialog();
//       }
//     });


//     txtid.addKeyListener(new KeyAdapter() {
//       @Override
//       public void keyReleased(KeyEvent e) {
//         updateButtonState();
//       }
//     });

//   }

//   public void populateTable() {
//     tableModel.setRowCount(0);

//     Connection conn = null;
//     PreparedStatement pstmt = null;
//     ResultSet rs = null;
//     i++;

//     try {
//       // Establish the connection
//       conn = DatabaseConnection.getConnection();
//       // System.out.println("empform: load populateTable " + i);

//       // Prepare the SQL SELECT statement
//       String sql =
//           "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT FROM Employees";

//       // Create the PreparedStatement
//       pstmt = conn.prepareStatement(sql);

//       // Execute the query
//       rs = pstmt.executeQuery();

//       // Iterate over the ResultSet and populate the table model
//       while (rs.next()) {
//         ArrayList<Object> row = new ArrayList<>();
//         row.add(rs.getInt("EMPLOYEE_ID"));
//         row.add(rs.getString("FIRST_NAME"));
//         row.add(rs.getString("LAST_NAME"));
//         row.add(rs.getString("PHONE"));
//         row.add(rs.getString("EMAIL"));
//         row.add(rs.getString("NID"));
//         row.add(rs.getString("POSITION"));
//         row.add(rs.getString("DEPARTMENT"));
//         tableModel.addRow(row.toArray());
//       }

//       // Adjust column widths after populating the table
//       adjustColumnWidths();

//     } catch (SQLException e) {
//       e.printStackTrace();
//     } finally {
//       // Close the resources
//       try {
//         if (rs != null) {
//           rs.close();
//         }
//         if (pstmt != null) {
//           pstmt.close();
//         }
//         if (conn != null) {
//           conn.close();
//         }
//       } catch (SQLException e) {
//         e.printStackTrace();
//       }
//     }
//   }

//   private static JLabel createCornerLabel() {
//     JLabel cornerLabel = new JLabel("");
//     cornerLabel.setBackground(Color.LIGHT_GRAY);
//     cornerLabel.setOpaque(true);
//     return cornerLabel;
//   }



//   private void adjustColumnWidths() {
//     // Set column widths based on content
//     TableColumnModel columnModel = table.getColumnModel();

//     // Set width for ID column (index 0) smaller than other columns
//     int[] columnWidths = {25, 75, 110, 80, 175, 80, 175, 175}; // Define desired widths
//     for (int i = 0; i < columnWidths.length; i++) {
//       TableColumn column = columnModel.getColumn(i);
//       column.setPreferredWidth(columnWidths[i]);
//       column.setMinWidth(columnWidths[i]);
//       column.setMaxWidth(columnWidths[i]);
//     }
//   }

//   // Custom TableCellRenderer to set row colors
//   private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
//     @Override
//     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//         boolean hasFocus, int row, int column) {
//       Component c =
//           super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//       if (!isSelected) {
//         if (row % 2 == 0) {// for highlist gray and white in table
//           c.setBackground(Color.LIGHT_GRAY);
//           c.setForeground(Color.BLACK);
//         } else {
//           c.setBackground(Color.WHITE);
//           c.setForeground(Color.BLACK);
//         }
//       } else {
//         c.setBackground(Color.BLUE); // for selected row
//         c.setForeground(Color.WHITE); // for selected row
//       }
//       return c;
//     }
//   }

//   // Custom HeaderRenderer to set column header colors
//   private static class CustomHeaderRenderer extends DefaultTableCellRenderer {
//     public CustomHeaderRenderer() {
//       setOpaque(true);
//     }

//     @Override
//     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//         boolean hasFocus, int row, int column) {
//       Component c =
//           super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//       c.setBackground(new Color(15, 41, 102));
//       c.setForeground(Color.WHITE);
//       return c;
//     }
//   }


//   @Override
//   public void actionPerformed(ActionEvent e) {
//     if (e.getSource() == btnadd) {
//       // Gather data from text fields
//       String firstName = txtname1.getText();
//       String lastName = txtname2.getText();
//       String phone = txtphone.getText();
//       String email = txtemail.getText();
//       String nid = txtnid.getText();
//       String position = txtposition.getText();
//       String department = txtdep.getText();

//       // Create an emp_info object
//       emp_info staff = new emp_info(0, firstName, lastName, Integer.parseInt(phone), email,
//           Integer.parseInt(nid), position, department);

//       // Insert the data into the database
//       ArrayList<emp_info> staffList = new ArrayList<>();
//       staffList.add(staff);
//       insertStaffInfo(staffList);

//       // Clear text fields after insertion
//       clearTextFields();
//     } else if (e.getSource() == btconfirmamend) {
//       // Gather data from text fields

//       int id = Integer.parseInt(txtid.getText());
//       String firstName = txtname1.getText();
//       String lastName = txtname2.getText();
//       String phone = txtphone.getText();
//       String email = txtemail.getText();
//       String nid = txtnid.getText();
//       String position = txtposition.getText();
//       String department = txtdep.getText();

//       // Create an emp_info object
//       emp_info staff = new emp_info(id, firstName, lastName, Integer.parseInt(phone), email,
//           Integer.parseInt(nid), position, department);

//       // Update the data in the database
//       System.out.println("Preparing to amend Staff information");
//       updateStaffInfo(staff);
//       btconfirmamend.setEnabled(false);

//       // Clear text fields after update
//       clearTextFields();


//     } else if (e.getSource() == btnremove) {

//       System.out.println("Preparing to delete Staff information");
//       // clearTextFields();

//     }
//   }

//   private void clearTextFields() {
//     txtname1.setText("");
//     txtname2.setText("");
//     txtphone.setText("");
//     txtemail.setText("");
//     txtnid.setText("");
//     txtposition.setText("");
//     txtdep.setText("");
//     txtid.setText("");
//   }

//   private void updateButtonState() {
//     // Check if txtid is empty
//     if (txtid.getText().trim().isEmpty()) {
//       btconfirmamend.setEnabled(false); // Disable the button
//     } else {
//       btconfirmamend.setEnabled(true); // Enable the button
//     }
//   }

//   private void addButtonToPanel(JPanel panel, JButton button) {
//     button.setPreferredSize(new Dimension(68, 25));
//     button.setMinimumSize(new Dimension(68, 25));
//     button.setBackground(Color.LIGHT_GRAY);
//     button.setFocusPainted(false);
//     button.setMargin(new Insets(0, 5, 0, 5));
//     button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//     if (button == btnremove) {
//       button.setBackground(Color.RED); // Exit button color set to red
//     }
//     panel.add(button);
//   }

//   private void addlabeltopanel(JPanel panell, JLabel Label, GridBagConstraints err, int gridy,
//       int gridx) {
//     err.gridy = gridy;
//     err.gridx = gridx;
//     Label.setPreferredSize(new Dimension(120, 30));
//     Label.setMinimumSize(new Dimension(120, 30));
//     Label.setBackground(Color.WHITE);
//     Label.setOpaque(true);
//     panell.add(Label, err);
//   }

//   private void addtxtfieldtopanel(JPanel panelll, JTextField field, GridBagConstraints nghz,
//       int gridy, int gridx) {
//     nghz.gridx = gridx;
//     nghz.gridy = gridy;
//     field.setPreferredSize(new Dimension(290, 30));
//     field.setMinimumSize(new Dimension(290, 30));
//     field.setBackground(Color.white);
//     field.setOpaque(true);
//     field.setForeground(Color.BLACK);
//     panelll.add(field, nghz);
//   }

//   public static int getStaffCount() {

//     Connection conn = null;
//     PreparedStatement pstmt = null;
//     ResultSet rs = null;
//     int count = 0;

//     try {
//       // Establish the connection
//       conn = DatabaseConnection.getConnection();
//       System.out.println("Info_input StaffCount: Connected to the database");

//       // Prepare the SQL COUNT statement
//       String sql = "SELECT COUNT(*) FROM Employees";

//       // Create the PreparedStatement
//       pstmt = conn.prepareStatement(sql);

//       // Execute the query
//       rs = pstmt.executeQuery();

//       // Retrieve the count from the ResultSet
//       if (rs.next()) {
//         count = rs.getInt(1);
//       }

//     } catch (SQLException e) {
//       e.printStackTrace();
//     } finally {
//       // Close the resources
//       try {
//         if (rs != null) {
//           rs.close();
//         }
//         if (pstmt != null) {
//           pstmt.close();
//         }
//         if (conn != null) {
//           conn.close();
//         }
//       } catch (SQLException e) {
//         e.printStackTrace();
//       }
//     }
//     return count;
//   }

//   private void handleAmendment(int id) {
//     // Implement the logic to handle the amendment using the provided ID
//     System.out.println("Amendment requested for ID: " + id);
//     // You can call updateStaffInfo or any other logic here
//     getStaffInfo(id, txtid, txtname1, txtname2, txtphone, txtemail, txtnid, txtposition, txtdep);
//     updateButtonState();
//   }

//   private void handleDeletion(int id) {
//     // Implement the logic to handle the deletion using the provided ID
//     System.out.println("Delete requested for ID: " + id);
//     // You can call updateStaffInfo or any other logic here
//     deleteStaffInfo(id);
//     // updateButtonState();
//   }

//   public String getStaffNameById(int id) {

//     Connection conn = null;
//     PreparedStatement pstmt = null;
//     ResultSet rs = null;
//     String empname = null;

//     try {
//       // Establish the connection
//       conn = DatabaseConnection.getConnection();
//       System.out.println("Info_input_insert: Connected to the database");

//       // Prepare the SQL INSERT statement
//       String sql = "SELECT FIRST_NAME, LAST_NAME FROM Employees WHERE EMPLOYEE_ID = ?";
//       pstmt = conn.prepareStatement(sql);
//       pstmt.setInt(1, id); // Set the ID parameter
//       rs = pstmt.executeQuery();
//       // Check if a result is returned
//       if (rs.next()) {
//         // int fetchid = rs.getInt("id");
//         String firstName = rs.getString("first_name");
//         String lastName = rs.getString("last_name");

//         // Execute the statement
//         empname = firstName + " " + lastName;
//       }

//       // System.out.println("All staff information inserted successfully!");

//     } catch (SQLException e) {
//       e.printStackTrace();
//     } finally {
//       // Close the resources
//       try {
//         if (pstmt != null) {
//           pstmt.close();
//         }
//         if (conn != null) {
//           conn.close();
//         }
//       } catch (SQLException e) {
//         e.printStackTrace();
//       }
//     }
//     return empname; // Placeholder
//   }


//   private void showConfirmationDialog(int id) {
//     int staffId = id;
//     String staffName = getStaffNameById(staffId);
//     JFrame frame = new JFrame("Attendance");
//     JPanel panel1 = new JPanel(new GridBagLayout());
//     JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
//     JLabel label = new JLabel("<html>Are you sure that you want to delete?<br>ID: " + id
//         + "<br> Name: " + staffName + "</html>");
//     // JLabel label2 = new JLabel(labelText2);
//     // JTextField textField = new JTextField();
//     JButton yesButton = new JButton("Yes");
//     JButton noButton = new JButton("No");
//     Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

//     label.setFont(customFont);
//     // label2.setFont(customFont);
//     // textField.setPreferredSize(new Dimension(120, 20));
//     // textField.setMinimumSize(new Dimension(120, 20));
//     yesButton.setBackground(Color.RED);
//     yesButton.setFocusPainted(false);
//     noButton.setBackground(Color.LIGHT_GRAY);
//     noButton.setFocusPainted(false);

//     yesButton.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         handleDeletion(id);
//         frame.dispose();
//       }
//     });

//     noButton.addActionListener(new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         frame.dispose();
//       }
//     });

//     GridBagConstraints gbc = new GridBagConstraints();
//     gbc.insets = new Insets(5, 5, 5, 5);
//     gbc.gridx = 0;
//     gbc.gridy = 0;
//     panel1.add(label, gbc);

//     gbc.gridx = 1;
//     // panel1.add(textField, gbc);

//     panel1.setPreferredSize(new Dimension(250, 80));
//     panel1.setBackground(Color.WHITE);

//     panel2.setPreferredSize(new Dimension(250, 40));
//     panel2.setBackground(new Color(15, 41, 102));
//     panel2.add(yesButton);
//     panel2.add(Box.createRigidArea(new Dimension(20, 0))); // 20-pixel horizontal gap
//     panel2.add(noButton);

//     frame.setResizable(false);

//     yesButton.addActionListener(this);
//     noButton.addActionListener(this);

//     frame.setLocationRelativeTo(null);
//     Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attandance V1\\img\\naga.png");
//     frame.setIconImage(icon);
//     frame.add(panel1, BorderLayout.CENTER);
//     frame.add(panel2, BorderLayout.SOUTH);
//     frame.pack();
//     frame.setLocationRelativeTo(null); // Center the frame after packing
//     frame.setVisible(true);
//   }



//   private void createDialog(String title, String labelText, ActionListener actionListener) {
//     JFrame frame = new JFrame(title);
//     JPanel panel1 = new JPanel(new GridBagLayout());
//     JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
//     JLabel label = new JLabel(labelText);
//     // JLabel label2 = new JLabel(labelText2);
//     JTextField textField = new JTextField();
//     JButton okButton = new JButton("OK");
//     Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

//     label.setFont(customFont);
//     // label2.setFont(customFont);
//     textField.setPreferredSize(new Dimension(120, 20));
//     textField.setMinimumSize(new Dimension(120, 20));
//     okButton.setBackground(Color.LIGHT_GRAY);
//     okButton.setFocusPainted(false);

//     GridBagConstraints gbc = new GridBagConstraints();
//     gbc.insets = new Insets(5, 5, 5, 5);
//     gbc.gridx = 0;
//     gbc.gridy = 0;
//     panel1.add(label, gbc);

//     gbc.gridx = 1;
//     panel1.add(textField, gbc);

//     panel1.setPreferredSize(new Dimension(250, 80));
//     panel1.setBackground(Color.WHITE);

//     panel2.setPreferredSize(new Dimension(250, 40));
//     panel2.setBackground(new Color(15, 41, 102));
//     panel2.add(okButton);

//     frame.setResizable(false);

//     okButton.addActionListener(actionListener);

//     frame.setLocationRelativeTo(null);
//     Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attandance V1\\img\\naga.png");
//     frame.setIconImage(icon);
//     frame.add(panel1, BorderLayout.CENTER);
//     frame.add(panel2, BorderLayout.SOUTH);
//     frame.pack();
//     frame.setLocationRelativeTo(null); // Center the frame after packing
//     frame.setVisible(true);
//   }

//   public void showAmendDialog() {
//     createDialog("Attendance System V1", "Amend by ID: ", new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         JTextField textField = (JTextField) ((JPanel) ((JButton) e.getSource()).getParent()
//             .getParent().getComponent(0)).getComponent(1);
//         String idForAmend = textField.getText();
//         try {
//           int idAmend = Integer.parseInt(idForAmend.trim());
//           SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close the dialog
//           handleAmendment(idAmend); // Call the amendment handling method
//         } catch (NumberFormatException ex) {
//           JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Invalid Input",
//               JOptionPane.ERROR_MESSAGE);
//         }
//       }
//     });
//   }

//   public void showDeleteDialog() {
//     createDialog("Attendance System V1", "Delete by ID: ", new ActionListener() {
//       @Override
//       public void actionPerformed(ActionEvent e) {
//         JTextField textField = (JTextField) ((JPanel) ((JButton) e.getSource()).getParent()
//             .getParent().getComponent(0)).getComponent(1);
//         String idForDelete = textField.getText();
//         try {
//           int idDelete = Integer.parseInt(idForDelete.trim());
//           SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close the dialog
//           showConfirmationDialog(idDelete); // Show confirmation dialog
//           // handleDeletion(idDelete); // Call the deletion handling method
//         } catch (NumberFormatException ex) {
//           JOptionPane.showMessageDialog(null, "Please enter a valid ID.", "Invalid Input",
//               JOptionPane.ERROR_MESSAGE);
//         }
//       }
//     });
//   }

//   public static void insertStaffInfo(ArrayList<emp_info> staffList) {

//     Connection conn = null;
//     PreparedStatement pstmt = null;

//     try {
//       // Establish the connection
//       conn = DatabaseConnection.getConnection();
//       System.out.println("Info_input_insert: Connected to the database");

//       // Prepare the SQL INSERT statement
//       String sql =
//           "INSERT INTO Employees (FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT) VALUES (?, ?, ?, ?, ?, ?, ?)";

//       // Create the PreparedStatement
//       pstmt = conn.prepareStatement(sql);

//       // Iterate over the ArrayList and insert data
//       for (emp_info staff : staffList) {
//         pstmt.setString(1, staff.getFirstName());
//         pstmt.setString(2, staff.getLastName());
//         pstmt.setInt(3, staff.getPhone());
//         pstmt.setString(4, staff.getEmail());
//         pstmt.setInt(5, staff.getNid());
//         pstmt.setString(6, staff.getPosition());
//         pstmt.setString(7, staff.getDepartment());

//         // Execute the statement
//         pstmt.executeUpdate();
//       }
//       System.out.println("All staff information inserted successfully!");

//     } catch (SQLException e) {
//       e.printStackTrace();
//     } finally {
//       // Close the resources
//       try {
//         if (pstmt != null) {
//           pstmt.close();
//         }
//         if (conn != null) {
//           conn.close();
//         }
//       } catch (SQLException e) {
//         e.printStackTrace();
//       }
//     }
//   }

//   public static void updateStaffInfo(emp_info staff) {

//     Connection conn = null;
//     PreparedStatement pstmt = null;
//     try {
//       // Establish the connection
//       conn = DatabaseConnection.getConnection();
//       System.out.println("Info_input_update: Connected to the database");

//       // Prepare the SQL UPDATE statement
//       String sql =
//           "UPDATE Employees SET FIRST_NAME = ?, LAST_NAME = ?, PHONE = ?, EMAIL = ?, NID = ?, POSITION = ?, DEPARTMENT = ? WHERE EMPLOYEE_ID = ?";

//       // Create the PreparedStatement
//       pstmt = conn.prepareStatement(sql);

//       // Set the parameters for the update
//       pstmt.setString(1, staff.getFirstName());
//       pstmt.setString(2, staff.getLastName());
//       pstmt.setInt(3, staff.getPhone());
//       pstmt.setString(4, staff.getEmail());
//       pstmt.setInt(5, staff.getNid());
//       pstmt.setString(6, staff.getPosition());
//       pstmt.setString(7, staff.getDepartment());
//       pstmt.setInt(8, staff.getId());

//       // Execute the update
//       pstmt.executeUpdate();
//       System.out.println("Staff information updated successfully!");

//     } catch (SQLException e) {
//       e.printStackTrace();
//     } finally {
//       // Close the resources
//       try {
//         if (pstmt != null) {
//           pstmt.close();
//         }
//         if (conn != null) {
//           conn.close();
//         }
//       } catch (SQLException e) {
//         e.printStackTrace();
//       }
//     }
//   }

//   public static void deleteStaffInfo(int id) {

//     Connection conn = null;
//     PreparedStatement pstmt = null;

//     try {
//       // Establish the connection
//       conn = DatabaseConnection.getConnection();
//       System.out.println("Info_input_delete: Connected to the database");

//       // Prepare the SQL DELETE statement
//       String sql = "DELETE FROM Employees WHERE EMPLOYEE_ID = ?";

//       // Create the PreparedStatement
//       pstmt = conn.prepareStatement(sql);

//       // Set the parameter for the delete
//       pstmt.setInt(1, id);

//       // Execute the delete
//       pstmt.executeUpdate();

//       System.out.println("Staff with ID " + id + " deleted successfully!");

//     } catch (SQLException e) {
//       e.printStackTrace();
//     } finally {
//       // Close the resources
//       try {
//         if (pstmt != null) {
//           pstmt.close();
//         }
//         if (conn != null) {
//           conn.close();
//         }
//       } catch (SQLException e) {
//         e.printStackTrace();
//       }
//     }
//   }


//   public static void getStaffInfo(int id, JTextField txtid, JTextField txtname1,
//       JTextField txtname2, JTextField txtphone, JTextField txtemail, JTextField txtnid,
//       JTextField txtposition, JTextField txtdep) {

//     Connection conn = null;
//     PreparedStatement pstmt = null;
//     ResultSet rs = null;
//     try {
//       // Establish the connection
//       conn = DatabaseConnection.getConnection();
//       System.out.println("Info_input_getStaffInfo: Connected to the database");

//       // Prepare the SQL DELETE statement
//       String sql =
//           "SELECT ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, NID, POSITION, DEPARTMENT FROM Employees WHERE EMPLOYEE_ID = ?";

//       // Create the PreparedStatement
//       pstmt = conn.prepareStatement(sql);

//       // Set the parameter for the delete
//       pstmt.setInt(1, id);

//       // Execute the delete
//       rs = pstmt.executeQuery();
//       // Check if a result is returned
//       if (rs.next()) {
//         int fetchid = rs.getInt("id");
//         String firstName = rs.getString("first_name");
//         String lastName = rs.getString("last_name");
//         String phone = rs.getString("phone");
//         String email = rs.getString("email");
//         String nid = rs.getString("nid");
//         String position = rs.getString("position");
//         String department = rs.getString("department");


//         // Set the values to the labels
//         txtid.setText(String.valueOf(fetchid));
//         txtname1.setText(firstName);
//         txtname2.setText(lastName);
//         txtphone.setText(phone);
//         txtemail.setText(email);
//         txtnid.setText(nid);
//         txtposition.setText(position);
//         txtdep.setText(department);

//         System.out.println("Staff information fetched for Amend successfully!");
//       } else {
//         System.out.println("No staff found with ID: " + id);
//       }

//     } catch (SQLException e) {
//       e.printStackTrace();
//     } finally {
//       // Close the resources
//       try {
//         if (pstmt != null) {
//           pstmt.close();
//         }
//         if (conn != null) {
//           conn.close();
//         }
//       } catch (SQLException e) {
//         e.printStackTrace();
//       }
//     }
//   }
// }
