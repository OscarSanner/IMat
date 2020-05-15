package main.PersonalData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.IWizardPage;
import main.ShoppingCart.ShoppingCart;
import main.Timetable.Timetable;

import java.io.IOException;

public class PersonalData extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public AnchorPane personalDataMainAnchorPane;

    public Timetable timetablePage = new Timetable(this);
    public ShoppingCart parentBackendController;


    public PersonalData(ShoppingCart parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PersonalData.fxml"));
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
        personalDataMainAnchorPane.getChildren().add(timetablePage);
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
