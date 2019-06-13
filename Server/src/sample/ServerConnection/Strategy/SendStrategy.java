package sample.ServerConnection.Strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ServerConnection.FileControllerSingleton;
import sample.ServerConnection.SemaphoreSingleton;
import sample.ServerConnection.Session;

import java.io.*;
import java.nio.file.Paths;
public class SendStrategy implements Strategy {

    Logger log = LoggerFactory.getLogger(getClass().getName());
    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
        log.info("użyto sendstrategy");
        byte[] bytesOfFile = session.downloadFile(Integer.parseInt(parameters[3]));
        log.info("użyto session.download");
        int dirNumber = FileControllerSingleton.getInstance().amountOfFilesInDirs.getAndAdd(1)%
                FileControllerSingleton.getInstance().listOfCsv.size();
        log.info("utworzono dirnames");

        File file = Paths.get(pathToServerDir+"/"+dirNumber+"/"+parameters[2]).toFile();
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))){
            bos.write(bytesOfFile, 0, Integer.parseInt(parameters[3]));
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File csv = FileControllerSingleton.getInstance().listOfCsv.get(dirNumber);
        try( PrintWriter writer = new PrintWriter(new FileOutputStream(csv),true)) {
            SemaphoreSingleton.getWritersSemaphore(dirNumber).acquire();
            SemaphoreSingleton.getReadersSemaphore(dirNumber).acquire();
        }catch (InterruptedException|IOException e){
            e.printStackTrace();
        }
        finally {
            SemaphoreSingleton.getWritersSemaphore(dirNumber).release();
            SemaphoreSingleton.getReadersSemaphore(dirNumber).release();
        }
        log.info("aktywowano writers ");
    }
}
