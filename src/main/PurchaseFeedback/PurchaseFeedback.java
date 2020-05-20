package main.PurchaseFeedback;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.iMatBackendController;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class PurchaseFeedback extends AnchorPane {

    @FXML Label feedbackLabel;
    private iMatBackendController parentController;
    private Timeline animation;
    private int counter = 1;


    public PurchaseFeedback(iMatBackendController parentController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PurchaseFeedback.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.parentController = parentController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    //================================================================================
    // Animation
    //================================================================================
    public boolean animationIsPlaying = false;

    public void extendAnimation(){
        counter++;
    }

    public void startAnimation(Product product, String quantity, String unitSuffix){

        parentController.showPurchaseFeedback(product, quantity, unitSuffix);

        if(animationIsPlaying){
            extendAnimation();
            animationIsPlaying=true;
        }else {
            animation = new Timeline(new KeyFrame(Duration.seconds(1), e->countDown()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
            animationIsPlaying = true;
        }

    }
    private void countDown(){
        if(counter > 0){
            counter--;
        }
        else {
            parentController.closePurchaseFeedback();
            counter = 1;
            animation.stop();
            animationIsPlaying = false;
        }
    }
    public void setFeedbackLabel(String information){
        feedbackLabel.setText(information);
    }


}
