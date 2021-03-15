package Helpers;

public class ConsoleColors {
  public static final String BOLD = "\u001b[1m";
  public static final String BRIGHT_BLUE = "\u001b[34;1m";
  public static final String BRIGHT_RED = "\u001b[31;1m";
  public static final String UNDERLINE = "\u001b[4m";
  public static final String RESET = "\u001b[0m";

  public static String bold(Object toBold) {
    return BOLD + toBold + RESET;
  }

  public static String underline(Object toUnderline) {
    return UNDERLINE + toUnderline + RESET;
  }

  public static String brightBlue(Object toBrightBlue) {
    return BRIGHT_BLUE + toBrightBlue + RESET;
  }

  public static String brightRed(Object toBrightRed) {
    return BRIGHT_RED + toBrightRed + RESET;
  }
}
