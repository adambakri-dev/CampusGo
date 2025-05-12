package ProjectConsole.Controllers;

import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Users.Student;

import java.util.Scanner;

public class ChooserRoleController {
    private UsersDataBase db;
    public ChooserRoleController(UsersDataBase db){
        this.db=db;
    }
    //
    public void ChRole(Student student){
        ProfileCotroller profileCotroller=new ProfileCotroller(db , student);
        Scanner scanner=new Scanner(System.in);
        System.out.println("------------------------\nchoose role the that you want : \n 1- Driver \n 2- Passenger \n--------------------------");
        int choice= scanner.nextInt();
        if (choice==1){
            System.out.println("you are now a Driver");
            student.SetIsDriver(true);
            profileCotroller.DriverProfile();

        } else if (choice==2) {
            System.out.println("you are now a Passenger");
            student.SetIsPassenger(true);
            profileCotroller.PassengerProfile();
        }else {
            System.out.println("this is a wrong choice");
        }
    }

}
