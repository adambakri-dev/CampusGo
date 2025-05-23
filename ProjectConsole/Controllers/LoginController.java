package ProjectConsole.Controllers;

import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class LoginController {
    private Button RegisterButton;
    private UsersDataBase db;
    ChooserRoleController chooserRoleController=new ChooserRoleController(db);
    public LoginController(UsersDataBase db){
        this.db=db;
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
    public void showWelcomeWindow() {
        System.out.println("hello from button");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProjectConsole/UI/RegisterUI.fxml"));
            // استبدل "/path/to/your/fxml/Welcome.fxml" بالمسار الصحيح لملف الـFXML داخل resources

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
