package main.ShoppingCart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.IWizardPage;
import main.PersonalData.PersonalData;
import main.iMatBackendController;

import java.io.IOException;

public class ShoppingCart extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public Button shoppingCartNextButton;

    @FXML
    public AnchorPane shoppingCartMainAnchorPane;

    public PersonalData personalDataPage = new PersonalData(this);
    public iMatBackendController parentBackendController;

    public ShoppingCart(iMatBackendController backendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = backendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void onNextButtonPressed(){
        shoppingCartMainAnchorPane.getChildren().add(personalDataPage);
        //PersonalData.inputCustomerInfo();
    }

    @Override
    public void closeWizard() {
        parentBackendController.mainAnchorPane.getChildren().remove(this);
    }

    @Override
    public void previousStep() {
        closeWizard();
    }

    //----------------FAKTISK KOD-----------------




}
