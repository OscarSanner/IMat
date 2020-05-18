package main.OrderListTabItem;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrderListTabItem extends AnchorPane {

    public OrderListTabItem(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderListTabItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
