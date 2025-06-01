package Project.Controllers;

import Project.DataBase.UsersDataBase;
import Project.Users.Student;
import Project.Utils.ID_Check;
import Project.Utils.Name_Check;
import Project.Utils.Pass_Check;

import java.util.Scanner;

public class RegisterController {
    UsersDataBase db;
    public RegisterController(){}

    public RegisterController(UsersDataBase db) {
        this.db = db;
    }

    public void Register()//This function will take an info about new user to save it in DataBase
    {
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
        // All this if are temporary just to know if i had a wrong with login
        if (ID_Check.IsValid(id)) {
            if (Name_Check.IsValidname(name)){
                if (Pass_Check.IsValid(password)){
                    // This will add a new student and add data to constructor in class Student
                    Student newstudent = new Student(id, name, password,email,college,gender);
                    db.adduser(id, newstudent);//This will add a new student for database this in class (UsersDataBase)
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