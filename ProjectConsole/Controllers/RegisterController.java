package ProjectConsole.Controllers;

import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Users.Student;
import ProjectConsole.Utils.ID_Check;
import ProjectConsole.Utils.Name_Check;
import ProjectConsole.Utils.Pass_Check;

import java.util.Scanner;

public class RegisterController {
    UsersDataBase db;

    public RegisterController(UsersDataBase db) {
        this.db = db;
    }

    public void Register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID : ");
        String id = scanner.nextLine();
        System.out.println("enter your name : ");
        String name = scanner.nextLine();
        System.out.println("enter your password : ");
        String password = scanner.nextLine();
        System.out.println("enter your email : ");
        String email= scanner.nextLine();
        System.out.println("enter your college : ");
        String college= scanner.nextLine();
        System.out.println("enter your gender : ");
        String gender= scanner.nextLine();
        if (ID_Check.IsValid(id)) {
            if (Name_Check.IsValidname(name)){
                if (Pass_Check.IsValid(password)){
                    Student newstudent = new Student(id, name, password,email,college,gender);
                    db.adduser(id, newstudent);
                }else {
                    System.out.println("invalid password");
                }
            }else {
                System.out.println("invalid name");
            }
        } else {
            System.out.println("invalid id");
        }
    }
}