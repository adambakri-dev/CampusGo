package ProjectConsole.DataBase;

import ProjectConsole.Ride.Ride;
import ProjectConsole.Users.Driver;
import ProjectConsole.Users.Passenger;
import ProjectConsole.Users.Student;

import java.util.*;

// This Class will make a student search for the ride and this class will show a student a list of ride in same of his location.
public class RidesDataBase {
    private Driver driver;
    private Passenger passenger;
    private UsersDataBase db;
    private HashMap <String , Ride> RideDataBase;//=> this will have a Driver ID as a Key This and class Ride for the Value
    private HashMap<Driver, List<Ride>> driverRides = new HashMap<>();

    public void addRide(int seats, Driver driver, HashMap<String, String> timeTravel, String rideID, String location, String destination) {
        Ride ride = new Ride(seats, driver, timeTravel, rideID, location, destination);
        driverRides.putIfAbsent(driver, new ArrayList<>());
        driverRides.get(driver).add(ride);
        System.out.println("Ride added successfully!");
    }

    public List<Ride> getRidesByDriver(Driver driver) {
        return driverRides.getOrDefault(driver, new ArrayList<>());
    }




}
