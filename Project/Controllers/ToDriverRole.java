package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import Project.Users.*;

public class ToDriverRole {
    @FXML
    private javafx.scene.control.TextField Seats;
    @FXML
    private javafx.scene.control.TextField Major;
    @FXML
    private javafx.scene.control.TextField CarModel;
    @FXML
    private javafx.scene.control.TextField Years;

    private UserDatabase userDB;
    private Student student;
    private Stage currentStage;
    public ToDriverRole(){}
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    public void ToDriverUI(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\ToDriver.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            ToDriverRole controller = loader.getController();
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

    // this will make the user go to Driver Role
    public void BecomeDriver(){
        // here will get all data from UI
        String seats=Seats.getText().trim();
        int seat=Integer.parseInt(seats);
        String major=Major.getText().trim();
        String year=Years.getText().trim();
        String carmodel=CarModel.getText().trim();
        // if all data are not like we need become a driver wioll be failed
        if (seat==0&&major.isEmpty()&&year.isEmpty()&&carmodel.isEmpty()){
            System.out.println("become a driver failed");
        }else {
            // maka a driver from the data we get
            Driver driver = new Driver(seat, carmodel, student, major, year);
            this.userDB=new UserDatabase();
            // this will add a driver to data base and the CSV file for drivers
            userDB.addDriver(driver);
            System.out.println("become a driver success");
            // after becoming a driver will transfer the user to his Profile.
            ProfileCotroller profileCotroller=new ProfileCotroller();
            profileCotroller.setStudent(student);
            profileCotroller.setDriver(driver);
            profileCotroller.DriverProfileUI();
            currentStage.close();
        }
    }
}
