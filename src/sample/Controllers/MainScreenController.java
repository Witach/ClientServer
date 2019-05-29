package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainScreenController {

    @FXML
    private StackPane mainPane;

    @FXML
    public void initialize(){
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../Screens/SignInScreen.fxml"));
        Pane pane =  null;
        try{
            pane  = loader.load();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        mainPane.getChildren().add(pane);
    }
}
