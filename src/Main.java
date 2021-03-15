import API.WeatherAPI1;
import API.WeatherAPI2;
import API.WeatherAPI3;

class Main {
  private static void section(int num) {
    System.out.println("-----");
    System.out.println("Weather API " + num);
  }

  public static void main(String[] args) {
    section(1);
    WeatherAPI1.main(args);

    section(2);
    WeatherAPI2.main(args);

    section(3);
    WeatherAPI3.main(args);
  }
}
