package main.WideShoppingCartItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.ShoppingCart.ShoppingCart;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Objects;


public class WideShoppingCartItem extends AnchorPane {

    private ShoppingItem shoppingItem;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private main.ShoppingCart.ShoppingCart parentController;

    //Scenebuilder elements
    @FXML ImageView itemImage;
    @FXML Label itemLabel;
    @FXML Label unitPriceLabel;
    @FXML Label totalPriceLabel;
    @FXML TextField amountTextField;
    @FXML ImageView paperBinImage;
    @FXML public ImageView plusImage;
    @FXML public ImageView minusImage;

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
        this.unitPriceLabel.setText(String.valueOf((int)shoppingItem.getProduct().getPrice()) + "kr/" + shoppingItem.getProduct().getUnitSuffix());

        this.totalPriceLabel.setText(String.valueOf((int)shoppingItem.getAmount() * (int)shoppingItem.getProduct().getPrice()) + " kr");

        this.amountTextField.setText(String.valueOf((int)shoppingItem.getAmount()));
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
    //================================================================================
    // Paperbin Hover
    //================================================================================
    @FXML Label paperBinLabel;
    @FXML
    public void onPaperBinEnter(){
        paperBinImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/paperbin_blue_big_hover.png"
        ))));
        paperBinLabel.setStyle("-fx-font-weight: bold;");
    }
    @FXML
    public void onPaperBinExit(){
        paperBinImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/paperbin_blue_big.png"
        ))));
        paperBinLabel.setStyle("-fx-font-weight: normal;");
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
