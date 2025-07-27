package Project.Controllers;


import java.time.DayOfWeek;
import java.time.LocalDate;
import Project.DataBase.RidesDataBase;
import Project.Ride.Ride;
import Project.Users.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class AddRideController {
    private Driver driver;
    private RidesDataBase rideDB;
    private ProfileCotroller profileController;

    public void setProfileController(ProfileCotroller profileController) {
        this.profileController = profileController;
    }
    public AddRideController(){}
    public void setDriver(Driver driver){
        this.driver=driver;
        this.rideDB=new RidesDataBase(driver);
        initDriverInfo();
    }

    @FXML
    private Spinner<String> HourPicker;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private Button AddRideButton;
    @FXML
    private Label Name;
    @FXML
    private Label College;
    @FXML
    private Label Mail;
    @FXML
    private Label Location;
// this function will specialize the time to way I want
public void TimeValidating() {
    // this will make us put the hours in suitable way im Spinner <String> HourPicker
    ObservableList<String> times = FXCollections.observableArrayList();
    // this will make us just choose hours between 8Am-9PM
    for (int h = 8; h <= 21; h++) {
        // and this will make you just increase 15 minutes every time
        for (int m = 0; m < 60; m += 15) {
            if (h == 21 && m > 0)
                break;
            String time = String.format("%02d:%02d", h, m);
            //this list will save a times from 8:00 to 21:00 to show them in the spinner
            times.add(time);
            //the list will be like :
            // {8:00,8:15,......,20:54,21:00} amd in every time he will show me one of these index
        }
    }
    HourPicker.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(times));
    //To make a dates in calendar like we want like what the days we don't want them or something like this.
    DatePicker.setDayCellFactory(picker -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            //we call it to make sure that she will put every date in page we in on the best way without wrongs before we put a conditions
            super.updateItem(date, empty);
            if (date.isBefore(LocalDate.now())) {
                // if the date is in the past that will mke us can't choose this date
                setDisable(true);
                // and we will make every box in this color
                setStyle("-fx-background-color: #ffc0cb;");
            }
            if (date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
                // this will make us can't choose any saturday
                setDisable(true);
                // and we will make every box in this color
                setStyle("-fx-background-color: #90ee90;");
            }

            if (date.equals(LocalDate.now())) {
                // and there is condition that we can't choose a day we in
                setDisable(true);
                // and we will make every box in this color
                setStyle("-fx-background-color: #add8e6;");
            }
        }
    });
}
    // there is two options in every time you will make a ride :
    // 1- from location to college
    // 2- from college to location


    public void LocationToCollegeRide() {
        // this will take a hour and date we choose in UI
        String selectedHour = HourPicker.getValue();
        LocalDate selectedDate = DatePicker.getValue();
        // if we didn't choose any date will notification the user
        if (selectedHour == null || selectedDate == null) {
            showNotice("choose A Date ");
            return;
        }
        // in this block of code he will make a day and date in the way i will save them in CSV File.
        String dayName = selectedDate.getDayOfWeek().toString().substring(0, 1).toUpperCase() +
                selectedDate.getDayOfWeek().toString().substring(1).toLowerCase();
        String dateAndDay = dayName + " " + selectedDate.toString();
        // he will get the info from driver that I pass him to class
        int seats = driver.getSeats();
        String location = driver.getLocation();
        String destination = driver.getCollege();

        // this for-each will check if the user already have a ride in the same hour and date the user chose
        List<Ride> existingRides = rideDB.getRidesByDriver(driver.getId());
        for (Ride ride : existingRides) {
            if (ride.getDateAndDay().equalsIgnoreCase(dateAndDay) && ride.getHour().equalsIgnoreCase(selectedHour)) {
                showNotice("you already have a ride with this date&time");
                return;
            }
        }
        // if not he will add it to DataBase
        rideDB.addRide(seats, driver, location, destination, selectedHour, dateAndDay);
        showNotice("Ride Added Successfully");
        //and now he will update the list in Driver Profile UI
        if (profileController != null) {
            profileController.loadDriverRidesToListView();
        }
    }

    public void CollegeToLocationRide() {
        String selectedHour = HourPicker.getValue();
        LocalDate selectedDate = DatePicker.getValue();
        if (selectedHour == null || selectedDate == null) {
            showNotice("choose A Date ");
            return;
        }
        String dayName = selectedDate.getDayOfWeek().toString().substring(0, 1).toUpperCase() +
                selectedDate.getDayOfWeek().toString().substring(1).toLowerCase();
        String dateAndDay = dayName + " " + selectedDate.toString();

        int seats = driver.getSeats();
        String location = driver.getLocation();
        String destination = driver.getCollege();

        List<Ride> existingRides = rideDB.getRidesByDriver(driver.getId());
        for (Ride ride : existingRides) {
            if (ride.getDateAndDay().equalsIgnoreCase(dateAndDay) && ride.getHour().equalsIgnoreCase(selectedHour)) {
                showNotice("you already have a ride with this date&time");
                return;
            }
        }
        rideDB.addRide(seats, driver, destination, location, selectedHour, dateAndDay);
        showNotice("Ride Added Successfully");

        if (profileController != null) {
            profileController.loadDriverRidesToListView();
        }
    }


    private void initDriverInfo() {
        if (driver != null) {
            Name.setText(driver.getName());
            Mail.setText(driver.getEmail());
            College.setText(driver.getCollege());
            Location.setText(driver.getLocation());
        }
    }

    public static void showNotice(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Notice");
        window.setMinWidth(300);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #1d1a8f;");

        Button closeButton = new Button("OK" );
        closeButton.setStyle("-fx-background-color: #ffbb00; -fx-text-fill: black; -fx-background-radius: 10;");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f8f8ff; -fx-alignment: center;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}

