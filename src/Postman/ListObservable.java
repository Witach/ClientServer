package Postman;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ListObservable extends Thread {
    private DirectoryVisitor directoryVisitor;
    private ObservableList fileList;
    private Logger log = LoggerFactory.getLogger(getClass());
    private Postman postman;
    public ListObservable(DirectoryVisitor directoryVisitor, Postman postman){
        this.directoryVisitor = directoryVisitor;
        this.postman = postman;
    }

    public void setListView(ObservableList fileList){
        this.fileList = fileList;
    }

    public void init(){
        this.start();
        log.info("uruchomienie ListObservable");
    }


    @Override
    public void run() {
        log.info("run");
            List<String> filesInDir =  directoryVisitor.getFileList().stream().map(File::getName).collect(Collectors.toList());
            filesInDir.stream().forEach( file->{
                if(!fileList.contains(file)) {
                    fileList.add(file);
                    postman.sendFile(this.directoryVisitor.getUserName(),file,this.directoryVisitor.getDirPath());
                }
            });
            Iterator iter = fileList.iterator();
            while(iter.hasNext()){
               String tmp = (String)iter.next();
               if(!filesInDir.contains(tmp))
                   iter.remove();
            }
            //log.info(fileList.toString());
        }
        //log.info("zakończenie");
}
