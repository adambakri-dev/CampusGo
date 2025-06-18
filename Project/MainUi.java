//package Project;
//import java.io.File;
//
//import Project.Controllers.ProfileCotroller;
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.Parent;
//import javafx.stage.Stage;
//import Project.Controllers.LoginController;
//import Project.Controllers.RegisterController;
//import Project.DataBase.UsersDataBase;
//import Project.Utils.EmailCheck;
//
//
//public class MainUi extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        try {
//            FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\LoginUI.fxml").toURI().toURL());
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("CampusGo Login");
//            primaryStage.show();
//        } catch (Exception e) {
//            e.printStackTrace(); // اطبع الخطأ في الـ console
//        }
//
//    }
//
//    public static void main(String[] args) {
//        UsersDataBase db=new UsersDataBase();
//        LoginController loginController=new LoginController(db);
//        RegisterController registerController=new RegisterController(db);
//        launch(args);
//    }
//}
