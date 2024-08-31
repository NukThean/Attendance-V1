package Login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import loginpage.User;
import loginpage.UserDatabase;
import userPage.userPage;

public class UserLogin extends JFrame implements ActionListener {
  private JLabel lblEmpId = new JLabel("Employee ID ");
  private JLabel lbltxt = new JLabel("Login");
  private JLabel lbltxt2 = new JLabel("<html><u>Forgot password?</u></html>");
  private JLabel lblPw = new JLabel("Password ");
  private JLabel lblLogin = new JLabel("<html>" + "<div style='text-align:center;'>"
      + "<span style='font-size:17px;'>WHAT THE BRO</span>");

  private JTextField txtUserId = new JTextField();
  private JPasswordField txtPw = new JPasswordField();
  private JCheckBox showpw = new JCheckBox("Show Password?");
  private JButton btnNext = new JButton("Login");
  private JButton btnLogin = new JButton("Login");
  private JPanel pwPanel = new JPanel();
  private userPage userPanel;
  int error;

  private enum LoginState {
    CHECK_USERID, ENABLE_PASSWORD, VERIFY_PASSWORD, ADD_PASSWORD, USER_BLOCKED
  }

  LoginState State = LoginState.CHECK_USERID;

  public UserLogin() {
    setLayout(new BorderLayout());

    JPanel pagecenter = new JPanel(new BorderLayout());
    pagecenter.setPreferredSize(new Dimension(280, 300));
    pagecenter.setBackground(Color.WHITE);
    JPanel pagewest = new JPanel(new GridBagLayout());
    pagewest.setPreferredSize(new Dimension(120, 0));
    pagewest.setBackground(Color.BLUE.darker());

    lbltxt.setFont(new Font("Times New Roman", Font.PLAIN, 24));
    lbltxt.setForeground(Color.WHITE);
    lblEmpId.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    lblEmpId.setForeground(Color.BLACK);
    lblPw.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    lblPw.setForeground(Color.BLACK);

    lblPw.setForeground(Color.BLACK);
    Font textFieldFont = new Font("Times New Roman", Font.PLAIN, 15);
    txtUserId.setFont(textFieldFont);
    txtPw.setFont(textFieldFont);
    showpw.setFont(new Font("Times New Roman", Font.PLAIN, 12));
    showpw.setBackground(Color.WHITE);
    showpw.setFocusPainted(false);
    lbltxt2.setFont(new Font("Times New Roman", Font.PLAIN, 12));

    lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    lblLogin.setForeground(Color.WHITE);

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

    pwPanel = new JPanel(new GridBagLayout());
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

    addButtonToPanel(south, btnNext, 0);
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
    btnLogin.setPreferredSize(new Dimension(60, 30));
    btnLogin.setMinimumSize(new Dimension(60, 30));
    btnLogin.setMargin(new Insets(0, 5, 0, 5));
    south.add(btnLogin, lt);

    btnNext.setPreferredSize(new Dimension(60, 30));
    btnNext.setMinimumSize(new Dimension(60, 30));
    btnNext.setMargin(new Insets(0, 5, 0, 5));
    south.add(btnNext, lt);

    lt.fill = GridBagConstraints.CENTER;
    north.add(lbltxt, lt);

    btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

    btnLogin.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        State = LoginState.VERIFY_PASSWORD;
        handleLoginPw();
      }
    });

    btnNext.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleLogin();
      }
    });

    txtUserId.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnNext.doClick();
        txtPw.requestFocus();
      }
    });

    txtPw.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnLogin.doClick();
      }
    });

    txtPw.setEnabled(false);
    pwPanel.setVisible(false);
    showpw.setVisible(false);
    btnLogin.setVisible(false);


    lt.insets = new Insets(10, 0, 180, 0);

    pagecenter.add(north, BorderLayout.NORTH);
    pagecenter.add(center, BorderLayout.CENTER);
    pagecenter.add(south, BorderLayout.SOUTH);
    pagewest.add(lblLogin, lt);

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

  public void showUserPage(User user) {
    this.userPanel = new userPage(user);
    this.setVisible(false);
    userPanel.setVisible(true);
  }

  public void EnablePW() {
    System.out.println("EnablePW");
    State = LoginState.ENABLE_PASSWORD;
    handleLogin();
  }

  public void VerifyPW() {
    System.out.println("VerifyPW");
    State = LoginState.VERIFY_PASSWORD;
    handleLogin();
  }

  public void WrongPw() {
    JOptionPane.showMessageDialog(null, "Wrong password hehe");
  }

  public void addPw() {
    State = LoginState.ADD_PASSWORD;
    handleLoginPw();
  }

  public void userBlock() {
    State = LoginState.ENABLE_PASSWORD;
    handleLogin();
    State = LoginState.USER_BLOCKED;
    handleLoginPw();
  }

  private void handleLogin() {
    int userId;

    try {
      userId = Integer.parseInt(txtUserId.getText().trim());
    } catch (NumberFormatException o) {
      JOptionPane.showMessageDialog(this, "Invalid Employee ID. Please enter a valid number.",
          "Error", JOptionPane.ERROR_MESSAGE);
      System.out.println("Invalid id format");
      return;
    }

    switch (State) {

      case CHECK_USERID:
        System.out.println("CASE CHECK USERID");
        if (UserDatabase.CheckuserIdExist(userId)) {
          error = UserDatabase.get_failed_attempts(userId);
          if (error > 2) {
            userBlock();
            JOptionPane.showMessageDialog(this,
                "User " + userId + " is blocked from login. please contact the system administator",
                "Error", JOptionPane.ERROR_MESSAGE);
          } else {
            EnablePW();
          }

        } else {
          JOptionPane.showMessageDialog(this, "User ID does not exist. Please try again.", "Error",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("UserID not exist");
          return;
        }
        break;

      case ENABLE_PASSWORD:
        pwPanel.setVisible(true);
        txtPw.setEnabled(true);
        txtPw.setVisible(true);
        showpw.setVisible(true);
        btnNext.setVisible(false);
        btnLogin.setVisible(true);
        System.out.println("CASE ENABLE PASSWORD");

        if (UserDatabase.CheckuserIdExist(userId) && UserDatabase.CheckuserpwExist(userId)) {
          System.out.println("ID and PW Exist");
          System.out.println(UserDatabase.CheckuserpwExist(userId));


        } else {
          System.out.println("ID Exist & PW Not Exist");
          addPw();
          if (UserDatabase.CheckuserpwExist(userId)) {
            pwPanel.setVisible(true);
            showpw.setVisible(true);
          }
        }

        break;

      default:
        JOptionPane.showMessageDialog(this, "Unexpected state. Please try again.", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
  }


  private void handleLoginPw() {
    int userId;
    User user;
    String password;


    try {
      userId = Integer.parseInt(txtUserId.getText().trim());
    } catch (NumberFormatException o) {
      JOptionPane.showMessageDialog(this, "Invalid Employee ID. Please enter a valid number.",
          "Error", JOptionPane.ERROR_MESSAGE);
      System.out.println("Invalid id format");
      return;
    }

    switch (State) {

      case VERIFY_PASSWORD:
        System.out.println("CASE VERIFY PASSWORD");
        password = new String(txtPw.getPassword());
        user = UserDatabase.authenticate(userId, password);

        error = UserDatabase.get_failed_attempts(userId);

        if (user != null) {
          System.out
              .println("User authenticated.\nUserID: " + userId + "\nRole: " + user.getRole());
          showUserPage(user);
          UserDatabase.reset_fail_attempts(userId);
        } else if (!UserDatabase.CheckuserpwTrue(userId, password)) {
          WrongPw();
          error = error + 1;
          System.out.println("UserID " + userId + " Attempt to log in with wrong password");
          UserDatabase.fail_login(userId);
          if (error >= 3) {
            txtPw.setEnabled(false);
            txtUserId.setEnabled(false);
            btnLogin.setEnabled(false);
            System.out.println("USERID " + userId + " BLOCKED FROM LOGIN");
            JOptionPane.showMessageDialog(this,
                "User " + userId + " is blocked from login, please contact the system administator",
                "Error", JOptionPane.ERROR_MESSAGE);
            UserDatabase.set_status(userId);
          }
        }
        break;


      case ADD_PASSWORD:

        String newPassword = UserDatabase.handleNullPassword(userId);
        System.out.println(newPassword);

        break;

      default:
        JOptionPane.showMessageDialog(this, "Unexpected state. Please try again.", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;

      case USER_BLOCKED:
        txtPw.setEnabled(false);
        txtUserId.setEnabled(false);
        btnLogin.setEnabled(false);
        System.out.println("USERID " + userId + " BLOCKED FROM LOGIN");
    }
  }

  public static void main(String[] args) {
    new UserLogin();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
