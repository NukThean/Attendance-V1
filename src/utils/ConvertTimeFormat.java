package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;

public class ConvertTimeFormat {
  public static String ConvertTo24h(String time) {
    String time24 = "";
    try {
      SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
      SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss a");
      Date date = parseFormat.parse(time);
      time24 = displayFormat.format(date);
      System.out.println(parseFormat.format(date) + " = " + time24);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return time24;
  }

  // Method to convert 24-hour time format to 12-hour format
  public static String ConvertTo12h(String time) {
    String time12 = "";
    try {
      SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm:ss a");
      SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
      Date date = parseFormat.parse(time);
      time12 = displayFormat.format(date);
      System.out.println(parseFormat.format(date) + " = " + time12);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return time12;
  }

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
    ConvertTimeFormat.ConvertTo12h("17:30:22");
    ConvertTimeFormat.ConvertTo24h("9:30:22 AM");
  }
}

