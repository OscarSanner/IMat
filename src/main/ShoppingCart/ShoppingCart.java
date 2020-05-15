package main.ShoppingCart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.PersonalData.PersonalData;
import main.iMatBackendController;

import java.io.IOException;

public class ShoppingCart extends AnchorPane {

    @FXML
    public Button shoppingCartNextButton;

    @FXML
    public AnchorPane shoppingCartMainAnchorPane;

    public PersonalData personalDataPage = new PersonalData();
    public iMatBackendController backendController;

    public ShoppingCart(iMatBackendController backendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.backendController = backendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void onNextButtonClickEvent(){
        shoppingCartMainAnchorPane.getChildren().add(personalDataPage);
    }
}
