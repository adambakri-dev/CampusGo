package ProjectConsole;

import ProjectConsole.Controllers.ChooserRoleController;
import ProjectConsole.Controllers.LoginController;
import ProjectConsole.Controllers.RegisterController;
import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Users.Driver;
import ProjectConsole.Utils.EmailCheck;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        UsersDataBase db=new UsersDataBase();
        //db => DataBase
        EmailCheck emailCheck=new EmailCheck();
        LoginController loginController = new LoginController(db);
        RegisterController registerController = new RegisterController(db);
        ChooserRoleController chooserRoleController=new ChooserRoleController(db);
        Driver driver;
        while (true){
            System.out.println("--- Welcome To CampusGo ---");
            System.out.println("Choose what you want : " +
                    "\n1- Register " +
                    "\n2- Login" +
                    "\n3- Exit");
            int choice= scanner.nextInt();
            if (choice==1){
                registerController.Register();
            } else if (choice==2) {
                loginController.Login();
            } else if (choice==3) {
                System.out.println("Goodbye!");
                break;
            }else {
                System.out.println("this isn't a option");
            }
        }
    }
}
