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

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PersonalData extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public AnchorPane personalDataMainAnchorPane;

    public Timetable timetablePage = new Timetable(this);
    public ShoppingCart parentBackendController;

    @FXML private CheckBox personalCheckBox;


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

        inputCustomerInfo();
        makeLblsInvisible();

    }

    @FXML
    public void onNextButtonPressed(){

        if(allFilledInCorrectly()) {
            personalDataMainAnchorPane.getChildren().add(timetablePage);
        }
    }

    @Override
    public void closeWizard() {
        parentBackendController.getChildren().remove(this);
        parentBackendController.closeWizard();

        if (personalCheckBox.isSelected()) {
            setCustomerInfo();
        }
    }

    @Override
    public void previousStep() {
        parentBackendController.getChildren().remove(this);
    }

    @FXML
    public void checkIfCorrect() {

        areaErrorLbl.setVisible(false);


        /*if(!areaTextField.getText().isEmpty()) {
            areaErrorLbl.setVisible(false);
        } else {
            areaErrorLbl.setVisible(true);
        }*/
    }

    //----------------FAKTISK KOD-----------------

    // Personen som handlar
    public Customer customer = IMatDataHandler.getInstance().getCustomer();


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

   // sätter kundinfo som skrevs in av kunden
    public void setCustomerInfo() {
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
        emailTextField.textProperty().setValue(customer.getEmail());
        mobileNumberTextField.textProperty().setValue(customer.getMobilePhoneNumber());
        postcodeTextField.textProperty().setValue(customer.getPhoneNumber());
        lastNameTextField.textProperty().setValue(customer.getLastName());
        firstNameTextField.textProperty().setValue(customer.getFirstName());
        addressTextField.textProperty().setValue(customer.getAddress());
        areaTextField.textProperty().setValue(customer.getPostCode());

    }

    public boolean isCustomerInfoComplete(){
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

       if(isCustomerInfoComplete()) {
           firstNameErrorLbl.setVisible(false);
           lastNameErrorLbl.setVisible(false);
           emailErrorLbl.setVisible(false);
           adressErrorLbl.setVisible(false);
           postCodeErrorLbl.setVisible(false);
           areaErrorLbl.setVisible(false);
           mobileErrorLbl.setVisible(false);

           return true;
       } else {
            if(firstNameTextField.getText().isEmpty()) {
                firstNameErrorLbl.setVisible(true);
            }

            if(lastNameTextField.getText().isEmpty()) {
                lastNameErrorLbl.setVisible(true);
            }

            if(emailTextField.getText().isEmpty() || !isInEmailForm(emailTextField)){
                emailErrorLbl.setVisible(true);
            }

            if(mobileNumberTextField.getText().isEmpty() || !isComplete(mobileNumberTextField,getMinAllowedLength(mobileNumberTextField)) || !containsDigitsOnly(mobileNumberTextField)) {
                mobileErrorLbl.setVisible(true);
           }

            if(!containsDigitsOnly(postcodeTextField) || !isComplete(postcodeTextField,getMinAllowedLength(postcodeTextField)) || postcodeTextField.getText().isEmpty()) {
                postCodeErrorLbl.setVisible(true);
            }

            if(addressTextField.getText().isEmpty()) {
                adressErrorLbl.setVisible(true);
            }

            if(areaTextField.getText().isEmpty()) {
                areaErrorLbl.setVisible(true);
            }

           return false;
       }
       //return false;
    }



    public void makeLblsInvisible() {
        firstNameErrorLbl.setVisible(false);
        lastNameErrorLbl.setVisible(false);
        emailErrorLbl.setVisible(false);
        adressErrorLbl.setVisible(false);
        postCodeErrorLbl.setVisible(false);
        areaErrorLbl.setVisible(false);
        mobileErrorLbl.setVisible(false);
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
