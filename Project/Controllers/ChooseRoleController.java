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
    // from this we will get the window to close it when we finish from it
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }
    // this will take a student from login to take his info and check them in User Data Base
    public void setStudent(Student student) {
        this.student = student;
    }
    public ChooseRoleController() {}

    // this is UI for Choose Role
    public void ChooseRoleUI() {
        try {
            // this is a Variable in JavaFx to save in it a UI we made
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\ChooseRoleUI.fxml")
                            .toURI().toURL()
            );
            //URI : this will define this File
            //URL : will translate this file to any way I want

            //Parent : this variable we can save on it all the type of elements in javafx and will take them from file
            Parent root = loader.load();
            ChooseRoleController controller = loader.getController();
            controller.setStudent(student);
            // this is the scene and we save on it all the elements in root
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            controller.setCurrentStage(stage);
            // Stage : is the window that the user will see and we will put on it the scene that have all elements we save them in root
            stage.setScene(scene);
            stage.setTitle("CampusGo Choose Role");
            // this method will show us the window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*there is two roles Driver/Passenger and we will take the info of user and after that he will choose the role he want
     and we will check in User Data Base if he suitable to be Driver/Passenger
    */

    // this function is the responsible to user to choose a Driver
    public void DriverRole() {
        // this will get a ID of student
        String id = student.getId();
        userDB = new UserDatabase();
        //now we got the info of this student if he is a driver or passenger
        Driver driver = userDB.getDriverById(id);
        Passenger passenger = userDB.getPassengerById(id);
        //this mean that he wasn't a driver or passenger before
        if (driver == null && passenger == null) {
            // now the user will moved to Driver Role
            ToDriverRole toDriverRole = new ToDriverRole();
            toDriverRole.setStudent(student);
            toDriverRole.ToDriverUI();
        } else {
            // this mean that he was a driver before
            if (driver != null) {
                // so he will go to his profile directly
                GoToDriverProfile(driver);
            } else if (passenger != null) {
                // if he was a passenger and want to be a driver will move user to To Driver Role
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
            ToPassengerRole toPassengerRole = new ToPassengerRole();
            toPassengerRole.setStudent(student);
            toPassengerRole.ToPassengerRoleUI();
        } else {
            if (passenger != null) {
                GoToPassengerProfile(passenger);
            } else if (driver != null) {
                Passenger newPassenger = new Passenger(driver.getMajor(), driver.getYear(), student);
                userDB.addPassenger(newPassenger);
                GoToPassengerProfile(newPassenger);
            }
        }
    }

    public void GoToDriverProfile(Driver driver) {
        // this will take a stage and close it after we finished from it
        if (currentStage != null) {
            currentStage.close();
        }
        ProfileCotroller profileCotroller = new ProfileCotroller();
        // and will transfer the user and student to this class
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
