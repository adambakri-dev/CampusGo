package Project.Controllers;

import Project.DataBase.UserDatabase;
import Project.Users.Student;

import java.util.Scanner;

public class RegisterController {

    public RegisterController(){}

    private UserDatabase userDB;

    public RegisterController(UserDatabase db) {
        this.userDB = db;
    }
    public boolean register(String id, String name, String password, String email, String college, String gender) {
        if (userDB.userExists(id)) {
            return false; // المستخدم موجود
        }
        Student newStudent = new Student(id, name, password, email, college, gender);
        return userDB.register(newStudent);
    }

    //====================================================================================
    //this is a comsole part
    public void Register()//This function will take an info about new user to save it in DataBase
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID : ");
        String id = scanner.nextLine();
        if (userDB.userExists(id)){
            System.out.println("this id is used");
            return;
        }
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
        if (register(id,name,password,email,college,gender)){
            System.out.println("register completed");
        }else {
            System.out.println("register failed");
        }
    }


//    //===================================================================================================
//    //this is a ui part
//    @FXML
//    private TextField ID;
//    @FXML
//    private TextField Password;
//    @FXML
//    private TextField Username;
//    @FXML
//    private TextField mail;
//    @FXML
//    private TextField code;
//    @FXML
//    private TextField phone;
//    public void RegisterUI(ActionEvent event){
//        String id=ID.getText().trim();
//        String password=Password.getText().trim();
//        String username=Username.getText().trim();
//        String Mail=mail.getText().trim();
//        String Code=code.getText().trim();
//        String Phone=phone.getText().trim();
//        Student newstudent = new Student(id, username, password,Mail,"Azrieli","male");
//        db.adduser(id, newstudent);
//        System.out.println(db.GetUserByID(id));
//        System.out.println("register completed");
//        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        currentStage.close();
//        try {
//            FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\LoginUI.fxml").toURI().toURL());
//            Parent root = loader.load();
//            Stage loginStage = new Stage();
//            loginStage.setScene(new Scene(root));
//            loginStage.setTitle("CampusGo Login");
//            loginStage.show();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

}