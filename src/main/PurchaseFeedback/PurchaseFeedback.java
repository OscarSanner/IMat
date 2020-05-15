package main.PurchaseFeedback;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PurchaseFeedback extends AnchorPane {

    public PurchaseFeedback(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PurchaseFeedback.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
