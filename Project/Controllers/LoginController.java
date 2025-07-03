package Project.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Project.DataBase.UserDatabase;
import Project.Users.Student;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {
    // this class will take information and data from user to check and make him sign in and after that he will go to ChooseRoleController or other according to his status in database.
    private UserDatabase userDB;//This is a CSV DataBase that contain all information about all signed-in users.

    public LoginController(UserDatabase db) {
        this.userDB = db;
    }
    public LoginController(){}

    @FXML
    private Button loginin;
    @FXML
    private PasswordField PassField;
    @FXML
    private TextField IdField;

    public void Login() {

        String userId = IdField.getText().trim();
        String password = PassField.getText().trim();
        if (userId.isEmpty() || password.isEmpty()) {
            System.out.println("the fields are empty");
        }
        if (login(userId,password)==null){
            System.out.println("login failed");
        }else {
            System.out.println("Login Success");
            GoToChooseRole();
        }
    }

    //this will check if he did login before or not and will handle with it according to a situation.
    public Student login(String id, String password) {
        this.userDB=new UserDatabase();
        return userDB.login(id, password);
    }

    @FXML
    private Button registerButton;
    public void GoToRegister(ActionEvent event){
       RegisterController registerController=new RegisterController();
       registerController.RegisterUI(event);
    }

    public void GoToChooseRole(){
        ChooseRoleController chooseRoleController=new ChooseRoleController();
        chooseRoleController.ChooseRoleUI();
    }
}