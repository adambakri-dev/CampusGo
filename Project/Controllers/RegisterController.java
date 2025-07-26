package Project.Controllers;
import Project.Utils.Pass_Check;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.PasswordField;
import Project.DataBase.UserDatabase;
import Project.Users.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.File;
import java.io.IOException;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;


public class RegisterController {

    public RegisterController() {}

    private UserDatabase userDB;
    @FXML
    private TextField ID;
    @FXML
    private TextField Username;
    @FXML
    private TextField College;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField mail;
    @FXML
    private TextField code;
    @FXML
    private TextField Location;
    @FXML
    private Button registerbutton;
    @FXML
    private Button Return;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;

    private ToggleGroup genderGroup;


    public void RegisterUI(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\RegisterUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("CampusGo Register");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This function will take an info about new user to save it in DataBase
    public void Register() {
        Pass_Check PasswordChecker=new Pass_Check();
        String userId = ID.getText().trim();
        String username= Username.getText().trim();
        String password=Password.getText().trim();
        String Mail=mail.getText().trim();
        String location= Location.getText().trim();
        String college=College.getText().trim();
        boolean UserNameCheck=username.length()>5&&username.length()<20;
        boolean IdCheck=userId.length()>1&&userId.length()<= 9;
        this.userDB=new UserDatabase();
        if (userId.isEmpty()|| username.isEmpty() || password.isEmpty() || Mail.isEmpty() || location.isEmpty() || college.isEmpty()){
            if (UserNameCheck==false){
                showNotice("Invalid UserName");
                return;
            }
            if (IdCheck==false){
                showNotice("Invalid ID");
                return;
            }
            showNotice("Register Failed");
            return;
        }else {
            if (RegisterChecker(userId,username,password,Mail,college,getSelectedGender(),location) && PasswordChecker.IsValid(password)&&UserNameCheck&&IdCheck){
                showNotice("register success");
            }
        }
    }
    public boolean RegisterChecker(String id, String name, String password, String email, String college, String gender,String location) {
        if (userDB.userExists(id)) {
            showNotice("This ID is already Exist");
            return false;
        }
        Student newStudent = new Student(id, name, password, email, college, gender,location);
        ReturnToLogin();
        return userDB.register(newStudent);
    }

    @FXML
    public void initialize() {
        genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        maleRadio.setSelected(true);
    }
    public String getSelectedGender() {
        RadioButton selected = (RadioButton) genderGroup.getSelectedToggle();
        if (selected != null) {
            return selected.getText();
        }
        return null;
    }



    public void ReturnToLogin(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\LoginUI.fxml")
                            .toURI().toURL()
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerbutton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("CampusGo Register");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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