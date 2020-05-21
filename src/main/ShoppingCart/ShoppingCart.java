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
import main.IWizardPage;
import main.PersonalData.PersonalData;
import main.ShoppingCartItem.ShoppingCartItem;
import main.WideShoppingCartItem.WideShoppingCartItem;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.Objects;

public class ShoppingCart extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    //SceneBuiler
    @FXML public AnchorPane shoppingCartMainAnchorPane;
    @FXML public FlowPane shoppingCartFlowPane;
    @FXML public Label totalSumLabel;
    @FXML public ImageView escapeHatchImage;

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

    public void onNextButtonPressed(){
        shoppingCartMainAnchorPane.getChildren().add(personalDataPage);
        personalDataPage.inputCustomerInfo();


    }

    @Override
    public void closeWizard() {
        parentBackendController.mainAnchorPane.getChildren().remove(this);
        parentBackendController.updateProductFlowpane(parentBackendController.currentCategory);
        parentBackendController.updateShoppingCart();

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
        shoppingCartFlowPane.setVgap(7);
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

}
