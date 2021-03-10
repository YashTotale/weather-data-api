/*
 Represents information about a NWS weather station
 As a minimum, you must add in info so that WeatherAPI3 will compile and 
 run as intended.  Feel free to add more!*/

public class WeatherStation {
   // instance variables needed:
   // name, id, state, latitude, longitude
  private String name;// more needed


  public WeatherStation(String n){// more parameters needed
    this.name = n;
  }

   // and getters for each
   public String getId(){
     return null;
   }
   public String getName(){
     return null;
   }
  
   /* Determine if this weather station is located in the given state */
   public boolean isLocatedInState(String st) {
      return false;
   }

   // add in a toString, too!
   
}