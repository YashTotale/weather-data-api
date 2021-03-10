import core.data.DataSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherAPI1 {
    public static final String BOLD = "\u001b[1m";
    public static final String RESET = "\u001b[0m";
    // Change below if using repl.it
    public static final String IMAGES_DIR = System.getProperty("user.dir") + "/images";

    private Field[] fields = {
            new Field("Location", "location"),
            new Field("Latitude", "latitude"),
            new Field("Longitude", "longitude"),
            new Field("Observation Time", "observation_time"),
            new Field("Visibility", "visibility_mi"),
            new Field("Temperature", "temperature_string"),
            new Field("Wind", "wind_string"),
            new Field("Wind chill", "windchill_string"),
            new Field("Pressure", "pressure_string"),
    };

    private DataSource ds;
    private final HashMap<String, String> data = new HashMap<>();
    private String id;

    public static void main(String[] args) {
        WeatherAPI1 livermore = new WeatherAPI1("KLVK", true);
        livermore.showInfo();

        System.out.println();

        WeatherAPI1 dallas = new WeatherAPI1("KDFW");
        dallas.showInfo();

        System.out.println();

        WeatherAPI1 atlanta = new WeatherAPI1("KATL");
        atlanta.showInfo();

        System.out.println();

        WeatherAPI1 miami = new WeatherAPI1("KMIA");
        miami.showInfo();

        getAndPresentUsingJOptionPane();
    }

    public WeatherAPI1(String id) {
        this(id, false);
    }

    public WeatherAPI1(String id, boolean printUsage) {
        this.id = id;
        ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml");

        ds.setCacheTimeout(15 * 60);
        ds.load();
        if (printUsage) ds.printUsageString();

        for (Field field : fields) {
            String key = field.getKey();
            if (ds.hasFields(key)) {
                String value = ds.fetchString(key);
                data.put(field.getName(), value);
            }
        }

        getImage();
    }

    public void showInfo() {
        for (Field field : fields) {
            String name = field.getName();
            if (data.containsKey(name)) {
                System.out.println(name + ": " + morph(data.get(name)));
            }
        }
    }

    public ArrayList<String> getInfo() {
        ArrayList<String> info = new ArrayList<>();

        for (Field field : fields) {
            String name = field.getName();
            if (data.containsKey(name)) {
                info.add(name + ": " + data.get(name));
            }
        }

        return info;
    }

    private String morph(Object toMorph) {
        return BOLD + toMorph + RESET;
    }

    private void getImage() {
        String baseURL;

        if (ds.hasFields("icon_url_base", "icon_url_name")) {
            baseURL = (ds.fetchString("icon_url_base") + ds.fetchString("icon_url_name")).replaceAll("http", "https");
            try {
                URL url = new URL(baseURL);
                RenderedImage image = ImageIO.read(url);

                File images = new File(IMAGES_DIR);
                if(!images.exists()) {
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

    public static void getAndPresentUsingJOptionPane() {
        while (true) {
            try {
                String location = JOptionPane.showInputDialog(null, "Enter a location");
                if (location == null || location.length() == 0) break;
                WeatherAPI1 userInput = new WeatherAPI1(location);
                ArrayList<String> info = userInput.getInfo();
                JOptionPane.showMessageDialog(null, info.toString(), "Information for " + location, JOptionPane.INFORMATION_MESSAGE);
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Enter a valid ID", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class Field {
    private final String name;
    private final String key;

    public Field(String name, String key) {
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
