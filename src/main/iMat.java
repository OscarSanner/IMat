package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class iMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("main/Res/iMat");

        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"), bundle);

        Scene scene = new Scene(root, 960, 720);

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
//        Order order = new Order();
//        order.setDate(new Date());
//        IMatDataHandler.getInstance().getOrders().add(order);

        //To test if it is able to collect all products from the requested category umu
        /*for(Product p: ProductHandler.getProductsFromCategory("Kött")){
            System.out.println(p.getName());
        }*/

    }

    // Ser till att allt sparas inför nästa gång programmet öppnas
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> IMatDataHandler.getInstance().shutDown(), "Shutdown-thread"));
        launch(args);
    }
}
