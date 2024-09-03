package Admin.LoginUtils;

import javax.swing.*;
import Admin.Admindashboard.center3;
import Admin.Admindashboard.sidebar;
import Admin.LeaveRequest.LeaveReq;
import Admin.attendance.attform;
import Admin.emp_data.*;
import java.awt.*;

public class AdminMain extends JFrame {

  private CardLayout switchLayout;
  private JPanel mainPanel;

  public AdminMain() {
    // Initialize the panels
    sidebar leftPanel = new sidebar(this);
    center3 centerPanel = new center3();
    EmpTable employeePanel = new EmpTable();
    attform attendancePanel = new attform();
    LeaveReq reportPanel = new LeaveReq();

    switchLayout = new CardLayout();
    mainPanel = new JPanel(switchLayout); // card layout inside jpanel

    // Add the panels to the CardLayout panel
    mainPanel.add(centerPanel, "Dashboard");
    mainPanel.add(employeePanel, "Employee");
    mainPanel.add(attendancePanel, "Attendance");
    mainPanel.add(reportPanel, "Leave Req");

    // Add placeholders for other forms (panels)
    mainPanel.add(new JPanel(), "Help");

    // Add panels to the main frame
    add(mainPanel, BorderLayout.CENTER);
    add(leftPanel, BorderLayout.WEST);

    // Main frame settings
    Image iconImage = Toolkit.getDefaultToolkit().getImage("D:\\Attendance V1\\img\\naga.png");
    setIconImage(iconImage);
    setTitle("Attendance System V1.0");
    setSize(1024, 768);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setVisible(false); // Set visible to true

    // Show the dashboard by default
    switchLayout.show(mainPanel, "Dashboard");
  }

  // Method to switch between different panels
  public void switchPanel(String panelName) {
    switchLayout.show(mainPanel, panelName);
  }

}


