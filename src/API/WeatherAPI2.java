package API;

import Helpers.ConsoleColors;
import core.data.DataSource;

import java.util.ArrayList;

public class WeatherAPI2 {
  ArrayList<Observation> obs = new ArrayList<>();

  public static void main(String[] args) {
    WeatherAPI2 wapi = new WeatherAPI2("KATL", "KSAV", "KCLE");

    System.out.println();

    System.out.println("Coldest: " + wapi.coldestObservation());
    //wapi.highestPressure();
  }

  public WeatherAPI2(String... ids) {
    for (String id : ids) {
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml");
      ds.setCacheTimeout(15 * 60);
      ds.load();
      Observation ob = ds.fetch("API.Observation", "location", "weather", "temp_f", "wind_degrees");
      System.out.println(id + ": " + ob);
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

  public void highestPressure() {
    ArrayList<Observation> observations = new ArrayList<>();
    // fill observations with at least 5 API.Observation objects

    // find the API.Observation with the highest barometric pressure.

    // find the range from the highest to lowest pressures


  }

  public String toString() {
    return obs.toString();
  }
}


/* Represents a weather observation at a point in time*/
class Observation {
  String location;
  String description;
  float temp;    // in fahrenheit
  int windDir;   // in degrees

  Observation(String location, String description, float temp, int windDir) {
    this.location = location;
    this.description = description;
    this.temp = temp;
    this.windDir = windDir;
  }

  /* determine if the temperature of this observation is colder than 'that's */
  public boolean colderThan(Observation that) {
    return this.temp < that.temp;
  }

  /* produce a string describing this observation */
  public String toString() {
    return (ConsoleColors.bold(location) + ": " + temp + " degrees, " + description + " (wind: " + windDir + " degrees)");
  }
}
