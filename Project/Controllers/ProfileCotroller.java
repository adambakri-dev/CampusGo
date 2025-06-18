package Project.Controllers;

import Project.DataBase.RidesDataBase;
import Project.DataBase.UserDatabase;
import Project.Ride.Ride;
import Project.Users.Driver;
import Project.Users.Passenger;
import Project.Users.Student;

import java.util.*;

public class ProfileCotroller {
    Ride ride;
    private RidesDataBase RideDB;// This will make us enter to RideDataBase Class
    private Student student;// This will make us a parent class (student) to child class (driver)
    private Driver driver;
    private Passenger passenger;
    private UserDatabase DB;

    public ProfileCotroller(UserDatabase DB, Student student) {
        this.DB = DB;
        this.student = student;
    }

    public ProfileCotroller() {
    }

    public void DriverProfile(Driver driver) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Now you are a driver");
        System.out.println("What you want to do as a driver \n 1- Add Ride \n 2- Show Rides \n 3- Delete Ride \n 4- exit");
        int Choice = scanner.nextInt();
        RidesDataBase RidesDB = new RidesDataBase(driver);
        if (Choice == 1) {
            AddRide(driver);
        } else if (Choice == 2) {
            RidesDB.showDriverRides(driver.getName());
            DriverProfile(driver);
        } else if (Choice == 3) {
            RidesDB.showDriverRides(driver.getName());
            System.out.println("enter the index to delete : ");
            int index = scanner.nextInt();
            RidesDB.DeleteMyRide(driver.getName(), index);

        } else if (Choice == 4) {
        }
    }

    public void AddRide(Driver driver) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, ArrayList<String>> TimeTravel = new HashMap<>();
        ArrayList<String> AllDays = new ArrayList<>(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"));

        while (true) {
            for (int i = 0; i < AllDays.size(); i++) {
                System.out.println((i + 1) + "- " + AllDays.get(i));
            }
            System.out.println("Choose a day (By Number) : \n -if you want to exit press 0-");
            int Choice = scanner.nextInt();
            scanner.nextLine();
            if (Choice == 0) {
                DriverProfile(driver); // ارجع إلى صفحة السائق
                break;
            }
            if (Choice <= 5 && Choice >= 1) {
                String ChosenDay = AllDays.get(Choice - 1);
                System.out.println("you chose " + ChosenDay);
                System.out.println("Choose a Time For A Ride Format=>(8:00) : ");
                String Time = scanner.nextLine();

                TimeTravel.putIfAbsent(ChosenDay, new ArrayList<>());
                TimeTravel.get(ChosenDay).add(Time);

                System.out.println(" you choose :  " + ChosenDay + " - " + Time);
            } else {
                System.out.println(" not correct option ");
            }

            System.out.println(" did you want to choose another day (yes/no):  ");
            String answer = scanner.nextLine();
            if (answer.equals("no")) {
                RidesDataBase rideDB = new RidesDataBase(driver);
                rideDB.addRide(driver.getSeats(), driver, TimeTravel, driver.getLocation(), driver.getCollege());
                break;
            } else if (answer.equals("yes")) {
                DriverProfile(driver);
            } else {
                System.out.println("this is not a option");
            }
        }
    }

    public void PassengerProfile(Passenger passenger) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("now you are a passenger.");
        System.out.println("what you want to do as a passenger : \n 1- Show Rides \n 2- Search For A Ride By Driver Name\n 3- Exit");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("Enter the day of ride : ");
            String day= scanner.nextLine();
            scanner.nextLine();
            System.out.println("enter the time (9:00) : ");
            String time= scanner.nextLine();
            RidesDataBase RideDB=new RidesDataBase();
            List<Ride> suitableRides = RideDB.showrides(passenger.getLocation(), passenger.getCollege(), day, time);
            if (suitableRides.isEmpty()) {
                System.out.println("No suitable rides found for your criteria.");
            } else {
                System.out.println("Suitable rides:");
                for (Ride r : suitableRides) {
                    System.out.println(r);
                }
            }
        } else if (choice == 2) {

        } else if (choice == 3) {

        } else {
            System.out.println("this is not a choice");
        }
    }
}
