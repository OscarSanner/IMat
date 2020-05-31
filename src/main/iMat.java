package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.*;

import java.time.LocalDateTime;
import java.util.*;

public class iMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("main/Res/iMat");

        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"), bundle);

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("main/Res/styles.css");

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/shop.png"
        ))));
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> IMatDataHandler.getInstance().shutDown(), "Shutdown-thread"));
        launch(args);
    }
}