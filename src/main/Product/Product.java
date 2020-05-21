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
     @FXML Label amountLabel;

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private iMatBackendController parentController;
    private se.chalmers.cse.dat216.project.Product product;
    private ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    private ShoppingItem shoppingItem;

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
        boolean exists = false;
        List<ShoppingItem> currentCart;
        currentCart = dataHandler.getShoppingCart().getItems();
        for(ShoppingItem s: currentCart){
            if(s.getProduct().equals(shoppingItem.getProduct())){
                    exists = true;
            }
        }
       /* if(currentCart.isEmpty()){
            parentController.createItemtoShoppingCart(shoppingItem);
            System.out.println("Cart empty. Creating new shoppingitem.");
        }else */if(exists) {
            parentController.increaseItem(shoppingItem, 1);
        }
        else{
            parentController.createItemtoShoppingCart(shoppingItem);
        }parentController.updateShoppingCart();


        amountLabel.setText(String.valueOf(shoppingItem.getAmount()));

        buyButton.setVisible(false);    //Hides the button temporarily to show the +/- buttons underneath.

        parentController.purchaseFeedback.startAnimation(product, "1", product.getUnitSuffix());

    }

    @FXML
    protected void onPlusButtonPressed(){
        parentController.increaseItem(shoppingItem, 1);
        parentController.updateShoppingCart();

        amountLabel.setText(String.valueOf(shoppingItem.getAmount()));

    }
    @FXML
    protected void onMinusButtonPressed(){
            parentController.decreaseItem(shoppingItem, 1);
            if (shoppingItem.getAmount() <= 0) {
                buyButton.setVisible(true);
                shoppingItem.setAmount(1);
                shoppingCart.removeItem(shoppingItem);

            }
            parentController.updateShoppingCart();

            amountLabel.setText(Double.toString(shoppingItem.getAmount()));
    }

    @FXML
    protected void onClick(){
        parentController.openProductView(product, this);
        parentController.detailedViewPage.setShoppingItem(shoppingItem);
    }

    public void setQuantityLabel(String amount){
        amountLabel.setText(amount);

    }
    public Product buyButtonVisible(boolean state){
        buyButton.setVisible(state);

        if(!state){
        shoppingItem.setAmount(1);}
        return this;
    }
    public int getProductIDDD(){
        return product.getProductId();
    }
}
