package test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeDifferenceExample {
    public static void main(String[] args) {
        // Define the time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        // Example times
        String startTimeStr = "08:00:00 AM";
        String actualTimeStr = "08:00:40 AM";

        // Parse the time strings to LocalTime
        LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
        LocalTime actualTime = LocalTime.parse(actualTimeStr, formatter);

        // Calculate the difference
        long minutesLate = Duration.between(startTime, actualTime).toMinutes();

        // Check if the actual time is after the start time
        if (minutesLate > 0) {
            System.out.println("Late by " + minutesLate + " minutes.");
        } else {
            System.out.println("On time.");
        }
    }
}
