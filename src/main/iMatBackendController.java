package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;

import javafx.scene.control.Button;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;



import javafx.scene.effect.GaussianBlur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.CustomerSupport.CustomerSupport;
import main.DetailedView.DetailedView;

import main.ListTitlePane.ListTitlePane;
import main.OrderTabTitlePane.OrderTabTitlePane;
import main.ShoppingCart.ShoppingCart;
import main.ShoppingCartItem.ShoppingCartItem;
import se.chalmers.cse.dat216.project.*;

import java.util.*;

import main.PersonalData.PersonalData;
import main.ShoppingCart.ShoppingCart;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import main.ListTitlePane.ListTitlePane;
import main.OrderTabTitlePane.OrderTabTitlePane;
import main.PurchaseFeedback.PurchaseFeedback;
import main.ShoppingCart.ShoppingCart;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;


public class iMatBackendController implements Initializable {

    //----------------NAVIGATION OCH INIT------------------

    @FXML public StackPane orderTabPane;
    @FXML public StackPane listTabPane;
    @FXML public StackPane myPageTabPane;
    @FXML public StackPane shoppingTabPane;
    @FXML public StackPane homePane;
    @FXML public FlowPane subCategoryFlowPane;
    @FXML public FlowPane productFlowPane;
    @FXML public Button orderTabButton;
    @FXML public Button listTabButton;
    @FXML public Button myPageTabButton;
    @FXML public Button shoppingTabButton;
    @FXML public Button testButton;
    @FXML public AnchorPane mainAnchorPane;
    @FXML public Button checkoutButton;
    @FXML public Accordion orderAccordion;
    @FXML public Accordion listAccordion;
    @FXML public SplitPane categoryPane;
    @FXML public ScrollPane productsScrollPane;

    @FXML public Label sumLabel;
    @FXML public ImageView escapeHatchImage;

    public CustomerSupport customerSupportPage = new CustomerSupport(this);
    public ShoppingCart shoppingCartPage = new ShoppingCart(this);
    public DetailedView detailedViewPage = new DetailedView(this);

    public PurchaseFeedback purchaseFeedback = new PurchaseFeedback(this);

    public StackPane blurPane = new StackPane();


    //public Customer customer = IMatDataHandler.getInstance().getCustomer();

    @FXML private javafx.scene.control.TextField firstName;
    @FXML private javafx.scene.control.TextField lastName;
    @FXML private javafx.scene.control.TextField mobileNumber;
    @FXML private javafx.scene.control.TextField email;
    @FXML private javafx.scene.control.TextField address;
    @FXML private javafx.scene.control.TextField postcode;
    @FXML private javafx.scene.control.TextField area;
    @FXML private javafx.scene.control.TextField cardNumber;
    @FXML private Label postCodeAmountErrorLabel;
    @FXML private Label mobileAmountErrorLabel;
    @FXML private Label emailStyleErrorLabel;
    @FXML private Label postCodeStyleErrorLabel;
    @FXML private Label mobileStyleErrorLabel;
    @FXML private javafx.scene.control.TextField year;
    @FXML private javafx.scene.control.TextField cvc;
    @FXML private Button saveButton;

    @FXML
    public void onOrderTabSelect(){
        orderTabPane.toFront();
        populateOrderPane();
        calculateAccordionSize(orderAccordion);
    }

    private void calculateAccordionSize(Accordion accordion) {
        if(accordion.getExpandedPane() == null){
            accordion.setPrefHeight(67 * accordion.getChildrenUnmodifiable().size());
        }else{
            System.out.println(accordion.getChildrenUnmodifiable().size());
            ICustomTitledPane activePane = (ICustomTitledPane) accordion.getExpandedPane();
            System.out.println(activePane.getOrder().getItems().size());
            accordion.setPrefHeight((67 * accordion.getChildrenUnmodifiable().size()) + (60 * activePane.getOrder().getItems().size()));
        }
    }

    private void populateOrderPane() {
        orderAccordion.getPanes().clear();
        for(Order o : IMatDataHandler.getInstance().getOrders()){
            OrderTabTitlePane ot = new OrderTabTitlePane(o);
            orderAccordion.getPanes().add(ot);
            ot.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    calculateAccordionSize(orderAccordion);
                }
            });
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
        calculateAccordionSize(listAccordion);
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
        mainAnchorPane.getChildren().add(shoppingCartPage);
        saveCustomerInfo();
        shoppingCartPage.populateShoppingCartPage();
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
        mainAnchorPane.getChildren().remove(blurPane);
        undoBlurBackground();
    }


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






