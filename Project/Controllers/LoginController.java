package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class LoginController {
    // this class will take information and data from user to check and make him sign in and after that he will go to ChooseRoleController or other according to his status in database.
    private UserDatabase userDB;//This is a CSV DataBase that conatain all information about all signed-in users.

    public LoginController(UserDatabase db) {
        this.userDB = db;
    }
    public LoginController(){}

    // UI Functions
    @FXML
    private Button loginin;
    @FXML
    public void LoginUI(ActionEvent event){
        System.out.println("hello");
    }

    public void Login() {
        Student student = new Student();
        ChooseRoleController ch = new ChooseRoleController(userDB);
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter id : ");
        String id = scanner.nextLine();
        System.out.println("enter password : ");
        String password = scanner.nextLine();
        if (login(id, password) == null) {
            System.out.println("login failed");// if he not signed in will return a student with null that mean he haven't account or he enter something wrong.
        } else { // if he in a database like a student not as a driver or student he will make him to choose and move them to ChooseRoleController
            System.out.println("login success.");
            System.out.println("what you want to be :\n 1- Driver \n 2-Passenger \n 3-Return");
            int choice = scanner.nextInt();
            if (choice == 1) {
                ch.Driver(userDB.login(id, password));
            } else if (choice == 2) {
                ch.Passenger(userDB.login(id, password));
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("this is not a choice.");
            }
        }
    }

    //this will check if he did login before or not and will handle with it according to a situation.
    public Student login(String id, String password) {
        return userDB.login(id, password);
    }



}