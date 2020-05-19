package main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.CustomerSupport.CustomerSupport;
import main.DetailedView.DetailedView;
import main.ListTitlePane.ListTitlePane;
import main.OrderTabTitlePane.OrderTabTitlePane;
import main.ShoppingCart.ShoppingCart;
import se.chalmers.cse.dat216.project.*;

import java.util.Map;

public class iMatBackendController {

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    public StackPane orderTabPane;
    @FXML
    public StackPane listTabPane;
    @FXML
    public StackPane myPageTabPane;
    @FXML
    public StackPane shoppingTabPane;
    @FXML
    public StackPane homePane;
    @FXML
    public Button orderTabButton;
    @FXML
    public Button listTabButton;
    @FXML
    public Button myPageTabButton;
    @FXML
    public Button shoppingTabButton;
    @FXML
    public Button testButton;
    @FXML
    public AnchorPane mainAnchorPane;
    @FXML
    public Button checkoutButton;
    @FXML
    public Accordion orderAccordion;
    @FXML
    public Accordion listAccordion;
    @FXML
    public SplitPane categoryPane;

    public CustomerSupport customerSupportPage = new CustomerSupport(this);
    public ShoppingCart shoppingCartPage = new ShoppingCart(this);
    public DetailedView detailedViewPage = new DetailedView(this);

    //public Customer customer = IMatDataHandler.getInstance().getCustomer();

    @FXML
    private javafx.scene.control.TextField firstName;
    @FXML
    private javafx.scene.control.TextField lastName;
    @FXML
    private javafx.scene.control.TextField mobileNumber;
    @FXML
    private javafx.scene.control.TextField email;
    @FXML
    private javafx.scene.control.TextField address;
    @FXML
    private javafx.scene.control.TextField postcode;
    @FXML
    private javafx.scene.control.TextField area;
    @FXML
    private javafx.scene.control.TextField cardNumber;
    @FXML
    private Label postCodeAmountErrorLabel;
    @FXML
    private Label mobileAmountErrorLabel;
    @FXML
    private Label emailStyleErrorLabel;
    @FXML
    private Label postCodeStyleErrorLabel;
    @FXML
    private Label mobileStyleErrorLabel;
    @FXML
    private javafx.scene.control.TextField year;
    @FXML
    private javafx.scene.control.TextField cvc;
    @FXML
    private Button saveButton;

    @FXML
    public void onOrderTabSelect(){
        orderTabPane.toFront();
        populateOrderPane();
    }

    private void populateOrderPane() {
        for(Order o : IMatDataHandler.getInstance().getOrders()){
            orderAccordion.getPanes().add(new OrderTabTitlePane(o));
        }
    }

    @FXML
    public void onShoppingTabSelect(){
        shoppingTabPane.toFront();
    }

    @FXML
    public void onMyPageTabSelect(){
        myPageTabPane.toFront();
        loadUserInfo();
        makeLblsInvisible();
    }

    @FXML
    public void onListtabSelect(){
        listTabPane.toFront();
        populateListPane();
    }

    private void populateListPane() {
        for(Map.Entry<Order, String> entry : IMatBackendEngine.getInstance().savedOrders.entrySet()){
            listAccordion.getPanes().add(new ListTitlePane(entry.getKey(), entry.getValue()));
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
        mainAnchorPane.getChildren().add(shoppingCartPage);
        saveCustomerInfo();



    }

    @FXML
    public void onSaveButtonPressed(){


        if(allFilledInCorrectly()) {
            saveCustomerInfo();

        }

    }

    @FXML
    public void closeDetailedView(){
        mainAnchorPane.getChildren().remove(detailedViewPage);
    }

    /*public void openProductView(Product product){
        detailedViewPage.populateProductDetailedView(product);
        mainAnchorPane.getChildren().add(detailedViewPage);
    }*/

    public Image getSquareImage(Image image){

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if(image.getWidth() > image.getHeight()){
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int)(image.getWidth() - width)/2;
            y = 0;
        }

        else if(image.getHeight() > image.getWidth()){
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height)/2;
        }

        else{
            //Width equals Height, return original image
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
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
    }

    //----------------FAKTISK KOD-----------------



//------------------------------------------------------------MinSida-----------------------------------------------------------------------//

    //Customer customer = IMatDataHandler.getInstance().getCustomer();

