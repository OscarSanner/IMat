package main.ShoppingCart;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ShoppingCart extends AnchorPane {

    public ShoppingCart(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
