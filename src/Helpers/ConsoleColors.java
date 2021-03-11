package Helpers;

public class ConsoleColors {
  public static final String BOLD = "\u001b[1m";
  public static final String RESET = "\u001b[0m";

  public static String bold(Object toBold) {
    return BOLD + toBold + RESET;
  }
}
