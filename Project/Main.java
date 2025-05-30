package Project;

import Project.Controllers.LoginController;
import Project.Controllers.RegisterController;
import Project.DataBase.UsersDataBase;
import Project.Utils.EmailCheck;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        UsersDataBase db=new UsersDataBase();
        //db => DataBase
        EmailCheck emailCheck=new EmailCheck();
        LoginController loginController = new LoginController();
        RegisterController registerController = new RegisterController(db);
        while (true){
            System.out.println("--- Welcome To CampusGo ---");
            System.out.println("Choose what you want : " +
                    "\n1- Register " +
                    "\n2- Login" +
                    "\n3- Exit ");
            int choice= scanner.nextInt();
            if (choice==1){
                registerController.Register();
            } else if (choice==2) {
                loginController.Login();
            } else if (choice==3) {
                System.out.println("Goodbye! see you later");
                break;
            }else {
                System.out.println("this isn't a option");
            }
        }
    }
}
