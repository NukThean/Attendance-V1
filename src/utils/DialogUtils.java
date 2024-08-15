package utils;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DialogUtils {
  public static void createDialog(String title, String labelText, ActionListener actionListener) {
    JFrame frame = new JFrame(title);
    JPanel panel1 = new JPanel(new GridBagLayout());
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel(labelText);
    // JLabel label2 = new JLabel(labelText2);
    JTextField textField = new JTextField();
    JButton okButton = new JButton("OK");
    Font customFont = new Font("Times New Roman", Font.PLAIN, 16);

    label.setFont(customFont);
    // label2.setFont(customFont);
    textField.setPreferredSize(new Dimension(120, 20));
    textField.setMinimumSize(new Dimension(120, 20));
    okButton.setBackground(Color.LIGHT_GRAY);
    okButton.setFocusPainted(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel1.add(label, gbc);

    gbc.gridx = 1;
    panel1.add(textField, gbc);

    panel1.setPreferredSize(new Dimension(250, 80));
    panel1.setBackground(Color.WHITE);

    panel2.setPreferredSize(new Dimension(250, 40));
    panel2.setBackground(new Color(15, 41, 102));
    panel2.add(okButton);

    frame.setResizable(false);

    okButton.addActionListener(actionListener);

    frame.setLocationRelativeTo(null);
    Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Attandance V1\\img\\naga.png");
    frame.setIconImage(icon);
    frame.add(panel1, BorderLayout.CENTER);
    frame.add(panel2, BorderLayout.SOUTH);
    frame.pack();
    frame.setLocationRelativeTo(null); // Center the frame after packing
    frame.setVisible(true);
  }

}
