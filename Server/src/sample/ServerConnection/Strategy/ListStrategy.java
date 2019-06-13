package sample.ServerConnection.Strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ServerConnection.Search;
import sample.ServerConnection.SemaphoreSingleton;
import sample.ServerConnection.Session;
import sample.ServerConnection.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class ListStrategy implements Strategy{

    Logger log = LoggerFactory.getLogger(getClass().getName());
    @Override
    public void reply(Session session, String parthToServerDir, String... parameters) throws IOException {
        log.info("strategia list");
        Semaphore semaphore = SemaphoreSingleton.getReadersSemaphore(0);
        List<File> fileList = null;
        try {
            semaphore.acquire();
            fileList = Search.getListOfFileWithCheckPermmision(parameters[1]);
        } catch (InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }finally {
            semaphore.release();
        }
        log.info("użyto getListOfFilesWithPermissionCheck");
        List<String> listOfNames = fileList.stream()
                .map(a -> a.getName())
                .collect(Collectors.toList());
        log.info("utworzono listOfNames");
        String message = Tokenizer.create().tokenize(listOfNames);
        log.info("użyto tokenizer");
        session.sendMessage(message);
        log.info("użyto session,sendMessage");
    }
}
