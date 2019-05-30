package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
public class MainScreenController{

    @FXML
    private StackPane mainPane;
    private Logger log = LoggerFactory.getLogger(getClass());
    @FXML
    public void initialize(){
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../Screens/SignInScreen.fxml"));
        log.info("Wczytano SignInScreen.fxml");
        Pane pane =  null;
        try{
            pane  = loader.load();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        mainPane.getChildren().add(pane);
        log.info("Załadownao SignInScreen.fxml");
    }
}
