package main.ListTitlePane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.ICustomTitledPane;
import main.IMatBackendEngine;
import main.OrderListTabItem.OrderListTabItem;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ListTitlePane extends TitledPane implements ICustomTitledPane {

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    FlowPane orderTitlePaneFlowPane;
    @FXML
    AnchorPane orderTitleAnchorPane;
    @FXML
    Label nameLabel;
    Order order;
    iMatBackendController parent;
    private boolean added = false;

    public ListTitlePane(Order o, String s, iMatBackendController parent){
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
        this.order = o;
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

    @Override
    public Order getOrder() {
        return this.order;
    }

    public void removeAsSaved(){
        IMatBackendEngine.getInstance().removeSavedOrder(this.getOrder());
        parent.onListtabSelect();
    }

    public void addOrderToCart(){
        if(!added){
            for(ShoppingItem item : order.getItems()) {
                IMatDataHandler.getInstance().getShoppingCart().addItem(item);
                parent.updateShoppingCart();
            }
            added = true;
        }
    }
    public void onMouseClicked(){
        parent.calculateAccordionSize(parent.listAccordion);
    }
}
