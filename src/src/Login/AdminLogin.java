package src.Login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.dashboard.img;
import src.loginpage.AdminDatabase;
import src.loginpage.AdminMain;
import src.loginpage.User;

public class AdminLogin extends JFrame implements ActionListener {
  private JLabel lblEmpId = new JLabel("Administator ID ");
  private JLabel lbltxt = new JLabel("Login");
  private JLabel lbltxt2 = new JLabel("<html><u>Forgot password?</u></html>");
  private JLabel lblPw = new JLabel("Password ");
  private JTextField txtUserId = new JTextField();
  private JPasswordField txtPw = new JPasswordField();
  private JCheckBox showpw = new JCheckBox("Show Password?");
  private JButton btnLogin = new JButton("Login");
  private AdminMain mainApp;
  int error = 0;

  public AdminLogin() {
    mainApp = new AdminMain();
    setLayout(new BorderLayout());
    img graphic = new img();

    JPanel pagecenter = new JPanel(new BorderLayout());
    pagecenter.setPreferredSize(new Dimension(280, 300));
    pagecenter.setBackground(Color.WHITE);
    JPanel pagewest = new JPanel(new BorderLayout());
    pagewest.setPreferredSize(new Dimension(120, 0));
    pagewest.setBackground(Color.WHITE);

    lbltxt.setFont(new Font("Times New Roman", Font.PLAIN, 24));
    lbltxt.setForeground(Color.WHITE);
    lblEmpId.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    lblEmpId.setForeground(Color.BLACK);
    lblPw.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    lblPw.setForeground(Color.BLACK);
    Font textFieldFont = new Font("Times New Roman", Font.PLAIN, 15);
    txtUserId.setFont(textFieldFont);
    txtPw.setFont(textFieldFont);
    showpw.setFont(new Font("Times New Roman", Font.PLAIN, 12));
    showpw.setBackground(Color.WHITE);
    showpw.setFocusPainted(false);
    lbltxt2.setFont(new Font("Times New Roman", Font.PLAIN, 12));

    JPanel center = new JPanel(new GridBagLayout());
    center.setPreferredSize(new Dimension(400, 200));
    center.setBackground(Color.WHITE);

    JPanel north = new JPanel(new GridBagLayout());
    north.setPreferredSize(new Dimension(400, 45));
    north.setBackground(new Color(15, 41, 102));

    JPanel south = new JPanel(new GridBagLayout());
    south.setPreferredSize(new Dimension(400, 45));
    south.setBackground(new Color(15, 41, 102));

    JPanel empIdPanel = new JPanel(new GridBagLayout());
    empIdPanel.setPreferredSize(new Dimension(400, 100));
    empIdPanel.setBackground(Color.WHITE);

    JPanel pwPanel = new JPanel(new GridBagLayout());
    pwPanel.setPreferredSize(new Dimension(400, 100));
    pwPanel.setBackground(Color.WHITE);

    txtUserId.setPreferredSize(new Dimension(100, 25));
    txtPw.setPreferredSize(new Dimension(100, 25));
    showpw.setPreferredSize(new Dimension(25, 25));

    GridBagConstraints lt = new GridBagConstraints();
    lt.insets = new Insets(10, 10, 10, 10);
    lt.fill = GridBagConstraints.HORIZONTAL;
    lt.gridx = 0;
    lt.gridy = 0;
    lt.weightx = 1;
    lt.weighty = 1;

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 15, 5, 15);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;

    addButtonToPanel(south, btnLogin, 0);

    lt.insets = new Insets(5, 10, 5, 10);
    empIdPanel.add(lblEmpId, lt);
    lt.gridy = 1;
    empIdPanel.add(txtUserId, lt);
    lt.gridy = 0;
    pwPanel.add(lblPw, lt);
    lt.gridy = 1;
    pwPanel.add(txtPw, lt);

    lt.insets = new Insets(8, 8, 0, 8);
    lt.gridwidth = 2;
    lt.gridy = 0;
    center.add(empIdPanel, lt);
    lt.insets = new Insets(0, 8, 10, 8);
    lt.gridy = 1;
    center.add(pwPanel, lt);
    lt.insets = new Insets(5, 15, 15, 141);
    lt.gridwidth = 0;
    lt.gridy = 2;
    center.add(showpw, lt);
    lbltxt2.setPreferredSize(new Dimension(80, 20));
    lbltxt2.setMinimumSize(new Dimension(80, 20));
    lt.insets = new Insets(5, 165, 15, 10);
    lt.gridx = 1;
    lt.gridy = 2;
    center.add(lbltxt2, lt);

    lt.insets = new Insets(10, 10, 10, 10);
    lt.fill = GridBagConstraints.CENTER;
    lt.gridwidth = 1;
    lt.gridx = 0;
    lt.gridy = 0;
    btnLogin.setPreferredSize(new Dimension(50, 30));
    btnLogin.setMinimumSize(new Dimension(50, 30));
    btnLogin.setMargin(new Insets(0, 5, 0, 5));
    south.add(btnLogin, lt);

