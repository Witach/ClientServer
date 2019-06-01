package Postman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Postman{
    private ExecutorService executor;
    private Logger log = LoggerFactory.getLogger(getClass());

    public Postman(ExecutorService executor){
        this.executor = executor;
    }
    //SEND|USER|NAMEOFFILE|SIZEOFFILE
//DOWN|USER|FILENAME
//SHARE|USER|TOUSER
//LIST|USER
    public void sendFile(final String userName,final  String fileName, final String dirPath){
        log.info("SendFile1");
        executor.submit(()-> {
            log.info("SendFile2");
                try {
                    File file = new File(dirPath+"\\"+fileName);
                    String message = "SEND|"+userName+"|"+fileName+"|"+(int)file.length();
                    log.info("Sending 3");
                    Connection connection = Connection.factory();
                    log.info("Sending 4");
                    connection.sendFile(message,file);
                    connection.shutDown();
                }catch (IOException e){
                    log.info("Sending error");
                    e.printStackTrace();
                }
        });
    }

    public void downFile(final String userName,final  String fileName, final String dirPath){
        executor.submit(()->{
                try {
                    String message = "DOWN|"+userName+"|"+fileName;
                    Connection connection = Connection.factory();
                    connection.downloadFile(message,dirPath+"\\"+fileName);
                    connection.shutDown();
                }catch (IOException e){
                    e.printStackTrace();
                }
        });
    }

    public void share(final String userName, final String fileName){
        executor.submit(()->{
            try {
                String message = "SHARE|"+userName+"|"+fileName;
                Connection connection = Connection.factory();
                connection.sendMessage(message);
                connection.shutDown();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void list(final String userName, final ArrayList<String> fileList){
        executor.submit(()->{
            try {
                String message = "LIST|"+userName;
                Connection connection = Connection.factory();
                connection.sendMessage(message);
                String fileListString = connection.getMessage();
                Arrays.stream(fileListString.split("|")).forEach(fileName->fileList.add(fileName));
                connection.shutDown();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

}
