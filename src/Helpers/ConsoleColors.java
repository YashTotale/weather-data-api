package Helpers;

public class ConsoleColors {
  public static final String BOLD = "\u001b[1m";
  public static final String UNDERLINE = "\u001b[4m";
  public static final String RESET = "\u001b[0m";

  public static String bold(Object toBold) {
    return BOLD + toBold + RESET;
  }

  public static String underline(Object toBold) {
    return UNDERLINE + toBold + RESET;
  }
}
