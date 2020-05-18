package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.CustomerSupport.CustomerSupport;
import main.DetailedView.DetailedView;
import main.PersonalData.PersonalData;
import main.ShoppingCart.ShoppingCart;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

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





}

