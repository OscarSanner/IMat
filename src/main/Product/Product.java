package main.Product;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.*;
import java.io.IOException;

public class Product extends AnchorPane {

     @FXML ImageView itemImage;
     @FXML Label itemLabel;
     @FXML Label priceLabel;
     @FXML Button buyButton;

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private iMatBackendController parentController;
    private se.chalmers.cse.dat216.project.Product product;

    public Product(iMatBackendController iMatBackendController, se.chalmers.cse.dat216.project.Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = iMatBackendController;
        this.product = product;

        this.itemImage.setImage(parentController.getSquareImage(dataHandler.getFXImage(product)));
        this.itemLabel.setText(product.getName());
        this.priceLabel.setText(String.valueOf(product.getPrice()) + " kr");
    }

    //TODO: Add buyButton interaction


    @FXML
    protected void onClick(Event event){parentController.openProductView(product);}

}
