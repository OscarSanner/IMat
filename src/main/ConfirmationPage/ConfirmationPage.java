package main.ConfirmationPage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.Payment.Payment;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ConfirmationPage extends AnchorPane {

    //----------------NAVIGATION OCH INIT------------------

    @FXML public ImageView escapeHatchImage;
    private Date deliveryTime;

    public Payment parentBackendController;
    private Order order;



    public ConfirmationPage(Payment parentBackendController, Date deliveryTime, Order order){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmationPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;
        this.deliveryTime = deliveryTime;
        this.order = order;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setupConfirmationPage();

    }

    public void onGoBackToFrontButtonPressed(){
        parentBackendController.closeWizard();
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



    //----------------FAKTISK KOD-----------------


    @FXML TextField orderNumberTextField;
    @FXML TextField orderDateTextField;
    @FXML TextField deliveryTimeTextField;

    private void setupConfirmationPage(){



        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMMM yyyy");

        orderNumberTextField.setText(String.valueOf(order.getOrderNumber()));
        orderDateTextField.setText(timeFormat.format(order.getDate()));
        deliveryTimeTextField.setText(timeFormat.format(deliveryTime));



    }
}
