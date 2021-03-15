package Helpers;

import core.data.DataSource;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeatherStation {
  private String id;
  private DataSource ds;
  private final HashMap<String, String> data = new HashMap<>();

  // Change below if using repl.it
  public static final String IMAGES_DIR = System.getProperty("user.dir") + "/images";

  private static final String LOCATION = "Location";
  private static final String LATITUDE = "Latitude";
  private static final String LONGITUDE = "Longitude";
  private static final String NAME = "Name";
  private static final String STATE = "State";

  public static final WeatherField[] fields = {
    new WeatherField(LOCATION, "location"),
    new WeatherField(LATITUDE, "latitude"),
    new WeatherField(LONGITUDE, "longitude"),
    new WeatherField("Observation Time", "observation_time"),
    new WeatherField("Visibility", "visibility_mi"),
    new WeatherField("Temperature", "temperature_string"),
    new WeatherField("Wind", "wind_string"),
    new WeatherField("Wind chill", "windchill_string"),
    new WeatherField("Pressure", "pressure_string"),
  };

  public WeatherStation(String id, String name, String state, String latitude, String longitude) {
    this.id = id;

    data.put(NAME, name);
    data.put(STATE, state);
    data.put(LATITUDE, latitude);
    data.put(LONGITUDE, longitude);
  }

  public WeatherStation(String id) {
    this(id, false);
  }

  public WeatherStation(String id, boolean printUsage) {
    this.id = id;
    ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml");

    ds.setCacheTimeout(15 * 60);
    ds.load();
    if (printUsage) ds.printUsageString();

    for (WeatherField field : fields) {
      String key = field.getKey();
      if (ds.hasFields(key)) {
        String value = ds.fetchString(key);
        data.put(field.getName(), value);
      }
    }

    getImage();
  }

  public String getId() {
    return this.id;
  }

  public String getLocation() {
    return data.get(LOCATION);
  }

  public String getState() {
    return data.get(STATE);
  }

  public boolean isLocatedInState(String st) {
   return getState().equals(st);
  }

  public ArrayList<String> getDataStrings() {
    return getDataStrings(false);
  }

  public ArrayList<String> getDataStrings(boolean bold) {
    ArrayList<String> dataStrs = new ArrayList<>();

    for (Map.Entry<String, String> entry : data.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      dataStrs.add(key + ": " + (bold ? ConsoleColors.bold(value) : value));
    }

    return dataStrs;
  }

  public String toString() {
    return getDataStrings().toString();
  }

  public void showInfo() {
    ArrayList<String> info = getDataStrings(true);

    for (String s : info) {
      System.out.println(s);
    }
  }

  private void getImage() {
    if (ds != null) {
      String baseURL;

      if (ds.hasFields("icon_url_base", "icon_url_name")) {
        baseURL = (ds.fetchString("icon_url_base") + ds.fetchString("icon_url_name")).replaceAll("http", "https");
        try {
          URL url = new URL(baseURL);
          RenderedImage image = ImageIO.read(url);

          File images = new File(IMAGES_DIR);
          if (!images.exists()) {
            images.mkdir();
          }

          String filePath = IMAGES_DIR + "/" + id + ".png";

          ImageIO.write(image, "png", new File(filePath));
          System.out.println("Saved image to: " + filePath);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
