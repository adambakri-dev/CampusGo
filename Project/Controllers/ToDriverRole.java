package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
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
    public ToDriverRole(){}
    public void setStudent(Student student) {
        this.student = student;
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
            stage.setScene(scene);
            stage.setTitle("To Driver");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BecomeDriver(){
        String seats=Seats.getText().trim();
        int seat=Integer.parseInt(seats);
        String major=Major.getText().trim();
        String year=Years.getText().trim();
        String carmodel=CarModel.getText().trim();
        if (seat==0&&major.isEmpty()&&year.isEmpty()&&carmodel.isEmpty()){
            System.out.println("become a driver failed");
        }else {
            Driver driver = new Driver(seat, carmodel, student, major, year);
            this.userDB=new UserDatabase();
            userDB.updateUser(driver);
            System.out.println("become a driver success");
        }

    }
    public void BecomePassenger(){}

}
