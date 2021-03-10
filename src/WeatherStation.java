import Helpers.WeatherField;
import core.data.DataSource;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherStation {
  private String id;
  private DataSource ds;
  private final HashMap<String, String> data = new HashMap<>();

  public static final String BOLD = "\u001b[1m";
  public static final String RESET = "\u001b[0m";
  // Change below if using repl.it
  public static final String IMAGES_DIR = System.getProperty("user.dir") + "/images";

  public static final WeatherField[] fields = {
    new WeatherField("Location", "location"),
    new WeatherField("Latitude", "latitude"),
    new WeatherField("Longitude", "longitude"),
    new WeatherField("Observation Time", "observation_time"),
    new WeatherField("Visibility", "visibility_mi"),
    new WeatherField("Temperature", "temperature_string"),
    new WeatherField("Wind", "wind_string"),
    new WeatherField("Wind chill", "windchill_string"),
    new WeatherField("Pressure", "pressure_string"),
  };

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

  // and getters for each
  public String getId() {
    return null;
  }

  public String getName() {
    return null;
  }

  /* Determine if this weather station is located in the given state */
  public boolean isLocatedInState(String st) {
    return false;
  }

  // add in a toString, too!

  public void showInfo() {
    for (WeatherField field : fields) {
      String name = field.getName();
      if (data.containsKey(name)) {
        System.out.println(name + ": " + BOLD + data.get(name) + RESET);
      }
    }
  }

  public ArrayList<String> getInfoStrings() {
    ArrayList<String> info = new ArrayList<>();

    for (WeatherField field : fields) {
      String name = field.getName();
      if (data.containsKey(name)) {
        info.add(name + ": " + data.get(name));
      }
    }

    return info;
  }

  private void getImage() {
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
