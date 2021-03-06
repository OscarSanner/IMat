package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.control.Button;

import javafx.fxml.Initializable;
import javafx.scene.Node;


import javafx.scene.effect.GaussianBlur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import main.CustomerSupport.CustomerSupport;
import main.DetailedView.DetailedView;

import main.ListTitlePane.ListTitlePane;
import main.OrderTabTitlePane.OrderTabTitlePane;
import main.ShoppingCart.ShoppingCart;
import main.ShoppingCartItem.ShoppingCartItem;
import se.chalmers.cse.dat216.project.*;

import java.math.BigDecimal;
import java.util.*;

import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import main.PurchaseFeedback.PurchaseFeedback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class iMatBackendController implements Initializable {

    //----------------NAVIGATION OCH INIT------------------

    @FXML public StackPane orderTabPane;
    @FXML public StackPane listTabPane;
    @FXML public StackPane myPageTabPane;
    @FXML public StackPane shoppingTabPane;
    @FXML public StackPane homePane;
    @FXML public FlowPane subCategoryFlowPane;
    @FXML public FlowPane productFlowPane;
    @FXML public Button testButton;
    @FXML public AnchorPane mainAnchorPane;
    @FXML public Button checkoutButton;
    @FXML public Accordion orderAccordion;
    @FXML public Accordion listAccordion;
    @FXML public SplitPane categoryPane;
    @FXML public ScrollPane productsScrollPane;
    @FXML public FlowPane carouselFlowPane;
    @FXML public ScrollPane carouselScrollPane;

    @FXML public Button offersButton;
    @FXML public Button favouritesButton;

    @FXML public Label sumLabel;
    @FXML public ImageView escapeHatchImage;
    @FXML public Button meatAndFishButton;
    @FXML public Button veggiesButton;
    @FXML public Button drinkButton;
    @FXML public Button dairyButton;
    @FXML public Button pantryButton;
    @FXML public Button snacksButton;
    @FXML public Button spicesButton;
    @FXML public Button breadButton;


    public CustomerSupport customerSupportPage = new CustomerSupport(this);
    public ShoppingCart shoppingCartPage = new ShoppingCart(this);
    public DetailedView detailedViewPage = new DetailedView(this);

    public PurchaseFeedback purchaseFeedback = new PurchaseFeedback(this);

    public StackPane blurPane = new StackPane();


    public Customer customer = IMatDataHandler.getInstance().getCustomer();
    public CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();

    @FXML private javafx.scene.control.TextField firstName;
    @FXML private javafx.scene.control.TextField lastName;
    @FXML private javafx.scene.control.TextField mobileNumber;
    @FXML private javafx.scene.control.TextField email;
    @FXML private javafx.scene.control.TextField address;
    @FXML private javafx.scene.control.TextField postcode;
    @FXML private javafx.scene.control.TextField area;
    @FXML private Label postCodeAmountErrorLabel;
    @FXML private Label mobileAmountErrorLabel;
    @FXML private Label emailStyleErrorLabel;
    @FXML private Label postCodeStyleErrorLabel;
    @FXML private Label mobileStyleErrorLabel;
    //@FXML private javafx.scene.control.TextField cardNumber;
    @FXML private javafx.scene.control.TextField cardNumber1;
    @FXML private javafx.scene.control.TextField cardNumber2;
    @FXML private javafx.scene.control.TextField cardNumber3;
    @FXML private javafx.scene.control.TextField cardNumber4;
    @FXML private javafx.scene.control.TextField year;
    @FXML private javafx.scene.control.TextField month;
    @FXML private javafx.scene.control.TextField cvc;
    @FXML private Button saveButton;
    @FXML private Label paymentAmountErrorLbl;
    @FXML private Label paymentStyleErrorLbl;
    @FXML private Label aboutListLabel;
    @FXML private Label aboutOrderLabel;

    public boolean homepaneIsFront = true;

    Integer placeholder = null;

    //Resets the other unselected tabs
    /*public void resetSelectedTab(String currentTab){
        shoppingTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/handla_tab.png"
        ))));
        orderTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/order_tab.png"
        ))));
        listTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/inkopslistor_tab.png"
        ))));
        myPageTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/min_sida_tab.png"
        ))));

        switch (currentTab){
            case "order":
                orderTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        "main/Res/Images/order_tab_clicked.png"
                ))));
                break;
            case "shopping":
                shoppingTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        "main/Res/Images/handla_tab_clicked.png"
                ))));
                break;
            case "myPage":
                myPageTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        "main/Res/Images/min_sida_tab_clicked.png"
                ))));
                break;
            case "inkop":
                listTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        "main/Res/Images/inkopslistor_tab_clicked.png"
                ))));
                break;
            default:
                orderTabImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        "main/Res/Images/next.png"
                ))));
                System.out.println("Error currentTab");
                break;
        }
    }*/



    public void calculateAccordionSize(Accordion accordion) {
        if(accordion.getExpandedPane() == null){
            accordion.setPrefHeight((67 + 8) * accordion.getChildrenUnmodifiable().size());
            System.out.print("Checked for no child accordion = ");
            System.out.println(accordion.getPrefHeight());
        }else{
            System.out.println(accordion.getChildrenUnmodifiable().size());
            ICustomTitledPane activePane = (ICustomTitledPane) accordion.getExpandedPane();
            System.out.println(activePane.getOrder().getItems().size());
            accordion.setPrefHeight(((67 + 8) * accordion.getChildrenUnmodifiable().size()) + (60 * activePane.getOrder().getItems().size()));
            System.out.print("Checked with expanded pane = ");
            System.out.println(accordion.getPrefHeight());
        }
    }

    private void populateOrderPane() {
        orderAccordion.getPanes().clear();
        for(Order o : IMatDataHandler.getInstance().getOrders()){
            OrderTabTitlePane ot = new OrderTabTitlePane(o, this);
            orderAccordion.getPanes().add(ot);
            ot.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    calculateAccordionSize(orderAccordion);
                }
            });
        }
    }

    @FXML Button shoppingTab;
    @FXML Button orderTab;
    @FXML Button listTab;
    @FXML Button myPageTab;

    Button tabs [] = {shoppingTab, orderTab, listTab, myPageTab};


    @FXML
    public void onOrderTabSelect(){
        orderTabPane.toFront();
        homepaneIsFront = false;
        populateOrderPane();
        calculateAccordionSize(orderAccordion);
        if(!(orderAccordion.getChildrenUnmodifiable().size() == 0)){
            aboutOrderLabel.setText("");
        }
        //resetSelectedTab("order");
        IMatBackendEngine.getInstance().clearActiveCategory();
        IMatBackendEngine.getInstance().clearActiveTab();
        IMatBackendEngine.getInstance().setActiveTab(orderTab);
    }
    @FXML
    public void onShoppingTabSelect(){
        shoppingTabPane.toFront();
        //resetSelectedTab("shopping");
        IMatBackendEngine.getInstance().restoreActiveCategory();
        IMatBackendEngine.getInstance().clearActiveTab();
        IMatBackendEngine.getInstance().setActiveTab(shoppingTab);
    }

    @FXML
    public void onMyPageTabSelect(){
        myPageTabPane.toFront();
        homepaneIsFront = false;
        loadUserInfo();
        makeLblsInvisible();
        //resetSelectedTab("myPage");
        IMatBackendEngine.getInstance().clearActiveCategory();
        IMatBackendEngine.getInstance().clearActiveTab();
        IMatBackendEngine.getInstance().setActiveTab(myPageTab);
    }

    @FXML
    public void onListtabSelect(){
        listTabPane.toFront();
        homepaneIsFront = false;
        //IMatBackendEngine.getInstance().sordListOfSaved();
        populateListPane();
        calculateAccordionSize(listAccordion);
        if(!listAccordion.getChildrenUnmodifiable().isEmpty()){
            aboutListLabel.setText(" ");
        }
        //resetSelectedTab("inkop");
        IMatBackendEngine.getInstance().clearActiveCategory();
        IMatBackendEngine.getInstance().clearActiveTab();
        IMatBackendEngine.getInstance().setActiveTab(listTab);
        if(listAccordion.getChildrenUnmodifiable().isEmpty()){

        }
    }

    private void populateListPane() {
        listAccordion.getPanes().clear();
        for(Map.Entry<Order, String> entry : IMatBackendEngine.getInstance().savedOrders.entrySet()){
            ListTitlePane ltp = new ListTitlePane(entry.getKey(), entry.getValue(), this);
            listAccordion.getPanes().add(ltp);
            ltp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    calculateAccordionSize(listAccordion);
                }
            });
        }
    }

    @FXML
    public void saveOrder(){

    }

    @FXML
    public void onCustomerSupportPressed(){
            mainAnchorPane.getChildren().add(customerSupportPage);
    }


    public void closeCustomerSupport() {
        mainAnchorPane.getChildren().remove(customerSupportPage);
    }

    @FXML
    public void onCheckoutButtonPressed(){

        if(!shoppingCart.getItems().isEmpty()){
            mainAnchorPane.getChildren().add(shoppingCartPage);
            //saveCustomerInfo();
            shoppingCartPage.populateShoppingCartPage();
        }

    }
    @FXML
    public void onSaveButtonPressed(){
        if(checkCardNumber() && checkCVC() && checkEmail() && checkMobile() && checkMonth() && checkPostCode() && checkYear()) { //allFilledInCorrectly()
            saveCustomerInfo();
        }
    }
    @FXML
    public void closeDetailedView(){
        mainAnchorPane.getChildren().remove(detailedViewPage);
        mainAnchorPane.getChildren().remove(blurPane);
        undoBlurBackground();
    }


    @FXML
    public void mySiteErrorLabelDisapear() {
        postCodeAmountErrorLabel.setVisible(false);
        postCodeStyleErrorLabel.setVisible(false);
    }
    @FXML
    public void mySiteErrorLabelDisapear2() {
        mobileAmountErrorLabel.setVisible(false);
        mobileStyleErrorLabel.setVisible(false);
    }
    @FXML
    public void mySiteErrorLabelDisapear3() {
        emailStyleErrorLabel.setVisible(false);
    }

    public void onHomeButtonPressed(){
        homePane.toFront();
        shoppingTabPane.toFront();
        homepaneIsFront = true;
        populateCarouselFlowPane();
       // theRealResetOfSearchBar();
        IMatBackendEngine.getInstance().clearActiveCategory();
    }

    //----------------FAKTISK KOD-----------------
