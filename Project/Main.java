package Project;

import Project.Controllers.LoginController;
import Project.Controllers.RegisterController;
import Project.DataBase.UserDatabase;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDatabase db = new UserDatabase();  // this is a user data base with csv
        LoginController loginController = new LoginController(db);  // login controller that from it we will taka a information from user and check if it correct from data base
        RegisterController registerController = new RegisterController(db);// register controller that will add a new user and add it to database and will check if this user is already added

        while (true) {
            System.out.println("--- Welcome To CampusGo ---");
            System.out.println("Choose what you want : " +
                    "\n1- Register " +
                    "\n2- Login" +
                    "\n3- Exit ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {

            } else if (choice == 2) {
                loginController.Login();
            } else if (choice == 3) {
                System.out.println("Goodbye! see you later");
                break;
            } else {
                System.out.println("this isn't an option");
            }
        }
    }
}
