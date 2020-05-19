package main.PersonalData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.IWizardPage;
import main.ShoppingCart.ShoppingCart;
import main.Timetable.Timetable;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class PersonalData extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public AnchorPane personalDataMainAnchorPane;

    public Timetable timetablePage = new Timetable(this);
    public ShoppingCart parentBackendController;

    @FXML private CheckBox personalCheckBox;

   private Customer customer = IMatDataHandler.getInstance().getCustomer();


    public PersonalData(ShoppingCart parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PersonalData.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentBackendController = parentBackendController;

       //System.out.println(customer.getFirstName());
        inputCustomerInfo();
       //System.out.println(customer.getFirstName());
        makeLblsInvisible();
    }

    @FXML
    public void onNextButtonPressed(){

       // Customer customer = IMatDataHandler.getInstance().getCustomer();
        //System.out.println(customer.getFirstName());

        if (personalCheckBox.isSelected()) {
            setCustomerInfo();
            personalCheckBox.setSelected(true);
             setCustomerInfo();
        }

        if(allFilledInCorrectly()) {
            personalDataMainAnchorPane.getChildren().add(timetablePage);

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

    @FXML
    public void errorlabeldisapear() {

        areaErrorLbl.setVisible(false);


        /*if(!areaTextField.getText().isEmpty()) {
            areaErrorLbl.setVisible(false);
        } else {
            areaErrorLbl.setVisible(true);
        }*/
    }

    @FXML
    public void errorlabeldisapear2() {

        firstNameErrorLbl.setVisible(false);

    }
    @FXML
    public void errorlabeldisapear3() {

        lastNameErrorLbl.setVisible(false);

    }
    @FXML
    public void errorlabeldisapear4() {

        postCodeAmountErrorLbl.setVisible(false);
        postCodeStyleErrorLbl.setVisible(false);
        postCodeErrorLbl.setVisible(false);

    }
    @FXML
    public void errorlabeldisapear5() {

        adressErrorLbl.setVisible(false);

    }
    @FXML
    public void errorlabeldisapear6() {

        mobileAmountErrorLbl.setVisible(false);
        mobileStyleErrorLbl.setVisible(false);
        mobileErrorLbl.setVisible(false);

    }
    @FXML
    public void errorlabeldisapear7() {

        emailErrorLbl.setVisible(false);
        emailStyleErrorLbl.setVisible(false);

    }

    //----------------FAKTISK KOD-----------------

    // Personen som handlar
   // private Customer customer = IMatDataHandler.getInstance().getCustomer();


    // alla textfält i betalningswizard
    @FXML
    private javafx.scene.control.TextField firstNameTextField;
    @FXML
    private javafx.scene.control.TextField lastNameTextField;
    @FXML
    private javafx.scene.control.TextField postcodeTextField;
    @FXML
    private javafx.scene.control.TextField mobileNumberTextField;
    @FXML
    private javafx.scene.control.TextField emailTextField;
    @FXML
    private javafx.scene.control.TextField addressTextField;
    @FXML
    private javafx.scene.control.TextField areaTextField;
    @FXML
    private TextField extraInfTextField; //finns inte
    @FXML
    private Label firstNameErrorLbl;
    @FXML
    private Label lastNameErrorLbl;
    @FXML
    private Label adressErrorLbl;
    @FXML
    private Label postCodeErrorLbl;
    @FXML
    private Label areaErrorLbl;
    @FXML
    private Label mobileErrorLbl;
    @FXML
    private Label emailErrorLbl;
    @FXML
    private Label postCodeStyleErrorLbl;
    @FXML
    private Label mobileStyleErrorLbl;
    @FXML
    private Label emailStyleErrorLbl;
    @FXML
    private Label mobileAmountErrorLbl;
    @FXML
    private Label postCodeAmountErrorLbl;

   // sätter kundinfo som skrevs in av kunden
    private void setCustomerInfo() {
       Customer customer = IMatDataHandler.getInstance().getCustomer();
            customer.setFirstName(firstNameTextField.getText());
            customer.setLastName(lastNameTextField.getText());
            customer.setMobilePhoneNumber(mobileNumberTextField.getText());
            customer.setEmail(emailTextField.getText());
            customer.setAddress(addressTextField.getText());
            customer.setPostAddress(areaTextField.getText());
            customer.setPostCode(postcodeTextField.getText());
    }


    // fyller i sparade informationen i beatlningssteget
    public void inputCustomerInfo(){
       Customer customer = IMatDataHandler.getInstance().getCustomer();

        emailTextField.setText(customer.getEmail());
        mobileNumberTextField.setText(customer.getMobilePhoneNumber());
        postcodeTextField.setText(customer.getPostCode());
        lastNameTextField.setText(customer.getLastName());
        firstNameTextField.setText(customer.getFirstName());
        addressTextField.setText(customer.getAddress());
        areaTextField.setText(customer.getPostAddress());


    /*
        emailTextField.textProperty().setValue(customer.getEmail());
        mobileNumberTextField.textProperty().setValue(customer.getMobilePhoneNumber());
        postcodeTextField.textProperty().setValue(customer.getPostCode());
        lastNameTextField.textProperty().setValue(customer.getLastName());
        firstNameTextField.textProperty().setValue(customer.getFirstName());
        addressTextField.textProperty().setValue(customer.getAddress());
        areaTextField.textProperty().setValue(customer.getPostAddress());
                */

    }

    private boolean isCustomerInfoComplete(){
        return !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty()
                && !emailTextField.getText().isEmpty() && isInEmailForm(emailTextField)
                && !mobileNumberTextField.getText().isEmpty()
                && !addressTextField.getText().isEmpty()
                && isComplete(mobileNumberTextField,getMinAllowedLength(mobileNumberTextField))
                && containsDigitsOnly(mobileNumberTextField) && containsDigitsOnly(postcodeTextField)
                && isComplete(postcodeTextField,getMinAllowedLength(postcodeTextField));

    }

    private boolean isInEmailForm(TextField textField){
        return (textField.getText().contains("@") && textField.getText().contains(".") || textField.getText().isEmpty());
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
    private boolean isEmpty(TextField textField){
        return textField.getText().isEmpty();
    }

    private boolean isComplete(TextField textfield, int completeLength){
        return textfield.getText().length() == completeLength;
    }
    private int getMinAllowedLength(TextField textField){
        if(textField.equals(mobileNumberTextField)){
            return 10;
        } else if(textField.equals(postcodeTextField)){
            return 5;
        } else {
            return 0;
        }
    }


    private boolean allFilledInCorrectly() {

        if (isCustomerInfoComplete()) {
            firstNameErrorLbl.setVisible(false);
            lastNameErrorLbl.setVisible(false);
            emailErrorLbl.setVisible(false);
            adressErrorLbl.setVisible(false);
            postCodeErrorLbl.setVisible(false);
            areaErrorLbl.setVisible(false);
            mobileErrorLbl.setVisible(false);
            mobileStyleErrorLbl.setVisible(false);
            mobileAmountErrorLbl.setVisible(false);
            emailStyleErrorLbl.setVisible(false);
            postCodeStyleErrorLbl.setVisible(false);
            postCodeAmountErrorLbl.setVisible(false);

            return true;

        } else{
            if (firstNameTextField.getText().isEmpty()) {
                firstNameErrorLbl.setVisible(true);
            }

            if (lastNameTextField.getText().isEmpty()) {
                lastNameErrorLbl.setVisible(true);
            }

            if (emailTextField.getText().isEmpty() /*|| !isInEmailForm(emailTextField)*/) {
                emailErrorLbl.setVisible(true);
                emailStyleErrorLbl.setVisible(false);
            }
            if (!isInEmailForm(emailTextField)) {
                emailErrorLbl.setVisible(false);
                emailStyleErrorLbl.setVisible(true);
            }

            if (mobileNumberTextField.getText().isEmpty() ) {
                mobileErrorLbl.setVisible(true);
                mobileStyleErrorLbl.setVisible(false);
                mobileAmountErrorLbl.setVisible(false);
            } else if (!isComplete(mobileNumberTextField, getMinAllowedLength(mobileNumberTextField))) {
                mobileErrorLbl.setVisible(false);
                mobileAmountErrorLbl.setVisible(true);
                mobileStyleErrorLbl.setVisible(false);
            } else if (!containsDigitsOnly(mobileNumberTextField)) {
                mobileErrorLbl.setVisible(false);
                mobileAmountErrorLbl.setVisible(false);
                mobileStyleErrorLbl.setVisible(true);
            }
            if (postcodeTextField.getText().isEmpty()) {
                postCodeErrorLbl.setVisible(true);
                postCodeAmountErrorLbl.setVisible(false);
                postCodeStyleErrorLbl.setVisible(false);
            } else if (!containsDigitsOnly(postcodeTextField)) {
                postCodeErrorLbl.setVisible(false);
                postCodeAmountErrorLbl.setVisible(false);
                postCodeStyleErrorLbl.setVisible(true);
            } else if (!isComplete(postcodeTextField, getMinAllowedLength(postcodeTextField))) {
                postCodeErrorLbl.setVisible(false);
                postCodeAmountErrorLbl.setVisible(true);
                postCodeStyleErrorLbl.setVisible(false);
            }

            if (addressTextField.getText().isEmpty()) {
                adressErrorLbl.setVisible(true);
            }

            if (areaTextField.getText().isEmpty()) {
                areaErrorLbl.setVisible(true);
            }

            return false;
        }
    }
       //return false;



    public void makeLblsInvisible() {
        firstNameErrorLbl.setVisible(false);
        lastNameErrorLbl.setVisible(false);
        emailErrorLbl.setVisible(false);
        adressErrorLbl.setVisible(false);
        postCodeErrorLbl.setVisible(false);
        areaErrorLbl.setVisible(false);
        mobileErrorLbl.setVisible(false);
        mobileStyleErrorLbl.setVisible(false);
        mobileAmountErrorLbl.setVisible(false);
        emailStyleErrorLbl.setVisible(false);
        postCodeStyleErrorLbl.setVisible(false);
        postCodeAmountErrorLbl.setVisible(false);
    }

    /* public void errorMeasureIfEmpty(TextField textField){
        if(textField.equals(firstNameTextField)){
            firstNameErrorLbl.setStyle("-fx-border-width: 3px; -fx-border-color: #FF0000;");
            firstNameErrorLbl.setText("Fältet kan inte vara tomt");
        } else if(textField.equals(lastNameTextField)){
            lastNameErrorLbl.setText("Fältet kan inte vara tomt");
        }else if(textField.equals(addressTextField)){
            adressErrorLbl.setText("Fältet kan inte vara tomt");
        }else if(textField.equals(postcodeTextField)){
            postCodeErrorLbl.setText("Fältet kan inte vara tomt");
        }else if(textField.equals(areaTextField)){
            areaErrorLbl.setText("Fältet kan inte vara tomt");
        }else if(textField.equals(mobileNumberTextField)){
            mobileErrorLbl.setText("Fältet kan inte vara tomt");
        }else if(textField.equals(emailTextField)){
            emailErrorLbl.setText("Fältet kan inte vara tomt");
            emailErrorLbl.setVisible(true);
        } else{
            textField.setStyle("");
        }
    }*/

   /*
    private void setErrorMessageOnIcon(Node node){
        StringBuilder messageBuilder = new StringBuilder();

        if(node instanceof TextField) {

            TextField textField = (TextField) node;

            if (isEmpty(textField)) {
                messageBuilder.append("Fältet kan inte vara tomt\n");
            }
            if (textField.equals(mobileNumberTextField) || textField.equals(postcodeTextField)) {
                if (!containsDigitsOnly(textField)) {
                    messageBuilder.append("Fältet får endast innehålla siffror, bindestreck och mellanslag\n");
                }

                if(!isComplete(textField,getMinAllowedLength(textField))){
                    messageBuilder.append("Fältet måste innehålla minst " + String.valueOf(getMinAllowedLength(textField)) + " siffror\n");
                }
            }

            if (textField.equals(emailTextField)) {
                if (!isInEmailForm(textField)) {
                    messageBuilder.append("Fältet måste ha formen: email@email.com\n");
                }
            }
        } else if(node instanceof ComboBox){

            ComboBox comboBox = (ComboBox) node;

            if(comboBox.getSelectionModel().isEmpty()){
                messageBuilder.append("Fältet kan inte vara tomt\n");
            }
        }

*/

}
