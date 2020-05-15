package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.CustomerSupport.CustomerSupport;
import main.ShoppingCart.ShoppingCart;

public class iMatBackendController {

    @FXML
    public StackPane orderTabPane;
    @FXML
    public StackPane listTabPane;
    @FXML
    public StackPane myPageTabPane;
    @FXML
    public StackPane shoppingTabPane;
    @FXML
    public Button orderTabButton;
    @FXML
    public Button listTabButton;
    @FXML
    public Button myPageTabButton;
    @FXML
    public Button shoppingTabButton;
    @FXML
    public AnchorPane mainAnchorPane;

    public CustomerSupport customerSupportPage = new CustomerSupport(this);
    public ShoppingCart shoppingCartPage = new ShoppingCart();




    @FXML
    public void onOrderTabSelect(){
        orderTabPane.toFront();
    }

    @FXML
    public void onShoppingTabSelect(){
        shoppingTabPane.toFront();
    }

    @FXML
    public void onMyPageTabSelect(){
        myPageTabPane.toFront();
    }

    @FXML
    public void onListtabSelect(){
        listTabPane.toFront();
    }

    @FXML
    public void onCustomerSupportPressed(){
            mainAnchorPane.getChildren().add(customerSupportPage);
    }

    public void closeCustomerSupport() {
        mainAnchorPane.getChildren().remove(customerSupportPage);
    }
}
