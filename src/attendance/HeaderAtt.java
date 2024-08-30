package attendance;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HeaderAtt extends JPanel {
  JLabel label1 = new JLabel("Recent Attendance");
  JButton btn1 = new JButton("Sort by ID");

  public HeaderAtt() {
    label1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    label1.setBackground(Color.BLACK);

    btn1.setPreferredSize(new Dimension(100, 25));
    btn1.setMinimumSize(new Dimension(100, 25));
    btn1.setBackground(Color.LIGHT_GRAY);
    btn1.setFocusPainted(false);
    btn1.setMargin(new Insets(0, 5, 0, 5));
    btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Add ActionListener to btn1
    btn1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Code to sort attendance by ID
        SortByEmp.showSearchIDDialog();

      }
    });

    setLayout(new BorderLayout());
    JPanel MainHeader = new JPanel(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(0, 5, 0, 0);
    constraints.fill = GridBagConstraints.NONE;
    constraints.anchor = GridBagConstraints.SOUTHWEST;
    constraints.weightx = 1;
    constraints.weighty = 1;

    constraints.gridx = 0;
    constraints.gridy = 0;
    MainHeader.add(label1, constraints);
    constraints.anchor = GridBagConstraints.SOUTHEAST;
    MainHeader.add(btn1, constraints);

    add(MainHeader, BorderLayout.CENTER);
    setPreferredSize(new Dimension(894, 80));
    setBackground(Color.WHITE);
    setVisible(true);
  }
}
