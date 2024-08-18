package test;

import javax.swing.JComboBox;

public class Timeutil {

  // Method to get the index based on AM/PM
  public static int getIndexForAmPm(JComboBox<String> comboBox, String amPm) {
    if (amPm.equals("AM")) {
      return 0; // Index for AM
    } else if (amPm.equals("PM")) {
      return 1; // Index for PM
    } else {
      return -1; // Return -1 for invalid input
    }
  }

  public static void main(String[] args) {
    // Example usage
    JComboBox<String> cmbTime1 = new JComboBox<>(new String[] {"AM", "PM"});
    JComboBox<String> cmbTime2 = new JComboBox<>(new String[] {"AM", "PM"});

    String AmPm1 = "AM";
    String AmPm2 = "PM";

    int index1 = getIndexForAmPm(cmbTime1, AmPm1);
    if (index1 != -1) {
      cmbTime1.setSelectedIndex(index1);
    } else {
      System.out.println("Invalid AM/PM value for cmbTime1");
    }

    int index2 = getIndexForAmPm(cmbTime2, AmPm2);
    if (index2 != -1) {
      cmbTime2.setSelectedIndex(index2);
    } else {
      System.out.println("Invalid AM/PM value for cmbTime2");
    }
  }
}

