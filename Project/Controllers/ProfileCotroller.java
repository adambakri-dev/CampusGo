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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


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
    private Button RemoveRideButton;
    @FXML
    private Button Search;
    @FXML
    private Button Reserve;
    @FXML
    private ListView<Ride>RegisteredRides;
    @FXML
    private ListView<Ride> RecommendedRides;



    // Driver and Passenger UI
    @FXML
    private Label Name;
    @FXML
    private Label College;
    @FXML
    private Label Mail;
    @FXML
    private Label Location;


    private Student student;
    private Driver driver;
    private Passenger passenger;



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
            controller.setCurrentStage(stage);
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
            controller.TimeValidating();
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
            controller.loadRecommendedRides();
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


    //====================Passenger Operations================
    //delete ride the passenger registered to it before
    public void removeSelectedRide() {
        Ride selectedRide = RegisteredRides.getSelectionModel().getSelectedItem();
        if (selectedRide != null && passenger != null) {
            RidesDataBase rideDB = new RidesDataBase(passenger);
            rideDB.removePassengerFromRide(selectedRide, passenger);
            System.out.println("✅ Ride removed successfully from registered rides.");
            loadReservedPassengerRides();
            boolean isSuitable = selectedRide.getLocation().equalsIgnoreCase(passenger.getLocation()) &&
                    selectedRide.getDestination().equalsIgnoreCase(passenger.getCollege());

            if (isSuitable) {
                ObservableList<Ride> recommendedList = RecommendedRides.getItems();
                if (!recommendedList.contains(selectedRide)) {
                    recommendedList.add(selectedRide);
                    RecommendedRides.setItems(recommendedList);
                    System.out.println("✅ Ride added to recommended rides.");
                }
            }

        } else {
            System.out.println("❌ No ride selected or passenger is null.");
        }
    }
    // reserve a ride that he choose from recommended rides
    @FXML
    public void reserveSelectedRide() {
        Ride selectedRide = RecommendedRides.getSelectionModel().getSelectedItem();
        if (selectedRide != null && passenger != null) {
            RidesDataBase rideDB = new RidesDataBase(passenger);
            rideDB.reserveRideForPassenger(selectedRide, passenger);
            loadReservedPassengerRides();
            ObservableList<Ride> recommendedList = RecommendedRides.getItems();
            recommendedList.remove(selectedRide);
            RecommendedRides.setItems(recommendedList);

            System.out.println("✅ Ride reserved and removed from recommended list.");
        } else {
            System.out.println("❌ No ride selected or passenger is null.");
        }
    }

    // this will load all reserved ride to registered rides list when the ui open
    public void loadReservedPassengerRides() {
        if (passenger != null) {
            RidesDataBase rideDB = new RidesDataBase(passenger);
            List<Ride> registeredRides = rideDB.getRegisteredRidesForPassenger(passenger);
            List<Ride> filteredRides = filterFutureRides(registeredRides);
            ObservableList<Ride> observableRides = FXCollections.observableArrayList(filteredRides);
            RegisteredRides.setItems(observableRides);
            RegisteredRides.setCellFactory(param -> new ListCell<Ride>() {
                @Override
                protected void updateItem(Ride ride, boolean empty) {
                    super.updateItem(ride, empty);
                    if (empty || ride == null) {
                        setText(null);
                    } else {
                        setText(ride.getLocation() + " ➡ " + ride.getDestination() +
                                " 🕐 " + ride.getHour() +
                                " 📅 " + ride.getDateAndDay() +
                                " 👥 " + ride.getSeats()  + " seats");
                    }
                }
            });
        }
    }
    // this will load all reserved ride to recommended rides list when the ui open
    public void loadRecommendedRides() {
        if (passenger != null) {
            RidesDataBase rideDB = new RidesDataBase(passenger);
            List<Ride> recommendedRides = rideDB.getRecommendedRides(passenger);
            List<Ride> filteredRides = filterFutureRides(recommendedRides);
            ObservableList<Ride> observableRides = FXCollections.observableArrayList(filteredRides);
            RecommendedRides.setItems(observableRides);
            RecommendedRides.setCellFactory(param -> new ListCell<Ride>() {
                @Override
                protected void updateItem(Ride ride, boolean empty) {
                    super.updateItem(ride, empty);
                    if (empty || ride == null) {
                        setText(null);
                    } else {
                        setText(ride.getLocation() + " ➡ " + ride.getDestination() +
                                " 🕐 " + ride.getHour() +
                                " 📅 " + ride.getDateAndDay() +
                                " 👥 " + ride.getSeats() + " seats");
                    }
                }
            });
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
    //=====================Driver Operation==================
    public void loadDriverRidesToListView() {
        if (driver != null) {
            RidesDataBase rideDB = new RidesDataBase(driver);
            List<Ride> driverRides = rideDB.getRidesByDriver(driver.getId());
            List<Ride> filteredRides = filterFutureRides(driverRides);
            ObservableList<Ride> observableRides = FXCollections.observableArrayList(filteredRides);
            RidesList.setItems(observableRides);
            RidesList.setCellFactory(param -> new ListCell<Ride>() {
                @Override
                protected void updateItem(Ride ride, boolean empty) {
                    super.updateItem(ride, empty);
                    if (empty || ride == null) {
                        setText(null);
                    } else {
                        setText(ride.getLocation() +
                                " ➡ " + ride.getDestination() +
                                " 🕐 " + ride.getHour() +
                                " 📅 " + ride.getDateAndDay() +
                                " 👥 " + ride.getSeats() + " seats");
                    }
                }
            });
        }
    }
    @FXML
    private void handleDeleteRide() {
        RidesDataBase rideDB=new RidesDataBase(driver);
        Ride selectedRide = RidesList.getSelectionModel().getSelectedItem();
        if (selectedRide != null) {
            int index = RidesList.getSelectionModel().getSelectedIndex();
            rideDB.deleteMyRide(driver.getId(), index);
            loadDriverRidesToListView();
            System.out.println("✅ Ride has been deleted successfully.");
        } else {
            System.out.println("❌ Please select a ride to delete.");
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


    //==========================================================================================
    private List<Ride> filterFutureRides(List<Ride> rides) {
        List<Ride> filtered = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Ride ride : rides) {
            try {
                String[] dateParts = ride.getDateAndDay().split(" ");
                if (dateParts.length < 2) continue;
                LocalDate rideDate = LocalDate.parse(dateParts[1]);
                LocalTime rideTime = LocalTime.parse(ride.getHour(), timeFormatter);
                if (rideDate.isAfter(today) || (rideDate.isEqual(today) && rideTime.isAfter(now))) {
                    filtered.add(ride);
                }
            } catch (Exception e) {
                System.out.println("Error parsing date/time for ride: " + e.getMessage());
            }
        }
        return filtered;
    }
}