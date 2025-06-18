package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.Student;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class LoginController {

    private UserDatabase userDB;

    public LoginController(UserDatabase db) {
        this.userDB = db;
    }
    public void Login(){

        ChooseRoleController ch=new ChooseRoleController(userDB);
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter id : ");
        String id= scanner.nextLine();
        System.out.println("enter password : ");
        String password= scanner.nextLine();
        if (login(id,password)==null){
            System.out.println("login failed");

        }else {
            System.out.println("login success.");
            System.out.println("what you want to be :\n 1- Driver \n 2-Passenger");
            int choice= scanner.nextInt();
            if (choice==1){
                ch.Driver(userDB.login(id,password));
            } else if (choice==2) {
                ch.Passenger(userDB.login(id,password));
            }else {
                System.out.println("that's not a choice");
            }
        }
    }
    public Student login(String id, String password) {
        return userDB.login(id, password);
    }

//    @FXML
//    private TextField IdField;
//
//    @FXML
//    private PasswordField PassField;
//    @FXML
//    private Button loginin;
//    public void LoginUI(ActionEvent event){
//        System.out.println("hello");
//        String Id = IdField.getText().trim();
//        String password = PassField.getText().trim();
//        if (db.Login(Id,password))// This will check if id exist and check if this password for This User
//        {
//            Student student=db.GetUserByID(Id);
//            chooserRoleController.ChRole(student);
//            System.out.println(Id);
//        }else {
//            db.getuser();
//            System.out.println("hello 2");
//        }
//
//    }
//    @FXML
//    private Button registerButton;
//    @FXML
//    private void OpenRegisterUI(ActionEvent event) {
//        try {
//            File fxmlFile =new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\RegisterUI.fxml");
//            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setTitle("CampusGo Register");
//            stage.setScene(new Scene(root));
//            stage.show();
//            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            currentStage.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
}