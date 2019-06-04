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
        log.info("Initialize mainScreen");
        loadMainScreen();
    }

    public void loadMainScreen() {
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../Screens/SignInScreen.fxml"));
        log.info("Wczytano SignInScreen.fxml");
        Pane pane =  null;
        try{
            pane  = loader.load();
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        SignInController signInController = loader.getController();
        signInController.setMainScreenController(this);
        setScreen(pane);
        log.info("Załadownao SignInScreen.fxml");
    }

    public void setScreen(Pane pane){
        log.info("Ładowanie pane");
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
        log.info("wyswietlenie pane");
    }
}
