package main.TimetableItem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TimetableItem extends AnchorPane {

    public TimetableItem(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimetableItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
