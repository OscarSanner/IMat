package main.ShoppingCart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.IMatBackendEngine;
import main.IWizardPage;
import main.PersonalData.PersonalData;
import main.ShoppingCartItem.ShoppingCartItem;
import main.WideShoppingCartItem.WideShoppingCartItem;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ShoppingCart extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    //SceneBuiler
    @FXML public AnchorPane shoppingCartMainAnchorPane;
    @FXML public FlowPane shoppingCartFlowPane;
    @FXML public Label totalSumLabel;
    @FXML public ImageView escapeHatchImage;
    @FXML public Button saveAsListButton;


    //Other
    public PersonalData personalDataPage = new PersonalData(this);
    public iMatBackendController parentBackendController;
    public se.chalmers.cse.dat216.project.ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();

    Customer customer = IMatDataHandler.getInstance().getCustomer();

    public ShoppingCart(iMatBackendController backendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = backendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initShoppingCartFlowPane();
    }
    @FXML
    public void onNextButtonPressed(){
        shoppingCartMainAnchorPane.getChildren().add(personalDataPage);
        personalDataPage.inputCustomerInfo();

    }

    @Override
    public void closeWizard() {
        parentBackendController.mainAnchorPane.getChildren().remove(this);
        parentBackendController.updateProductFlowpane(parentBackendController.currentCategory);
        parentBackendController.updateShoppingCart();
        saveAsListButton.setStyle("");
        saveAsListButton.getStyleClass().add("button");
        saveAsListButton.getStyleClass().add("subCategoryButton");
        saveAsListButton.setStyle("-fx-border-width: 0; -fx-background-radius: 30; -fx-border-width: 0");
        saveAsListButton.setText("Spara som inköpslista");
        saveAsListButton.setDisable(false);

    }

    //================================================================================
    // Escapehatch Hover
    //================================================================================
    @FXML
    public void onEscapeHatchEnter(){
        escapeHatchImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/escape_hatch_hover.png"
        ))));
    }
    @FXML
    public void onEscapeHatchExit(){
        escapeHatchImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/escape_hatch.png"
        ))));
    }

    @Override
    public void previousStep() {closeWizard(); }

    //----------------FAKTISK KOD-----------------

    public void initShoppingCartFlowPane(){
        shoppingCartFlowPane.setOrientation(Orientation.HORIZONTAL); //NOT VERTICAL
        shoppingCartFlowPane.getChildren().clear();
        shoppingCartFlowPane.setVgap(10);
        shoppingCartFlowPane.setPadding(new Insets(9,6,9,6));
        shoppingCart.addShoppingCartListener(scl2);
        shoppingCart.clear();
    }

    private ShoppingCartListener scl2 = new ShoppingCartListener() {
        @Override
        public void shoppingCartChanged(CartEvent cartEvent) {
            populateShoppingCartPage();
        }
    };
    public void populateShoppingCartPage(){
        shoppingCartFlowPane.getChildren().clear();

        int sum = 0;
        for(ShoppingItem s: shoppingCart.getItems()){
            shoppingCartFlowPane.getChildren().add(new WideShoppingCartItem(this, s));
            sum += (int)(s.getAmount() * s.getProduct().getPrice());
        }

        totalSumLabel.setText(String.valueOf(sum) + " kr");
    }

    public void saveAsList(){
        Order order = new Order();
        List<ShoppingItem> items = new ArrayList<>();
        for(ShoppingItem item : IMatDataHandler.getInstance().getShoppingCart().getItems()){
            items.add(item);
        }
        order.setItems(items);
        order.setDate(new Date());
        IMatBackendEngine.getInstance().addSavedOrder(order);
        //parentBackendController.onListtabSelect();
        saveAsListButton.setStyle("-fx-background-color: green; -fx-border-width: 0; -fx-background-radius: 30; -fx-border-width: 0");
        saveAsListButton.setText("Inköpslista tillagd!");
        saveAsListButton.setDisable(true);
    }
}