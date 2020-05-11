package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class iMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("main/Res/iMat");

        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"), bundle);

        Scene scene = new Scene(root, 800, 500);

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
    }
}
