package ProjectConsole.Controllers;

import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Ride.Ride;
import ProjectConsole.Users.Driver;
import ProjectConsole.Users.Passenger;
import ProjectConsole.Users.Student;

import java.util.*;

public class ProfileCotroller {
    private UsersDataBase db;
    private Student student;
    private Driver driver;
    private Ride ride;
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
        if (seats>=1 && seats<=4 && !Location.isEmpty() && !CarModel.isEmpty()){
            Driver driver=new Driver(seats,CarModel,Location,student);
            this.driver=driver;

            System.out.println("What you want as a driver : \n 1- Add Ride \n 2-Show Your Rides \n 3- Return \n 4-Exit To MainMenu ");
            int DriverChoice= scanner.nextInt();

            switch (DriverChoice){
                case 1 :
                    AddRide(driver);
                    break;
                case 2 :

                case 3 :

                case 4 :

            }

        }else {
            System.out.println("You Have SomeThing Wrong");
        }
    }
    public void AddRide(Driver driver){
        Scanner scanner=new Scanner(System.in);
        HashMap<String , String> TimeTravel=new HashMap<>();
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
                scanner.nextLine();
                TimeTravel.put(ChosenDay, Time);
                System.out.println(TimeTravel.get(ChosenDay));
                System.out.println("You Choose this day for a ride : "+TimeTravel.get(ChosenDay) +"\n");
                new Ride(driver.getSeats(),driver,TimeTravel, driver.getLocation(), driver.getCollege());
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
    }
}
