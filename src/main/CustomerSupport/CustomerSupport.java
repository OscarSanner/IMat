package main.CustomerSupport;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CustomerSupport extends AnchorPane {

    public CustomerSupport(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerSupport.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
