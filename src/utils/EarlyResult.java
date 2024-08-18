package utils;

public class EarlyResult {
  private static long minutesEarly_L;
  private static boolean isEarly;

  public EarlyResult(long minutesEarly_L, boolean isEarly) {
    this.minutesEarly_L = minutesEarly_L;
    this.isEarly = isEarly;
  }

  public static long getMinutesEarly() {
    return minutesEarly_L;
  }

  public static boolean isEarly() {
    return isEarly;
  }
}

