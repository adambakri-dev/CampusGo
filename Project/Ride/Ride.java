package Project.Ride;

import Project.Users.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// This class will have a direct operation with class Driver
// but passengers Operations will be in class RideDataBase
public class Ride {
    private int seats;
    private Driver driver;
    private HashMap<String, ArrayList<String>> timeTravel;
    private String location;
    private String destination;
    private List <String> Passengers;
    private String hour;
    private String dateAndDay;


    public Ride(int seats, Driver driver, String location, String destination, String hour, String dateAndDay, List<String> passengers) {
        this.seats = seats;
        this.driver = driver;
        this.location = location;
        this.destination = destination;
        this.hour = hour;
        this.dateAndDay = dateAndDay;
        this.Passengers = passengers;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    public String getHour() { return hour; }
    public String getDateAndDay() { return dateAndDay; }
    public Driver getDriver() { return driver; }
    public String getDriverName() { return driver.getName(); }
    public String getLocation() { return location; }
    public String getDestination() { return destination; }
    public HashMap<String, ArrayList<String>> getTimeTravel() { return timeTravel; }
    public int getSeats() { return seats; }

    public List<String> getPassengers() {
        return Passengers;
    }

    @Override
    public String toString() {
        return getDriverName() + "," + location + "," + destination + "," + timeTravel.toString() + "," + seats;
    }
}
