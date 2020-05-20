package main;

import javafx.fxml.FXML;
<<<<<<< Updated upstream
import javafx.scene.control.Button;
=======
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
>>>>>>> Stashed changes
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.CustomerSupport.CustomerSupport;
import main.DetailedView.DetailedView;
<<<<<<< Updated upstream
import main.PersonalData.PersonalData;
import main.ShoppingCart.ShoppingCart;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
=======
import main.ListTitlePane.ListTitlePane;
import main.OrderTabTitlePane.OrderTabTitlePane;
import main.PurchaseFeedback.PurchaseFeedback;
import main.ShoppingCart.ShoppingCart;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
>>>>>>> Stashed changes

public class iMatBackendController implements Initializable {

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
    public Button orderTabButton;
    @FXML
    public Button listTabButton;
    @FXML
    public Button myPageTabButton;
    @FXML
    public Button shoppingTabButton;
    @FXML
    public AnchorPane mainAnchorPane;
    @FXML
    public Button checkoutButton;

    public CustomerSupport customerSupportPage = new CustomerSupport(this);
    public ShoppingCart shoppingCartPage = new ShoppingCart(this);
    public DetailedView detailedViewPage = new DetailedView(this);
    public PurchaseFeedback purchaseFeedback = new PurchaseFeedback(this);

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
    private javafx.scene.control.TextField year;
    @FXML
    private javafx.scene.control.TextField cvc;

    @FXML
    public void onOrderTabSelect(){
        orderTabPane.toFront();
    }

    @FXML
    public void onShoppingTabSelect(){
        shoppingTabPane.toFront();
    }

    @FXML
    public void onMyPageTabSelect(){
        myPageTabPane.toFront();
        loadUserInfo();
    }

    @FXML
    public void onListtabSelect(){
        listTabPane.toFront();
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
    }

    @FXML
    public void closeDetailedView(){
        mainAnchorPane.getChildren().remove(detailedViewPage);
    }

    public void openProductView(Product product){
        detailedViewPage.populateProductDetailedView(product);
        mainAnchorPane.getChildren().add(detailedViewPage);
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

    //----------------FAKTISK KOD-----------------

    @FXML
    private void loadUserInfo(){
        Customer customer = IMatDataHandler.getInstance().getCustomer();
        CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();
        firstName.setText(customer.getFirstName()); //customer.getFirstName()
        //resetBorder(forNameChange);
        lastName.setText(customer.getLastName());
        //resetBorder(lastNameChange);
        email.setText(customer.getEmail());
        //resetBorder(emailChange);
        address.setText(customer.getAddress());
        //resetBorder(adrChange);
        postcode.setText(customer.getPostCode());
        //resetBorder(postNrChange);
        mobileNumber.setText(customer.getMobilePhoneNumber());
        area.setText(customer.getPostAddress());
        cardNumber.setText(creditCard.getCardNumber());
        //year.setText(creditCard.getValidYear() && creditCard.getValidMonth());
        //cvc.setText(creditCard.getVerificationCode());


    }





<<<<<<< Updated upstream
=======
    }

    public void onMeatAndFishButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kött & Fisk");
        populateProductFlowpane("Kött & Fisk");
        productsScrollPane.setVvalue(0); //Resets the scrollpane to the top

    }

    public void onVeggiesButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Frukt & Grönt");
        populateProductFlowpane("Frukt & Grönt");
        productsScrollPane.setVvalue(0);
    }

    public void onDrinkButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Dryck");
        populateProductFlowpane("Dryck");
        productsScrollPane.setVvalue(0);
    }

    public void onDairyButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Mejeri");
        populateProductFlowpane("Mejeri");
        productsScrollPane.setVvalue(0);
    }

    public void onPantryButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Skafferi");
        populateProductFlowpane("Skafferi");
        productsScrollPane.setVvalue(0);
    }

    public void onSnacksButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Snacks");
        populateProductFlowpane("Snacks");
        productsScrollPane.setVvalue(0);
    }

    public void onSpicesButtonPressed(){
        categoryPane.toFront();
        subCategoryFlowPane.getChildren().removeAll();
        productFlowPane.getChildren().removeAll();
        populateSubCategoryFlowPane("Kryddor");
        populateProductFlowpane("Kryddor");
        productsScrollPane.setVvalue(0);
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

    private void populateProductFlowpane( String category) {
        productFlowPane.getChildren().clear();

        for(Product p : ProductHandler.getProductsFromCategory(category)){
                productFlowPane.getChildren().add(new main.Product.Product(this, p));
        }
    }



    public void openProductView(Product product){
        detailedViewPage.populateProductDetailedView(product);
        mainAnchorPane.getChildren().add(detailedViewPage);
        detailedViewPage.setLayoutX(85);
        detailedViewPage.setLayoutY(110);
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


    //================================================================================
    // Init
    //================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupPurchaseFeedback();
        setupProductFlowpane();

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
>>>>>>> Stashed changes
}

