package main.TimetableItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.Timetable.Timetable;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

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
        unselectedCircleImage.setVisible(true);
        selectedCircleImage.setVisible(false);
    }

    //----------------FAKTISK KOD-----------------
    //================================================================================
    // Timetable Hover
    //================================================================================
    @FXML
    public void onUnselectedEnter(){
        unselectedCircleImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/gray_circle_hover.png"
        ))));
    }
    @FXML
    public void onUnselectedExit(){
        unselectedCircleImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/gray_circle.png"
        ))));
    }
    @FXML
    public void onSelectedEnter(){
        selectedCircleImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/green_circle_hover.png"
        ))));
    }
    @FXML
    public void onSelectedExit(){
        selectedCircleImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/green_circle.png"
        ))));
    }

    @FXML
    private void selectDate(){
        timeTableController.clear();
        timeTableController.onDatePress(selectedDate);
        unselectedCircleImage.setVisible(false);
        selectedCircleImage.setVisible(true);


    }
    @FXML
    public void deSelectDate(){
        timeTableController.onDatePress(null);
        unselectedCircleImage.setVisible(true);
        selectedCircleImage.setVisible(false);
    }

}
