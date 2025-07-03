package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ChooseRoleController {
    // This Class It is responsible to transfer a Student to Driver or Passenger as he want.
    private UserDatabase userDB;//This is a CSV DataBase that conatain all information about all signed-in users.

    public ChooseRoleController(UserDatabase db) {
        this.userDB = db;
    }
    public ChooseRoleController(){}

    public void ChooseRoleUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\ChooseRoleUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("CampusGo Choose Role");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    //This Function Will Take A Information from Student To be A driver.
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
        becomeDriver(student,seats,carmodel,location,major,year);//And this function this will add a driver to Driver Class.
    }
    public boolean becomeDriver(Student student, int seats, String carModel, String location, String major, String year) {
        Driver driver = new Driver(seats, carModel, location, student, major, year);
        ProfileCotroller DriverProfile=new ProfileCotroller(userDB,driver);//this will make him enter the ProfileController as a Driver
        DriverProfile.DriverProfile(driver);//this is a function of his operations like a driver
        return userDB.updateUser(driver);//this will update a status of user from student to a driver in CSV file

    }

    //This Function Will Take A Information from Student To be A Passenger.
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
        becomePassenger(student,location,major,year,college);//And this function this will add a driver to Passenger Class.
    }
    public boolean becomePassenger(Student student, String location, String major, String year,String college) {
        Passenger passenger = new Passenger(location, major, year, student,college);
        ProfileCotroller PassengerProfile=new ProfileCotroller(userDB,passenger);//this will make him enter the ProfileController as a Passenger.
        PassengerProfile.PassengerProfile(passenger);//this is a function of his operations as a Passenger.
        return userDB.updateUser(passenger);//this will update a status of user from student to a Passenger in CSV file
    }
}
