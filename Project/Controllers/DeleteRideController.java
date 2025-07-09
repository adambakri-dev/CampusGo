package Project.Controllers;

import Project.DataBase.RidesDataBase;
import Project.Ride.Ride;
import Project.Users.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class DeleteRideController implements Initializable {
    private Driver driver;
    private RidesDataBase rideDB;
    private ProfileCotroller profileController;

    @FXML
    private Button DeleteRide;
    @FXML
    private Button Return;
    @FXML
    private ListView<Ride> RidesListDelete;
    @FXML
    private Label Name;
    @FXML
    private Label College;
    @FXML
    private Label Mail;
    @FXML
    private Label Location;

    public DeleteRideController() {}

    public void setDriver(Driver driver) {
        this.driver = driver;
        this.rideDB = new RidesDataBase(driver);
        initDriverInfo();
        loadDriverRidesToListView();
    }

    public void setProfileController(ProfileCotroller profileController) {
        this.profileController = profileController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (driver != null) {
            loadDriverRidesToListView();
        }
    }
    public void loadDriverRidesToListView() {
        if (driver != null) {
            RidesDataBase rideDB = new RidesDataBase(driver); // قاعدة بيانات خاصة بالسائق
            List<Ride> driverRides = rideDB.getRidesByDriver(driver.getName());

            ObservableList<Ride> observableRides = FXCollections.observableArrayList(driverRides);
            RidesListDelete.setItems(observableRides);
            RidesListDelete.setCellFactory(param -> new ListCell<Ride>() {
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

    @FXML
    private void handleDeleteRide() {
        Ride selectedRide = RidesListDelete.getSelectionModel().getSelectedItem();
        if (selectedRide != null) {
            int index = RidesListDelete.getSelectionModel().getSelectedIndex();
            rideDB.deleteMyRide(driver.getName(), index);
            loadDriverRidesToListView();
            if (profileController != null) {
                profileController.loadDriverRidesToListView();
            }
            System.out.println("✅ Ride has been deleted successfully.");
        } else {
            System.out.println("❌ Please select a ride to delete.");
        }
    }

    @FXML
    private void handleReturn() {
        Return.getScene().getWindow().hide();
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
