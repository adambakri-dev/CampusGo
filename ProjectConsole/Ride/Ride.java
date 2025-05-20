package ProjectConsole.Ride;

import ProjectConsole.DataBase.RidesDataBase;
import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Users.Driver;
import ProjectConsole.Users.Passenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// This class will have a direct operation with class Driver
// but passengers Operations will be in class RideDataBase
public class Ride {
    private int seats;
    private String location;// This A Location Of The Driver And It Will Be A Start Point
    private String destination;// This A Location Of The College And It Will Be A End Point
    private String rideID;// This is A ID Of The Ride And It Will Make Easy To Search A Ride By ID
    private Driver driver;
    private List<String> passengers;//This is SomeThing temp
    private HashMap<String, ArrayList<String>> timeTravel = new HashMap<>();//This a HashMap for Travel Time This Will Save a Time In
    // The Day In The Key And The Time In The Value


    // This will make a New Ride From The Driver
    public Ride(int seats, Driver driver, HashMap<String, ArrayList<String>> timeTravel, String location, String destination) {
        this.seats = seats;//This Number Of Seats Of The Car's Driver
        this.driver = driver;
        this.timeTravel = timeTravel;
        this.rideID = rideID;
        this.location = location;
        this.destination = destination;
    }

    public String getLocation() {
        return location;
    }
    public int getSeats() {
        return seats;
    }
    public String getRideID() {
        return rideID;
    }
    public HashMap<String, ArrayList<String>> getTimeTravel() {
        return timeTravel;
    }
    public String getDestination() {
        return destination;
    }
    public void showRideInfo() {
        System.out.println("Ride ID: " + rideID);
        System.out.println("Driver: " + driver.getName()); // assuming driver has getName()
        System.out.println("Seats: " + seats);
        System.out.println("From: " + location + " To: " + destination);
        System.out.println("Time Table: " + timeTravel);
    }
}
