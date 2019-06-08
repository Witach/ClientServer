package Postman;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ListObservable{
    private DirectoryVisitor directoryVisitor;
    private List<String> fileList;
    private List<String> filesFromServer;
    //private Logger log = LoggerFactory.getLogger(getClass());
    private Postman postman;

    public static ListObservable factory(String userName, String dirPath){
        DirectoryVisitor directoryVisitor = new DirectoryVisitor(userName,dirPath);
        Postman postman = new Postman(Executors.newFixedThreadPool(5));
        SemaphoreSingleton.create();
        return new ListObservable(directoryVisitor,postman,new ArrayList<String>());
    }

    private ListObservable(DirectoryVisitor directoryVisitor, Postman postman,List<String> filesFromServer){
        this.directoryVisitor = directoryVisitor;
        this.postman = postman;
        this.filesFromServer = filesFromServer;
    }

    public void setListView(List fileList){
        this.fileList = fileList;
    }

    public void getListOfFileFromServer(){
        postman.list(this.directoryVisitor.getUserName(),this.filesFromServer);
         List list = directoryVisitor.getFileList();
        Iterator iter = filesFromServer.iterator();

        while(iter.hasNext()){
            String fileName = (String)iter.next();
            if(list.contains(fileName.toUpperCase())){
                postman.downFile(directoryVisitor.getUserName(),fileName,directoryVisitor.getDirPath());
                fileList.add(fileName);
            }
        }
    }

    public void run() {
        //log.info("run");
            List<String> filesInDir =  directoryVisitor.getFileList().stream().map(File::getName).collect(Collectors.toList());
            filesInDir.stream().forEach( file->{
                if(!fileList.contains(file)) {
                    fileList.add(file);
                    if(!filesFromServer.contains(file.toUpperCase())){
                        postman.sendFile(this.directoryVisitor.getUserName(),file,this.directoryVisitor.getDirPath());
                    }
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
