package main.CustomerSupport;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.iMatBackendController;

import java.io.IOException;

public class CustomerSupport extends AnchorPane {

    @FXML
    public Button closeWindowButton;

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

}
