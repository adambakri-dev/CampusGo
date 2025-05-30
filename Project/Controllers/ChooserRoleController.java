package Project.Controllers;
import Project.DataBase.UsersDataBase;
import Project.Users.Student;
import java.util.Scanner;
//This Class Used For Choose The Role That a User Want To Be.
public class ChooserRoleController {
    private UsersDataBase db;
    public ChooserRoleController(UsersDataBase db){
        this.db=db;
    }
    public void ChRole(Student student){
        ProfileCotroller profileCotroller=new ProfileCotroller(db , student);
        Scanner scanner=new Scanner(System.in);
        System.out.println("------------------------ \nchoose role the that you want : \n 1- Driver \n 2- Passenger \n 3- Exit \n --------------------------");
        int choice= scanner.nextInt();
        if (choice==1){
            System.out.println("you are now a Driver");
            student.SetIsDriver(true);
            profileCotroller.DriverProfile();//=> this turn the function for do operators as a Driver
        } else if (choice==2) {
            System.out.println("you are now a Passenger");
            student.SetIsPassenger(true);
            profileCotroller.PassengerProfile();//=> this turn the function for do operators as a Passenger
        } else if (choice==3) {
            return;
        } else {
            System.out.println("this is a wrong choice");
        }
    }

}
