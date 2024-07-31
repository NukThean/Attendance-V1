package src.dashboard;

import javax.swing.*;

import src.loginpage.AdminMain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class sidebar extends JPanel implements ActionListener {
  private JButton btnDb = new JButton("Dashboard");
  private JButton btnStaff = new JButton("Employee");
  private JButton btnAtd = new JButton("Attendance");
  private JButton btnReport = new JButton("Report");
  private JButton btnHelp = new JButton("Help");
  private JButton btnSetting = new JButton("Setting");
  private JButton btnExit = new JButton("Exit");
  private JLabel icon = new JLabel(new ImageIcon("D:\\Attandance V1\\img\\Rupp_logo.png"));
  private JButton selectedButton; // Track the selected button
  private AdminMain mainApp; // Reference to MainApp

  public sidebar(AdminMain mainApp) {
    this.mainApp = mainApp;

    setLayout(new BorderLayout());

    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setPreferredSize(new Dimension(130, 130));
    topPanel.setBackground(new Color(11, 57, 163));
    topPanel.add(icon, BorderLayout.CENTER);

    JPanel botPanel = new JPanel(new BorderLayout());
    botPanel.setPreferredSize(new Dimension(130, 250));
    botPanel.setBackground(new Color(15, 41, 102));

    JPanel buttonPanel = new JPanel(new GridBagLayout()); // center panel for button
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 15, 5, 15); // Add some spacing between buttons
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.weightx = 1; // Allow horizontal resizing
    gbc.weighty = 1; // Allow resize vertically

    Font customFont = new Font("Times New Roman", Font.PLAIN, 17);

    // Apply custom font to buttons
    btnDb.setFont(customFont);
    btnStaff.setFont(customFont);
    btnAtd.setFont(customFont);
    btnReport.setFont(customFont);
    btnHelp.setFont(customFont);
    btnSetting.setFont(customFont);
    btnExit.setFont(customFont);

    addButtonToPanel(buttonPanel, btnDb, gbc, 0);
    gbc.insets = new Insets(5, 15, 5, 15); // Add some spacing between buttons
    addButtonToPanel(buttonPanel, btnStaff, gbc, 1);
    addButtonToPanel(buttonPanel, btnAtd, gbc, 2);
    addButtonToPanel(buttonPanel, btnReport, gbc, 3);
    addButtonToPanel(buttonPanel, btnHelp, gbc, 4);
    addButtonToPanel(buttonPanel, btnSetting, gbc, 5);
    addButtonToPanel(buttonPanel, btnExit, gbc, 6);

    // Set the background color of the buttonPanel to cyan
    buttonPanel.setBackground(new Color(15, 41, 102));

    add(topPanel, BorderLayout.NORTH);
    add(botPanel, BorderLayout.SOUTH);
    add(buttonPanel, BorderLayout.CENTER);
    setPreferredSize(new Dimension(130, 768)); // Set the size of the left panel
    setBackground(Color.CYAN);

    btnExit.addActionListener(e -> System.exit(0));

    // Select the Dashboard button by default
    btnDb.setBackground(new Color(11, 57, 163));
    btnDb.setForeground(Color.WHITE);
    selectedButton = btnDb;

    // Add action listeners to buttons
    btnDb.addActionListener(this);
    btnStaff.addActionListener(this);
    btnAtd.addActionListener(this);
    btnReport.addActionListener(this);
    btnHelp.addActionListener(this);
    btnSetting.addActionListener(this);
    btnExit.addActionListener(this);
  }

  private void addButtonToPanel(JPanel panel, JButton button, GridBagConstraints gbc, int gridy) {
    gbc.gridy = gridy;
    button.setPreferredSize(new Dimension(100, 30));
    button.setMinimumSize(new Dimension(100, 30));
    button.setBackground(Color.LIGHT_GRAY);
    button.setFocusPainted(false);
    button.setMargin(new Insets(0, 5, 0, 5));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    if (button == btnExit) {
      button.setBackground(Color.RED); // Exit button color set to red
    }
    panel.add(button, gbc);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton sourceButton = (JButton) e.getSource();
    if (sourceButton != btnExit) {
      if (selectedButton != null) {
        selectedButton.setBackground(Color.LIGHT_GRAY); // Reset color of previously selected button
        selectedButton.setForeground(Color.BLACK);
      }
      sourceButton.setBackground(new Color(11, 57, 163)); // Set color for selected button
      sourceButton.setForeground(Color.WHITE);
      selectedButton = sourceButton;

      // Switch panel based on button pressed
      String panelName = sourceButton.getText();
      mainApp.switchPanel(panelName); // call switchPanel in AdminMain
    }
  }
}
