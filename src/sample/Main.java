package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;


public class Main extends Application {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void start(Stage primaryStage) throws Exception{
        log.info("Uruchomienie aplikacji");
        Parent root = FXMLLoader.load(getClass().getResource("Screens/MainScreen.fxml"));
        log.info("Wczytano MainScreen.fxml");
        primaryStage.setTitle("Client");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int heightOfScreen = screenSize.height;
        int widthOfScreen = screenSize.width;
        primaryStage.setScene(new Scene(root,widthOfScreen/5, heightOfScreen/3 ));
        primaryStage.show();
        log.info("Wyświetlono scene");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