// ------------------------------------------------------------Navigation-----------------------------------------------------------------------//


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



//------------------------------------------------------------MinSida-----------------------------------------------------------------------//

    @FXML
    public void errorBoarderDisapear1() {

        cardNumber1.setStyle("");
    }
    @FXML
    public void errorBoarderDisapear2() {

        cardNumber2.setStyle("");
    }
    @FXML
    public void errorBoarderDisapear3() {

        cardNumber3.setStyle("");
    }
    @FXML
    public void errorBoarderDisapear4() {

        cardNumber4.setStyle("");
    }
    @FXML
    public void errorBoarderDisapear5() {

        cvc.setStyle("");
    }
    @FXML
    public void errorBoarderDisapear6() {

        month.setStyle("");
    }
    @FXML
    public void errorBoarderDisapear7() {

        year.setStyle("");
    }

    @FXML
    private void loadUserInfo(){
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        email.setText(customer.getEmail());
        address.setText(customer.getAddress());
        postcode.setText(customer.getPostCode());
        mobileNumber.setText(customer.getMobilePhoneNumber());
        area.setText(customer.getPostAddress());
        //cardNumber1.setText(creditCard.getCardNumber());
        fillCreditCardNumberTextField();


        if(creditCard.getVerificationCode() != 0) {
            cvc.setText(String.valueOf(creditCard.getVerificationCode()));
        } else {
            cvc.setText("");
            cvc.setPromptText("CVC/CVV");
        }

        if(creditCard.getValidMonth() != 0) {
            month.setText(String.valueOf(creditCard.getValidMonth()));
        } else {
            month.setText("");
            month.setPromptText("MM");
        }

        if(creditCard.getValidYear() != 0) {
            year.setText(String.valueOf(creditCard.getValidYear()));
        } else {
            year.setText("");
            year.setPromptText("ÅR");
        }

    }

    private void saveCustomerInfo() {
        customer.setFirstName(firstName.getText());
        customer.setLastName(lastName.getText());
        customer.setMobilePhoneNumber(mobileNumber.getText());
        customer.setEmail(email.getText());
        customer.setAddress(address.getText());
        customer.setPostAddress(area.getText());
        customer.setPostCode(postcode.getText());
        //creditCard.setCardNumber(cardNumber.getText());
        creditCard.setCardNumber(saveCardNumber());

        if(!cvc.getText().isEmpty()) {
            creditCard.setVerificationCode(Integer.valueOf(cvc.getText()));
        } else {
            creditCard.setVerificationCode(0);
            cvc.setText("");
            cvc.setPromptText("CVC/CVV");
        }

        if(!month.getText().isEmpty()) {
            creditCard.setValidMonth(Integer.valueOf(month.getText()));
        } else {
            creditCard.setValidMonth(0);
            month.setText("");
            month.setPromptText("MM");
        }

        if(!year.getText().isEmpty()) {
            creditCard.setValidYear(Integer.valueOf(year.getText()));
        } else {
            creditCard.setValidYear(0);
            year.setText("");
            year.setPromptText("ÅR");
        }
    }


    private boolean isCustomerInfoComplete(){
        return isInEmailForm(email)
                && containsDigitsOnly(mobileNumber)
                && containsDigitsOnly(postcode)
                && isComplete(postcode,getMinAllowedLength(postcode))
                && isComplete(mobileNumber,getMinAllowedLength(mobileNumber))
                && containsDigitsOnly(cvc)
                && containsDigitsOnly(month)
                && containsDigitsOnly(year)
                && isComplete(cvc, getMinAllowedLength(cvc))
                && isComplete(month, getMinAllowedLength(month))
                && isComplete(year, getMinAllowedLength(year))
                && containsDigitsOnly(cardNumber1)
                && containsDigitsOnly(cardNumber2)
                && containsDigitsOnly(cardNumber3)
                && containsDigitsOnly(cardNumber4)
                && isComplete(cardNumber1,getMinAllowedLength(cardNumber1))
                && isComplete(cardNumber2,getMinAllowedLength(cardNumber2))
                && isComplete(cardNumber3,getMinAllowedLength(cardNumber3))
                && isComplete(cardNumber4,getMinAllowedLength(cardNumber4));

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

    private boolean isComplete(TextField textfield, int completeLength){
        return textfield.getText().length() == completeLength;
    }
    private int getMinAllowedLength(TextField textField){
        if(textField.equals(mobileNumber)){
            return 10;
        } else if(textField.equals(postcode)){
            return 5;
        /*} else if (textField.equals(textField.equals(cardNumber1) || textField.equals(cardNumber2) ||textField.equals(cardNumber3) ||textField.equals(cardNumber4))){
            return 4;*/
        }else if(textField.equals(cvc)) {
            return 3;
        } else if(textField.equals(month)){
            return 2;
        } else if(textField.equals(year)){
            return 2;
        } else {
            return 0;
        }
    }

    private boolean checkPostCode() {

        boolean check = true;
        if (postcode.getText().isEmpty()) {
            postCodeAmountErrorLabel.setVisible(false);
            postCodeStyleErrorLabel.setVisible(false);
            check = true;
        } else if (!containsDigitsOnly(postcode) && !(postcode.getText().isEmpty())) {
            postCodeStyleErrorLabel.setVisible(true);
            postCodeAmountErrorLabel.setVisible(false);

            check = false;
        } else if (!isComplete(postcode, getMinAllowedLength(postcode)) && !(postcode.getText().isEmpty())) {
            postCodeStyleErrorLabel.setVisible(false);
            postCodeAmountErrorLabel.setVisible(true);
            check = false;
        }
       return check;
    }

    private boolean checkMobile() {

        boolean check = true;

        if (mobileNumber.getText().isEmpty()) {
            mobileAmountErrorLabel.setVisible(false);
            mobileStyleErrorLabel.setVisible(false);
            check = true;
        } else if (!containsDigitsOnly(mobileNumber) && !(mobileNumber.getText().isEmpty())) {
            mobileAmountErrorLabel.setVisible(false);
            mobileStyleErrorLabel.setVisible(true);
            check = false;
        } else if (!isComplete(mobileNumber, getMinAllowedLength(mobileNumber)) && !(mobileNumber.getText().isEmpty())) {
            mobileAmountErrorLabel.setVisible(true);
            mobileStyleErrorLabel.setVisible(false);
            check = false;
        }
        return check;
    }

    private boolean checkEmail() {

        boolean check = true;

        if (email.getText().isEmpty()) {
            emailStyleErrorLabel.setVisible(false);
            check = true;
        } else if (!(email.getText().isEmpty()) && !(isInEmailForm(email))) {//*|| !isInEmailForm(emailTextField)*//*) {
            emailStyleErrorLabel.setVisible(true);
            check = false;
        }
        return check;
    }

    private boolean checkCardNumber() {
        boolean check = true;

        if (cardNumber1.getText().isEmpty() && cardNumber2.getText().isEmpty() && cardNumber3.getText().isEmpty() && cardNumber4.getText().isEmpty()) {
            check = true;
            errorMessageMyPage(cardNumber1,4);
            errorMessageMyPage(cardNumber2,4);
            errorMessageMyPage(cardNumber3,4);
            errorMessageMyPage(cardNumber4,4);
            paymentStyleErrorLbl.setVisible(false);
            paymentAmountErrorLbl.setVisible(false);

        } else if (containsDigitsOnly(cardNumber1) && containsDigitsOnly(cardNumber2) && containsDigitsOnly(cardNumber3)
                && containsDigitsOnly(cardNumber4) && isComplete(cardNumber1, 4)
                && isComplete(cardNumber2, 4) && isComplete(cardNumber3, 4)
                && isComplete(cardNumber4, 4)) {
            check = true;
            errorMessageMyPage(cardNumber1,4);
            errorMessageMyPage(cardNumber2,4);
            errorMessageMyPage(cardNumber3,4);
            errorMessageMyPage(cardNumber4,4);
            paymentStyleErrorLbl.setVisible(false);
            paymentAmountErrorLbl.setVisible(false);

        } else if (!containsDigitsOnly(cardNumber1) && !cardNumber1.getText().isEmpty()
                || !containsDigitsOnly(cardNumber2) && !cardNumber2.getText().isEmpty()
                || !containsDigitsOnly(cardNumber3) && !cardNumber3.getText().isEmpty()
                || !containsDigitsOnly(cardNumber4) && !cardNumber4.getText().isEmpty()) {
            paymentAmountErrorLbl.setVisible(false);
            paymentStyleErrorLbl.setVisible(true);
            if (!containsDigitsOnly(cardNumber1)){
                errorMessageMyPage(cardNumber1,4);
            }
            if (!containsDigitsOnly(cardNumber2)){
                errorMessageMyPage(cardNumber2,4);
            }
            if (!containsDigitsOnly(cardNumber3)){
                errorMessageMyPage(cardNumber3,4);
            }
            if (!containsDigitsOnly(cardNumber4)){
                errorMessageMyPage(cardNumber4,4);
            }
            check = false;
        }
        else if (isComplete(cardNumber1, 4) && !cardNumber1.getText().isEmpty()
                || isComplete(cardNumber2, 4) && !cardNumber2.getText().isEmpty()
                || isComplete(cardNumber3, 4) && !cardNumber3.getText().isEmpty()
                || isComplete(cardNumber4, 4) && !cardNumber4.getText().isEmpty()){
            paymentAmountErrorLbl.setVisible(true);
            paymentStyleErrorLbl.setVisible(false);

            if (!isComplete(cardNumber1, 4)){
                errorMessageMyPage(cardNumber1,4);
            }
            if (!isComplete(cardNumber2, 4)){
                errorMessageMyPage(cardNumber2,4);
            }
            if (!isComplete(cardNumber3, 4)){
                errorMessageMyPage(cardNumber3,4);
            }
            if (!containsDigitsOnly(cardNumber4) || !isComplete(cardNumber4, 4)){
                errorMessageMyPage(cardNumber4,4);
            }
            check =  false;
        }
        return check;
    }

    private boolean checkMonth() {
        boolean check = true;

        if (month.getText().isEmpty()) {
            paymentAmountErrorLbl.setVisible(false);
            paymentStyleErrorLbl.setVisible(false);
            errorMessageMyPage(month,2);

            check = true;

        } else if (!containsDigitsOnly(month) && !(month.getText().isEmpty())) {
            paymentAmountErrorLbl.setVisible(false);
            paymentStyleErrorLbl.setVisible(true);
            errorMessageMyPage(month,2);
            check = false;
        } else if (!isComplete(month, getMinAllowedLength(month)) && !(month.getText().isEmpty())) {
            paymentAmountErrorLbl.setVisible(true);
            paymentStyleErrorLbl.setVisible(false);
            errorMessageMyPage(month,2);

            check = false;
        }

        return check;
    }

    private boolean checkYear() {

        boolean check = true;

        if (year.getText().isEmpty()) {
            paymentAmountErrorLbl.setVisible(false);
            paymentStyleErrorLbl.setVisible(false);
            errorMessageMyPage(year,2);

            check = true;

        } else if (!containsDigitsOnly(year) && !(year.getText().isEmpty())) {
            paymentAmountErrorLbl.setVisible(false);
            paymentStyleErrorLbl.setVisible(true);
            errorMessageMyPage(year,2);

            check = false;

        } else if (!isComplete(year, 2) && !(year.getText().isEmpty())) {
            paymentAmountErrorLbl.setVisible(true);
            paymentStyleErrorLbl.setVisible(false);
            errorMessageMyPage(year,2);
            check = false;
        }
        return check;

    }

    private boolean checkCVC() {
       boolean check = true;

        if (cvc.getText().isEmpty()) {
            paymentAmountErrorLbl.setVisible(false);
            paymentStyleErrorLbl.setVisible(false);
            errorMessageMyPage(cvc,3);

            check = true;

    } else if (!containsDigitsOnly(cvc) && !(cvc.getText().isEmpty())) {
            paymentAmountErrorLbl.setVisible(false);
            paymentStyleErrorLbl.setVisible(true);
            errorMessageMyPage(cvc,3);

            check = false;
        } else if (!isComplete(cvc, getMinAllowedLength(cvc)) && !(cvc.getText().isEmpty())) {
            paymentAmountErrorLbl.setVisible(true);
            paymentStyleErrorLbl.setVisible(false);
            errorMessageMyPage(cvc,3);

            check = false;
        }
        return check;
    }


    private void makeLblsInvisible() {
        mobileStyleErrorLabel.setVisible(false);
        mobileAmountErrorLabel.setVisible(false);
        emailStyleErrorLabel.setVisible(false);
        postCodeAmountErrorLabel.setVisible(false);
        postCodeStyleErrorLabel.setVisible(false);
        paymentAmountErrorLbl.setVisible(false);
        paymentStyleErrorLbl.setVisible(false);
    }

    private void fillCreditCardNumberTextField(){
        if(!creditCard.getCardNumber().isEmpty()) {
            int length = creditCard.getCardNumber().length();

            if (length >= 4){
                cardNumber1.setText(creditCard.getCardNumber().substring(0, 4));
            }
            if (length >= 8){
                cardNumber2.setText(creditCard.getCardNumber().substring(4, 8));
            }
            if (length >= 12){
                cardNumber3.setText(creditCard.getCardNumber().substring(8, 12));
            }
            if (length >= 16){
                cardNumber4.setText(creditCard.getCardNumber().substring(12, 16));
            }
        } else {
            cardNumber1.clear();
            cardNumber2.clear();
            cardNumber3.clear();
            cardNumber4.clear();
        }
    }

    private String saveCardNumber(){
        StringBuilder number = new StringBuilder();
        number.append(cardNumber1.getText());
        number.append(cardNumber2.getText());
        number.append(cardNumber3.getText());
        number.append(cardNumber4.getText());

        return number.toString();
    }
    public void errorMessageMyPage(TextField textField, int completeLength){
        if(!containsDigitsOnly(textField) && !textField.getText().isEmpty() || !isComplete(textField, completeLength) && !textField.getText().isEmpty()){
            textField.setStyle("-fx-border-width: 3px; -fx-border-color: #FF0000;");
        } else {
            textField.setStyle("");
        }
    }

    public boolean CvcIsEmpty() {
        if(cvc.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean MonthIsEmpty() {
        if(month.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean YearIsEmpty() {
        if(year.getText().isEmpty()) {
            return true;
        }
        return false;
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------//


//----------------------------------------------------------KOD FÖR KATEGORISIDAN-------------------------------------------------------------------------------//
//TODO: För att komma åt den FlowPane där vi skall ha subkategorier: kalla på "subCategoryFlowPane"
//TODO: För att komma åt den FLowPane där vi skall ha produkter: kalla på: ""

    public String currentCategory;
    //kopplad till högerpilen vid carouselen.
    @FXML
    public void rotateCarouselRight(){
        carouselScrollPane.setHvalue(carouselScrollPane.getHvalue() + 20.0);

    }

    //kopplad till vänsterpilen vid carouselen.
    @FXML
    public void rotateCarouselLeft(){
        carouselScrollPane.setHvalue(carouselScrollPane.getHvalue() - 20.0);
    }

    //För att komma åt CarouselFlowPane, kalla på "carouselFlowPane".
    @FXML
    public void populateCarouselFlowPane(){

        carouselFlowPane.getChildren().clear();

        main.Product.Product temporaryProduct;
        List<main.Product.Product> listofProducts = new ArrayList<>();
        for (Product p: ProductHandler.getProductsFromCategory("Bär")){
            temporaryProduct = new main.Product.Product(this, p);

            for(ShoppingItem s: dataHandler.getShoppingCart().getItems()){
                if(s.getProduct().equals(p)){
                    temporaryProduct.setQuantityLabel(String.valueOf((int)s.getAmount()));
                    temporaryProduct.buyButtonVisible(false);
                }
            }

            listofProducts.add(temporaryProduct);
        }
        //BubbleSort
        int n = listofProducts.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (listofProducts.get(j).getProductIDDD() > listofProducts.get(j+1).getProductIDDD())
                {
                    // swap arr[j+1] and arr[i]
                    main.Product.Product temp = listofProducts.get(j);
                    listofProducts.set(j, listofProducts.get(j+1));
                    listofProducts.set(j+1, temp);
                }
            }
        }
        for(main.Product.Product pepe: listofProducts){
            carouselFlowPane.getChildren().add(pepe);
        }

    }

    //kopplad till newsImage.
    public void onNewsImagePressed(){

    }

    //kopplad till videoImage.
    public void onVideoImagePressed(){

    }

    public void onFavouritesButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateProductFlowpane("Mina Favoriter");
        IMatBackendEngine.getInstance().setActiveCategory(favouritesButton);
        populateSubCategoryFlowPane("favourites");
        shoppingTabPane.toFront();
       // theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        hideSubCategories();
    }

    public void onOffersButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateProductFlowpane("Bär");

        IMatBackendEngine.getInstance().setActiveCategory(offersButton);
        populateSubCategoryFlowPane("Offers");
       // theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        hideSubCategories();
    }

    public void onBreadButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Bröd");
        populateProductFlowpane("Bröd");
        productsScrollPane.setVvalue(0);
        categoryPane.toFront();
       // theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        //hideSubCategories();
    }

    public void onMeatAndFishButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kött & Fisk");
        populateProductFlowpane("Kött & Fisk");
        productsScrollPane.setVvalue(0); //Resets the scrollpane to the top
        shoppingTabPane.toFront();
       // theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        showSubCategories();
    }

    public void onVeggiesButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Frukt & Grönt");
        populateProductFlowpane("Frukt & Grönt");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
       // theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        showSubCategories();
    }

    public void onDrinkButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Dryck");
        populateProductFlowpane("Dryck");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
      //  theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        showSubCategories();
    }

    public void onDairyButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Mejeri");
        populateProductFlowpane("Mejeri");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
        //theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        //hideSubCategories();
    }

    public void onPantryButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Skafferi");
        populateProductFlowpane("Skafferi");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
       // theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        showSubCategories();
    }

    public void onSnacksButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Snacks");
        populateProductFlowpane("Snacks");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
      //  theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();

        showSubCategories();
    }

    public void onSpicesButtonPressed(){
        IMatBackendEngine.getInstance().clearActiveCategory();
        categoryPane.toFront();
        homepaneIsFront = false;
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kryddor");
        populateProductFlowpane("Kryddor");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
     //   theRealResetOfSearchBar();
        searchResultLabel.setVisible(false);
        onShoppingTabSelect();
    }

    private void populateSubCategoryFlowPane(String category) {
        List<Button> buttons = new ArrayList<Button>();

        switch (category){
            case "Kött & Fisk":{
                Button meat = new Button("Kött");
                Button fish = new Button("Fisk");
                buttons.add(meat);
                buttons.add(fish);
                IMatBackendEngine.getInstance().setActiveCategory(meatAndFishButton);
                break;
            }
            case "Frukt & Grönt": {
                Button berries = new Button("Bär");
                Button fruit = new Button("Frukt");
                Button veggies = new Button("Grönsaker");
                buttons.add(berries);
                buttons.add(veggies);
                buttons.add(fruit);
                IMatBackendEngine.getInstance().setActiveCategory(veggiesButton);
                break;
            }
            case "Dryck": {
                Button warmDrinks = new Button("Varma drycker");
                Button coldDrinks = new Button("Kalla drycker");
                buttons.add(warmDrinks);
                buttons.add(coldDrinks);
                IMatBackendEngine.getInstance().setActiveCategory(drinkButton);
                break;
        }
            case "Mejeri": {
                Button dairy = new Button("Mejeri");
                buttons.add(dairy);
                IMatBackendEngine.getInstance().setActiveCategory(dairyButton);
                break;
            }
            case "Skafferi":  {
                Button flourSugarSalt = new Button("Mjöl, socker, salt");
                Button pasta = new Button("Pasta");
                Button potatoRice = new Button("Potatis, ris");
                buttons.add(flourSugarSalt);
                buttons.add(pasta);
                buttons.add(potatoRice);
                IMatBackendEngine.getInstance().setActiveCategory(pantryButton);
                break;
            }
            case "Snacks" : {
                Button sweets = new Button("Sötsaker");
                Button seeds = new Button("Nötter och frön");
                buttons.add(sweets);
                buttons.add(seeds);
                IMatBackendEngine.getInstance().setActiveCategory(snacksButton);
                break;
            }
            case "Kryddor": {
                Button spices = new Button("Kryddor");
                buttons.add(spices);
                IMatBackendEngine.getInstance().setActiveCategory(spicesButton);
                break;
            }
            case "Bröd": {
                Button bread = new Button("Bröd");
                buttons.add(bread);
                IMatBackendEngine.getInstance().setActiveCategory(breadButton);
                break;
            }
        }
        subCategoryFlowPane.getChildren().clear();
        subCategoryFlowPane.setPadding(new Insets(9,30,30,9));
        subCategoryFlowPane.setHgap(10);

        //Listener for Subcategory buttons
        for(Button button : buttons){
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    populateProductFlowpane(button.getText());
                    productsScrollPane.setVvalue(0);
                    IMatBackendEngine.getInstance().clearActiveSubcategory();
                    IMatBackendEngine.getInstance().setActiveSubcategory(button);
                }
            });
            button.setPrefSize(10 * button.getText().length() + 50,60);
            button.getStyleClass().add("subCategoryButton");
            button.getStyleClass().add("cursor-hand");
            button.setAlignment(Pos.CENTER);
            button.setPadding(new Insets(0, 0, 15, 0));
            subCategoryFlowPane.getChildren().add(button);
        }
    }

    private void populateProductFlowpane(String category) {

        if(homepaneIsFront){
            populateCarouselFlowPane();
        }else{
            List<main.Product.Product> listofProducts = new ArrayList<>();

            productFlowPane.getChildren().clear();

            setupProductFlowpane();

            currentCategory = category;
            main.Product.Product temporaryProduct;
            for(Product p : ProductHandler.getProductsFromCategory(category)){
                temporaryProduct = new main.Product.Product(this, p);

                for(ShoppingItem s: dataHandler.getShoppingCart().getItems()){
                    if(s.getProduct().equals(p)){
                        temporaryProduct.setQuantityLabel(String.valueOf((int)s.getAmount()));
                        temporaryProduct.buyButtonVisible(false);
                    }
                }

                listofProducts.add(temporaryProduct);

            }
            //BubbleSort
            int n = listofProducts.size();
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-i-1; j++){
                    if (listofProducts.get(j).getProductIDDD() > listofProducts.get(j+1).getProductIDDD())
                    {
                        // swap arr[j+1] and arr[i]
                        main.Product.Product temp = listofProducts.get(j);
                        listofProducts.set(j, listofProducts.get(j+1));
                        listofProducts.set(j+1, temp);
                    }
                }
            }

            for(main.Product.Product pepe: listofProducts){
                productFlowPane.getChildren().add(pepe);
            }
        }

    }


    public void showPurchaseFeedback(Product product, String quantity, String unitSuffix){
        purchaseFeedback.setFeedbackLabel(product.getName() + " har lagts till i varukorgen");
        purchaseFeedback.setVisible(true);

        System.out.println(purchaseFeedback.isVisible()
        );
    }
    public void closePurchaseFeedback(){
        purchaseFeedback.setVisible(false);
    }
    //------------------------------------------------------------Kundvagn (Startsida)-----------------------------------------------------------------------//

    @FXML public FlowPane shoppingCartFlowPane;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private se.chalmers.cse.dat216.project.ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    @FXML public ScrollPane shoppingCartScrollPane;


    private ShoppingCartListener scl = new ShoppingCartListener() {
        @Override
        public void shoppingCartChanged(CartEvent cartEvent) {
            updateShoppingCart();

        }
    };

    private Stack<ShoppingCartItem> temporaryStack = new Stack<>();

    //================================================================================
    // FlowPane
    //================================================================================

    public void initShoppingCartFlowPane(){
        shoppingCartFlowPane.setOrientation(Orientation.HORIZONTAL); //NOT VERTICAL
        shoppingCartFlowPane.getChildren().clear();
        shoppingCartFlowPane.setVgap(7);
        shoppingCartFlowPane.setPadding(new Insets(9,6,9,6));
        shoppingCart.addShoppingCartListener(scl);
        shoppingCart.clear();
    }

    public void updateShoppingCart(){

        shoppingCartFlowPane.getChildren().clear();
        temporaryStack.clear();

        int iterations = 0;
        double sum = 0;
        for(ShoppingItem s: shoppingCart.getItems()){
            iterations++;
            temporaryStack.push(new ShoppingCartItem(this, s));
            sum += (s.getAmount() * s.getProduct().getPrice());
            sum = Math.round(sum * 100.0)/100.0;
        }
        for(int i = 0; i <= iterations; i++){
            if(!temporaryStack.isEmpty()){
                shoppingCartFlowPane.getChildren().add(temporaryStack.pop());
            }
        }

        sumLabel.setText(sum + " kr");

        if(!shoppingCart.getItems().isEmpty()){
            IMatBackendEngine.getInstance().morePliancyNext(checkoutButton);
        }else{
            IMatBackendEngine.getInstance().lessPliancyNext(checkoutButton);
        }

    }
    //================================================================================
    // Functions connected to backend
    //================================================================================

    public void createItemtoShoppingCart(ShoppingItem shoppingItem){
        shoppingCart.addItem(shoppingItem);

    }

    public void increaseItem(ShoppingItem shoppingItem, double amount){
        shoppingItem.setAmount(shoppingItem.getAmount() + amount);
    }
    public void decreaseItem(ShoppingItem shoppingItem, double amount){
        shoppingItem.setAmount(shoppingItem.getAmount() - amount);
    }

    public void removeShoppingItem(ShoppingItem shoppingItem, ShoppingCartItem shoppingCartItem){
        shoppingCart.removeItem(shoppingItem);
        updateShoppingCart();
    }

    public void updateProductFlowpane(String category){
        productFlowPane.getChildren().clear();
        String pmet;

        List<Integer> currentGOE = new ArrayList<>();
        List<main.Product.Product> listofProducts = new ArrayList<>();

        for(Product pd: ProductHandler.getProductsFromCategory(category)){
            for(ShoppingItem st: shoppingCart.getItems()){
                if(st.getProduct().getProductId() == pd.getProductId() && st.getAmount() > 0)
                {
                    if(!currentGOE.contains(pd.getProductId())){
                        main.Product.Product tempProd = new main.Product.Product(this, pd);
                        tempProd.buyButtonVisible(false);
                        pmet = String.valueOf((int)st.getAmount());
                        tempProd.setQuantityLabel(pmet);

                        currentGOE.add(pd.getProductId());
                        listofProducts.add(tempProd);
                    }

                }
            }
        }
        for(Product PT : ProductHandler.getProductsFromCategory(category)){
            if(!currentGOE.contains(PT.getProductId())){
                listofProducts.add(new main.Product.Product(this, PT));
            }
        }

        //BubbleSort
        int n = listofProducts.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (listofProducts.get(j).getProductIDDD() > listofProducts.get(j+1).getProductIDDD())
                {
                    // swap arr[j+1] and arr[i]
                    main.Product.Product temp = listofProducts.get(j);
                    listofProducts.set(j, listofProducts.get(j+1));
                    listofProducts.set(j+1, temp);
                }
            }
        }

        for(main.Product.Product pepe: listofProducts){
            productFlowPane.getChildren().add(pepe);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------//



    //------------------------------------------------------------Searchbar-----------------------------------------------------------------------//


    @FXML TextField searchBarTextField;
    List<Product> searchedItems;
    @FXML Label searchResultLabel;

    public void theRealResetOfSearchBar(){
        searchBarTextField.setText("Sök tusentals varor... ");
    }

    @FXML
    public void searchBarKeyPressed(javafx.scene.input.KeyEvent evt) {
        //if(evt.getCode() == KeyCode.ENTER) {  //__________________________Keep if you want to search after "ENTER" keypress.

            shoppingTabPane.toFront();
            homepaneIsFront = false;


            String userInput = searchBarTextField.getText();

            if(userInput.isEmpty()){
                homePane.toFront();
                homepaneIsFront= true;

            }else{
                searchResultLabel.setVisible(true);
                searchResultLabel.setText("Sökresultat för " + "'" + userInput + "'");
                searchedItems = dataHandler.findProducts(userInput);
                updateProductsWithSearch(userInput);
                populateSubCategoryFlowPane("search");


            }
      //  }
    }

    public void updateProductsWithSearch(String userInput){

        productFlowPane.getChildren().clear();

        List<Integer> currentGOE = new ArrayList<>();
        List<main.Product.Product> listOfProducts = new ArrayList<>();

        for(Product p: searchedItems){
            for(ShoppingItem st: shoppingCart.getItems()){
                if(st.getProduct().equals(p) && st.getAmount() > 0){
                    if(!currentGOE.contains(p.getProductId())){
                        main.Product.Product tempProd = new main.Product.Product(this, p);
                        tempProd.buyButtonVisible(false);
                        tempProd.setQuantityLabel(String.valueOf((int)st.getAmount()));

                        currentGOE.add(p.getProductId());
                        listOfProducts.add(tempProd);
                    }
                }
            }
        }

        for(Product pp: searchedItems){
            if(!currentGOE.contains(pp.getProductId())){
                listOfProducts.add(new main.Product.Product(this, pp));
            }
        }

        //BubbleSort
        int n = listOfProducts.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (listOfProducts.get(j).getProductIDDD() > listOfProducts.get(j+1).getProductIDDD())
                {
                    // swap arr[j+1] and arr[i]
                    main.Product.Product temp = listOfProducts.get(j);
                    listOfProducts.set(j, listOfProducts.get(j+1));
                    listOfProducts.set(j+1, temp);
                }
            }
        }
        for(main.Product.Product pepe: listOfProducts){
            productFlowPane.getChildren().add(pepe);
        }
        categoryPane.toFront();
    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------------//


    public void hideSubCategories(){
        categoryPane.setDividerPositions(0.0051);
        categoryPane.setPrefHeight(591);
        productFlowPane.setPrefHeight(588);
        productsScrollPane.setPrefHeight(588);

    }
    public void showSubCategories(){
        categoryPane.setDividerPositions(0.0924);
        categoryPane.setPrefHeight(591);
        productFlowPane.setPrefHeight(538);
        productsScrollPane.setPrefHeight(538);
    }

    //================================================================================
    // Init
    //================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupPurchaseFeedback();
        setupProductFlowpane();
        initShoppingCartFlowPane();
        setUpCarousel();
        homepaneIsFront = true;
        populateCarouselFlowPane();
        carouselScrollPane.setHmax(100);
        searchResultLabel.setVisible(false);
        IMatBackendEngine.getInstance().lessPliancyNext(checkoutButton);



        IMatBackendEngine.getInstance().clearActiveTab();
        IMatBackendEngine.getInstance().setActiveTab(shoppingTab);

        // To test favourite section.
        /*dataHandler.addFavorite(70);
        dataHandler.addFavorite(32);*/
        for(Double d: categoryPane.getDividerPositions()){
            System.out.println(d);
        }
    }

    private void setUpCarousel(){
        carouselFlowPane.setPadding(new Insets(4,4,4,4));
        carouselFlowPane.setHgap(8);
        carouselFlowPane.setOrientation(Orientation.VERTICAL);
    }
    private void setupPurchaseFeedback(){
        mainAnchorPane.getChildren().add(purchaseFeedback);
        purchaseFeedback.setLayoutX(210);
        purchaseFeedback.setLayoutY(50);
    }
    private void setupProductFlowpane(){
        productsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //Removes horizontal scrollPane
        productFlowPane.setPadding(new Insets(9,0,9,6));
        productFlowPane.setHgap(8);
        productFlowPane.setVgap(15);
    }


    public void openProductView(Product product, main.Product.Product productClass){

        detailedViewPage.setProduct(productClass);

        mainAnchorPane.getChildren().add(blurPane);
        purchaseFeedback.toFront();
        blurPane.setPrefHeight(mainAnchorPane.getPrefHeight());
        blurPane.setPrefWidth(mainAnchorPane.getPrefWidth());
        blurPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.65)");
        detailedViewPage.populateProductDetailedView(product);
        blurBackground();
        mainAnchorPane.getChildren().add(detailedViewPage);
        detailedViewPage.setLayoutX(120);
        detailedViewPage.setLayoutY(130);
        blurPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                closeDetailedView();
            }
        });

    }

    private void blurBackground() {
        for(Node n : mainAnchorPane.getChildren()){
            if(!(n.equals(purchaseFeedback))) {
                n.setEffect(new GaussianBlur());
            }
        }
    }

    private void undoBlurBackground() {
        for(Node n : mainAnchorPane.getChildren()){
            n.setEffect(null);
        }
    }


    public void onButtonHover(){

    }


}


