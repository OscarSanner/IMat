package main.ConfirmationPage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.Payment.Payment;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.Objects;

public class ConfirmationPage extends AnchorPane {

    //----------------NAVIGATION OCH INIT------------------

    @FXML public ImageView escapeHatchImage;


    public Payment parentBackendController;

    public ConfirmationPage(Payment parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmationPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

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
    @FXML public void shutDownApplication(){
        Platform.exit();
        IMatDataHandler.getInstance().shutDown();
    }

    //----------------FAKTISK KOD-----------------
}
