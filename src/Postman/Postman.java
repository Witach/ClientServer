package Postman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Postman{
    ExecutorService executor;
    private Logger log = LoggerFactory.getLogger(getClass());

    public Postman(ExecutorService executor){
        this.executor = executor;
    }
    //SEND|USER|NAMEOFFILE|SIZEOFFILE
//DOWN|USER|FILENAME
//SHARE|USER|FILENAME|TOUSER
//LIST|USER
    //USERS
    public void sendFile(final String userName,final  String fileName, final String dirPath){
        log.info("SendFile1");
        executor.submit(()-> {
            log.info("SendFile2");
                try {
                        File file = new File(dirPath+"\\"+fileName);
                        String message = "SEND;"+userName+";"+fileName+";"+(int)file.length();
                        log.info("Sending 3");
                        Connection connection = Connection.factory();
                        log.info("Sending 4");
                        connection.sendFile(message,file);
                    }catch (IOException e){
                        log.info("Sending error");
                    e.printStackTrace();
                }
        });
    }

    public void downFile(final String userName,final  String fileName, final String dirPath){
        executor.submit(()->{
                try {
                    String message = "DOWN;"+userName+";"+fileName;
                    Connection connection = Connection.factory();
                    connection.downloadFile(message,dirPath+"\\"+fileName);
                }catch (IOException e){
                    e.printStackTrace();
                }
        });
    }

    public void share(final String userName, final String fileName, final String toUser){
        executor.submit(()->{
            try {
                String message = "SHARE;"+userName+";"+fileName+";"+toUser;
                Connection connection = Connection.factory();
                connection.sendMessage(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void list(final String userName, final AtomicReference<List<String>> atomicListOfFile, AtomicBoolean flag){
        executor.submit(()->{
            try {
                String message = "LIST;"+userName;
                Connection connection = Connection.factory();
                connection.sendMessage(message);
                String fileListString = connection.getMessage();
                String[] fileList = fileListString.split(";");
                atomicListOfFile.set(Arrays.asList(fileList));
                flag.set(true);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public String[] listOfUsers(){
        String[] list = null;
            try {
                String message = "USERS";
                Connection connection = Connection.factory();
                connection.sendMessage(message);
                String usersListString = connection.getMessage();
                list =  usersListString.split(";");
                return list;
            }catch (IOException e){
                e.printStackTrace();
            }
            return list;
    }
}
