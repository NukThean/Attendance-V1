package Admin.Admindashboard;

// sub layout with sub-sub layout
import java.time.LocalDate;
import javax.swing.*;
import Admin.emp_data.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class center3 extends JPanel implements ActionListener {
  // -------------DATE------------------------
  LocalDate today = LocalDate.now();
  int day = today.getDayOfMonth();
  int month = today.getMonthValue();
  int year = today.getYear();
  int StaffCount = EmpTable.getStaffCount();
  int StaffPresent = EmpTable.getStaffPresent();
  int StaffOnLeave = ShiftCount.getTodayLeaveCount();
  int StaffShiftToday = ShiftCount.getTodayShiftCount();
  int StaffLate = EmpTable.getStaffLate();
  int StaffEarlyOut = EmpTable.getStaffEarlyOut();
  int getStaffAbsence = EmpTable.getStaffAbsence();
  // ------------------------------------------
  private JLabel txttop1 = new JLabel("Dashboard");
  private JLabel txttop2 = new JLabel("HR Department");
  private JLabel todaydate = new JLabel("Today's Date " + day + "/" + month + "/" + year);
  private JLabel totalEmp = new JLabel("Total Employee");
  private JLabel late = new JLabel("Late Attendance");
  private JLabel earlyout = new JLabel("Early Leave");
  private JLabel present = new JLabel("   Present   ");
  private JLabel todayShift = new JLabel("Today Shift");
  private JLabel onleave = new JLabel("On Leave");
  private JLabel absent = new JLabel("T-1 Absence");
  private JLabel staffcount = new JLabel("" + StaffCount);
  private JLabel staffPresent = new JLabel("" + StaffPresent);
  private JLabel stafflate = new JLabel("" + StaffLate);
  private JLabel staffEarlyOut = new JLabel("" + StaffEarlyOut);
  private JLabel TminusAbsence = new JLabel("" + getStaffAbsence);
  private JLabel shiftCount = new JLabel("" + StaffShiftToday);
  private JLabel leaveCount = new JLabel("" + StaffOnLeave);

  public center3() {
    // ---------------------Emoji and Font--------------------
    img emojiPanel = new img();
    todaydate.setFont(new Font("Times New Roman", Font.PLAIN, 23));
    txttop1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    txttop2.setFont(new Font("Times New Roman", Font.PLAIN, 20));

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

    // Set up GridBagLayout and constraints
    JPanel ccent = new JPanel(new GridBagLayout());// Center-center panel
    ccent.setPreferredSize(new Dimension(894, 688));
    ccent.setBackground(Color.WHITE);

    ctop.add(txtctop, BorderLayout.NORTH); // Add txtctop panel to ctop panel

    GridBagConstraints aac = new GridBagConstraints();
    aac.insets = new Insets(10, 20, 250, 20);
    aac.fill = GridBagConstraints.NONE; // Prevents stretching
    aac.anchor = GridBagConstraints.CENTER; // Centers the panel
    aac.weightx = 1;
    aac.weighty = 1;

    GridBagConstraints ddc = new GridBagConstraints();
    ddc.insets = new Insets(10, 20, 0, 20);
    ddc.fill = GridBagConstraints.NONE; // Prevents stretching
    ddc.anchor = GridBagConstraints.CENTER; // Centers the panel
    ddc.weightx = 1;
    ddc.weighty = 1;

    GridBagConstraints bbc = new GridBagConstraints();
    bbc.insets = new Insets(10, 10, 10, 20);
    bbc.fill = GridBagConstraints.HORIZONTAL;
    bbc.weightx = 1;
    bbc.weighty = 1;

    // Use the addDbToPanel method to create and add each sub-panel
    addDbToPanel(ccent, null, null, 0, 0, null, emojiPanel.getEmoji1(), totalEmp, staffcount);
    addDbToPanel(ccent, null, null, 1, 0, null, emojiPanel.getEmoji2(), late, stafflate);

    addDbToPanel(ccent, aac, bbc, 0, 0, null, emojiPanel.getEmoji4(), present, staffPresent);
    addDbToPanel(ccent, ddc, null, 0, 0, null, emojiPanel.getEmoji8(), todayShift, shiftCount);

    addDbToPanel(ccent, aac, bbc, 1, 0, null, emojiPanel.getEmoji5(), onleave, leaveCount); // Placeholder
    // for leave
    // value
    bbc.insets = new Insets(10, 10, 10, 10);
    addDbToPanel(ccent, null, bbc, 2, 0, null, emojiPanel.getEmoji3(), earlyout, staffEarlyOut);
    addDbToPanel(ccent, aac, bbc, 2, 0, null, emojiPanel.getEmoji6(), absent, TminusAbsence);

    add(ctop, BorderLayout.NORTH);
    add(ccent, BorderLayout.CENTER);
    setBackground(Color.GREEN);
    ccent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Handle actions here if needed
  }

  private void addDbToPanel(JPanel panel, GridBagConstraints customConstraints,
      GridBagConstraints aac, int gridx, int gridy, Dimension size, JLabel emoji, JLabel txt,
      JLabel value) {

    JPanel Dbpanel = new JPanel(new GridBagLayout());
    value.setFont(new Font("Times New Roman", Font.PLAIN, 30));
    txt.setFont(new Font("Times New Roman", Font.PLAIN, 18));
    // Set panel size
    if (size != null) {
      Dbpanel.setPreferredSize(size);
      Dbpanel.setMinimumSize(size);
      Dbpanel.setMaximumSize(size);
    } else {
      Dimension defaultSize = new Dimension(250, 89);
      Dbpanel.setPreferredSize(defaultSize);
      Dbpanel.setMinimumSize(defaultSize);
      Dbpanel.setMaximumSize(defaultSize);
    }
    Dbpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    Dbpanel.setBackground(new Color(224, 224, 224));

    GridBagConstraints itemConstraints;
    if (aac == null) {
      itemConstraints = new GridBagConstraints();
      itemConstraints.insets = new Insets(10, 10, 10, 10);
      itemConstraints.fill = GridBagConstraints.HORIZONTAL;
      itemConstraints.gridx = 0;
      itemConstraints.gridy = 0;
    } else {
      itemConstraints = aac;
      // Override gridx and gridy based on provided values
      itemConstraints.gridx = gridx;
      itemConstraints.gridy = gridy;
    }

    Dbpanel.add(emoji, itemConstraints);

    itemConstraints.gridx++;
    Dbpanel.add(txt, itemConstraints);

    itemConstraints.gridx++;
    Dbpanel.add(value, itemConstraints);

    // If customConstraints is null, create a default set of constraints
    GridBagConstraints panelConstraints;
    if (customConstraints == null) {
      panelConstraints = new GridBagConstraints();
      panelConstraints.insets = new Insets(0, 20, 500, 20);
      panelConstraints.gridx = gridx;
      panelConstraints.gridy = gridy;
      // panelConstraints.fill = GridBagConstraints.HORIZONTAL;
      panelConstraints.fill = GridBagConstraints.NONE; // Prevents stretching
      panelConstraints.anchor = GridBagConstraints.CENTER; // Centers the panel
      panelConstraints.weightx = 1;
      panelConstraints.weighty = 1;
    } else {
      panelConstraints = customConstraints;
      // Override gridx and gridy based on provided values
      panelConstraints.gridx = gridx;
      panelConstraints.gridy = gridy;
    }

    panel.add(Dbpanel, panelConstraints);
  }

}
