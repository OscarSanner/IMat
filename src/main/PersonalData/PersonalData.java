package main.PersonalData;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PersonalData extends AnchorPane {

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
}
