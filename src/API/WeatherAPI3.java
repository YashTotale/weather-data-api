package API;/*
 * NOTE:  When you want to run this file, change Main.java
 */

import Helpers.WeatherStation;
import core.data.DataSource;

import java.util.ArrayList;
import java.util.Scanner;

public class WeatherAPI3 {
   public static void main(String[] args) {
     DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/index.xml").load();
     ArrayList<WeatherStation> allstns = ds.fetchList("Helpers.WeatherStation", "station/station_name");//, "station/station_id", "station/state",             "station/latitude", "station/longitude");
      System.out.println("Total stations: " + allstns.size());

      Scanner sc = new Scanner(System.in);
      System.out.println("Enter a state abbreviation: ");
      String state = sc.next();
      System.out.println("Stations in " + state);
      ArrayList<WeatherStation> stationsInState = new ArrayList<>();
      for (WeatherStation ws : allstns) {
         if (ws.isLocatedInState(state)) {
           System.out.println("  " + ws.getId() + ": " + ws.getLocation());
         }
      }
   }
}
