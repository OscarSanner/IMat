package main.Timetable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.IWizardPage;
import main.Payment.Payment;
import main.PersonalData.PersonalData;

import java.io.IOException;

public class Timetable extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp


    @FXML
    public AnchorPane timetableMainAnchorPane;
    public Payment paymentPage = new Payment(this);
    public PersonalData parentBackendController;


    public Timetable(PersonalData parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timetable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void onNextButtonPressed(){
        timetableMainAnchorPane.getChildren().add(paymentPage);
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
