package ProjectConsole.DataBase;

import ProjectConsole.Users.Student;

import java.util.HashMap;
import java.util.Scanner;

public class UsersDataBase {
    private HashMap <String , Student> db = new HashMap<>();

    // AddUser => For Register
    public void adduser(String id , ProjectConsole.Users.Student student){
        if (db.containsKey(id)){
            System.out.println("the id already exist");
        }else {
            db.put(id,student);
            System.out.println("---register complete---\n");

        }
    }

    // Login => For Login
    public boolean Login(String enteredid,String enteredpass){
        boolean IsExist=false;
        if (db.containsKey(enteredid)){
            Student student=db.get(enteredid);
            if (student.getPassword().equals(enteredpass)){
                IsExist=true;
            }else {
                System.out.println("invalid password");
                IsExist=false;
            }
        }else {
            System.out.println("this id doesn't exist");
            IsExist=false;
        }

        if (IsExist){
            System.out.println("/!\\ --login success-- /!\\");
            return true;

        }else {
            System.out.println("login failed");
            return false;
        }
    }

    // Print all Students
    public void getuser(){
        for (Student student : db.values()){
            student.toString();
        }
    }

    // Get Student By ID For Change a Status To Student
    public Student GetUserByID(String id) {
        Student student=db.get(id);
        return student;
    }
}
