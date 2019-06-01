package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main extends Application {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void start(Stage primaryStage) throws Exception{
        log.info("Uruchomienie aplikacji");
        Parent root = FXMLLoader.load(getClass().getResource("Screens/MainScreen.fxml"));
        log.info("Wczytano MainScreen.fxml");
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        log.info("Wyświetlono scene");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
