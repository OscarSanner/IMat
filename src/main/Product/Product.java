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
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Product extends AnchorPane {

     @FXML ImageView itemImage;
     @FXML Label itemLabel;
     @FXML Label priceLabel;
     @FXML Button buyButton;

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private iMatBackendController parentController;
    private se.chalmers.cse.dat216.project.Product product;
    private ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    private ShoppingItem shoppingItem;
    private int itemCounter = 0;

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
        this.shoppingItem = new ShoppingItem(product, 1);

        //this.itemImage.setImage(parentController.getSquareImage(dataHandler.getFXImage(product)));
        this.itemImage.setImage(dataHandler.getFXImage(product));
        this.itemLabel.setText(product.getName());
        this.priceLabel.setText(String.valueOf(product.getPrice()) + " kr");
    }

    @FXML
    protected void onBuyButtonPressed(){
        //shoppingCart.addItem(shoppingItem);
        boolean exists = false;
        List<ShoppingItem> currentCart = new ArrayList<>();
        currentCart = dataHandler.getShoppingCart().getItems();
        for(ShoppingItem s: currentCart){
            if(s.getProduct().equals(shoppingItem.getProduct())){
                    exists = true;
            }
        }
        if(currentCart.isEmpty()){
            parentController.createItemtoShoppingCart(shoppingItem);
            System.out.println("Cart empty. Creating new shoppingitem.");
        }else if(exists) {
            parentController.increaseItem(shoppingItem, 1);
        }
        else{
            parentController.createItemtoShoppingCart(shoppingItem);
        }parentController.updateShoppingCart();

        //parentController.createItemtoShoppingCart(shoppingItem);

        buyButton.setVisible(false);    //Hides the button temporarily to show the +/- buttons underneath.
        itemCounter++;

        parentController.purchaseFeedback.startAnimation(product, "1", product.getUnitSuffix());

    }

    @FXML
    protected void onPlusButtonPressed(){
        //shoppingItem.setAmount(shoppingItem.getAmount()+1);

        parentController.increaseItem(shoppingItem, 1);
        itemCounter++;
        parentController.updateShoppingCart();
    }
    @FXML
    protected void onMinusButtonPressed(){
        //shoppingItem.setAmount(shoppingItem.getAmount()-1);

        parentController.decreaseItem(shoppingItem, -1);
        itemCounter--;
        if(itemCounter==0){
            buyButton.setVisible(true);
        }
        parentController.updateShoppingCart();

    }

    @FXML
    protected void onClick(){
        parentController.openProductView(product);
        parentController.detailedViewPage.setShoppingItem(shoppingItem);
    }

}
