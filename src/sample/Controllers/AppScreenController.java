package sample.Controllers;
import Postman.ListObservable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.application.Platform;

import java.util.ArrayList;


public class AppScreenController {
    private MainScreenController mainScreenController;
    private ListObservable listObservable;
    private ObservableList<String> fileList;
    private Timeline timeline;
    private Logger log = LoggerFactory.getLogger(getClass());
    @FXML
    Text annoucment;
    @FXML
    ListView files;

    public void setMainScreenController(MainScreenController mainScreenController){
        this.mainScreenController = mainScreenController;
        log.info("Ustawiono mainScreen dla AppScreen");
    }

    public void setListObservableAndInit(ListObservable listObservable){
        this.listObservable = listObservable;
        this.listObservable.setListView(fileList);
        log.info("Ustawiono postman dla AppScreen");
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.setAutoReverse(true);
        doTime();
    }

    @FXML
    public void initialize(){
        log.info("initializacja AppScreen");
        ListProperty tmp = new SimpleListProperty();
        this.fileList = FXCollections.observableArrayList(new ArrayList<String>());
        tmp.set(this.fileList);
        files.itemsProperty().bindBidirectional(tmp);
    }

    private void doTime(){
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                listObservable.run();
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();

    }

}
