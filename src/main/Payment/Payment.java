package main.Payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.ConfirmationPage.ConfirmationPage;
import main.IWizardPage;
import main.Timetable.Timetable;

import java.io.IOException;

public class Payment extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public Button payButton;
    @FXML
    public AnchorPane paymentMainAnchorPane;

    public ConfirmationPage confirmationPage = new ConfirmationPage(this);
    public Timetable parentBackendController;



    public Payment(Timetable parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onPayButtonPressed(){
        paymentMainAnchorPane.getChildren().add(confirmationPage);
    }

    @Override
    public void closeWizard() {
        parentBackendController.getChildren().remove(this);
        parentBackendController.closeWizard();
    }

    @Override
    public void previousStep() {
        parentBackendController.getChildren().remove(this);
    }

    //----------------FAKTISK KOD-----------------

}
