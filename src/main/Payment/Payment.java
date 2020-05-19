package main.Payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.ConfirmationPage.ConfirmationPage;
import main.IWizardPage;
import main.Timetable.Timetable;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.Date;

public class Payment extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public Button payButton;
    @FXML
    public AnchorPane paymentMainAnchorPane;
    @FXML
    private javafx.scene.control.TextField cardNumberTextField;
    @FXML
    private javafx.scene.control.TextField monthTextField;
    @FXML
    private javafx.scene.control.TextField yearTextField;
    @FXML
    private javafx.scene.control.TextField cvcTextField;
    @FXML
    public Label cardErrorLabel;
    @FXML
    public Label cardStyleErrorLabel;
    @FXML
    public Label cardAmountErrorLabel;
    @FXML
    public CheckBox ceckButtonPayment;


    public IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    public ConfirmationPage confirmationPage = new ConfirmationPage(this);
    public ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    public Timetable parentBackendController;
    public Date deliveryTime;
    public Order order;


    public Payment(Timetable parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;
        //this.deliveryTime = deliveryTime; My heart is broken

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        inputPaymentInfo();
        makePaymentLblsInvisible();
    }

    public void onPayButtonPressed(){
        paymentMainAnchorPane.getChildren().add(confirmationPage);
        finalizePurchase();

        if (ceckButtonPayment.isSelected()) {
            setPaymentInfo();
        }

        if(allFilledInCorrectly()) {
            paymentMainAnchorPane.getChildren().add(parentBackendController);

        }

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


    // sätter kundinfo som skrevs in av kunden
    private void setPaymentInfo() {
        CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();
        creditCard.setCardNumber(cardNumberTextField.getText());
        creditCard.setVerificationCode(Integer.parseInt(cvcTextField.getText()));
        creditCard.setValidMonth(Integer.parseInt(monthTextField.getText()));
        creditCard.setValidYear(Integer.parseInt(yearTextField.getText()));
    }


    // fyller i sparade informationen i beatlningssteget
    private void inputPaymentInfo() {
        CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();
        cardNumberTextField.setText(creditCard.getCardNumber());
        cvcTextField.setText(String.valueOf(creditCard.getVerificationCode()));
        monthTextField.setText(String.valueOf(creditCard.getValidMonth()));
        yearTextField.setText(String.valueOf(creditCard.getValidYear()));
    }

    private boolean isPaymentInfoComplete(){
        return isComplete(cardNumberTextField,getMinAllowedLength(cardNumberTextField))
                && containsDigitsOnly(cardNumberTextField) && containsDigitsOnly(cardNumberTextField)
                && containsDigitsOnly(cvcTextField) && containsDigitsOnly(cvcTextField)
                && containsDigitsOnly(monthTextField) && containsDigitsOnly(monthTextField)
                && containsDigitsOnly(yearTextField) && containsDigitsOnly(yearTextField)
                && isComplete(cvcTextField,getMinAllowedLength(cvcTextField))
                && isComplete(monthTextField,getMinAllowedLength(monthTextField))
                && isComplete(yearTextField,getMinAllowedLength(yearTextField));
    }

    private boolean containsDigitsOnly(TextField textField){
        char[] chars = textField.getText().toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(!Character.isDigit(chars[i]) && !String.valueOf(chars[i]).equals(" ") && !String.valueOf(chars[i]).equals("-")){
                return false;
            }
        }
        return true;
    }

    private boolean isComplete(TextField textfield, int completeLength){
        return textfield.getText().length() == completeLength;
    }
    private int getMinAllowedLength(TextField textField){
        if(textField.equals(cardNumberTextField)){
            return 16;
        } else if(textField.equals(cvcTextField)) {
            return 1;
        } else if(textField.equals(monthTextField)){
            return 1;
        } else if(textField.equals(yearTextField)){
            return 4;
        } else {
            return 0;
        }
    }

    private boolean allFilledInCorrectly() {

        if (isPaymentInfoComplete()) {
            cardStyleErrorLabel.setVisible(false);
            cardAmountErrorLabel.setVisible(false);
            cardErrorLabel.setVisible(false);

            return true;

        } else{

            if (!containsDigitsOnly(cardNumberTextField)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(cardNumberTextField, getMinAllowedLength(cardNumberTextField)) && !(cardNumberTextField.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if (!(cardNumberTextField.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);
            }
            if (!containsDigitsOnly(cvcTextField)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(cvcTextField, getMinAllowedLength(cvcTextField)) && !(cvcTextField.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if (!(cvcTextField.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);
            }
            if (!containsDigitsOnly(monthTextField)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(monthTextField, getMinAllowedLength(monthTextField)) && !(monthTextField.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if (!(monthTextField.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);
            }
            if (!containsDigitsOnly(yearTextField)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(yearTextField, getMinAllowedLength(yearTextField)) && !(yearTextField.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if (!(yearTextField.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);
            }

            return false;
        }
    }

    private void makePaymentLblsInvisible() {
        cardStyleErrorLabel.setVisible(false);
        cardAmountErrorLabel.setVisible(false);
        cardErrorLabel.setVisible(false);
    }

    }



