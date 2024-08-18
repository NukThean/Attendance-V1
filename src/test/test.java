package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
  public static void main(String[] args) {
    // Create an instance of the 'test' class
    test obj = new test();

    // Call the ConvertTo24h method
    String time24 = obj.ConvertTo24h("6:00:15 PM");

  }

  public String ConvertTo24h(String time) {
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
}
