package Project.Controllers;

import Project.DataBase.RidesDataBase;
import Project.Ride.Ride;
import Project.Users.Passenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private Passenger passenger;
    private ProfileCotroller profileController;
    private RidesDataBase rideDB;

    public SearchController() {}

    public void SetPassenger(Passenger passenger) {
        this.passenger = passenger;
        this.rideDB = new RidesDataBase(passenger);
        initPassengerInfo();
    }

    public void setProfileController(ProfileCotroller profileController) {
        this.profileController = profileController;
    }

    @FXML
    private Button Reserve;
    @FXML
    private Button Search;
    @FXML
    private ListView<Ride> RideList;
    @FXML
    private Spinner<String> HourPicker;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField LocationField;
    @FXML
    private TextField CollegeField;
    @FXML
    private Label Name;
    @FXML
    private Label College;
    @FXML
    private Label Mail;
    @FXML
    private Label Location;


    @FXML
    private void SearchRides() {
        String location = LocationField.getText().trim();
        String destination = CollegeField.getText().trim();
        String hour = HourPicker.getValue();  // Spinner returns selected value
        LocalDate selectedDate = DatePicker.getValue();

        if (location.isEmpty() || destination.isEmpty() || hour == null || selectedDate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("please enter a location and destination");
            alert.show();
            return;
        }

        String dayName = selectedDate.getDayOfWeek().toString().substring(0, 1).toUpperCase()
                + selectedDate.getDayOfWeek().toString().substring(1).toLowerCase();
        String dateAndDay = dayName + " " + selectedDate.toString();

        List<Ride> matchedRides = rideDB.searchRides(location, destination, hour, dateAndDay);

        if (matchedRides.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Rides Found");
            alert.setHeaderText(null);
            alert.setContentText("there is not suitable rides");
            alert.show();
            return;
        }

        ObservableList<Ride> observableRides = FXCollections.observableArrayList(matchedRides);
        RideList.setItems(observableRides);
        RideList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Ride ride, boolean empty) {
                super.updateItem(ride, empty);
                if (empty || ride == null) {
                    setText(null);
                } else {
                    setText(ride.getLocation() + " ➡ " + ride.getDestination() +
                            " 🕐 " + ride.getHour() + " 📅 " + ride.getDateAndDay() );
                }
            }
        });
    }

    public void reserveSelectedRide() {
        Ride selectedRide = RideList.getSelectionModel().getSelectedItem();

        if (selectedRide != null && passenger != null) {
            // احجز الرحلة في ملف CSV
            RidesDataBase rideDB = new RidesDataBase(passenger);
            rideDB.reserveRideForPassenger(selectedRide, passenger);

            // تحديث قائمة الرحلات المحجوزة
            profileController.loadReservedPassengerRides();
            // تحديث قائمة الرحلات المقترحة بحذف الرحلة المحجوزة
            ObservableList<Ride> RidesList = RideList.getItems();
            RidesList.remove(selectedRide);
            RideList.setItems(RidesList);

            System.out.println("✅ Ride reserved and removed from recommended list.");
        } else {
            System.out.println("❌ No ride selected or passenger is null.");
        }
    }

    private void initPassengerInfo() {
        if (passenger != null) {
            Name.setText(passenger.getName());
            Mail.setText(passenger.getEmail());
            College.setText(passenger.getCollege());
            Location.setText(passenger.getLocation());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList <String> times = FXCollections.observableArrayList();
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
        });
    }
}
