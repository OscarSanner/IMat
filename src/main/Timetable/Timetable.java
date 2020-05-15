package main.Timetable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Timetable extends AnchorPane {

    public Timetable(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timetable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
