package main.ListTitlePane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.OrderListTabItem.OrderListTabItem;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ListTitlePane extends TitledPane{

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    FlowPane orderTitlePaneFlowPane;
    @FXML
    AnchorPane orderTitleAnchorPane;
    @FXML
    Label nameLabel;

    public ListTitlePane(Order o, String s){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListTitlePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        populatePane(o.getItems());
        nameLabel.setText(s);

    }

    public void populatePane(List<ShoppingItem> items){
        if(items != null){
            for(ShoppingItem i : items){
                orderTitlePaneFlowPane.getChildren().add(new OrderListTabItem(i.getAmount(), i.getProduct(), i.getTotal()));
            }
        }
    }
    public int calculateExpansion() {
        return 60 * orderTitlePaneFlowPane.getChildren().size();
    }
}
