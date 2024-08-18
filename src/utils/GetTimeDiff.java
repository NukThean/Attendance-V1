package utils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GetTimeDiff {

  public static LateResult getIn_Diff(String startTimeStr, String actualTimeStr) {
    // Define the time format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Parse the time strings to LocalTime
    LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
    LocalTime actualTime = LocalTime.parse(actualTimeStr, formatter);

    // Calculate the difference
    long minutesLate = Duration.between(startTime, actualTime).toMinutes();

    // Check if the actual time is after the start time
    boolean isLate = minutesLate > 0;
    System.out.println("Time differences by " + minutesLate + " minutes.");

    return new LateResult(minutesLate, isLate);
  }

  public static EarlyResult getLeave_Diff(String leaveTimeStr, String actualTimeStr) {
    // Define the time format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Parse the time strings to LocalTime
    LocalTime leaveTime = LocalTime.parse(leaveTimeStr, formatter);
    LocalTime actualTime = LocalTime.parse(actualTimeStr, formatter);

    // Calculate the difference
    long minutesDifference = Duration.between(leaveTime, actualTime).toMinutes();

    // Check if the actual time is before the leave time
    boolean isEarly = minutesDifference < 0;
    // long absoluteMinutesDifference = Math.abs(minutesDifference);

    System.out.println("Time difference: " + minutesDifference + " minutes.");

    return new EarlyResult(minutesDifference, isEarly);
  }

  // You might want to uncomment this if you are testing
  // public static void main(String[] args) {
  // LateResult result = GetTimeDiff.getIn_Diff("12:00:00", "13:05:07");
  // System.out.println("Minutes late: " + result.getMinutesLate());
  // System.out.println("Is late: " + result.isLate());
  // }
}
