package Helpers;

public class WeatherField {
  private final String name;
  private final String key;

  public WeatherField(String name, String key) {
    this.name = name;
    this.key = key;
  }

  public String getName() {
    return this.name;
  }

  public String getKey() {
    return this.key;
  }
}
