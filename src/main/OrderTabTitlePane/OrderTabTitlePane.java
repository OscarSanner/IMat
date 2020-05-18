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

public class OrderTabTitlePane extends TitledPane{

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    FlowPane orderTitlePaneFlowPane;
    @FXML
    AnchorPane orderTitleAnchorPane;

    public OrderTabTitlePane(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderTabTitlePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        populatePane(new Order());
    }

    public void populatePane(Order order){
        orderTitlePaneFlowPane.getChildren().add(new OrderListTabItem());
    }

    public int calculateExpansion() {
        return 60 * orderTitlePaneFlowPane.getChildren().size();
    }
}
