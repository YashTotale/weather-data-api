package API;

import Helpers.WeatherStation;

import javax.swing.*;

public class WeatherAPI1 {
    public static void main(String[] args) {
        WeatherStation livermore = new WeatherStation("KLVK", true);
        livermore.showInfo();

        System.out.println();

        WeatherStation dallas = new WeatherStation("KDFW");
        dallas.showInfo();

        System.out.println();

        WeatherStation atlanta = new WeatherStation("KATL");
        atlanta.showInfo();

        System.out.println();

        WeatherStation miami = new WeatherStation("KMIA");
        miami.showInfo();

        getAndPresentUsingJOptionPane();
    }


    public static void getAndPresentUsingJOptionPane() {
        while (true) {
            try {
                String location = JOptionPane.showInputDialog(null, "Enter a location");
                if (location == null || location.length() == 0) break;
                WeatherStation userInput = new WeatherStation(location);
                JOptionPane.showMessageDialog(null, userInput, "Information for " + location, JOptionPane.INFORMATION_MESSAGE);
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Enter a valid ID", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
