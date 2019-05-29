package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Client");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int heightOfScreen = screenSize.height;
        int widthOfScreen = screenSize.width;
        primaryStage.setScene(new Scene(root,widthOfScreen/5, heightOfScreen/3 ));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
