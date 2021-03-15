package API;

import Helpers.ConsoleColors;
import Helpers.WeatherStation;
import core.data.DataSource;

import java.util.*;

public class WeatherAPI3 {
  private static ArrayList<WeatherStation> stations;
  private static HashMap<String, ArrayList<WeatherStation>> states = new HashMap<>();

  public static void main(String[] args) {
    fetchStations();
    getStates();

    System.out.println("Available States: " + states.keySet());
    System.out.println();

    Scanner sc = new Scanner(System.in);
    System.out.print("Enter a state abbreviation: ");

    String state = sc.next();

    if(!states.containsKey(state)) {
      throw new InputMismatchException("Enter a valid state!");
    };

    System.out.println();
    System.out.println(ConsoleColors.underline("Stations in " + ConsoleColors.bold(state)));

    ArrayList<WeatherStation> stateStations = states.get(state);

    for (WeatherStation station : stateStations) {
        System.out.println("-----");
        station.showInfo();
    }
  }

  public static void fetchStations() {
    DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/index.xml").load();
    stations = ds.fetchList("Helpers.WeatherStation", "station/station_id", "station/station_name", "station/state", "station/latitude", "station/longitude");
    System.out.println("Total stations: " + ConsoleColors.bold(stations.size()));
  }

  public static void getStates() {
    for (WeatherStation station : stations) {
      String state = station.getState();
      if (states.containsKey(state)) {
        states.get(state).add(station); // Add station to state ArrayList
      } else {
        states.put(state, new ArrayList<>(Collections.singletonList(station))); // Create new state ArrayList
      }
    }
  }
}