    @FXML
    private void loadUserInfo(){
        Customer customer = IMatDataHandler.getInstance().getCustomer();
      // CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        email.setText(customer.getEmail());
        address.setText(customer.getAddress());
        postcode.setText(customer.getPostCode());
        mobileNumber.setText(customer.getMobilePhoneNumber());
        area.setText(customer.getPostAddress());
       // cardNumber.setText(creditCard.getCardNumber());
        //year.setText(creditCard.getValidYear() && creditCard.getValidMonth());
        //cvc.setText(creditCard.getVerificationCode());

    }
    private void saveCustomerInfo() {
        Customer customer = IMatDataHandler.getInstance().getCustomer();
        customer.setFirstName(firstName.getText());
        customer.setLastName(lastName.getText());
        customer.setMobilePhoneNumber(mobileNumber.getText());
        customer.setEmail(email.getText());
        customer.setAddress(address.getText());
        customer.setPostAddress(area.getText());
        customer.setPostCode(postcode.getText());


    }

    private boolean isCustomerInfoComplete(){
        return isInEmailForm(email) && isComplete(mobileNumber,getMinAllowedLength(mobileNumber))
                && containsDigitsOnly(mobileNumber) && containsDigitsOnly(postcode)
                && isComplete(postcode,getMinAllowedLength(postcode));
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
        } else {
            return 0;
        }
    }

    private boolean allFilledInCorrectly() {

        if (isCustomerInfoComplete()) {
            emailStyleErrorLabel.setVisible(false);
            mobileAmountErrorLabel.setVisible(false);
            mobileStyleErrorLabel.setVisible(false);
            postCodeAmountErrorLabel.setVisible(false);
            postCodeStyleErrorLabel.setVisible(false);

            return true;

        } else{

            if (!(email.getText().isEmpty()) && !(isInEmailForm(email))){//*|| !isInEmailForm(emailTextField)*//*) {
                emailStyleErrorLabel.setVisible(true);
            }
            if (!containsDigitsOnly(mobileNumber) && !(mobileNumber.getText().isEmpty())) {
                mobileAmountErrorLabel.setVisible(false);
                mobileStyleErrorLabel.setVisible(true);
            } else if (!isComplete(mobileNumber, getMinAllowedLength(mobileNumber)) && !(mobileNumber.getText().isEmpty())) {
                mobileAmountErrorLabel.setVisible(true);
                mobileStyleErrorLabel.setVisible(false);
            }
            if (!containsDigitsOnly(postcode) && !(postcode.getText().isEmpty())) {
                postCodeStyleErrorLabel.setVisible(true);
                postCodeAmountErrorLabel.setVisible(false);
            } else if (!isComplete(postcode, getMinAllowedLength(postcode)) && !(postcode.getText().isEmpty())) {
                postCodeStyleErrorLabel.setVisible(false);
                postCodeAmountErrorLabel.setVisible(true);
            }

            return false;
        }
    }

    private void makeLblsInvisible() {
        mobileStyleErrorLabel.setVisible(false);
        mobileAmountErrorLabel.setVisible(false);
        emailStyleErrorLabel.setVisible(false);
        postCodeAmountErrorLabel.setVisible(false);
        postCodeStyleErrorLabel.setVisible(false);
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------//



    public void openProductView(Product product){
        detailedViewPage.populateProductDetailedView(product);
        mainAnchorPane.getChildren().add(detailedViewPage);
    }


//----------------------------------------------------------KOD FÖR KATEGORISIDAN----------------------------------------------------------------------------//
//TODO: För att komma åt gridden, kalla på:

    //kopplad till högerpilen vid carouselen.
    @FXML
    public void rotateCarouselRight(){

    }

    //kopplad till vänsterpilen vid carouselen.
    @FXML
    public void rotateCarouselLeft(){

    }

    //För att komma åt CarouselFlowPane, kalla på "carouselFlowPane".
    @FXML
    public void populateCarouselFlowPane(){

    }

    //kopplad till newsImage.
    public void onNewsImagePressed(){
        categoryPane.toFront();
    }

    //kopplad till videoImage.
    public void onVideoImagePressed(){
        categoryPane.toFront();
    }

    public void onFavouritesButtonPressed(){
        categoryPane.toFront();
    }

    public void onOffersButtonPressed(){
        categoryPane.toFront();
    }

    public void onMeatAndFishButtonPressed(){
        categoryPane.toFront();
    }

    public void onVeggiesButtonPressed(){
        categoryPane.toFront();
    }

    public void onDrinkButtonPressed(){
        categoryPane.toFront();
    }

    public void onDairyButtonPressed(){
        categoryPane.toFront();
    }

    public void onPantryButtonPressed(){
        categoryPane.toFront();
    }

    public void onSnacksButtonPressed(){
        categoryPane.toFront();
    }

    public void onSpicesButtonPressed(){
        categoryPane.toFront();
    }

}

