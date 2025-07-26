package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class ChooseRoleController {
    private Student student;
    @FXML
    private Button Driver;
    @FXML
    private Button Passenger;

    private UserDatabase userDB;
    private Stage currentStage;

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ChooseRoleController() {}

    public void ChooseRoleUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\ChooseRoleUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            ChooseRoleController controller = loader.getController();
            controller.setStudent(student);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            controller.setCurrentStage(stage);
            stage.setScene(scene);
            stage.setTitle("CampusGo Choose Role");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DriverRole() {
        String id = student.getId();
        userDB = new UserDatabase();
        Driver driver = userDB.getDriverById(id);
        Passenger passenger = userDB.getPassengerById(id);

        if (driver == null && passenger == null) {
            System.out.println("you are not registered as driver");
            ToDriverRole toDriverRole = new ToDriverRole();
            toDriverRole.setStudent(student);
            toDriverRole.ToDriverUI();
        } else {
            if (driver != null) {
                System.out.println("you are a driver");
                GoToDriverProfile(driver);
            } else if (passenger != null) {
                ToDriverRole toDriverRole = new ToDriverRole();
                toDriverRole.setStudent(student);
                toDriverRole.ToDriverUI();
            }
        }
    }

    public void PassengerRole() {
        String id = student.getId();
        userDB = new UserDatabase();
        Passenger passenger = userDB.getPassengerById(id);
        Driver driver = userDB.getDriverById(id);

        if (passenger == null && driver == null) {
            System.out.println("you are not registered as passenger");
            ToPassengerRole toPassengerRole = new ToPassengerRole();
            toPassengerRole.setStudent(student);
            toPassengerRole.ToPassengerRoleUI();
        } else {
            if (passenger != null) {
                System.out.println("you are a passenger");
                GoToPassengerProfile(passenger);
            } else if (driver != null) {
                Passenger newPassenger = new Passenger(driver.getMajor(), driver.getYear(), student);
                System.out.println("you were a driver and now also a passenger");
//                userDB.updateUser(newPassenger);
                userDB.addPassenger(newPassenger);
                GoToPassengerProfile(newPassenger);
            }
        }
    }

    public void GoToDriverProfile(Driver driver) {
        if (currentStage != null) {
            currentStage.close();
        }
        ProfileCotroller profileCotroller = new ProfileCotroller();
        profileCotroller.setDriver(driver);
        profileCotroller.setStudent(student);
        profileCotroller.DriverProfileUI();
    }

    public void GoToPassengerProfile(Passenger passenger) {
        if (currentStage != null) {
            currentStage.close();
        }
        ProfileCotroller profileCotroller = new ProfileCotroller();
        profileCotroller.setStudent(student);
        profileCotroller.setPassenger(passenger);
        profileCotroller.PassengerProfileUI();
    }

    public static void showNotice(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Notice");
        window.setMinWidth(300);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #1d1a8f;");

        Button closeButton = new Button("OK");
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
