package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.*;

import java.time.LocalDateTime;
import java.util.*;

public class iMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("main/Res/iMat");

        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"), bundle);

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("main/Res/styles.css");

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                "main/Res/Images/398569911007576065.png"
        ))));


//        Order order = new Order();
//        order.setDate(new Date());
//        IMatDataHandler.getInstance().getOrders().add(order);

        //To test if it is able to collect all products from the requested category umu
        /*for(Product p: ProductHandler.getProductsFromCategory("Kö")){
        for(Product p: ProductHandler.getProductsFromCategory("Kött")){
        for(Product p: ProductHandler.getProductsFromCategory("Kött")){
            System.out.println(p.getName());
        }

    }
*/
        // Ser till att allt sparas inför nästa gång programmet öppnas


    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> IMatDataHandler.getInstance().shutDown(), "Shutdown-thread"));
        launch(args);
    }
}