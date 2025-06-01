package Project.Controllers;

import Project.DataBase.UsersDataBase;
import Project.Users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.*;
import javafx.stage.Stage;
import java.io.File;
import java.util.Scanner;

public class LoginController {
    private UsersDataBase db;
    ChooserRoleController chooserRoleController=new ChooserRoleController(db);
    public LoginController(){

    }
    public void Login(){//=> this function for login
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter the id : ");
        String enteredid= scanner.nextLine();
        System.out.println("enter the password : ");
        String enteredpass= scanner.nextLine();
        if (db.Login(enteredid,enteredpass))// This will check if id exist and check if this password for This User
        {
            Student student=db.GetUserByID(enteredid);
            chooserRoleController.ChRole(student);
        }
    }

    @FXML
     private Button loginin;

    @FXML
    private Button registerButton;

    @FXML
    private void RegisterUI(ActionEvent event) {
        System.out.println("hello");
        try {
            File fxmlFile =new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\RegisterUI.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("CampusGo Register");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void OpenRegisterUI(ActionEvent event){

    }

}
