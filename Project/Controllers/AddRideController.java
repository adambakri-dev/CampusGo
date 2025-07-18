package Project.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Project.DataBase.RidesDataBase;
import Project.Ride.Ride;
import Project.Users.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
                if (date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #90ee90;");
                }

                if (date.equals(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #add8e6;");
                }
            }
        });
    }
    public void AddRide() {
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

        // تحقق إذا رحلة بنفس الوقت والتاريخ موجودة بالفعل للسائق
        List<Ride> existingRides = rideDB.getRidesByDriver(driver.getId());
        for (Ride ride : existingRides) {
            if (ride.getDateAndDay().equalsIgnoreCase(dateAndDay) && ride.getHour().equalsIgnoreCase(selectedHour)) {
                showNotice("you already have a ride with this date&time");
                return;
            }
        }

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

