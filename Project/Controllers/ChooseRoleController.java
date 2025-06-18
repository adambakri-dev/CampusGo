package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.*;

import java.util.Scanner;

public class ChooseRoleController {
    private UserDatabase userDB;

    public ChooseRoleController(UserDatabase db) {
        this.userDB = db;
    }
    public void Driver(Student student){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter number of seats : " );
        int seats= scanner.nextInt();
        System.out.println("enter a car model : ");
        String carmodel= scanner.nextLine();
        scanner.nextLine();
        System.out.println("enter your  location : ");
        String location = scanner.nextLine();
        System.out.println("enter your major : ");
        String major= scanner.nextLine();
        System.out.println("enter in what year you are in college ; ");
        String year= scanner.nextLine();
        becomeDriver(student,seats,carmodel,location,major,year);
    }
    public boolean becomeDriver(Student student, int seats, String carModel, String location, String major, String year) {
        Driver driver = new Driver(seats, carModel, location, student, major, year);
//        driver.setIncome(0);
//        driver.setCountRides(0);
        ProfileCotroller DriverProfile=new ProfileCotroller(userDB,student);
        DriverProfile.DriverProfile(driver);
        return userDB.updateUser(driver);

    }
    public void Passenger(Student student){
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter your  location : ");
        String location = scanner.nextLine();
        System.out.println("enter your major : ");
        String major= scanner.nextLine();
        System.out.println("enter you college : ");
        String college= scanner.nextLine();
        System.out.println("enter in what year you are in college ; ");
        String year= scanner.nextLine();
        becomePassenger(student,location,major,year,college);
    }
    public boolean becomePassenger(Student student, String location, String major, String year,String college) {
        Passenger passenger = new Passenger(location, major, year, student,college);
        ProfileCotroller PassengerProfile=new ProfileCotroller(userDB,student);
        PassengerProfile.PassengerProfile(passenger);
        return userDB.updateUser(passenger);
    }
}
