package main.Payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.ConfirmationPage.ConfirmationPage;

import java.io.IOException;

public class Payment extends AnchorPane {

    @FXML
    public Button payButton;
    @FXML
    public AnchorPane paymentMainAnchorPane;

    public ConfirmationPage confirmationPage = new ConfirmationPage();


    public Payment(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onPayButtonPressed(){
        paymentMainAnchorPane.getChildren().add(confirmationPage);
    }
}
