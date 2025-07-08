package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ChooseRoleController {
    private Student student;
    @FXML
    private TextField Seats;
    @FXML
    private TextField CarModel;
    @FXML
    private TextField Major;
    @FXML
    private TextField Years;
    @FXML
    private Button Driver;
    @FXML
    private Button Passenger;
    // This Class It is responsible to transfer a Student to Driver or Passenger as he want.
    private UserDatabase userDB;//This is a CSV DataBase that conatain all information about all signed-in users.

    public ChooseRoleController(UserDatabase db) {
        this.userDB = db;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setUserDatabase(UserDatabase db) {
        this.userDB = db;
    }
    public ChooseRoleController() {}
    public void ChooseRoleUI(){
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
            stage.setScene(scene);
            stage.setTitle("CampusGo Choose Role");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void DriverRole(){
        String id=student.getId();
        userDB=new UserDatabase();
        Driver driver = userDB.getDriverById(id);
        if (driver==null){
            System.out.println("you are not a driver");
            ToDriverRole toDriverRole=new ToDriverRole();
            toDriverRole.setStudent(student);
            toDriverRole.ToDriverUI();
        }else {
            System.out.println("you are a driver");
            GoToDriverProfile(driver);
        }
    }

    public void PassengerRole(){
        String id=student.getId();
        userDB=new UserDatabase();
        Passenger passenger=userDB.getPassengerById(id);
        if (passenger==null){
            System.out.println("you are not passenger");
        }else {
            System.out.println("you are passenger");
        }
    }


    public void GoToDriverProfile(Driver driver){

    }
    public void GoToPassengerProfile(){}

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
