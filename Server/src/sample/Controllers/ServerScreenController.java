package sample.Controllers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import sample.ListObservable;
import sample.ServerConnection.Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerScreenController {
    @FXML
    ListView list0;
    @FXML
    ListView list1;
    @FXML
    ListView list2;
    @FXML
    ListView list3;
    @FXML
    ListView list4;
    @FXML
    TextArea logsInfo;
    private ArrayList<ListObservable> listOfListObservable;
    private ArrayList<ObservableList<String>> listOfFileList;
    private Timeline timeline;

    @FXML
    public void initialize(){
        listOfListObservable = new ArrayList<ListObservable>();
        listOfFileList = new ArrayList<ObservableList<String>>();
        prepareEnvironmentForServer(listOfListObservable,listOfFileList,new File("").getAbsolutePath()+"/Server");
        ListProperty tmp = new SimpleListProperty();
        tmp.set(listOfFileList.get(0));
        list0.itemsProperty().bindBidirectional(tmp);
        tmp = new SimpleListProperty();
        tmp.set(listOfFileList.get(1));
        list1.itemsProperty().bindBidirectional(tmp);
        tmp = new SimpleListProperty();
        tmp.set(listOfFileList.get(2));
        list2.itemsProperty().bindBidirectional(tmp);
        tmp = new SimpleListProperty();
        tmp.set(listOfFileList.get(3));
        list3.itemsProperty().bindBidirectional(tmp);
        tmp = new SimpleListProperty();
        tmp.set(listOfFileList.get(4));
        list4.itemsProperty().bindBidirectional(tmp);
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.setAutoReverse(true);
        doTime();
        Server server =new Server(Paths.get(new File("").getAbsolutePath()+"/Server").toString(),2,1);
        server.start();
    }

    private void doTime(){
        KeyFrame frame = new KeyFrame(Duration.seconds(1), (event) -> {
            listOfListObservable.stream().forEach(a->a.run());
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private void prepareEnvironmentForServer(List listOfListObservable, List listOfFileList, String pathToServerDir){
        createMainServerDir(pathToServerDir);
        for(int i=0;i<5;i++){
            createAndBindObservableListsToServerFolders(pathToServerDir,i);
            createCSVFileForDir(Paths.get(pathToServerDir+"/"+i).toString());
        }
    }

    private void createMainServerDir(String pathToServerDir){
        File Serverdir = new File(Paths.get(pathToServerDir).toString());
        if(!Serverdir.exists()||Serverdir.isFile())
            Serverdir.mkdir();
    }

    private void createAndBindObservableListsToServerFolders(String pathToServerDir,int fileNumber){
        ObservableList tmp_list = FXCollections.observableArrayList(new ArrayList<String>());
        listOfFileList.add(tmp_list);
        File dir = new File(Paths.get(pathToServerDir+"/"+fileNumber).toString());
        System.out.println(dir.getAbsolutePath());
        listOfListObservable.add(ListObservable.factory(dir.getAbsolutePath()));
        ((ListObservable)listOfListObservable.get(fileNumber)).setListView((List) listOfFileList.get(fileNumber));
    }

    private void createCSVFileForDir(String dirPath){
        File csv = new File(Paths.get(dirPath+"/info.csv").toString());
        try{
            if(!csv.exists())
                csv.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
