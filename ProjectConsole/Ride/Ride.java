package ProjectConsole.Ride;

import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Users.Driver;
import ProjectConsole.Users.Passenger;

import java.util.HashMap;

public class Ride {
    private HashMap<String , String> TimeTravel;
    private int CountRide;
    private String RideID;
    private Driver driver;
    private Passenger passenger;
    private String [] passengersNames;
    private String NameDriver;
    private String time;
    private String Location;
    private String [] day;
    private HashMap<String , ProjectConsole.Ride.Ride> Ride;
    private UsersDataBase db;
    HashMap <String , HashMap> ride;
    HashMap <Driver , Ride> InRide;

    public Ride(UsersDataBase db){
        this.db=db;
    }
    // Driver operation :
    public void AddRide(int CountRide , String [] day , String Location , String time , Driver driver ){
        
    }
    public void DeleteRide(){}
    public void ShowRideInfo(){}
    //Passenger Operations :
}
