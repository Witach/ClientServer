package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainScreenController {
    @FXML
    StackPane mainPane;

    @FXML
    public void initialize(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Screens/ServerScreen.fxml"));
        Pane pane = null;
        try{
            pane = loader.load();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        mainPane.getChildren().add(pane);
    }
}
