package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {

    @FXML
    private Pane mainPane;

    @FXML
    public void initialize(){
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("sample.fxml"));
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
