package Admin.emp_data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileView;
import loginpage.User;
import test.CustomTextField;
import utils.ConvertTimeFormat;
import utils.DatabaseConnection;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

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
  JLabel lblSshift = new JLabel("Start shift: ");
  JLabel lblEshift = new JLabel("End shift: ");
  JLabel lblUploadImg = new JLabel("Upload Img:");
  JLabel lblAnnualLeave = new JLabel("Annual Leave: ");
  JLabel lblSickLeave = new JLabel("Sick Leave: ");
  JLabel lblSpecialLeave = new JLabel("Special Leave: ");
  JLabel lblBlockLeave = new JLabel("Block Leave: ");


  JButton btnmore = new JButton("Add more");
  JButton btnfinish = new JButton("Finish");
  static JButton btnselectFile = new JButton("Select Image");

  JTextField txtname1 = new JTextField();
  JTextField txtname2 = new JTextField();
  JTextField txtsex = new JTextField();
  JTextField txtphone = new JTextField();
  JTextField txtemail = new JTextField();
  JTextField txtnid = new JTextField();
  JTextField txtposition = new JTextField();
  JComboBox<String> cmbdep = new JComboBox<>();
  JComboBox<String> cmbTime1 = new JComboBox<>();
  JComboBox<String> cmbTime2 = new JComboBox<>();
  JTextField txtid = new JTextField();
  CustomTextField txtSshift1 = new CustomTextField(3, 0, 0, 1, 1, 1, 0, Color.BLACK, "");
  CustomTextField txtSshift2 = new CustomTextField(3, 0, 0, 1, 0, 1, 0, Color.BLACK, "");
  CustomTextField txtblank1 = new CustomTextField(3, 0, 0, 1, 0, 1, 0, Color.BLACK, ":");
  CustomTextField txtEshift1 = new CustomTextField(3, 0, 0, 1, 1, 1, 0, Color.BLACK, "");
  CustomTextField txtEshift2 = new CustomTextField(3, 0, 0, 1, 0, 1, 1, Color.BLACK, "");
  CustomTextField txtblank2 = new CustomTextField(3, 0, 0, 1, 0, 1, 0, Color.BLACK, ":");
  Dimension customSize = new Dimension(0, 0);
  static JTextField txtFileLocation = new JTextField();

  JTextField txtAnnualLeave = new JTextField();
  JTextField txtSickLeave = new JTextField();
  JTextField txtSpecialLeave = new JTextField();
  JTextField txtBlockLeave = new JTextField();

  static String startShift;
  static String endShift;

  static JCheckBox chkMonday = new JCheckBox("Mon");
  static JCheckBox chkTuesday = new JCheckBox("Tues");
  static JCheckBox chkWednesday = new JCheckBox("Wed");
  static JCheckBox chkThursday = new JCheckBox("Thurs");
  static JCheckBox chkFriday = new JCheckBox("Fri");
  static JCheckBox chkSaturday = new JCheckBox("Sat");
  static JCheckBox chkSunday = new JCheckBox("Sun");



  static File selectedFile;

  public EmpInput() {

    setLayout(new BorderLayout());
    JPanel main = new JPanel(new BorderLayout());
    JPanel ccenter = new JPanel(new GridBagLayout());
    JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    txtblank1.setEnabled(false);
    txtblank2.setEnabled(false);


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
    lblSshift.setFont(customFont);
    lblEshift.setFont(customFont);
    lblUploadImg.setFont(customFont);

    lblAnnualLeave.setFont(customFont);
    lblSickLeave.setFont(customFont);
    lblSpecialLeave.setFont(customFont);
    lblBlockLeave.setFont(customFont);

    txtname1.setFont(customFont);
    txtname2.setFont(customFont);
    txtsex.setFont(customFont);
    txtphone.setFont(customFont);
    txtemail.setFont(customFont);
    txtnid.setFont(customFont);
    txtposition.setFont(customFont);
    cmbdep.setFont(customFont);
    cmbTime1.setFont(customFont);
    cmbTime2.setFont(customFont);
    txtid.setFont(customFont);
    txtEshift1.setFont(customFont);
    txtEshift2.setFont(customFont);
    txtSshift1.setFont(customFont);
    txtSshift2.setFont(customFont);
    txtblank1.setFont(customFont);
    txtblank2.setFont(customFont);
    txtFileLocation.setFont(customFont);
    txtid.setEditable(false);
    txtid.setBackground(Color.GRAY);
    txtAnnualLeave.setFont(customFont);
    txtSickLeave.setFont(customFont);
    txtSpecialLeave.setFont(customFont);
    txtBlockLeave.setFont(customFont);

    btnfinish.setFont(customFont);
    btnmore.setFont(customFont);
    txtFileLocation.setEnabled(false);
    customFont = new Font("Times New Roman", Font.PLAIN, 14);
    btnselectFile.setFont(customFont);
    customFont = new Font("Times New Roman", Font.PLAIN, 16);

    btnmore.addActionListener(this);
    btnfinish.addActionListener(this);

    err.insets = new Insets(5, 15, 0, 310);
    addlabeltopanel(ccenter, lblname1, err, 0, 0, null);
    err.insets = new Insets(10, 15, 5, 310);
    addlabeltopanel(ccenter, lblname2, err, 1, 0, null);
    addlabeltopanel(ccenter, lblsex, err, 2, 0, null);
    addlabeltopanel(ccenter, lblphone, err, 3, 0, null);
    addlabeltopanel(ccenter, lblemail, err, 4, 0, null);
    addlabeltopanel(ccenter, lblnid, err, 5, 0, null);

    Dimension customLblSize = new Dimension(95, 30);

    addlabeltopanel(ccenter, lblAnnualLeave, err, 6, 0, customLblSize);
    addlabeltopanel(ccenter, lblSickLeave, err, 7, 0, customLblSize);
    err.insets = new Insets(10, 115, 5, 10);
    addlabeltopanel(ccenter, lblBlockLeave, err, 6, 0, customLblSize);
    addlabeltopanel(ccenter, lblSpecialLeave, err, 7, 0, customLblSize);



    err.insets = new Insets(5, 0, 0, 310);

    addlabeltopanel(ccenter, lbldep, err, 0, 1, null);
    addlabeltopanel(ccenter, lblposition, err, 1, 1, null);
    addlabeltopanel(ccenter, lblSshift, err, 2, 1, null);
    addlabeltopanel(ccenter, lblUploadImg, err, 4, 1, null);
    addlabeltopanel(ccenter, lblid, err, 5, 1, null); // Add lblid to the panel
    err.insets = new Insets(0, 300, 0, 0);

    err.insets = new Insets(5, 70, 0, 0);
    addlabeltopanel(ccenter, lblEshift, err, 2, 1, null); // Add lblid to the panel

    nghz.insets = new Insets(10, 75, 5, 0);
    addtxtfieldtopanel(ccenter, txtname1, nghz, 0, 0, null);
    addtxtfieldtopanel(ccenter, txtname2, nghz, 1, 0, null);
    addtxtfieldtopanel(ccenter, txtsex, nghz, 2, 0, null);
    addtxtfieldtopanel(ccenter, txtphone, nghz, 3, 0, null);
    addtxtfieldtopanel(ccenter, txtemail, nghz, 4, 0, null);
    addtxtfieldtopanel(ccenter, txtnid, nghz, 5, 0, null);

    customLblSize = new Dimension(60, 30);

    nghz.insets = new Insets(10, 5, 5, 140);
    addtxtfieldtopanel(ccenter, txtAnnualLeave, nghz, 6, 0, customLblSize);
    addtxtfieldtopanel(ccenter, txtSickLeave, nghz, 7, 0, customLblSize);
    nghz.insets = new Insets(10, 280, 5, 5);
    addtxtfieldtopanel(ccenter, txtSpecialLeave, nghz, 6, 0, customLblSize);
    addtxtfieldtopanel(ccenter, txtBlockLeave, nghz, 7, 0, customLblSize);


    nghz.insets = new Insets(10, 75, 5, 0);

    addcomboboxtopanel(ccenter, cmbdep, nghz, 0, 1, null);
    addtxtfieldtopanel(ccenter, txtposition, nghz, 1, 1, null);
    addtxtfieldtopanel(ccenter, txtSshift1, nghz, 2, 1, null);
    addtxtfieldtopanel(ccenter, txtEshift1, nghz, 3, 1, null); // Add txtid to the panel
    addtxtfieldtopanel(ccenter, txtid, nghz, 5, 1, null); // Add txtid to the panel

    nghz.insets = new Insets(10, 0, 5, 15);
    customSize = new Dimension(200, 30);
    addtxtfieldtopanel(ccenter, txtFileLocation, nghz, 4, 1, customSize);


    customSize = new Dimension(20, 30);

    nghz.insets = new Insets(10, 0, 5, 195);
    addtxtfieldtopanel(ccenter, txtSshift1, nghz, 2, 1, customSize);
    nghz.insets = new Insets(10, 0, 5, 145);
    addtxtfieldtopanel(ccenter, txtSshift2, nghz, 2, 1, customSize);

    nghz.insets = new Insets(10, 185, 5, 0);
    addtxtfieldtopanel(ccenter, txtEshift1, nghz, 2, 1, customSize); // Add txtid to the panel
    nghz.insets = new Insets(10, 235, 5, 0);
    addtxtfieldtopanel(ccenter, txtEshift2, nghz, 2, 1, customSize); // Add txtid to the panel

    customSize = new Dimension(5, 30);
    nghz.insets = new Insets(10, 0, 5, 170);
    addtxtfieldtopanel(ccenter, txtblank1, nghz, 2, 1, customSize);
    nghz.insets = new Insets(10, 210, 5, 0);
    addtxtfieldtopanel(ccenter, txtblank2, nghz, 2, 1, customSize);

    customSize = new Dimension(50, 30);
    nghz.insets = new Insets(10, 0, 5, 75);
    addcomboboxtopanel(ccenter, cmbTime1, nghz, 2, 1, customSize);
    nghz.insets = new Insets(10, 305, 5, 0);
    addcomboboxtopanel(ccenter, cmbTime2, nghz, 2, 1, customSize);


    // Assuming you are adding these in the constructor

    GridBagConstraints chkConstraints = new GridBagConstraints();
    chkConstraints.insets = new Insets(10, 0, 5, 330);

    addCheckboxToPanel(ccenter, chkMonday, chkConstraints, 3, 1);
    chkConstraints.insets = new Insets(10, 0, 5, 220);
    addCheckboxToPanel(ccenter, chkTuesday, chkConstraints, 3, 1);
    chkConstraints.insets = new Insets(10, 0, 5, 110);
    addCheckboxToPanel(ccenter, chkWednesday, chkConstraints, 3, 1);
    chkConstraints.insets = new Insets(10, 0, 5, 0);
    addCheckboxToPanel(ccenter, chkThursday, chkConstraints, 3, 1);
    chkConstraints.insets = new Insets(10, 110, 5, 0);
    addCheckboxToPanel(ccenter, chkFriday, chkConstraints, 3, 1);
    chkConstraints.insets = new Insets(10, 210, 5, 0);
    addCheckboxToPanel(ccenter, chkSaturday, chkConstraints, 3, 1);
    chkConstraints.insets = new Insets(10, 310, 5, 0);
    addCheckboxToPanel(ccenter, chkSunday, chkConstraints, 3, 1);



    addButtonToPanel(south, btnmore, nghz, 0, 0);
    addButtonToPanel(south, btnfinish, nghz, 0, 0);


    setTitle("Employee Input");
    setSize(864, 688); // Adjust the size as needed
    setLocationRelativeTo(null); // Center the frame on the screen
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    btnselectFile.setPreferredSize(new Dimension(86, 29));
    btnselectFile.setMinimumSize(new Dimension(86, 29));
    btnselectFile.setBackground(Color.LIGHT_GRAY);
    btnselectFile.setFocusPainted(false);
    btnselectFile.setMargin(new Insets(0, 0, 0, 0));
    btnselectFile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    nghz.insets = new Insets(5, 270, 0, 0);
    nghz.gridx = 1;
    nghz.gridy = 4;
    south.setBackground(Color.white);
    ccenter.setBackground(Color.white);
    ccenter.add(btnselectFile, nghz);

    main.add(south, BorderLayout.SOUTH);
    main.add(ccenter, BorderLayout.CENTER);
    add(main, BorderLayout.CENTER);

    loadDepartments();
    loadTimeformat();

    btnselectFile.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Open the file chooser when the button is clicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image");

        // Set custom icons for files and directories
        fileChooser.setFileView(new FileView() {
          @Override
          public Icon getIcon(File f) {
            if (f.isDirectory()) {
              return new ImageIcon("path_to_custom_folder_icon.png"); // Update the path to your
                                                                      // custom folder icon
            } else {
              return new ImageIcon("path_to_custom_file_icon.png"); // Update the path to your
                                                                    // custom file icon
            }
          }
        });

        // Set the file filter to accept only image files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
          @Override
          public boolean accept(File f) {
            // Accept directories and image files only (e.g., .jpg, .jpeg, .png, .gif)
            if (f.isDirectory()) {
              return true;
            } else {
              String name = f.getName().toLowerCase();
              return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")
                  || name.endsWith(".gif");
            }
          }

          @Override
          public String getDescription() {
            return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
          }
        });

        // Open the file chooser dialog
        int result = fileChooser.showOpenDialog(null);

        // If the user selected a file, store it for future use
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          txtFileLocation.setText(selectedFile.getAbsolutePath());
          System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
      }
    });

    // Add drag-and-drop functionality
    DropTarget dropTarget = new DropTarget(txtFileLocation, new DropTargetListener() {
      @Override
      public void dragEnter(DropTargetDragEvent dtde) {
        // Optional: Add visual feedback for drag enter
      }

      @Override
      public void dragOver(DropTargetDragEvent dtde) {
        // Optional: Add visual feedback for drag over
      }

      @Override
      public void dropActionChanged(DropTargetDragEvent dtde) {
        // Optional: Handle any changes in the drop action
      }



      @Override
      public void drop(DropTargetDropEvent e) {
        try {
          // int id = 1; // Replace with the actual ID logic if needed
          e.acceptDrop(DnDConstants.ACTION_COPY);
          Transferable transferable = e.getTransferable();
          if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            java.util.List<?> files =
                (java.util.List<?>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
            if (!files.isEmpty()) {
              selectedFile = (File) files.get(0);
              // uploadImage(file.getAbsolutePath(), id);

              txtFileLocation.setText(selectedFile.getAbsolutePath());
              System.out.println("Dropped file: " + selectedFile.getAbsolutePath());
            }
          }
          e.dropComplete(true);
        } catch (Exception ex) {
          ex.printStackTrace();
          e.rejectDrop();
        }
      }

      @Override
      public void dragExit(DropTargetEvent dte) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dragExit'");
      }
    });



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

  private void loadTimeformat() {
    // Sample departments, resplace with actual department names from your database
    // or configuration
    String[] timeFormat = User.gettimeFormat();
    for (String tim : timeFormat) {
      cmbTime1.addItem(tim);
      cmbTime2.addItem(tim);
    }
    cmbTime2.setSelectedIndex(1);
  }

  private void addlabeltopanel(JPanel panell, JLabel Label, GridBagConstraints err, int gridy,
      int gridx, Dimension size) {
    err.gridy = gridy;
    err.gridx = gridx;
    if (size != null) {
      Label.setPreferredSize(size);
      Label.setMinimumSize(size);
      Label.setMaximumSize(size);
    } else {
      Label.setPreferredSize(new Dimension(82, 30));
      Label.setMinimumSize(new Dimension(82, 30));
    }
    Label.setBackground(Color.white);
    Label.setOpaque(true);
    panell.add(Label, err);
  }

  private void addtxtfieldtopanel(JPanel panelll, JTextField field, GridBagConstraints nghz,
      int gridy, int gridx, Dimension size) {
    nghz.gridx = gridx;
    nghz.gridy = gridy;
    // Set the preferred and minimum size of the text field
    if (size != null) {
      field.setPreferredSize(size);
      field.setMinimumSize(size);
      field.setMaximumSize(size);
    } else {
      // Set default size if no specific size is provided
      field.setPreferredSize(new Dimension(290, 30));
      field.setMinimumSize(new Dimension(290, 30));
    }
    field.setBackground(Color.white);
    field.setOpaque(true);
    field.setForeground(Color.BLACK);
    panelll.add(field, nghz);
  }

  private void addCheckboxToPanel(JPanel panel, JCheckBox checkbox, GridBagConstraints constraints,
      int gridy, int gridx) {
    constraints.gridy = gridy;
    constraints.gridx = gridx;
    panel.add(checkbox, constraints);
  }


  private void addButtonToPanel(JPanel panel, JButton button, GridBagConstraints nghz, int gridy,
      int gridx) {
    nghz.gridx = gridx;
    nghz.gridy = gridy;

    button.setPreferredSize(new Dimension(85, 25));
    button.setMinimumSize(new Dimension(85, 25));
    button.setBackground(Color.LIGHT_GRAY);
    button.setFocusPainted(false);
    button.setMargin(new Insets(0, 5, 0, 5));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    panel.add(button);
  }

  private void addcomboboxtopanel(JPanel panelll, JComboBox<String> comboBox,
      GridBagConstraints nghz, int gridy, int gridx, Dimension size) {
    nghz.gridx = gridx;
    nghz.gridy = gridy;
    if (size != null) {
      comboBox.setPreferredSize(size);
      comboBox.setMinimumSize(size);
      comboBox.setMaximumSize(size);
    } else {
      // Set default size if no specific size is provided
      comboBox.setPreferredSize(new Dimension(290, 30));
      comboBox.setMinimumSize(new Dimension(290, 30));
    }
    comboBox.setBackground(Color.white);
    // comboBox.setOpaque(true);
    comboBox.setForeground(Color.BLACK);
    // comboBox.setBorder(new LineBorder(Color.BLACK, 1));
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
    txtEshift1.setText("");
    txtEshift2.setText("");
    txtSshift1.setText("");
    txtSshift2.setText("");
    chkMonday.setSelected(false);
    chkTuesday.setSelected(false);
    chkWednesday.setSelected(false);
    chkThursday.setSelected(false);
    chkFriday.setSelected(false);
    chkSaturday.setSelected(false);
    chkSunday.setSelected(false);
    txtFileLocation.setText("Drop or Select Pic");
  }

  public static void insertStaffInfo(ArrayList<Employee> staffList) {

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
      for (Employee staff : staffList) {
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
          String userSql = "INSERT INTO [User] (role, user_id) VALUES (?, ?)";
          String empShift = "INSERT INTO ShiftSchedule (employee_id, start_shift, end_shift)"
              + "VALUES (?, ?, ?)";
          String leaves =
              "INSERT INTO LeavesBalance (employee_id, annual_leave, sick_leave, special_leave, block_leave, remain_annual_leave, remain_sick_leave, remain_special_leave, remain_block_leave) "
                  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

          try (PreparedStatement userPstmt = conn.prepareStatement(userSql);
              PreparedStatement empPstmt = conn.prepareStatement(empShift);
              PreparedStatement leavePstmt = conn.prepareStatement(leaves)) {


            userPstmt.setString(1, "user"); // Set the role
            userPstmt.setInt(2, employeeId); // Set the employee ID

            empPstmt.setInt(1, employeeId);
            empPstmt.setString(2, startShift);
            empPstmt.setString(3, endShift);

            leavePstmt.setInt(1, employeeId);
            leavePstmt.setFloat(2, staff.getAnnaul_leave());
            leavePstmt.setFloat(3, staff.getSick_leave());
            leavePstmt.setFloat(4, staff.getSpecial_leave());
            leavePstmt.setFloat(5, staff.getBlock_leave());
            leavePstmt.setFloat(6, staff.getAnnaul_leave());
            leavePstmt.setFloat(7, staff.getSick_leave());
            leavePstmt.setFloat(8, staff.getSpecial_leave());
            leavePstmt.setFloat(9, staff.getBlock_leave());

            userPstmt.executeUpdate(); // Execute the insert for the User table
            empPstmt.executeUpdate();
            leavePstmt.executeUpdate();

            uploadImage(txtFileLocation.getText(), employeeId);
            insertWorkdays(employeeId, chkMonday.isSelected(), chkTuesday.isSelected(),
                chkWednesday.isSelected(), chkThursday.isSelected(), chkFriday.isSelected(),
                chkSaturday.isSelected(), chkSunday.isSelected());

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


  public static void uploadImage(String selectedFile2, int id) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DatabaseConnection.getConnection();
      String sql = "UPDATE Employees SET Emp_Img = ? WHERE Employee_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(2, id);

      File file = new File(selectedFile2);
      FileInputStream fis = new FileInputStream(file);
      pstmt.setBinaryStream(1, fis, (int) file.length());

      int rowAffected = pstmt.executeUpdate();
      if (rowAffected > 0) {
        System.out.println("Image uploaded successfully!");
      }

    } catch (SQLException | IOException se) {
      se.printStackTrace();
    } finally {
      try {
        if (pstmt != null)
          pstmt.close();
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
  }

  public static void insertWorkdays(int employeeId, boolean isMondaySelected,
      boolean isTuesdaySelected, boolean isWednesdaySelected, boolean isThursdaySelected,
      boolean isFridaySelected, boolean isSaturdaySelected, boolean isSundaySelected) {
    String insertWorkdaysSQL =
        "INSERT INTO DayShift (employee_id, monday, tuesday, wednesday, thursday, friday, saturday, sunday) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(insertWorkdaysSQL)) {

      pstmt.setInt(1, employeeId);
      pstmt.setBoolean(2, isMondaySelected);
      pstmt.setBoolean(3, isTuesdaySelected);
      pstmt.setBoolean(4, isWednesdaySelected);
      pstmt.setBoolean(5, isThursdaySelected);
      pstmt.setBoolean(6, isFridaySelected);
      pstmt.setBoolean(7, isSaturdaySelected);
      pstmt.setBoolean(8, isSundaySelected);

      pstmt.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
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
      String startShift = txtSshift1.getText() + txtblank1.getText() + txtSshift2.getText()
          + (String) cmbTime1.getSelectedItem();
      String endShift = txtEshift1.getText() + txtblank2.getText() + txtEshift2.getText()
          + (String) cmbTime1.getSelectedItem();

      float annaul_leave = Float.parseFloat(txtAnnualLeave.getText());
      float sick_leave = Float.parseFloat(txtSickLeave.getText());
      float special_leave = Float.parseFloat(txtSpecialLeave.getText());
      float block_leave = Float.parseFloat(txtBlockLeave.getText());



      // Create an emp_info object
      Employee staff = new Employee("", 0, firstName, lastName, sex, Integer.parseInt(phone), email,
          Integer.parseInt(nid), position, department, startShift, endShift, annaul_leave,
          sick_leave, special_leave, block_leave);

      // Insert the data into the database
      ArrayList<Employee> staffList = new ArrayList<>();
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
      startShift = ConvertTimeFormat.ConvertTo24h(txtSshift1.getText() + ":" + txtSshift2.getText()
          + ":00 " + (String) cmbTime1.getSelectedItem());
      endShift = ConvertTimeFormat.ConvertTo24h(txtEshift1.getText() + ":" + txtEshift2.getText()
          + ":00 " + (String) cmbTime2.getSelectedItem());

      float annaul_leave = Float.parseFloat(txtAnnualLeave.getText());
      float sick_leave = Float.parseFloat(txtSickLeave.getText());
      float special_leave = Float.parseFloat(txtSpecialLeave.getText());
      float block_leave = Float.parseFloat(txtBlockLeave.getText());



      // Create an emp_info object
      Employee staff = new Employee("", 0, firstName, lastName, sex, Integer.parseInt(phone), email,
          Integer.parseInt(nid), position, department, startShift, endShift, annaul_leave,
          sick_leave, special_leave, block_leave);

      // Insert the data into the database
      ArrayList<Employee> staffList = new ArrayList<>();
      staffList.add(staff);
      insertStaffInfo(staffList);

      dispose();
    }

  }
}
