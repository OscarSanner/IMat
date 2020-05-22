package main.ShoppingCartItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.Product.Product;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Objects;

public class ShoppingCartItem extends AnchorPane {

    private iMatBackendController parentController;



    private ShoppingItem shoppingItem;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private Product shoppingItemsProduct;

    //Scenebuilder elements
    @FXML private ImageView itemImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView paperBinImage;
    @FXML public TextField amountTextField;

    public ShoppingCartItem(iMatBackendController parentController, ShoppingItem shoppingItem){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        this.shoppingItem = shoppingItem;

        this.itemImage.setImage(dataHandler.getFXImage(shoppingItem.getProduct(), 70, 70));
        this.productNameLabel.setText(shoppingItem.getProduct().getName());
        this.amountTextField.setText(Double.toString(shoppingItem.getAmount()));
        this.priceLabel.setText(Double.toString(shoppingItem.getAmount() * shoppingItem.getProduct().getPrice()));
    }

    @FXML
    protected void onPlusButtonPressed(){
        for(ShoppingItem s: dataHandler.getShoppingCart().getItems()){
            if(s.getProduct().equals(shoppingItem.getProduct())){
                s.setAmount(s.getAmount() + 1);
                System.out.println("There is an idential shopping item in shoppingcart. Increase amount on the existing shoppingitem");
                amountTextField.setText(String.valueOf(s.getAmount()));
                priceLabel.setText(Double.toString(shoppingItem.getAmount() * shoppingItem.getProduct().getPrice()));
            }
        }
        parentController.updateShoppingCart();
        parentController.updateProductFlowpane(parentController.currentCategory);
    }
    @FXML
    protected void onMinusButtonPressed(){
        if(shoppingItem.getAmount()-1 <= 0){
            dataHandler.getShoppingCart().removeItem(shoppingItem);
            shoppingItem.setAmount(1);
        } else{
            for(ShoppingItem s: dataHandler.getShoppingCart().getItems()){
                if(s.getProduct().equals(shoppingItem.getProduct())){
                    s.setAmount(s.getAmount() - 1);
                    System.out.println("There is an idential shopping item in shoppingcart. Decrease amount on the existing shoppingitem");
                    amountTextField.setText(String.valueOf(s.getAmount()));
                    priceLabel.setText(String.valueOf(shoppingItem.getAmount() * shoppingItem.getProduct().getPrice()));
                }
            }
        }
        parentController.updateShoppingCart();
        parentController.updateProductFlowpane(parentController.currentCategory);

    }

    public ShoppingItem getShoppingItem() {
        return shoppingItem;
    }

    //================================================================================
    // Paperbin Hover
    //================================================================================
    @FXML
    public void onPaperBinEnter(){
        paperBinImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/paperbin_hover.png"
        ))));
    }
    @FXML
    public void onPaperBinExit(){
        paperBinImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/paperbin.png"
        ))));
    }
    @FXML
    public void onPaperbinPressed(){
        dataHandler.getShoppingCart().removeItem(shoppingItem);
        parentController.updateShoppingCart();
        parentController.updateProductFlowpane(parentController.currentCategory);


    }
}