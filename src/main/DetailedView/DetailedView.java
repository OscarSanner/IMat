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

import java.io.IOException;

public class DetailedView extends AnchorPane {

    private se.chalmers.cse.dat216.project.Product product;
    private iMatBackendController parentController;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();


    //Frontend elements
    @FXML
    private Label quantityLabel;
    @FXML private Button increaseButton;
    @FXML private Button decreaseButton;
    @FXML private Button addButton;
    @FXML private Button exitButton;

    //Elements connected to backend.
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label productCategoryLabel;
    @FXML private Label priceLabel;
    @FXML private TextArea productTextArea;

    public DetailedView(iMatBackendController backendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailedView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentController = backendController;
        //Fix load error with Detailed View

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    //TODO: Add buyButton interaction

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
