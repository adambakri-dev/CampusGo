package Project.Controllers;

import Project.DataBase.UsersDataBase;
import Project.Users.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
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
    private void showWelcomeWindow(ActionEvent event) {
        System.out.println("HELLO WORLD");
    }

    private void OpenRegisterUI(ActionEvent event){

    }

}
