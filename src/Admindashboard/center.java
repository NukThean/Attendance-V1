package Admindashboard;

// sub layout with sub-sub layout
import java.time.LocalDate;
import javax.swing.*;
import emp_data.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class center extends JPanel implements ActionListener {
  // -------------DATE------------------------
  LocalDate today = LocalDate.now();
  int day = today.getDayOfMonth();
  int month = today.getMonthValue();
  int year = today.getYear();
  int StaffCount = EmpTable.getStaffCount();
  int StaffPresent = EmpTable.getStaffPresent();
  int StaffLate = EmpTable.getStaffLate();
  int StaffEarlyOut = EmpTable.getStaffEarlyOut();
  // ------------------------------------------
  private JLabel txttop1 = new JLabel("Dashboard");
  private JLabel txttop2 = new JLabel("HR Department");
  private JLabel todaydate = new JLabel("Today's Date " + day + "/" + month + "/" + year);
  private JLabel totalEmp = new JLabel("Total Employee");
  private JLabel late = new JLabel("Late Attendance");
  private JLabel earlyout = new JLabel("Early Leave");
  private JLabel present = new JLabel("Present      ");
  private JLabel onleave = new JLabel("Permission Leave");
  private JLabel absent = new JLabel("T-1 Absence");
  private JLabel staffcount = new JLabel("" + StaffCount);
  private JLabel staffPresent = new JLabel("" + StaffPresent);
  private JLabel stafflate = new JLabel("" + StaffLate);
  private JLabel staffEarlyOut = new JLabel("" + StaffEarlyOut);

  public center() {
    // ---------------------Emoji and Font--------------------
    img emojiPanel = new img();
    todaydate.setFont(new Font("Times New Roman", Font.PLAIN, 23));
    txttop1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    txttop2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    totalEmp.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    late.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    earlyout.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    present.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    onleave.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    absent.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    staffcount.setFont(new Font("Times New Roman", Font.PLAIN, 30));
    staffPresent.setFont(new Font("Times New Roman", Font.PLAIN, 30));
    stafflate.setFont(new Font("Times New Roman", Font.PLAIN, 30));
    staffEarlyOut.setFont(new Font("Times New Roman", Font.PLAIN, 30));

    todaydate.setForeground(Color.WHITE);
    txttop1.setForeground(Color.WHITE);
    txttop2.setForeground(Color.WHITE);
    // ---------------------------------------------------------
    setLayout(new BorderLayout());
    JPanel ctop = new JPanel(new BorderLayout());// Center-top panel
    ctop.setPreferredSize(new Dimension(894, 80));
    ctop.setBackground(new Color(157, 160, 168));
    todaydate.setHorizontalAlignment(SwingConstants.CENTER);
    ctop.add(todaydate, BorderLayout.CENTER);

    JPanel txtctop = new JPanel(new GridBagLayout());
    txtctop.setBackground(new Color(15, 41, 102));
    GridBagConstraints abc = new GridBagConstraints();
    abc.insets = new Insets(10, 10, 10, 0);
    abc.fill = GridBagConstraints.HORIZONTAL;
    abc.gridx = 0;
    abc.weightx = 1; // Allow horizontal resizing
    abc.weighty = 1; // Allow resize vertically
    // Adding txttop1 and txttop2 labels to txtctop panel
    txtctop.add(txttop1, abc);
    abc.gridx++;
    abc.anchor = GridBagConstraints.CENTER;
    abc.insets = new Insets(10, 0, 10, 95);
    txtctop.add(txttop2, abc);

    // CENTER SUB PANEL
    JPanel ccent = new JPanel(new GridBagLayout());// Center-center panel
    ccent.setPreferredSize(new Dimension(894, 688));
    ccent.setBackground(Color.WHITE);

    ctop.add(txtctop, BorderLayout.NORTH); // Add txtctop panel to ctop panel

    GridBagConstraints bbc = new GridBagConstraints();
    bbc.insets = new Insets(10, 10, 30, 10);
    bbc.fill = GridBagConstraints.HORIZONTAL;
    bbc.gridx = 0;
    bbc.gridy = 0;
    bbc.weightx = 1; // allow resizing for emoji
    bbc.weighty = 1;

    // ------------------------------------------------------------
    // CENTER SUB SUB PANEL
    JPanel ccent1 = new JPanel(new GridBagLayout());
    ccent1.setPreferredSize(new Dimension(200, 89));
    ccent1.setBackground(new Color(224, 224, 224));
    ccent1.add(emojiPanel.getEmoji1(), bbc);

    bbc.gridx++;
    bbc.insets = new Insets(10, 0, 50, 10);
    ccent1.add(totalEmp, bbc);
    bbc.insets = new Insets(20, 45, 0, 10);
    // bbc.gridy++;
    ccent1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    ccent1.add(staffcount, bbc);
    // --------------------------------------------------------------------------------------
    JPanel ccent2 = new JPanel(new GridBagLayout());
    ccent2.setPreferredSize(new Dimension(200, 89));
    ccent2.setBackground(new Color(224, 224, 224));

    bbc.insets = new Insets(10, 10, 25, 10);
    bbc.fill = GridBagConstraints.HORIZONTAL;
    bbc.gridx = 0;
    bbc.gridy = 0;

    ccent2.add(emojiPanel.getEmoji2(), bbc);

    bbc.gridx++;
    bbc.insets = new Insets(10, 0, 50, 10);
    ccent2.add(late, bbc);
    bbc.insets = new Insets(20, 45, 0, 10);
    // bbc.gridy++;
    ccent2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    ccent2.add(stafflate, bbc);
    // --------------------------------------------------------------------------------------
    JPanel ccent3 = new JPanel(new GridBagLayout());
    ccent3.setPreferredSize(new Dimension(200, 89));
    ccent3.setBackground(new Color(224, 224, 224));;

    bbc.insets = new Insets(10, 10, 20, 10);
    bbc.fill = GridBagConstraints.HORIZONTAL;
    bbc.gridx = 0;
    bbc.gridy = 0;

    ccent3.add(emojiPanel.getEmoji3(), bbc);

    bbc.gridx++;
    bbc.insets = new Insets(10, 10, 50, 30);
    ccent3.add(earlyout, bbc);
    ccent3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    bbc.insets = new Insets(20, 45, 0, 10);
    ccent3.add(staffEarlyOut, bbc);
    // --------------------------------------------------------------------------------------
    JPanel ccent4 = new JPanel(new GridBagLayout());
    ccent4.setPreferredSize(new Dimension(200, 89));
    ccent4.setBackground(new Color(224, 224, 224));

    bbc.insets = new Insets(10, 10, 30, 30);
    bbc.fill = GridBagConstraints.HORIZONTAL;
    bbc.gridx = 0;
    bbc.gridy = 0;

    ccent4.add(emojiPanel.getEmoji4(), bbc);

    bbc.gridx++;
    bbc.insets = new Insets(10, 5, 50, 10);
    ccent4.add(present, bbc);
    bbc.insets = new Insets(20, 20, 0, 10);
    ccent4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    ccent4.add(staffPresent, bbc);
    // --------------------------------------------------------------------------------------
    JPanel ccent5 = new JPanel(new GridBagLayout());
    ccent5.setPreferredSize(new Dimension(200, 89));
    ccent5.setBackground(new Color(224, 224, 224));

    bbc.insets = new Insets(10, 0, 30, 10);
    bbc.fill = GridBagConstraints.HORIZONTAL;
    bbc.gridx = 0;
    bbc.gridy = 0;

    ccent5.add(emojiPanel.getEmoji5(), bbc);

    bbc.gridx++;
    bbc.insets = new Insets(10, 0, 50, 0);
    ccent5.add(onleave, bbc);
    ccent5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    // --------------------------------------------------------------------------------------
    JPanel ccent6 = new JPanel(new GridBagLayout());
    ccent6.setPreferredSize(new Dimension(200, 89));
    ccent6.setBackground(new Color(224, 224, 224));
    bbc.insets = new Insets(10, 0, 30, 20);

    bbc.fill = GridBagConstraints.HORIZONTAL;
    bbc.gridx = 0;
    bbc.gridy = 0;

    ccent6.add(emojiPanel.getEmoji6(), bbc);
    ccent6.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    bbc.gridx++;
    bbc.insets = new Insets(10, 0, 50, 15);
    ccent6.add(absent, bbc);
    // --------------------------------------------------------------------------------------

    GridBagConstraints boxinfo = new GridBagConstraints();
    boxinfo.insets = new Insets(40, 10, 10, 10);
    boxinfo.fill = GridBagConstraints.CENTER;
    boxinfo.anchor = GridBagConstraints.NORTH;
    boxinfo.weightx = 1;
    boxinfo.weighty = 1;

    // Add panels in a 2x3 grid
    boxinfo.gridx = 0;
    boxinfo.gridy = 0;
    ccent.add(ccent1, boxinfo);

    boxinfo.gridx = 1;
    ccent.add(ccent2, boxinfo);

    boxinfo.gridx = 2;
    ccent.add(ccent3, boxinfo);

    boxinfo.insets = new Insets(0, 10, 350, 10);

    boxinfo.gridx = 0;
    boxinfo.gridy = 1;
    ccent.add(ccent4, boxinfo);

    boxinfo.gridx = 1;
    ccent.add(ccent5, boxinfo);

    boxinfo.gridx = 2;
    ccent.add(ccent6, boxinfo);

    add(ctop, BorderLayout.NORTH);
    add(ccent, BorderLayout.CENTER);
    setBackground(Color.GREEN);
    // ccent.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
    ccent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Handle actions here if needed
  }
}
