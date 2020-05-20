package main.DetailedView;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class DetailedView extends AnchorPane {


    //Elements connected to backend.
    private se.chalmers.cse.dat216.project.Product product;
    private iMatBackendController parentController;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label productCategoryLabel;
    @FXML private Label priceLabel;
    @FXML private TextArea productTextArea;

    //Frontend elements
    @FXML
    private Label quantityLabel;
    @FXML private Button increaseButton; //may be removed
    @FXML private Button decreaseButton; //may be removed
    @FXML private Button addButton;      //may be removed
    @FXML private Button exitButton;     //may be removed


    public DetailedView(iMatBackendController backendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailedView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentController = backendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    public void closeDetailedView(){ parentController.closeDetailedView();}
    @FXML
    public void addToShoppingCart(){
        try
        {
            double temporaryNumber = Double.parseDouble(quantityLabel.toString());
            ShoppingItem shoppingItem = new ShoppingItem(product, temporaryNumber);
            shoppingCart.addItem(shoppingItem);
            parentController.purchaseFeedback.startAnimation(product, quantityLabel.getText(), product.getUnitSuffix());
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("Could not add item to shopping cart. String not convertable to Double.");
        }

    }
    @FXML
    public void increaseQuantity(Event event){
        try
        {
            int temporaryNumber = Integer.parseInt(quantityLabel.toString());
            temporaryNumber++;
            quantityLabel.setText(String.valueOf(temporaryNumber) + " kr");
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("String not convertable to int");
        }
    }
    @FXML
    public void decreaseQuantity(Event event){
        try
        {
            int temporaryNumber = Integer.parseInt(quantityLabel.toString());
            temporaryNumber--;
            if(temporaryNumber > 0){
                quantityLabel.setText(String.valueOf(temporaryNumber));
            } else{
                quantityLabel.setText(String.valueOf(0));
            }
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("String not convertable to int");
        }
    }

    public void populateProductDetailedView(Product product){

        this.product = product;
        this.productImage.setImage(parentController.getSquareImage(dataHandler.getFXImage(product)));
        this.productNameLabel.setText(product.getName());

        //Needs to be changed depending on our categories
        this.productCategoryLabel.setText(product.getCategory().name());

        this.priceLabel.setText(String.valueOf(product.getPrice()) + " kr");

        //Product information is missing
        this.productTextArea.setText("PRODUCT INFORMATION IS MISSING.--> Product.java");

    }

}
