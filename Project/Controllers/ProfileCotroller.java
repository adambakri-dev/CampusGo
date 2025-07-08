package Project.Controllers;

import Project.DataBase.RidesDataBase;
import Project.DataBase.UserDatabase;
import Project.Main;
import Project.Ride.Ride;
import Project.Users.Driver;
import Project.Users.Passenger;
import Project.Users.Student;

import java.util.*;
// This class will handle every driver/passenger operation and all those operations are connected with RideDataBase.
public class ProfileCotroller {
    Ride ride;
    private RidesDataBase RideDB;
    private Student student;
    private Driver driver;
    private Passenger passenger;
    private UserDatabase DB;

    //This for enter this class from this constructor as a driver from login or ChooseRole.
    public ProfileCotroller(UserDatabase DB, Driver driver) {
        this.DB = DB;
        this.driver = driver;
    }

    //This for enter this class from this constructor as a passenger from login or ChooseRole.
    public ProfileCotroller(UserDatabase DB, Passenger passenger) {
        this.DB = DB;
        this.passenger = passenger;
    }
}

//    public void DriverProfile(Driver driver) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Now you are a driver");
//        System.out.println("What you want to do as a driver \n 1" + "- Add Ride \n 2- Show Rides \n 3- Delete Ride \n 4- Change Role \n 5- Exit");
//        int Choice = scanner.nextInt();
//        RidesDataBase RidesDB = new RidesDataBase(driver);
//        if (Choice == 1) {
//            AddRide(driver);
//        } else if (Choice == 2) {
//            RidesDB.showDriverRides(driver.getName());
//            DriverProfile(driver);
//        } else if (Choice == 3) {
//            RidesDB.showDriverRides(driver.getName());
//            System.out.println("enter the index to delete : ");
//            int index = scanner.nextInt();
//            RidesDB.deleteMyRide(driver.getName(), index);
//
//        } else if (Choice == 4) {
//            Student student1=new Student(driver.getId(), driver.getName(),  driver.getPassword(), driver.getEmail(), driver.getCollege(), driver.getGender());
//            ChooseRoleController chooseRoleController=new ChooseRoleController(DB);
//            System.out.println("what you want to be :\n 1- Driver \n 2-Passenger \n 3-Return");
//            int choice= scanner.nextInt();
//            if (choice==1){
//                chooseRoleController.becomeDriver(student1,driver.getSeats(), driver.getCarModel(), driver.getLocation(), driver.getMajor(), driver.getYear());
//            } else if (choice==2) {
//                chooseRoleController.becomePassenger(student1, driver.getLocation(), driver.getMajor(), driver.getYear(), driver.getCollege());
//            }
//        }
//    }
//
//    public void AddRide(Driver driver) {
//        //this class will make us add a ride.
//        Scanner scanner = new Scanner(System.in);
//        HashMap<String, ArrayList<String>> TimeTravel = new HashMap<>();//this will contain a Day and the hour of the ride.
//        ArrayList<String> AllDays = new ArrayList<>(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"));
//
//        while (true) {
//            for (int i = 0; i < AllDays.size(); i++) {
//                System.out.println((i + 1) + "- " + AllDays.get(i));
//            }
//            System.out.println("Choose a day (By Number) : \n -if you want to exit press 0-");
//            int Choice = scanner.nextInt();
//            scanner.nextLine();
//            if (Choice == 0) {
//                DriverProfile(driver);
//            }
//            if (Choice <= 5 && Choice >= 1) {
//                String ChosenDay = AllDays.get(Choice - 1); // this will choose a day according to number.
//                System.out.println("you chose " + ChosenDay);
//                System.out.println("Choose a Time For A Ride Format=>(8:00) : ");
//                String Time = scanner.nextLine();
//                TimeTravel.putIfAbsent(ChosenDay, new ArrayList<>());
//                TimeTravel.get(ChosenDay).add(Time);
//                System.out.println(" you choose :  " + ChosenDay + " - " + Time);
//            } else {
//                System.out.println(" not correct option ");
//            }
//            System.out.println(" did you want to choose another day (yes/no):  ");
//            String answer = scanner.nextLine();
//            if (answer.equals("no")) {
//                RidesDataBase rideDB = new RidesDataBase(driver);
//                rideDB.addRide(driver.getSeats(), driver, TimeTravel, driver.getLocation(), driver.getCollege());
//                DriverProfile(driver);
//            } else if (answer.equals("yes")) {
//                RidesDataBase rideDB = new RidesDataBase(driver);
//                rideDB.addRide(driver.getSeats(), driver, TimeTravel, driver.getLocation(), driver.getCollege());
//                AddRide(driver);
//            } else {
//                System.out.println("this is not a option");
//            }
//        }
//    }
//
//    public void PassengerProfile(Passenger passenger) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("now you are a passenger.");
//        System.out.println("what you want to do as a passenger : \n 1- Show Rides \n 2- Search For A Ride By Driver Name\n 3- Exit");
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        if (choice == 1) {
//            System.out.println("Enter the day of ride : ");
//            String day = scanner.nextLine().trim();
//            System.out.println("enter the time (form => 9:00) : ");
//            String time = scanner.nextLine().trim();
//
//            RidesDataBase RideDB = new RidesDataBase(passenger);
//            List<Ride> suitableRides = RideDB.showRides(passenger.getLocation(), passenger.getCollege(), day, time); //and this will get a suitable ride from RideDataBase.
//
//            if (suitableRides.isEmpty()) {
//                System.out.println("❌ No suitable rides found for your criteria.");
//                PassengerProfile(passenger);
//            } else {
//                System.out.println("✅ Suitable rides:");
//                for (Ride r : suitableRides) {
//                    System.out.println(r);
//                }
//                System.out.println("you want to register in one : (yes/no)");
//                String answer=scanner.nextLine();
//                if (answer.equals("no")){
//                    PassengerProfile(passenger);
//                } else if (answer.equals("yes")) {
//                    System.out.println("Enter the ride number to register in: ");
//                    for (int i = 0; i < suitableRides.size(); i++) {
//                        System.out.println((i + 1) + " - " + suitableRides.get(i));
//                    }
//                    int rideIndex = scanner.nextInt();
//                    scanner.nextLine();
//                    if (rideIndex >= 1 && rideIndex <= suitableRides.size()) {
//                        Ride selectedRide = suitableRides.get(rideIndex - 1);
//                        RideDB.registerPassengerToRide(passenger, selectedRide);
//                    } else {
//                        System.out.println("❌ Invalid ride number.");
//                    }
//                    PassengerProfile(passenger);
//                }else {
//                    System.out.println("this is a wrong choice");
//                }
//
//            }
//
//        } else if (choice == 2) {
//            System.out.println("Enter a driver name: ");
//            String driverName = scanner.nextLine();
//
//            RidesDataBase rideDB = new RidesDataBase(passenger);
//            List<Ride> driverRides = rideDB.getRidesByDriver(driverName);
//
//            if (driverRides.isEmpty()) {
//                System.out.println("❌ No rides found for this driver.");
//                PassengerProfile(passenger);
//                return;
//            }
//
//            for (int i = 0; i < driverRides.size(); i++) {
//                System.out.println(i+1 + " - " + driverRides.get(i));
//            }
//
//            System.out.println("Do you want to register for one of these rides? (yes/no): ");
//            String answer = scanner.nextLine();
//
//            if (answer.equalsIgnoreCase("yes")) {
//                System.out.println("Enter the ride number you want to register for: ");
//                int rideIndex = scanner.nextInt();
//                scanner.nextLine();
//
//                if (rideIndex >= 0 && rideIndex < driverRides.size()) {
//                    Ride selectedRide = driverRides.get(rideIndex-1);
//                    rideDB.registerPassengerToRide(passenger, selectedRide);
//                } else {
//                    System.out.println("❌ Invalid ride index.");
//                }
//            } else if (!answer.equalsIgnoreCase("no")) {
//                System.out.println("❌ Invalid choice.");
//            }
//
//            PassengerProfile(passenger);
//        } else if (choice == 3) {
//            System.out.println("👋 Goodbye!");
//            return;
//        } else {
//            System.out.println("❌ This is not a valid choice.");
//        }
//    }
//}
