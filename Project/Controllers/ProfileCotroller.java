package Project.Controllers;

import Project.DataBase.RidesDataBase;
import Project.DataBase.UserDatabase;
import Project.Ride.Ride;
import Project.Users.Driver;
import Project.Users.Passenger;
import Project.Users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.File;
import java.io.IOException;
import java.util.*;
// This class will handle every driver/passenger operation and all those operations are connected with RideDataBase.
public class ProfileCotroller {
    private Stage currentStage;
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }
    // Driver UI
    @FXML
    private Button AddRide;
    @FXML
    private Button DeleteRide;
    @FXML
    private  ListView <Ride> RidesList;

    //Passenger UI
    @FXML
    private Button Search;
    @FXML
    private Button Reserve;
    @FXML
    private ListView<Ride>RegisteredRides;



    // Driver and Passenger UI
    @FXML
    private Label Name;
    @FXML
    private Label College;
    @FXML
    private Label Mail;
    @FXML
    private Label Location;
    @FXML
    private Button ChangeRole;
    @FXML
    private Button SignOut;
    @FXML
    private Button Out;

    Ride ride;
    private RidesDataBase RideDB;
    private Student student;
    private Driver driver;
    private Passenger passenger;
    private UserDatabase DB;


    public void setStudent(Student student) {
        this.student = student;
    }
    public void setDriver(Driver driver) {
        this.driver=driver;
    }
    public void setPassenger(Passenger passenger){
        this.passenger=passenger;
    }
    public ProfileCotroller(){}

    // Driver UI
    public void DriverProfileUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\DriverProfileUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            ProfileCotroller controller = loader.getController();
            controller.setStudent(student);
            controller.setDriver(driver);
            controller.initDriverProfile();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AddRideUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\DriverAddRide.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            AddRideController controller = loader.getController();
            controller.setDriver(driver);
            controller.setProfileController(this);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteRideUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\DeleteRideUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            DeleteRideController controller = loader.getController();
            controller.setDriver(driver);
            controller.setProfileController(this);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // PassengerUI
    public void PassengerProfileUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\PassengerProfileUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            ProfileCotroller controller = loader.getController();
            controller.setStudent(student);
            controller.setPassenger(passenger);
            controller.initPassengerProfile();
            controller.loadReservedPassengerRides();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            controller.setCurrentStage(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReserverUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\SearchUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            SearchController controller = loader.getController();
            controller.SetPassenger(passenger);
            controller.setProfileController(this);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SearchUI(){}







    //initialize UI
    public void loadDriverRidesToListView() {
        if (driver != null) {
            RidesDataBase rideDB = new RidesDataBase(driver);
            List<Ride> driverRides = rideDB.getRidesByDriver(driver.getName());
            ObservableList<Ride> observableRides = FXCollections.observableArrayList(driverRides);
            RidesList.setItems(observableRides);
            RidesList.setCellFactory(param -> new ListCell<Ride>() {
                @Override
                protected void updateItem(Ride ride, boolean empty) {
                    super.updateItem(ride, empty);
                    if (empty || ride == null) {
                        setText(null);
                    } else {
                        setText( ride.getLocation() +
                                " ➡ " + ride.getDestination() +
                                " 🕐 " + ride.getHour() +
                                " 📅 " + ride.getDateAndDay() +
                                " 👥 " + ride.getSeats() + " seats");
                    }
                }
            });
        }
    }

    public void loadReservedPassengerRides() {
        if (passenger != null) {
            RidesDataBase rideDB = new RidesDataBase(passenger);
            List<Ride> registeredRides = rideDB.getRegisteredRidesForPassenger(passenger);
            ObservableList<Ride> observableRides = FXCollections.observableArrayList(registeredRides);
            RegisteredRides.setItems(observableRides);
            RegisteredRides.setCellFactory(param -> new ListCell<Ride>() {
                @Override
                protected void updateItem(Ride ride, boolean empty) {
                    super.updateItem(ride, empty);
                    if (empty || ride == null) {
                        setText(null);
                    } else {
                        setText(
                                ride.getLocation() + " ➡ " + ride.getDestination() +
                                        " 🕐 " + ride.getHour() +
                                        " 📅 " + ride.getDateAndDay() +
                                        " 👥 " + ride.getSeats() + " seats"
                        );
                    }
                }
            });
        }
    }
    public void LoginUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\lLoginUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void initDriverProfile() {
        if (driver != null ) {
            Name.setText(driver.getName());
            College.setText(driver.getCollege());
            Mail.setText(driver.getEmail());
            Location.setText(driver.getLocation());
            loadDriverRidesToListView();
        }
    }
    public void initPassengerProfile(){
        if (passenger!=null){
            Name.setText(passenger.getName());
            College.setText(passenger.getCollege());
            Mail.setText(passenger.getEmail());
            Location.setText(passenger.getLocation());
        }
    }

}

