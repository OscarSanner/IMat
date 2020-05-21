package main.DetailedView;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;
import se.chalmers.cse.dat216.project.ProductCategory;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailedView extends AnchorPane {


    //Elements connected to backend.
    public se.chalmers.cse.dat216.project.Product product;
    private iMatBackendController parentController;
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    private main.Product.Product detailedViewProduct;
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label productCategoryLabel;
    @FXML private Label priceLabel;
    @FXML private TextArea productTextArea;
    @FXML private ImageView organicImage;
    @FXML private Label unitLabel;
    @FXML private Label unitSuffixLabel;

    //Frontend elements
    @FXML private Label quantityLabel;
   @FXML private ImageView favouriteImage;

    public ShoppingItem shoppingItem;



    public DetailedView(iMatBackendController backendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailedView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentController = backendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setEffect(null);

    }

    //================================================================================
    // Escapehatch Hover
    //================================================================================
    @FXML
    public void onFavouriteEnter(){
        if(!dataHandler.isFavorite(product)){
            favouriteImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "main/Res/Images/favourite_hover.png"
            ))));
        }else{
            favouriteImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "main/Res/Images/favourite_clicked_hover.png"
            ))));
        }

    }
    @FXML
    public void onFavouriteExit(){
        if(!dataHandler.isFavorite(product)){
            favouriteImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "main/Res/Images/favourite.png"
            ))));
        }else{
            favouriteImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "main/Res/Images/favourite_clicked.png"
            ))));
        }

    }

    @FXML
    public void onFavouriteClicked(){
        if(dataHandler.isFavorite(product)){
            dataHandler.removeFavorite(product);
            favouriteImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "main/Res/Images/favourite.png"
            ))));
        }else{
            dataHandler.addFavorite(product);
            favouriteImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "main/Res/Images/favourite_clicked.png"
            ))));
        }


    }

    @FXML
    public void closeDetailedView(){
        parentController.closeDetailedView();
        parentController.updateProductFlowpane(parentController.currentCategory);
    }
    @FXML
    public void addToShoppingCart(){
        try
        {
            List<ShoppingItem> currentCart = new ArrayList<>();
            currentCart = dataHandler.getShoppingCart().getItems();
            boolean alreadyAdded = false;

            for(ShoppingItem s: currentCart){
                if(s.getProduct().equals(shoppingItem.getProduct())){
                    alreadyAdded = true;
                    s.setAmount(s.getAmount() + Double.parseDouble(quantityLabel.getText()));
                    System.out.println("There is an idential shopping item in shoppingcart. Increase amount on the existing shoppingitem");
                    detailedViewProduct.setQuantityLabel(String.valueOf(s.getAmount()));

                }
            }

            if(Integer.parseInt(quantityLabel.getText()) == 0){
                // Do nothing
                System.out.println("zerooo");
            }
           else if(!alreadyAdded){
                shoppingItem.setAmount(Double.parseDouble(quantityLabel.getText()));
                parentController.createItemtoShoppingCart(shoppingItem);

                parentController.updateProductFlowpane(parentController.currentCategory);

                parentController.purchaseFeedback.startAnimation(product, quantityLabel.getText(), product.getUnitSuffix());
                detailedViewProduct.setQuantityLabel(String.valueOf(shoppingItem.getAmount()));

            }
            alreadyAdded = false;
            parentController.updateShoppingCart();


        }
        catch (NumberFormatException nfe)
        {
            System.out.println("Could not add item to shopping cart. String not convertable to Double.");
        }

    }
    @FXML
    public void increaseQuantity(){
        try
        {
            int temporaryNumber = Integer.parseInt(quantityLabel.getText());
            temporaryNumber++;
            quantityLabel.setText(String.valueOf(temporaryNumber));
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("String not convertable to int");
        }
    }
    @FXML
    public void decreaseQuantity(){
        try
        {
            int temporaryNumber = Integer.parseInt(quantityLabel.getText());
            temporaryNumber--;
            if(temporaryNumber > 0){
                quantityLabel.setText(String.valueOf(temporaryNumber));
            } else{
                quantityLabel.setText(String.valueOf(0));
            }
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("String not convertable to int");
        }
    }

    public void populateProductDetailedView(Product product){

        this.product = product;
        this.productImage.setImage(dataHandler.getFXImage(product));
        this.productNameLabel.setText(product.getName());

        if(product.isEcological()){
            organicImage.setVisible(true);
        }else{organicImage.setVisible(false);}

        //Needs to be changed depending on our categories
        this.productCategoryLabel.setText(getProductCategory(product));

        this.priceLabel.setText(String.valueOf(product.getPrice()) + " " + product.getUnit());

        this.unitSuffixLabel.setText(product.getUnitSuffix());

        //Product information is missing
        this.productTextArea.setText("PRODUCT INFORMATION IS MISSING.--> Product.java");

        onFavouriteExit();

    }

    private String getProductCategory(Product product){
        switch(product.getCategory()){
            case POD:       return "Grönsaker";
            case BREAD:     return "Bröd";
            case BERRY:     return "Bär";
            case CITRUS_FRUIT: return "Frukt";
            case HOT_DRINKS: return "Varma drycker";
            case COLD_DRINKS: return "Kalla drycker";
            case EXOTIC_FRUIT: return "Frukt";
            case FISH:      return "Fisk";
            case VEGETABLE_FRUIT: return "Grönsaker";
            case CABBAGE:   return "Grönsaker";
            case MEAT:      return "Kött";
            case DAIRIES:   return "Mejeri";
            case MELONS:    return "Frukt";
            case FLOUR_SUGAR_SALT: return "Mjöl, socker, salt";
            case NUTS_AND_SEEDS: return "Nötter och frön";
            case PASTA:     return "Pasta";
            case POTATO_RICE: return "Potatis och ris";
            case ROOT_VEGETABLE: return "Grönsaker";
            case FRUIT:     return "Frukt";
            case SWEET:     return "Sötsaker";
            case HERB:      return "Kryddor";
            default:    return null;
        }
    }

    public void setShoppingItem(ShoppingItem shoppingItem){
        this.shoppingItem = shoppingItem;
    }

    public void setProduct(main.Product.Product productClass) {
        detailedViewProduct = productClass;
    }
}
