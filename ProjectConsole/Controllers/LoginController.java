package ProjectConsole.Controllers;

import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Users.Student;

import java.util.Scanner;

public class LoginController {
    private UsersDataBase db;
    ChooserRoleController chooserRoleController=new ChooserRoleController(db);
    public LoginController(UsersDataBase db){
        this.db=db;
    }
    public void Login(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter the id : ");
        String enteredid= scanner.nextLine();
        System.out.println("enter the password : ");
        String enteredpass= scanner.nextLine();
        if (db.Login(enteredid,enteredpass)) {
            Student student=db.GetUserByID(enteredid);
            chooserRoleController.ChRole(student);
        }

    }
}
