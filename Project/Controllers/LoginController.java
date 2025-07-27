package Project.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Project.DataBase.UserDatabase;
import Project.Users.Student;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
    // this class will take information and data from user to check and make him sign in and after that he will go to ChooseRoleController or other according to his status in database.
    private UserDatabase userDB;//This is a CSV DataBase that contain all information about all signed-in users.

    public LoginController(UserDatabase db) {
        this.userDB = db;
    }
    public LoginController(){}
    @FXML
    private ListView <String> Rides;
    @FXML
    private Button loginin;
    @FXML
    private PasswordField PassField;
    @FXML
    private TextField IdField;

    public void Login() {
        // take the info from UI
        String userId = IdField.getText().trim();
        String password = PassField.getText().trim();
        // check that user enter data in ui
        if (userId.isEmpty() || password.isEmpty()) {
            showNotice("Please fill in both fields.");
            return;
        }
        //this will check if he did login before or not
        Student student = LoginChecker(userId, password);
        if (student == null) {
            showNotice("Login Failed: Incorrect ID or password.");
        } else {
            // this will take a stage from the scene and transfer every Stage to all classes in the code to close them when we need
            Stage currentStage = (Stage) loginin.getScene().getWindow();
            GoToChooseRole(currentStage, student);
        }
    }

    //this will check if he did login before or not and will handle with it according to a situation.
    public Student LoginChecker(String id, String password ) {
        this.userDB=new UserDatabase();
        // this function will check if this id is in data base and will check if the password he enter is his password
        return userDB.login(id, password);
    }

    @FXML
    private Button registerButton;
    // if the user press in register button he will move to Register UI
    public void GoToRegister(ActionEvent event){
       RegisterController registerController=new RegisterController();
       registerController.RegisterUI(event);
    }

    //if he finish login successfully he will go ToChooseRole UI
    public void GoToChooseRole(Stage currentstage ,Student student){
        currentstage.close();
        ChooseRoleController chooseRoleController=new ChooseRoleController();
        chooseRoleController.setStudent(student);
        chooseRoleController.ChooseRoleUI();
    }

    public static void showNotice(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Notice");
        window.setMinWidth(300);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #1d1a8f;");

        Button closeButton = new Button("OK" );
        closeButton.setStyle("-fx-background-color: #ffbb00; -fx-text-fill: black; -fx-background-radius: 10;");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f8f8ff; -fx-alignment: center;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}