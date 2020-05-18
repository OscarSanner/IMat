package main.Payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.ConfirmationPage.ConfirmationPage;
import main.IWizardPage;
import main.Timetable.Timetable;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;
import java.util.Date;

public class Payment extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public Button payButton;
    @FXML
    public AnchorPane paymentMainAnchorPane;

    public IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    public ConfirmationPage confirmationPage = new ConfirmationPage(this);
    public ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    public Timetable parentBackendController;
    public Date deliveryTime;
    public Order order;


    public Payment(Timetable parentBackendController, Date deliveryTime){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;
        this.deliveryTime = deliveryTime;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onPayButtonPressed(){
        paymentMainAnchorPane.getChildren().add(confirmationPage);
        finalizePurchase();

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

    private void finalizePurchase(){
        order = dataHandler.placeOrder();
        order.setDate(deliveryTime);
        shoppingCart.clear();

    }
}
