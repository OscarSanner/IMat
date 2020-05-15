package main.ConfirmationPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Payment.Payment;

import java.io.IOException;

public class ConfirmationPage extends AnchorPane {

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    public Button goBackToFrontButton;

    public Payment parentBackendController;

    public ConfirmationPage(Payment parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmationPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void onGoBackToFrontButtonPressed(){
        parentBackendController.closeWizard();
    }

    //----------------FAKTISK KOD-----------------
}
