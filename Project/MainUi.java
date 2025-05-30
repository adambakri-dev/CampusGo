package Project;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
// لأزرار الماوس (يمين/يسار/وسط)


public class MainUi extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // تحميل FXML وربطه بـ LoginController
        FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\Project\\UI\\LoginUI.fxml").toURI().toURL());
        Parent root = loader.load(); // تحميل الواجهة

        Scene scene = new Scene(root);
        primaryStage.setScene(scene); // تعيين المشهد للـ Stage
        primaryStage.setTitle("CampusGo Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
