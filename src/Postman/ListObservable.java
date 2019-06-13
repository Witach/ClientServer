package Postman;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ListObservable{
    private DirectoryVisitor directoryVisitor;
    private List<String> fileList;
    private List<String> filesSended;
    private AtomicReference<List<String>> filesFromServer;
    private List<String> perriorFilesFromServer;
    public AtomicBoolean listFlag;
    //private Logger log = LoggerFactory.getLogger(getClass());
    private Postman postman;

    public static ListObservable factory(String userName, String dirPath){
        DirectoryVisitor directoryVisitor = new DirectoryVisitor(userName,dirPath);
        Postman postman = new Postman(Executors.newFixedThreadPool(5));
        SemaphoreSingleton.create();
        return new ListObservable(directoryVisitor,postman);
    }

    private ListObservable(DirectoryVisitor directoryVisitor, Postman postman){
        this.directoryVisitor = directoryVisitor;
        this.postman = postman;
        this.filesSended = new ArrayList<>();
        this.listFlag = new AtomicBoolean(false);
    }

    public void setListView(List fileList){
        this.fileList = fileList;
    }

    public String[] getListOfUsersFromServer(){
        return postman.listOfUsers();
    }
    public void getListOfFileFromServer(){
        postman.list(this.directoryVisitor.getUserName(),this.filesFromServer,listFlag);
        /*Iterator iter = filesFromServer.iterator();

        while(iter.hasNext()){
            String fileName = (String)iter.next();
            if(!list.contains(fileName.toUpperCase())){
                postman.downFile(directoryVisitor.getUserName(),fileName,directoryVisitor.getDirPath());
            }
        }*/
    }

    public void run() {
        //log.info("run");
            List<String> filesInDir =  directoryVisitor.getFileList().stream().map(File::getName).collect(Collectors.toList());
            if(listFlag.get()){
                perriorFilesFromServer = filesFromServer.get();
            }
            filesInDir.stream().forEach( file->{
                if(!fileList.contains(file)) {
                    fileList.add(file);
                    if(listFlag.get()&&!filesSended.contains(file)&&!perriorFilesFromServer.contains(file)){
                        postman.sendFile(this.directoryVisitor.getUserName(),file,this.directoryVisitor.getDirPath());
                        filesSended.add(file);
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
