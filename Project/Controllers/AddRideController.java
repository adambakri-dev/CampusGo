package Project.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Project.DataBase.RidesDataBase;
import Project.Users.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.util.*;

public class AddRideController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> times = FXCollections.observableArrayList();
        for (int h = 8; h <= 21; h++) {
            for (int m = 0; m < 60; m += 15) {
                if (h == 21 && m > 0) break;
                String time = String.format("%02d:%02d", h, m);
                times.add(time);
            }
        }
        HourPicker.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(times));
        DatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        }
        );
    }
    public void AddRide(){
        String selectedHour = HourPicker.getValue();
        LocalDate selectedDate = DatePicker.getValue();
        if (selectedHour == null || selectedDate == null) {
            System.out.println("choose a date&time");
            return;
        }
        String dayName = selectedDate.getDayOfWeek().toString().substring(0, 1).toUpperCase() +
                selectedDate.getDayOfWeek().toString().substring(1).toLowerCase();
        String dateAndDay = dayName + " " + selectedDate.toString();

        int seats = driver.getSeats();
        String location = driver.getLocation();
        String destination = driver.getCollege();
        List<String> passengers = new ArrayList<>();

        rideDB.addRide(seats, driver, location, destination, selectedHour, dateAndDay);
        System.out.println("✅ Ride Added!");
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

}

