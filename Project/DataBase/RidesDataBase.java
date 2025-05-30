package Project.DataBase;

import Project.Ride.Ride;
import Project.Users.Driver;
import Project.Users.Passenger;

import java.util.*;

// This Class will make a student search for the ride and this class will show a student a list of ride in same of his location.
public class RidesDataBase {
    private Ride ride;
    private Driver driver;
    private Passenger passenger;
    private UsersDataBase db;
    private HashMap <String , Ride> RideDataBase;//=> this will have a Driver ID as a Key This and class Ride for the Value
    private HashMap<Driver, List<Ride>> driverRides = new HashMap<>();

    public RidesDataBase(Driver driver , UsersDataBase db){
        this.driver=driver;
        this.db=db;
    }
    public RidesDataBase(Passenger passenger , UsersDataBase db ){
        this.passenger=passenger;
        this.db=db;
    }

    // Driver Operations :
    public void addRide(int seats, Driver driver, HashMap<String,ArrayList<String>> timeTravel,  String location, String destination) {

        Ride ride = new Ride(seats, driver, timeTravel, location, destination);//=> this will save a ride info in ride class
        this.ride=ride;

        driverRides.putIfAbsent(driver, new ArrayList<>());//We IFAbsent because if it was the first time that this driver will add a ride we didn't get error
        // because in first time he will make a ride he will add a ride to null key
        // he wil be null because in this case this will be a first time that he added a ride and because the value ara ArrayList
        driverRides.get(driver).add(ride);
        System.out.println("Ride added successfully!");
    }

    public void  getRidesByDriver(Driver driver) {//This for print all ride for a driver and to a driver his rides
        for ( Ride ride1 : driverRides.get(driver)){
            ride1.showRideInfo();
        }
    }

    public void EditRide(){}
    public void DeleteRide(){}

    //Passenger Operations
    public void JoinRide(){}
    public void LeaveRide(){}
    public void SearchForRideByDriver(){}
    public void JoinedRides(){}





}
