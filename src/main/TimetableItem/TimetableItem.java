package main.TimetableItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Date;

public class TimetableItem extends AnchorPane {


    @FXML private Date date;

    public TimetableItem(Date date){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimetableItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.date = date;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
