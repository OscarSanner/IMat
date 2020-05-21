package main.WideShoppingCartItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.ShoppingCart.ShoppingCart;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class WideShoppingCartItem extends AnchorPane {

    private ShoppingItem shoppingItem;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private main.ShoppingCart.ShoppingCart parentController;

    //Scenebuilder elements
    @FXML ImageView itemImage;
    @FXML Label itemLabel;
    @FXML Label unitPriceLabel;
    @FXML Label amountLabel;
    @FXML Label totalPriceLabel;
    @FXML Label unitSuffixLabel;




    public WideShoppingCartItem(ShoppingCart parentController, ShoppingItem shoppingItem ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WideShoppingCartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;

        this.shoppingItem = shoppingItem;

        this.itemImage.setImage(dataHandler.getFXImage(shoppingItem.getProduct(), 90, 90)); //Fix size of image
        this.itemLabel.setText(shoppingItem.getProduct().getName());
        this.unitPriceLabel.setText(String.valueOf(shoppingItem.getProduct().getPrice()));
        this.unitSuffixLabel.setText(shoppingItem.getProduct().getUnitSuffix());
        this.totalPriceLabel.setText(String.valueOf(shoppingItem.getAmount() * shoppingItem.getProduct().getPrice()));
        this.amountLabel.setText(String.valueOf(shoppingItem.getAmount()));
    }

    @FXML
    public void removeShoppingItem(){
        dataHandler.getShoppingCart().removeItem(shoppingItem);
        parentController.populateShoppingCartPage();
        System.out.println("Removed " + shoppingItem.getProduct().getName() + " ShoppingItem from ShoppingCartPage");
    }

    @FXML
    protected void onPlusButtonPressed(){
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        parentController.populateShoppingCartPage();
    }
    @FXML
    protected void onMinusButtonPressed(){
        shoppingItem.setAmount(shoppingItem.getAmount() - 1);

        if(shoppingItem.getAmount() <= 0){
            shoppingItem.setAmount(1);
            dataHandler.getShoppingCart().removeItem(shoppingItem);
        }
        parentController.populateShoppingCartPage();
    }

}
