package src;

import src.dashboard.sidebar;
import src.dashboard.center;
import src.loginpage.AdminLogin;
import src.reportbt.reportform;
import src.attendance.attform;
import src.emp_data.*;
import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {

  private CardLayout cardLayout;
  private JPanel mainPanel;

  public MainApp() {
    // Initialize the panels
    sidebar leftPanel = new sidebar(this);
    center centerPanel = new center();
    EmpTable employeePanel = new EmpTable(); // Create an instance of empform
    attform attendancePanel = new attform(); // Create an instance of attform
    reportform reportPanel = new reportform();

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    // Add the panels to the CardLayout panel
    mainPanel.add(centerPanel, "Dashboard");
    mainPanel.add(employeePanel, "Employee");
    mainPanel.add(attendancePanel, "Attendance");
    mainPanel.add(reportPanel, "Report");

    // Add placeholders for other forms (panels)
    mainPanel.add(new JPanel(), "Help");
    mainPanel.add(new JPanel(), "Setting");

    // Add panels to the main frame
    add(mainPanel, BorderLayout.CENTER);
    add(leftPanel, BorderLayout.WEST);

    // Set the background color of the main frame
    getContentPane().setBackground(Color.WHITE);

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
    cardLayout.show(mainPanel, "Dashboard");
  }

  // Method to switch between different panels
  public void switchPanel(String panelName) {
    cardLayout.show(mainPanel, panelName);
  }

  public static void main(String[] args) {
    MainApp mainApp = new MainApp(); // Create MainApp instance
    new AdminLogin(mainApp); // Pass the MainApp reference to LoginTest
  }
}
