package Project.Controllers;

import Project.DataBase.RidesDataBase;
import Project.DataBase.UsersDataBase;
import Project.Users.Driver;
import Project.Users.Passenger;
import Project.Users.Student;

import java.util.*;

public class ProfileCotroller {
    private RidesDataBase RideDB;// This will make us enter to RideDataBase Class
    private UsersDataBase db;// This will make us enter a UserDataBase Class
    private Student student;// This will make us a parent class (student) to child class (driver)
    private Driver driver;
    private Passenger passenger;
    public ProfileCotroller(UsersDataBase db , Student student){
        this.db=db;
        this.student=student;
    }
    public void DriverProfile(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter a number of seat : ");
        int seats= scanner.nextInt();
        System.out.println("Enter your Car (with year) : ");
        scanner.nextLine();
        String CarModel= scanner.nextLine();
        System.out.println("Enter your location : ");
        String Location= scanner.nextLine();
        System.out.println("Enter Your Major : ");
        String Major= scanner.nextLine();
        System.out.println("Enter Your Year : ");
        String Year= scanner.nextLine();
        if (seats>=1 && seats<=4 && !Location.isEmpty() && !CarModel.isEmpty()){
            Driver driver=new Driver(seats,CarModel,Location,student,Major,Year);
            this.driver=driver;
            System.out.println("Now you are a driver");
            System.out.println("What you want to do as a driver \n 1- Add Ride \n 2- Show Rides \n 3- exit");
            int Choice= scanner.nextInt();
            if (Choice==1){
                AddRide(driver);
            } else if (Choice==2) {
                
            }

        }else {
            System.out.println("You Have SomeThing Wrong");
        }
    }
    public void AddRide(Driver driver){
        Scanner scanner=new Scanner(System.in);
        HashMap<String , ArrayList<String>> TimeTravel=new HashMap<>();
        ArrayList <String> AllDays=new ArrayList<>(Arrays.asList("Sunday","Monday","Tuesday","Wednesday","Thursday"));
        while (true) {
            for (int i = 0; i < AllDays.size(); i++) {
                System.out.println((i + 1) + "- " + AllDays.get(i));
            }
            System.out.println("Choose a day (By Number) : \n -if you want to exit press 0-");
            int Choice = scanner.nextInt();
            scanner.nextLine();
            if(Choice==0){
                DriverProfile();
            }
            if (Choice <= 5 && Choice >= 1) {
                String ChosenDay = AllDays.get(Choice - 1);
                System.out.println("you chose a " + ChosenDay + " for do a ride in it ");
                System.out.println("Choose a Time For A Ride Format=>(8:00) : ");
                String Time = scanner.nextLine();
                if (!TimeTravel.containsKey(ChosenDay)) {
                    //This will check if this day are chosen before
                    //if yes he will put the time normally
                    // but if not we cant add a time because it is a String and our hashmap values are ArrayList
                    // so we
                    TimeTravel.put(ChosenDay, new ArrayList<String>());
                }
                TimeTravel.get(ChosenDay).add(Time);
                System.out.println(TimeTravel.get(ChosenDay));
                System.out.println("You Choose this day for a ride : "+TimeTravel.get(ChosenDay) +"\n");
                RideDB=new RidesDataBase(driver,db);
                RideDB.addRide(driver.getSeats(),driver,TimeTravel, driver.getLocation(), driver.getCollege());
                RideDB.getRidesByDriver(driver);
            }else {
                System.out.println("you chosen a wrong choice");
            }

        }
    }
    public void PassengerProfile(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter a location : ");
        String Location= scanner.nextLine();
        System.out.println("Enter your major : ");
        String major= scanner.nextLine();
        System.out.println("Enter your year : ");
        String year= scanner.nextLine();
        Passenger passenger=new Passenger(Location,major,year,student);
        System.out.println(passenger);
        System.out.println("what you want to do as a passenger \n 1- Show Rides \n 2- Search For A Ride \n 3- Search For A Driver \n 4- Exit");
    }
}
