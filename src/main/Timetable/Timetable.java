package main.Timetable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.IWizardPage;
import main.Payment.Payment;
import main.PersonalData.PersonalData;
import main.TimetableItem.TimetableItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Timetable extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    /**
     * My disappointment is immeasurable and my day is ruined.
     */
    @FXML
    public AnchorPane timetableMainAnchorPane;
    @FXML public ImageView escapeHatchImage;
    public Payment paymentPage;
    public PersonalData parentBackendController;
    private Date deliveryTime;
    private List<TimetableItem> timetableItemList;

    //AnchorPanes elements
    @FXML private Label date1Label;
    @FXML private Label date2Label;
    @FXML private Label date3Label;
    @FXML private Label date4Label;
    @FXML private Label date5Label;
    @FXML private Label date6Label;
    @FXML private Label date7Label;
    @FXML private FlowPane timeTableFlowPane;


    public Timetable(PersonalData parentBackendController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timetable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentBackendController = parentBackendController;

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initDateLabels();
        updateTimeTable();

    }

    public void onNextButtonPressed(){
        paymentPage = new Payment(this);
        timetableMainAnchorPane.getChildren().add(paymentPage);
    }

    @Override
    public void closeWizard() {
        parentBackendController.getChildren().remove(this);
        parentBackendController.closeWizard();
    }

    @Override
    public void previousStep() {
        parentBackendController.getChildren().remove(this);
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


    //================================================================================
    // FlowPane
    //================================================================================
    private void updateTimeTable(){
        timeTableFlowPane.setOrientation(Orientation.VERTICAL); //FlowPane's order is now vertical.
        timeTableFlowPane.getChildren().clear();

        timeTableFlowPane.setHgap(25);
        timeTableFlowPane.setVgap(11.2);

        timetableItemList = getTimetableItems();

        for(TimetableItem t: timetableItemList){
            timeTableFlowPane.getChildren().add(t);
        }

    }
    private List<TimetableItem> getTimetableItems(){//Saves the required TimetableItems in a list.
        List<Date> currentWeek = getSevenDays();
        List<TimetableItem> tempList = new ArrayList<>();

        for (int i = 0; i<=28; i++){
            if(i <= 3){
                tempList.add(new TimetableItem(currentWeek.get(0), this));
            }
            else if(i <= 7){
                tempList.add(new TimetableItem(currentWeek.get(1), this));
            }
            else if(i <= 11){
                tempList.add(new TimetableItem(currentWeek.get(2), this));
            }
            else if(i <= 15){
                tempList.add(new TimetableItem(currentWeek.get(3), this));
            }
            else if(i <= 19){
                tempList.add(new TimetableItem(currentWeek.get(4), this));
            }
            else if(i <= 23){
                tempList.add(new TimetableItem(currentWeek.get(5), this));
            }
            else if(i <= 27){
                tempList.add(new TimetableItem(currentWeek.get(6), this));
            }
        }
        return tempList;
    }

    private List<Date> getSevenDays(){//Gets the current week saved in a list of Dates

        List<Date> currentWeek = new ArrayList<>();
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        for(int i = 0; i<=7; i++){
            currentWeek.add(c.getTime());
            c.add(Calendar.DATE, 1);
        }
        return currentWeek;
    }

    public void clear(){
        for(TimetableItem t: timetableItemList){
            t.deSelectDate();
        }

    }

    //================================================================================
    // Interaction
    //================================================================================


    public void onDatePress(Date date){ //Selects the date
        deliveryTime = date;
    }

    //================================================================================
    // Initialisation of date labels
    //================================================================================
    private void initDateLabels(){
        List<String> dates = getCurrentWeek();

        List<Label> STUPIDLIST = new ArrayList<>(Arrays.asList(date1Label, date2Label, date3Label, date4Label, date5Label, date6Label, date7Label));
        int count = 0;
        for(Label label: STUPIDLIST){
            label.setText(dates.get(count));
            count++;
        }

    }
    private List<String> getCurrentWeek(){    //Collects the dates for the current week.
        List<String> dates = new ArrayList<>();
        Date currentDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yy");
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        for(int i = 0; i<=7; i++){
            dates.add(convertDateToReadable(c.getTime(), timeFormat));
            c.add(Calendar.DATE, 1); //Incrementing date by one
        }

        return dates;
    }
    private String convertDateToReadable(Date date, SimpleDateFormat timeFormat){//Converts "dd--MM--yy" to "dd/MM"
        StringBuilder convertedDate = new StringBuilder();

        convertedDate.append((timeFormat.format(date)).substring(0,2)).append("/").append((timeFormat.format(date)).substring(3,5));
        String tempDate = convertedDate.toString();
        return tempDate;
    }



}
