package main.CustomerSupport;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.iMatBackendController;

import java.io.IOException;
import java.util.Objects;

public class CustomerSupport extends AnchorPane {

    //----------------NAVIGATION OCH INIT------------------

    @FXML
    public Button closeWindowButton;
    @FXML private ImageView escapeHatchImage;

    iMatBackendController parentBackendController;


    public CustomerSupport(iMatBackendController backendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerSupport.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = backendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onCloseWindowsButtonPressed(){
        parentBackendController.closeCustomerSupport();
    }

    //----------------FAKTISK KOD-----------------

    //================================================================================
    // Escapehatch Hover
    //================================================================================
    @FXML
    public void onEscapeHatchEnter(){
        escapeHatchImage.setImage(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/escape_hatch_hover.png"
        ))));
    }
    @FXML
    public void onEscapeHatchExit(){
        escapeHatchImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/escape_hatch.png"
        ))));
    }
    @FXML
    public void onHomeButtonPressed(){
        onCloseWindowsButtonPressed();
    }
}
