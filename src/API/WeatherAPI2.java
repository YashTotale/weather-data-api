package API;

import Helpers.ConsoleColors;
import core.data.DataSource;

import java.util.ArrayList;

public class WeatherAPI2 {
  ArrayList<Observation> obs = new ArrayList<>();

  public static void main(String[] args) {
    WeatherAPI2 wapi = new WeatherAPI2("KATL", "KBHM", "KSFO", "KSAV", "TJSJ", "KDSM", "KCLE");

    System.out.println();

    System.out.println(ConsoleColors.underline("Coldest") + ": " + wapi.coldestObservation());

    System.out.println();

    Observation[] highAndLow = wapi.highestAndLowestPressures();
    Observation highest = highAndLow[0];
    Observation lowest = highAndLow[1];

    System.out.println(ConsoleColors.underline("Highest Pressure") + ": " + highest);
    System.out.println(ConsoleColors.underline("Lowest Pressure") + ": "  + lowest);
    System.out.println(ConsoleColors.underline("Range of Pressure") + ": "  + (highest.getPressure() - lowest.getPressure()) + " mb");
  }

  public WeatherAPI2(String... ids) {
    for (String id : ids) {
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml");
      ds.setCacheTimeout(15 * 60);
      ds.load();
      Observation ob = ds.fetch("API.Observation", "location", "weather", "temp_f", "wind_degrees", "pressure_mb");
      System.out.println(ConsoleColors.underline(id) + ": " + ob);
      obs.add(ob);
    }
  }

  public Observation coldestObservation() {
    Observation coldest = null;

    for (Observation ob : obs) {
      if (coldest == null || ob.colderThan(coldest)) coldest = ob;
    }

    return coldest;
  }

  public Observation[] highestAndLowestPressures() {
    Observation highest = null;
    Observation lowest = null;

    for (Observation ob : obs) {
      if (highest == null || ob.higherPressureThan(highest)) highest = ob;
      if (lowest == null || ob.lowerPressureThan(lowest)) lowest = ob;
    }

    return new Observation[]{highest, lowest};
  }

  public String toString() {
    return obs.toString();
  }
}

class Observation {
  private final String location;
  private final String description;
  private final float temp;
  private final int windDir;
  private final float pressure;

  // If you did not want to include pressure
  Observation(String location, String description, float temp, int windDir) {
    this.location = location;
    this.description = description;
    this.temp = temp;
    this.windDir = windDir;
    this.pressure = 0.0F; // Not sure what to set it to
  }

  Observation(String location, String description, float temp, int windDir, float pressure) {
    this.location = location;
    this.description = description;
    this.temp = temp;
    this.windDir = windDir;
    this.pressure = pressure;
  }

  public boolean colderThan(Observation that) {
    return this.temp < that.temp;
  }

  public boolean lowerPressureThan(Observation that) {
    return this.pressure < that.pressure;
  }

  public boolean higherPressureThan(Observation that) {
    return this.pressure > that.pressure;
  }

  public String toString() {
    return (ConsoleColors.bold(location) + ": " + temp + " degrees, " + description + " (pressure: " + pressure + " mb, wind: " + windDir + " degrees)");
  }

  public String getLocation() {
    return this.location;
  }

  public String getDescription() {
    return description;
  }

  public float getTemp() {
    return temp;
  }

  public int getWindDir() {
    return windDir;
  }

  public float getPressure() {
    return pressure;
  }
}
