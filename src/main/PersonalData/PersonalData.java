package main.PersonalData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Timetable.Timetable;

import java.io.IOException;

public class PersonalData extends AnchorPane {

    @FXML
    public Button personalDataNextButton;
    @FXML
    public AnchorPane personalDataMainAnchorPane;

    public Timetable timetablePage = new Timetable();

    public PersonalData(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PersonalData.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onNextButtonPressed(){
        personalDataMainAnchorPane.getChildren().add(timetablePage);
    }
}
