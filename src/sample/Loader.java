package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import sample.Dialogs.ErrorDialog;

import java.io.IOException;

public class Loader {
    public static Pane loadFXMLAsPaneAndLog(FXMLLoader loader){
        Pane pane = null;
        try{
            pane = loader.load();
        }catch(IOException e){
            e.printStackTrace();
            ErrorDialog.showDialog();
            return null;
        }
        return pane;
    }
    private Loader(){}

}
