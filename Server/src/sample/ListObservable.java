package sample;

import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ListObservable{
    private DirectoryVisitor directoryVisitor;
    private List fileList;
   // private Logger log = LoggerFactory.getLogger(getClass());

    public static ListObservable factory(String dirPath){
        DirectoryVisitor directoryVisitor = new DirectoryVisitor(dirPath);
        return new ListObservable(directoryVisitor);
    }

    private ListObservable(DirectoryVisitor directoryVisitor){
        this.directoryVisitor = directoryVisitor;
    }

    public void setListView(List fileList){
        this.fileList = fileList;
    }

    public void run() {
       // log.info("run");
        List<String> filesInDir =  directoryVisitor.getFileList().stream().map(File::getName).collect(Collectors.toList());
        filesInDir.stream().forEach( file->{
            if(!fileList.contains(file)) {
                fileList.add(file);
            }
        });
        Iterator iter = fileList.iterator();
        while(iter.hasNext()){
            String tmp = (String)iter.next();
            if(!filesInDir.contains(tmp))
                iter.remove();
        }
    }
}
