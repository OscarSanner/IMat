package main.OrderTabTitlePane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.IWizardPage;
import main.OrderListTabItem.OrderListTabItem;
import main.Payment.Payment;
import main.PersonalData.PersonalData;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderTabTitlePane extends TitledPane{

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    FlowPane orderTitlePaneFlowPane;
    @FXML
    AnchorPane orderTitleAnchorPane;

    public OrderTabTitlePane(List<ShoppingItem> items){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderTabTitlePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        populatePane(items);
    }

    public void populatePane(List<ShoppingItem> items){
        for(ShoppingItem i : items){
            orderTitlePaneFlowPane.getChildren().add(new OrderListTabItem(i.getAmount(), i.getProduct(), i.getTotal()));
        }
    }

    public int calculateExpansion() {
        return 60 * orderTitlePaneFlowPane.getChildren().size();
    }
}
