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
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private iMatBackendController parent;

    Order order;

    public OrderTabTitlePane(Order order, iMatBackendController parent){
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
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMMM yyyy");
        dateLabel.setText(timeFormat.format(order.getDate()));
        this.parent = parent;
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

    public void onMouseClicked(){
        parent.calculateAccordionSize(parent.listAccordion);
    }
}
