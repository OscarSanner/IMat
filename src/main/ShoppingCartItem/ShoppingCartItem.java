package main.ShoppingCartItem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ShoppingCartItem extends AnchorPane {

    public ShoppingCartItem(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
