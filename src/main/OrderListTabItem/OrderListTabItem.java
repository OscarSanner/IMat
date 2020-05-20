package main.OrderListTabItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;


import java.io.File;
import java.io.IOException;

public class OrderListTabItem extends AnchorPane {

    @FXML
    ImageView itemImage;
    @FXML
    Label itemName;
    @FXML
    Label unitPriceLabel;
    @FXML
    Label amountLabel;
    @FXML
    Label totalPriceLabel;



    public OrderListTabItem(double amount, Product product, double total){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderListTabItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initCard(amount, product, total);
    }

    private void initCard(double amount, Product product, double total) {
        Image productImage = IMatDataHandler.getInstance().getFXImage(product);
        itemImage.setImage(productImage);
        itemName.setText(product.getName());
        unitPriceLabel.setText("Styckpris: " + total/amount);
        amountLabel.setText("Antal köpta: " + amount);
        totalPriceLabel.setText("Totalt: " + total);
    }
}
