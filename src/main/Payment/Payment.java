package main.Payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.ConfirmationPage.ConfirmationPage;
import main.IWizardPage;
import main.ShoppingCartItem.ShoppingCartItem;
import main.Timetable.Timetable;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class Payment extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML public Button payButton;
    @FXML public AnchorPane paymentMainAnchorPane;
    //@FXML private TextField cardNumberTextField;
    @FXML private TextField monthTextField;
    @FXML private TextField yearTextField;
    @FXML private TextField cvcTextField;
    @FXML public Label cardErrorLabel;
    @FXML public Label cardStyleErrorLabel;
    @FXML public Label cardAmountErrorLabel;
    @FXML public CheckBox checkButtonPayment;
    @FXML public ImageView escapeHatchImage;
    @FXML public Label sumPriceLabel;
    @FXML private TextField cardNumberTextField1;
    @FXML private TextField cardNumberTextField2;
    @FXML private TextField cardNumberTextField3;
    @FXML private TextField cardNumberTextField4;


    private CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();

    public Date deliveryTime;
    public IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    public ConfirmationPage confirmationPage;
    public ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    public Timetable parentBackendController;

    public Order order;


    public Payment(Timetable parentBackendController, Date deliveryTime){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.deliveryTime = deliveryTime;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentBackendController = parentBackendController;

        makePaymentLblsInvisible();


        double sum = getSum();
        sumPriceLabel.setText("Totalbelopp: " + String.valueOf(sum) + " kr");
    }

    private double getSum(){
        double sum = 0;
        for(ShoppingItem s: shoppingCart.getItems()){
            sum += (s.getAmount() * s.getProduct().getPrice());
            sum = Math.round(sum * 100.0)/100.0;
        }
        return sum;
    }

    @FXML
    public void onPayButtonPressed(){
        finalizePurchase();
        confirmationPage = new ConfirmationPage(this, deliveryTime, order);

        //paymentMainAnchorPane.getChildren().add(confirmationPage);


        if (checkButtonPayment.isSelected()) {
            setPaymentInfo();
        }
        if(allFilledInCorrectly()) {
            paymentMainAnchorPane.getChildren().add(confirmationPage);
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

    //================================================================================
    // Escapehatch Hover
    //================================================================================
    @FXML
    public void onEscapeHatchEnter(){
        escapeHatchImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/escape_hatch_hover.png"
        ))));
    }
    @FXML
    public void onEscapeHatchExit(){
        escapeHatchImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/escape_hatch.png"
        ))));
    }

    //----------------FAKTISK KOD-----------------

    private void finalizePurchase(){
        order = dataHandler.placeOrder();
        order.setDate(deliveryTime);
        shoppingCart.clear();

    }

    private void setPaymentInfo() {
        //CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();
        //creditCard.setCardNumber(cardNumberTextField.getText());

        creditCard.setVerificationCode(Integer.valueOf(cvcTextField.getText()));
        creditCard.setValidMonth(Integer.valueOf(monthTextField.getText()));
        creditCard.setValidYear(Integer.valueOf(yearTextField.getText()));
        saveCardNumber();
    }

    public void inputPaymentInfo() {
        fillCreditCardNumberTextField();

        if (creditCard.getValidMonth() != 0) {
            monthTextField.setText(String.valueOf(creditCard.getValidMonth()));
        } else {
            monthTextField.setText("");
            monthTextField.setPromptText("MM");
        }

        if (creditCard.getValidMonth() != 0) {
            yearTextField.setText(String.valueOf(creditCard.getValidYear()));
        } else {
            yearTextField.setText("");
            yearTextField.setPromptText("ÅR");
        }

        if (creditCard.getVerificationCode() != 0) {
            cvcTextField.setText(String.valueOf(creditCard.getVerificationCode()));
        } else {
            cvcTextField.setText("");
            cvcTextField.setPromptText("CVC/CVV");
        }

        if (isPaymentInfoComplete()) {
            checkButtonPayment.setSelected(true);
        } else {
            checkButtonPayment.setSelected(false);
        }
    }


    private boolean isPaymentInfoComplete(){
        return isComplete(cvcTextField,getMinAllowedLength(cvcTextField))
                && isComplete(monthTextField,getMinAllowedLength(monthTextField))
                && isComplete(yearTextField,getMinAllowedLength(yearTextField))
                && isComplete(cardNumberTextField1,getMinAllowedLength(cardNumberTextField1))
                && isComplete(cardNumberTextField2,getMinAllowedLength(cardNumberTextField2))
                && isComplete(cardNumberTextField3,getMinAllowedLength(cardNumberTextField3))
                && isComplete(cardNumberTextField4,getMinAllowedLength(cardNumberTextField4))
                //isComplete(cardNumberTextField,getMinAllowedLength(cardNumberTextField))
                //&& containsDigitsOnly(cardNumberTextField)
                && containsDigitsOnly(cardNumberTextField1)
                && containsDigitsOnly(cardNumberTextField2)
                && containsDigitsOnly(cardNumberTextField3)
                && containsDigitsOnly(cardNumberTextField4)
                && containsDigitsOnly(cvcTextField)
                && containsDigitsOnly(monthTextField)
                && containsDigitsOnly(yearTextField)
                //&& !cardNumberTextField.getText().isEmpty()
                && !cardNumberTextField1.getText().isEmpty()
                && !cardNumberTextField2.getText().isEmpty()
                && !cardNumberTextField3.getText().isEmpty()
                && !cardNumberTextField4.getText().isEmpty()
                && !cvcTextField.getText().isEmpty()
                && !monthTextField.getText().isEmpty()
                && !yearTextField.getText().isEmpty();
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
        if(textField.equals(cardNumberTextField1) || textField.equals(cardNumberTextField2) ||textField.equals(cardNumberTextField3) ||textField.equals(cardNumberTextField4)){
            return 4;
        } else if(textField.equals(cvcTextField)) {
            return 3;
        } else if(textField.equals(monthTextField)){
            return 2;
        } else if(textField.equals(yearTextField)){
            return 2;
        } else {
            return 0;
        }
    }
    private void fillCreditCardNumberTextField(){
        if(!creditCard.getCardNumber().isEmpty()) {
            int length = creditCard.getCardNumber().length();

            if (length >= 4){
                cardNumberTextField1.setText(creditCard.getCardNumber().substring(0, 4));
            }
            if (length >= 8){
                cardNumberTextField2.setText(creditCard.getCardNumber().substring(4, 8));
            }
            if (length >= 12){
                cardNumberTextField3.setText(creditCard.getCardNumber().substring(8, 12));
            }
            if (length >= 16){
                cardNumberTextField4.setText(creditCard.getCardNumber().substring(12, 16));
            }
        } else {
            cardNumberTextField1.clear();
            cardNumberTextField2.clear();
            cardNumberTextField3.clear();
            cardNumberTextField4.clear();
        }
    }
    private void saveCardNumber(){
        StringBuilder number = new StringBuilder();
        number.append(cardNumberTextField1.getText());
        number.append(cardNumberTextField2.getText());
        number.append(cardNumberTextField3.getText());
        number.append(cardNumberTextField4.getText());

        creditCard.setCardNumber(number.toString());
    }

    private boolean allFilledInCorrectly() {

        if (isPaymentInfoComplete()) {
            cardStyleErrorLabel.setVisible(false);
            cardAmountErrorLabel.setVisible(false);
            cardErrorLabel.setVisible(false);

            return true;

        } else{

            /*if (!containsDigitsOnly(cardNumberTextField)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(cardNumberTextField, getMinAllowedLength(cardNumberTextField)) && !(cardNumberTextField.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if ((cardNumberTextField.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);*/
            if (!containsDigitsOnly(cardNumberTextField1)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(cardNumberTextField1, getMinAllowedLength(cardNumberTextField1)) && !(cardNumberTextField1.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if ((cardNumberTextField1.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);
            }
            if (!containsDigitsOnly(cardNumberTextField2)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(cardNumberTextField2, getMinAllowedLength(cardNumberTextField2)) && !(cardNumberTextField2.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if ((cardNumberTextField2.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);
            }
            if (!containsDigitsOnly(cardNumberTextField3)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(cardNumberTextField3, getMinAllowedLength(cardNumberTextField3)) && !(cardNumberTextField3.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if ((cardNumberTextField3.getText().isEmpty())){
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(true);
                cardAmountErrorLabel.setVisible(false);
            }
            if (!containsDigitsOnly(cardNumberTextField4)) {
                cardStyleErrorLabel.setVisible(true);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(false);
            } else if (!isComplete(cardNumberTextField4, getMinAllowedLength(cardNumberTextField4)) && !(cardNumberTextField4.getText().isEmpty())) {
                cardStyleErrorLabel.setVisible(false);
                cardErrorLabel.setVisible(false);
                cardAmountErrorLabel.setVisible(true);
            } else if ((cardNumberTextField4.getText().isEmpty())){
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
            } else if ((cvcTextField.getText().isEmpty())){
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
            } else if ((monthTextField.getText().isEmpty())){
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
            } else if ((yearTextField.getText().isEmpty())){
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



