package main.TimetableItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.Timetable.Timetable;

import java.io.IOException;
import java.util.Date;

public class TimetableItem extends AnchorPane {

    private Timetable timeTableController;

    @FXML private Date selectedDate;

    //Scenebuilder Elements
    @FXML private ImageView unselectedCircleImage;
    @FXML private ImageView selectedCircleImage;


    public TimetableItem(Date date, Timetable timeTableController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimetableItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.selectedDate = date;
        this.timeTableController = timeTableController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.timeTableController = timeTableController;
    }

    //----------------FAKTISK KOD-----------------


    @FXML
    private void selectDate(){
        timeTableController.onDatePress(selectedDate);
        selectedCircleImage.toFront();
    }
    @FXML
    private void deSelectDate(){
        timeTableController.onDatePress(null);
        unselectedCircleImage.toFront();
    }

}
