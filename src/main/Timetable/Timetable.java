package main.Timetable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Payment.Payment;

import java.io.IOException;

public class Timetable extends AnchorPane {

    @FXML
    public Button timetableNextButton;
    @FXML
    public AnchorPane timetableMainAnchorPane;
    public Payment paymentPage = new Payment();

    public Timetable(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timetable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void onNextButtonPressed(){
        timetableMainAnchorPane.getChildren().add(paymentPage);
    }
}
