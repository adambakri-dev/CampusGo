package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.Driver;
import Project.Users.Passenger;
import Project.Users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ToPassengerRole {
    @FXML
    private TextField Years;
    @FXML
    private TextField Major;
    private Student student;
    private UserDatabase userDB;

    private Stage currentStage;
    public ToPassengerRole(){}
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }
    public void ToPassengerRoleUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\ToPassenger.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            ToPassengerRole controller = loader.getController();
            controller.setStudent(student);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            controller.setCurrentStage(stage);
            stage.setScene(scene);
            stage.setTitle("To Driver");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BecomePassenger(){
        this.userDB=new UserDatabase();
        String major=Major.getText().trim();
        String year=Years.getText().trim();

        if (major.isEmpty()&&year.isEmpty()){
            System.out.println("become passenger failed");
        }else {
            Passenger passenger=new Passenger(major,year,student);
            this.userDB=new UserDatabase();
            userDB.addPassenger(passenger);
//            userDB.updateUser(passenger);
            System.out.println("become a passenger success");
            ProfileCotroller profileCotroller=new ProfileCotroller();
            profileCotroller.setPassenger(passenger);
            profileCotroller.setStudent(student);
            profileCotroller.PassengerProfileUI();
            currentStage.close();
        }
    }
}
