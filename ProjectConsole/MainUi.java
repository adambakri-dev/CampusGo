package ProjectConsole;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import ProjectConsole.Controllers.LoginController; // تأكد من أنك استوردت الكلاس المناسب

public class MainUi extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // تحميل FXML وربطه بـ LoginController
        FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\watanimall\\Desktop\\college\\תוכנה\\project\\loginui.fxml").toURI().toURL());
        Parent root = loader.load(); // تحميل الواجهة

        Scene scene = new Scene(root); // إنشاء المشهد باستخدام الجذر
        primaryStage.setScene(scene); // تعيين المشهد للـ Stage
        primaryStage.setTitle("CampusGo Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
