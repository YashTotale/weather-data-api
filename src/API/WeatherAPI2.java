package API;

import core.data.DataSource;

import java.util.ArrayList;

/******   NOTE  When you want to run this file,
 You must change Main.java*/

public class WeatherAPI2 {
  public static void main(String[] args) {
    WeatherAPI2 wapi = new WeatherAPI2();
    wapi.coldestObservation();
    //wapi.highestPressure();
  }

  public void coldestObservation() {
    String id1 = "KATL";
    DataSource ds1 = DataSource.connect("http://weather.gov/xml/current_obs/" + id1 + ".xml");
    ds1.setCacheTimeout(15 * 60);
    ds1.load();


    Observation ob1 = ds1.fetch("API.Observation", "weather", "temp_f", "wind_degrees");
    System.out.println(id1 + ": " + ob1);

    String id2 = "KSAV";
    DataSource ds2 = DataSource.connect("http://weather.gov/xml/current_obs/" + id2 + ".xml");
    ds2.setCacheTimeout(15 * 60);
    ds2.load();

    Observation ob2 = ds2.fetch("API.Observation", "weather", "temp_f", "wind_degrees");
    System.out.println(id2 + ": " + ob2);
    // make a third API.Observation and find the coldest of the three


    if (ob1.colderThan(ob2)) {
      System.out.println("Colder at " + id1);
    } else {
      System.out.println("Colder at " + id2);
    }
  }

  public void highestPressure(){
    ArrayList<Observation> observations = new ArrayList<>();
    // fill observations with at least 5 API.Observation objects

    // find the API.Observation with the highest barometric pressure.

    // find the range from the highest to lowest pressures


  }
}


/* Represents a weather observation at a point in time*/
class Observation {
   float temp;    // in fahrenheit
   int windDir;   // in degrees
   String description;

  Observation(String description, float temp, int windDir) {
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
      return (temp + " degrees; " + description + " (wind: " + windDir + " degrees)");
   }
}
