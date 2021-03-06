package main.Product;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.util.Duration;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.tools.Tool;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Product extends AnchorPane {

     @FXML ImageView itemImage;
     @FXML Label itemLabel;
     @FXML Label priceLabel;
     @FXML Button buyButton;
     @FXML TextField amountTextField;
     @FXML ImageView plusImage;
     @FXML ImageView minusImage;
     @FXML Button tooltipButton;
     @FXML ImageView tooltipImage;

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
        this.priceLabel.setText(product.getPrice() + " kr/" + product.getUnitSuffix());

        Tooltip tooltip = new Tooltip("Tryck för mer information");
        tooltip.setShowDelay(Duration.seconds(0));
        tooltip.setStyle("-fx-font-size: 18");
        Tooltip.install(tooltipImage, tooltip);


    }

    //================================================================================
    // +/- Hover
    //================================================================================
    @FXML
    public void onPlusEnter(){
        plusImage.setImage(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/plus_hover.png"
        ))));
    }
    @FXML
    public void onPlusExit(){
        plusImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/plus.png"
        ))));
    }
    @FXML
    public void onMinusEnter(){
        minusImage.setImage(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/minus_hover.png"
        ))));
    }
    @FXML
    public void onMinusExit(){
        minusImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/minus.png"
        ))));
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
   if(exists) {
            parentController.increaseItem(shoppingItem, 1);
        }
        else{
            parentController.createItemtoShoppingCart(shoppingItem);
        }parentController.updateShoppingCart();


        amountTextField.setText(String.valueOf((int)shoppingItem.getAmount()) + " " + product.getUnitSuffix());

        buyButton.setVisible(false);    //Hides the button temporarily to show the +/- buttons underneath.

        //parentController.purchaseFeedback.startAnimation(product, "1", product.getUnitSuffix());
        parentController.shoppingCartScrollPane.setVvalue( parentController.shoppingCartScrollPane.getPrefViewportHeight());
    }

    @FXML
    protected void onPlusButtonPressed(){

        boolean alreadyAdded = false;
        for(ShoppingItem s: dataHandler.getShoppingCart().getItems()){
            if(s.getProduct().equals(shoppingItem.getProduct())){
                alreadyAdded = true;
                s.setAmount(s.getAmount() + 1);
                System.out.println("There is an idential shopping item in shoppingcart. Increase amount on the existing shoppingitem");
                amountTextField.setText(String.valueOf((int)s.getAmount()) + " " + product.getUnitSuffix());

            }
        }        parentController.updateShoppingCart();


    }
    @FXML
    protected void onMinusButtonPressed(){
       try{
           boolean removed = false;
           for(ShoppingItem s: dataHandler.getShoppingCart().getItems()){
               if(s.getProduct().equals(shoppingItem.getProduct()) && s.getAmount()-1 <= 0 && !removed){
                   dataHandler.getShoppingCart().removeItem(s);

                   buyButton.setVisible(true);
                   removed = true;
               }
               else if(s.getProduct().equals(shoppingItem.getProduct())){
                   s.setAmount(s.getAmount() - 1);
                   System.out.println("There is an idential shopping item in shoppingcart. Decrease amount on the existing shoppingitem");
                   amountTextField.setText(String.valueOf((int)s.getAmount()) + " " + product.getUnitSuffix());
               }
           }
           // }
           parentController.updateProductFlowpane(parentController.currentCategory);
           parentController.updateShoppingCart();


       }catch (ConcurrentModificationException ignore){
           System.out.println("ConcurrentModificaitonException"); //Don't look at this...
       }
    }

    @FXML
    protected void onClick(){
        parentController.openProductView(product, this);
        parentController.detailedViewPage.setShoppingItem(shoppingItem);
    }

    public void setQuantityLabel(String amount){

        amountTextField.setText(amount + " " + product.getUnitSuffix());

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
