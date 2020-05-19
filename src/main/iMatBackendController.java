package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import main.CustomerSupport.CustomerSupport;
import main.DetailedView.DetailedView;
import main.ListTitlePane.ListTitlePane;
import main.OrderTabTitlePane.OrderTabTitlePane;
import main.ShoppingCart.ShoppingCart;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.List;
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
    public FlowPane subCategoryFlowPane;
    @FXML
    public FlowPane productFlowPane;
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
            System.out.println(customer.getFirstName());
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

    Customer customer = IMatDataHandler.getInstance().getCustomer();

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
//TODO: För att komma åt den FlowPane där vi skall ha subkategorier: kalla på "subCategoryFlowPane"
//TODO: För att komma åt den FLowPane där vi skall ha produkter: kalla på: ""

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

    }

    //kopplad till videoImage.
    public void onVideoImagePressed(){

    }

    public void onFavouritesButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();

    }

    public void onOffersButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
    }

    public void onMeatAndFishButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kött & Fisk");
        populateProductFlowpane("Kött & Fisk");
    }

    public void onVeggiesButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Frukt & Grönt");
        populateProductFlowpane("Frukt & Grönt");
    }

    public void onDrinkButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Dryck");
        populateProductFlowpane("Dryck");
    }

    public void onDairyButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Mejeri");
    }

    public void onPantryButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Skafferi");
    }

    public void onSnacksButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Snacks");
    }

    public void onSpicesButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kryddor");
    }

    private void populateSubCategoryFlowPane(String category) {
        List<Button> buttons = new ArrayList<Button>();

        switch (category){
            case "Kött & Fisk":{
                Button meat = new Button("Kött");
                Button fish = new Button("Fisk");
                buttons.add(meat);
                buttons.add(fish);
                break;
            }
            case "Frukt & Grönt": {
                Button berries = new Button("Bär");
                Button fruit = new Button("Frukt");
                Button veggies = new Button("Grönsaker");
                buttons.add(berries);
                buttons.add(veggies);
                buttons.add(fruit);
                break;
            }
            case "Dryck": {
                Button warmDrinks = new Button("Varma drycker");
                Button coldDrinks = new Button("Kalla drycker");
                buttons.add(warmDrinks);
                buttons.add(coldDrinks);
                break;
        }
            case "Mejeri": {
                Button dairy = new Button("Mejeri");
                buttons.add(dairy);
                break;
            }
            case "Skafferi":  {
                Button flourSugarSalt = new Button("Mjöl, socker, salt");
                Button pasta = new Button("Pasta");
                Button potatoRice = new Button("Potatis, ris");
                buttons.add(flourSugarSalt);
                buttons.add(pasta);
                buttons.add(potatoRice);
                break;
            }
            case "Snacks" : {
                Button sweets = new Button("Sötsaker");
                Button seeds = new Button("Nötter och frön");
                buttons.add(sweets);
                buttons.add(seeds);
                break;
            }
            case "Kryddor": {
                Button spices = new Button("Kryddor");
                buttons.add(spices);
                break;
            }
        }
        subCategoryFlowPane.getChildren().clear();
        subCategoryFlowPane.setPadding(new Insets(9,30,30,9));
        subCategoryFlowPane.setHgap(10);
        for(Button button : buttons){
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    populateProductFlowpane(button.getText());
                }
            });
            button.setPrefSize(150,35);
            subCategoryFlowPane.getChildren().add(button);
        }
    }

    private void populateProductFlowpane( String button) {
        productFlowPane.getChildren().clear();
        for(Product p : ProductHandler.getProductsFromCategory(button)){
            main.Product.Product productCard = new main.Product.Product(this, p);
                productFlowPane.getChildren().add(productCard);
        }
    }
}

