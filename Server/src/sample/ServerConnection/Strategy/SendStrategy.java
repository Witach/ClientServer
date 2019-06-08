package sample.ServerConnection.Strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ServerConnection.SemaphoreSingleton;
import sample.ServerConnection.Session;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
public class SendStrategy implements Strategy {

    Logger log = LoggerFactory.getLogger(getClass().getName());
    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
        log.info("użyto sendstrategy");
       byte[] bytesOfFile = session.downloadFile(Integer.parseInt(parameters[3]));
        log.info("użyto session.download");
       ArrayList<String> dirNames = new ArrayList<>();
        File serverDir = new File(pathToServerDir);
        for(File file: serverDir.listFiles()){
            if(file.isDirectory()){
                dirNames.add(file.getName());
            }
        }
        log.info("utworzono dirnames");
       ArrayList<Thread> writers = new ArrayList<>();
        for(int i=0;i<dirNames.size();i++){
            writers.add(getThread(Paths.get(pathToServerDir+"/"+dirNames.get(i)).toString(),
                    parameters[2],
                    parameters[1],
                    i,
                    bytesOfFile
            ));
        }
        try {
            for(Thread thread: writers){
                thread.start();
            }
            for(Thread thread: writers){
                thread.join();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }
        log.info("aktywowano writers ");
    }

    private Thread getThread(final String filePath,final String fileName,final String username, int semaphoreIndex,byte[] file){
        return new Thread(()->{
            PrintWriter writer = null;
            try {
                FileOutputStream fos = new FileOutputStream(Paths.get(filePath+"/"+fileName).toString());
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(file, 0, file.length);
                bos.flush();
                bos.close();
                SemaphoreSingleton.getWritersSemaphore(semaphoreIndex).acquire();
                SemaphoreSingleton.getReadersSemaphore(semaphoreIndex).acquire();
                writer = new PrintWriter(new FileWriter( Paths.get(filePath+"/info.csv").toString(), true));
                writer.write(fileName + ";" + username + ";\n");
                writer.close();
            } catch (InterruptedException|IOException e){
                e.printStackTrace();
                System.exit(1);
            } finally {
                SemaphoreSingleton.getWritersSemaphore(semaphoreIndex).release();
                SemaphoreSingleton.getReadersSemaphore(semaphoreIndex).release();
            }
        });
    }

}
