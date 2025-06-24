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
    private ArrayList <String> PassengersInTravel=new ArrayList<>();

    public Ride(int seats, Driver driver, HashMap<String, ArrayList<String>> timeTravel, String location, String destination) {
        this.seats = seats;
        this.driver = driver;
        this.timeTravel = timeTravel;
        this.location = location;
        this.destination = destination;
    }

    public Driver getDriver() { return driver; }
    public String getDriverName() { return driver.getName(); }
    public String getLocation() { return location; }
    public String getDestination() { return destination; }
    public HashMap<String, ArrayList<String>> getTimeTravel() { return timeTravel; }
    public int getSeats() { return seats; }

    @Override
    public String toString() {
        return getDriverName() + "," + location + "," + destination + "," + timeTravel.toString() + "," + seats;
    }
}