//----------------------------------------------------------KOD FÖR KATEGORISIDAN-------------------------------------------------------------------------------//
//TODO: För att komma åt den FlowPane där vi skall ha subkategorier: kalla på "subCategoryFlowPane"
//TODO: För att komma åt den FLowPane där vi skall ha produkter: kalla på: ""

    public String currentCategory;
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
        populateProductFlowpane("Mina Favoriter");


        /*
        List<Integer> currentGOE = new ArrayList<>();
        List<main.Product.Product> listOfProducts = new ArrayList<>();

        for(Product p: dataHandler.favorites()){
            for(ShoppingItem st: shoppingCart.getItems()){
                if(st.getProduct().equals(p) && st.getAmount() > 0){
                    if(!currentGOE.contains(p.getProductId())){
                        main.Product.Product tempProd = new main.Product.Product(this, p);
                        tempProd.buyButtonVisible(false);
                        tempProd.setQuantityLabel(String.valueOf(st.getAmount()));

                        currentGOE.add(p.getProductId());
                        listOfProducts.add(tempProd);
                    }
                }
            }
        }

        for(Product pp: dataHandler.favorites()){
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
        categoryPane.toFront();*/
    }

    public void onOffersButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
    }
    public void onBreadButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Bröd");
        populateProductFlowpane("Bröd");
        productsScrollPane.setVvalue(0);
        categoryPane.toFront();
    }

    public void onMeatAndFishButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kött & Fisk");
        populateProductFlowpane("Kött & Fisk");
        productsScrollPane.setVvalue(0); //Resets the scrollpane to the top
        shoppingTabPane.toFront();
    }

    public void onVeggiesButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Frukt & Grönt");
        populateProductFlowpane("Frukt & Grönt");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
    }

    public void onDrinkButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Dryck");
        populateProductFlowpane("Dryck");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
    }

    public void onDairyButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Mejeri");
        populateProductFlowpane("Mejeri");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
    }

    public void onPantryButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Skafferi");
        populateProductFlowpane("Skafferi");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
    }

    public void onSnacksButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Snacks");
        populateProductFlowpane("Snacks");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
    }

    public void onSpicesButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kryddor");
        populateProductFlowpane("Kryddor");
        productsScrollPane.setVvalue(0);
        shoppingTabPane.toFront();
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
            case "Bröd": {
                Button bread = new Button("Bröd");
                buttons.add(bread);
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
                }
            });
            button.setPrefSize(150,35);
            subCategoryFlowPane.getChildren().add(button);
        }
    }

    private void populateProductFlowpane(String category) {
        List<main.Product.Product> listofProducts = new ArrayList<>();

        productFlowPane.getChildren().clear();

        setupProductFlowpane();

        currentCategory = category;
        main.Product.Product temporaryProduct;
        for(Product p : ProductHandler.getProductsFromCategory(category)){
            temporaryProduct = new main.Product.Product(this, p);

            for(ShoppingItem s: dataHandler.getShoppingCart().getItems()){
                if(s.getProduct().equals(p)){
                    temporaryProduct.setQuantityLabel(String.valueOf(s.getAmount()));
                    temporaryProduct.buyButtonVisible(false);
                }
            }

            listofProducts.add(temporaryProduct);
            //listofProducts.add(new main.Product.Product(this, p));

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


    public void showPurchaseFeedback(Product product, String quantity, String unitSuffix){
        purchaseFeedback.setFeedbackLabel(quantity + " " + unitSuffix + " " + product.getName() + " har lagts till i varukorgen");
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

    private List<ShoppingCartItem> visualShoppingItems = new ArrayList<>();

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
        visualShoppingItems.clear();

        int sum = 0;
        for(ShoppingItem s: shoppingCart.getItems()){
            visualShoppingItems.add(new ShoppingCartItem(this, s));
            sum += (int)(s.getAmount() * s.getProduct().getPrice());
        }
        for(ShoppingCartItem sI: visualShoppingItems){
            shoppingCartFlowPane.getChildren().add(sI);
        }

        sumLabel.setText(String.valueOf(sum) + " kr");

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
                        pmet = String.valueOf(st.getAmount());
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

    public void resetSearchBarText(){ //Nollställer searchbar
        searchBarTextField.setText("");
    }
    @FXML
    public void searchBarKeyPressed(javafx.scene.input.KeyEvent evt) {
        //if(evt.getCode() == KeyCode.ENTER) {  //__________________________Keep if you want to search after "ENTER" keypress.

            if(searchBarTextField.getText().equals("Sök miljontals varor...")){
                resetSearchBarText();
            }


            String userInput = searchBarTextField.getText();
            searchedItems = dataHandler.findProducts(userInput);
            if(searchedItems.isEmpty()){
                
            }else{
                updateProductsWithSearch(userInput);

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
                        tempProd.setQuantityLabel(String.valueOf(st.getAmount()));

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


    //================================================================================
    // Init
    //================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupPurchaseFeedback();
        setupProductFlowpane();
        initShoppingCartFlowPane();

        // To test favourite section.
       /* dataHandler.addFavorite(70);
        dataHandler.addFavorite(32);*/
    }
    private void setupPurchaseFeedback(){
        mainAnchorPane.getChildren().add(purchaseFeedback);
        purchaseFeedback.setLayoutX(145);
        purchaseFeedback.setLayoutY(36);
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
        detailedViewPage.setLayoutX(85);
        detailedViewPage.setLayoutY(110);
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


}

