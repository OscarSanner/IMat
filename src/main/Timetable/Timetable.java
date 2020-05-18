package main.Timetable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.IWizardPage;
import main.Payment.Payment;
import main.PersonalData.PersonalData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

public class Timetable extends AnchorPane implements IWizardPage {

    //----------------NAVIGATION OCH INIT------------------

    //TODO Alla wizardklasser behöver ha en fungerande "Customer Support" knapp

    @FXML
    public AnchorPane timetableMainAnchorPane;
    public Payment paymentPage;
    public PersonalData parentBackendController;
    private Date deliveryTime;

    //AnchorPanes elements
    @FXML private Label date1Label;
    @FXML private Label date2Label;
    @FXML private Label date3Label;
    @FXML private Label date4Label;
    @FXML private Label date5Label;
    @FXML private Label date6Label;
    @FXML private Label date7Label;


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

    }

    public void onNextButtonPressed(){
        paymentPage = new Payment(this, deliveryTime);
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

    //----------------FAKTISK KOD-----------------

    public void initDateLabels(){
        List<String> dates = getCurrentWeek();

        List<Label> STUPIDLIST = new ArrayList<>(Arrays.asList(date1Label, date2Label, date3Label, date4Label, date5Label, date6Label, date7Label));
        int count = 0;
        for(Label label: STUPIDLIST){
            label.setText(dates.get(count));
            count++;
        }

    }
    public List<String> getCurrentWeek(){    //Collects the dates for the current week.
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
    public String convertDateToReadable(Date date, SimpleDateFormat timeFormat){//Converts "dd--MM--yy" to "dd/MM"
        StringBuilder convertedDate = new StringBuilder();

        convertedDate.append((timeFormat.format(date)).substring(0,2)).append("/").append((timeFormat.format(date)).substring(3,5));
        String tempDate = convertedDate.toString();
        return tempDate;
    }


}
