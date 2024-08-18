package utils;

public class LateResult {
  private static long minutesLate;
  private static boolean isLate;

  public LateResult(long minutesLate, boolean isLate) {
    this.minutesLate = minutesLate;
    this.isLate = isLate;
  }

  public static long getMinutesLate() {
    return minutesLate;
  }

  public static boolean isLate() {
    return isLate;
  }
}
