package main.ShoppingCartItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.Product.Product;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ShoppingCartItem extends AnchorPane {

    private iMatBackendController parentController;



    private ShoppingItem shoppingItem;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private Product shoppingItemsProduct;

    //Scenebuilder elements
    @FXML private ImageView itemImage;
    @FXML private Label productNameLabel;
    @FXML private Label amountLabel;
    @FXML private Label priceLabel;

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
        this.amountLabel.setText(Double.toString(shoppingItem.getAmount()));
    }

    @FXML
    protected void onPlusButtonPressed(){
        parentController.increaseItem(shoppingItem, 1);
        parentController.updateShoppingCart();
        parentController.updateProductFlowpane(parentController.currentCategory);

        //amountLabel.setText(Double.toString(shoppingItem.getAmount()));

    }
    @FXML
    protected void onMinusButtonPressed(){
        parentController.decreaseItem(shoppingItem, 1);
        if (shoppingItem.getAmount() <= 0) {
            shoppingItem.setAmount(1);
            dataHandler.getShoppingCart().removeItem(shoppingItem);

        }
        parentController.updateShoppingCart();
        parentController.updateProductFlowpane(parentController.currentCategory);


       // amountLabel.setText(Double.toString(shoppingItem.getAmount()));
    }

    public ShoppingItem getShoppingItem() {
        return shoppingItem;
    }

    @FXML
    public void onPaperbinPressed(){
        dataHandler.getShoppingCart().removeItem(shoppingItem);
        parentController.updateProductFlowpane(parentController.currentCategory);
        parentController.updateShoppingCart();

    }
}