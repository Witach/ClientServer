package Postman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ListObservable{
    private DirectoryVisitor directoryVisitor;
    private List fileList;
    private Logger log = LoggerFactory.getLogger(getClass());
    private Postman postman;

    public static ListObservable factory(String userName, String dirPath){
        DirectoryVisitor directoryVisitor = new DirectoryVisitor(userName,dirPath);
        Postman postman = new Postman(Executors.newFixedThreadPool(5));
        return new ListObservable(directoryVisitor,postman);
    }

    private ListObservable(DirectoryVisitor directoryVisitor, Postman postman){
        this.directoryVisitor = directoryVisitor;
        this.postman = postman;
    }

    public void setListView(List fileList){
        this.fileList = fileList;
    }

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
        }
}
