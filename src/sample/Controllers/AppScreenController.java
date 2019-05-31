package sample.Controllers;
import Postman.ListObservable;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.application.Platform;

import java.util.ArrayList;


public class AppScreenController {
    private MainScreenController mainScreenController;
    private ListObservable listObservable;
    private ObservableList<String> fileList;

    private Logger log = LoggerFactory.getLogger(getClass());
    @FXML
    Text annoucment;
    @FXML
    ListView files;

    public void setMainScreenController(MainScreenController mainScreenController){
        this.mainScreenController = mainScreenController;
        this.fileList = FXCollections.observableArrayList(mainScreenController.);
        tmp.set(this.fileList);
        files.itemsProperty().bindBidirectional(tmp);
    }

    public void setListObservableAndInit(ListObservable listObservable){
        this.listObservable = listObservable;
        this.listObservable.setListView(fileList);
        log.info("Ustawiono postman dla AppScreen");
        Platform.runLater(this.listObservable);
    }

    @FXML
    public void initialize(){
        log.info("initializacja AppScreen");
        ListProperty tmp = new SimpleListProperty();
    }

}
