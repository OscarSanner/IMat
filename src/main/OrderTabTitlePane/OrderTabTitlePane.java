package main.OrderTabTitlePane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.ICustomTitledPane;
import main.IMatBackendEngine;
import main.OrderListTabItem.OrderListTabItem;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OrderTabTitlePane extends TitledPane implements ICustomTitledPane {

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    FlowPane orderTitlePaneFlowPane;
    @FXML
    AnchorPane orderTitleAnchorPane;
    @FXML
    Label dateLabel;
    @FXML
    Button saveButton;

    Order order;

    public OrderTabTitlePane(Order order){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderTabTitlePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.order = order;
        populatePane(order.getItems());
        dateLabel.setText("25e maj");
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

    public void onSaveButtonPressed(){
        IMatBackendEngine.getInstance().addSavedOrder(order);
    }

    public Order getOrder(){
        return this.order;
    }
}