    lt.fill = GridBagConstraints.CENTER;
    north.add(lbltxt, lt);

    btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    showpw.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lbltxt2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lbltxt2.setForeground(Color.BLUE.darker());
    lbltxt2.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        showForgotPwDialog();
      }

      public void mouseEntered(java.awt.event.MouseEvent evt) {
        lbltxt2.setText("<html><u><font color='15, 41, 102'>Forgot password?</font></u></html>");
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        lbltxt2.setText("<html><font color='BLUE'>Forgot password?</font></html>");
      }
    });

    txtUserId.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtPw.requestFocus();
      }
    });

    txtPw.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnLogin.doClick();
      }
    });

    btnLogin.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {
        // Not needed for this functionality
      }

      private boolean moveRight = true;

      @Override
      public void mouseMoved(MouseEvent e) {
        String empIdText = txtUserId.getText();
        String password = new String(txtPw.getPassword());
        if (empIdText.isEmpty() || password.isEmpty()) {
          int currentX = btnLogin.getX();
          int newX;

          if (moveRight) {
            newX = currentX + 100; // Move 100 pixels to the right
            if (newX + btnLogin.getWidth() > getWidth()) {
              newX = getWidth() - btnLogin.getWidth(); // Adjust if it exceeds the right boundary
            }
          } else {
            newX = currentX - 100; // Move 100 pixels to the left
            if (newX < 0) {
              newX = 0; // Adjust if it exceeds the left boundary
            }
          }

          btnLogin.setLocation(newX, btnLogin.getY());
          moveRight = !moveRight; // Toggle the direction for the next move
        }
      }
    });

    pagecenter.add(north, BorderLayout.NORTH);
    pagecenter.add(center, BorderLayout.CENTER);
    pagecenter.add(south, BorderLayout.SOUTH);
    pagewest.add(graphic.getEmoji7(), BorderLayout.CENTER);

    add(pagecenter, BorderLayout.CENTER);
    add(pagewest, BorderLayout.WEST);

    txtPw.setEchoChar('*');
    btnLogin.addActionListener(this);

    showpw.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          txtPw.setEchoChar((char) 0);
        } else {
          txtPw.setEchoChar('*');
        }
      }
    });

    Image iconImage = Toolkit.getDefaultToolkit().getImage("D:\\Attendance V1\\img\\naga.png");
    setIconImage(iconImage);
    setTitle("Attendance System V1.0");
    setSize(400, 300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);
  }

  private void addButtonToPanel(JPanel panel, JButton button, int gridx) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx;
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 5, 0, 5);
    button.setPreferredSize(new Dimension(100, 30));
    button.setMinimumSize(new Dimension(100, 30));
    button.setBackground(Color.LIGHT_GRAY);
    button.setFocusPainted(false);
    panel.add(button, gbc);
  }

  private void showForgotPwDialog() {
    JFrame frame = new JFrame("Attendance System V1");
    JPanel panel1 = new JPanel(new BorderLayout());
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel(
        "<html>Please contact your system administrator for assistance.<br>You can reach them at support@example.com or call 123-456-7890.</html>");
    JButton okButton = new JButton("OK");
    okButton.setBackground(Color.LIGHT_GRAY);
    okButton.setFocusPainted(false);

    panel1.setPreferredSize(new Dimension(300, 100)); // Adjusted dimensions for better layout
    panel1.setBackground(Color.WHITE);
    panel1.add(label, BorderLayout.CENTER);

    panel2.setPreferredSize(new Dimension(300, 40)); // Adjusted dimensions for better layout
    panel2.setBackground(new Color(15, 41, 102));
    panel2.add(okButton);
    frame.setResizable(false);

    // Set up the OK button to close the frame
    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });

    Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attendance V1\\img\\naga.png");
    frame.setIconImage(icon);
    frame.add(panel1, BorderLayout.CENTER);
    frame.add(panel2, BorderLayout.SOUTH);
    frame.pack();
    frame.setLocationRelativeTo(this); // Center relative to the main frame
    frame.setVisible(true);
  }

  public void showMainApp() {
    this.setVisible(false);
    mainApp.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnLogin) {
      String userid = txtUserId.getText();
      int userId;
      try {
        userId = Integer.parseInt(userid);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid Admin ID. Please enter a valid number.");
        return;
      }
      String password = new String(txtPw.getPassword());

      System.out.println("Attempting login with AdminID: " + userId);
      User user = AdminDatabase.Adminauthenticate(userId, password);

      if (user != null) {
        System.out.println("Admin authenticated. Role: " + user.getRole());
        showMainApp();
      } else {
        if (error < 2) {
          error += 1;
          JOptionPane.showMessageDialog(this, "Wrong account");
        } else {
          txtPw.setEnabled(false);
          txtUserId.setEnabled(false);
          JOptionPane.showMessageDialog(this, "Max attempts reached");
        }
      }
    }
  }

  public static void main(String[] args) {
    new AdminLogin();
  }
}
