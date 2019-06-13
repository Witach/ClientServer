package sample.ServerConnection.Strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ServerConnection.FileControllerSingleton;
import sample.ServerConnection.Search;
import sample.ServerConnection.SemaphoreSingleton;
import sample.ServerConnection.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
public class ShareStrategy implements Strategy {
    Logger log = LoggerFactory.getLogger(getClass().getName());

    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
        log.info("użyto share strategy");
        int dirNumber = Search.getNumberOfDirOfFile(parameters[2]);
        File csv = FileControllerSingleton.getInstance().listOfCsv.get(dirNumber);
        Scanner scanner = new Scanner(new FileInputStream(csv));
        long position = 0;
        String line = null;
        log.info("użyto stream do csv");
        log.info("znaleziono pozycje");
        Semaphore writersSemaphore = SemaphoreSingleton.getWritersSemaphore(dirNumber);
        Semaphore readersSemaphore = SemaphoreSingleton.getReadersSemaphore(dirNumber);
        try {
            readersSemaphore.acquire();
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                if(line.contains(parameters[2])){
                    if(line.contains(parameters[3])){
                        scanner.close();
                        return;
                    }
                    position += line.length();
                    if(scanner.hasNextLine())
                        line = scanner.nextLine();
                    else
                        line = "";
                    break;
                }else {
                    position += line.length();
                }
            }
            writersSemaphore.acquire();
            RandomAccessFile randomAccessFile = new RandomAccessFile(csv.getAbsolutePath(),"rw");
            randomAccessFile.seek(position);
            randomAccessFile.writeUTF(parameters[3]+",\n");
            if(line==null){
                return;
            }
            randomAccessFile.writeUTF(line);
            log.info("nadpisano pozwolenia");
            while(scanner.hasNextLine()){
                randomAccessFile.writeUTF(scanner.nextLine());
            }
            randomAccessFile.close();
            scanner.close();
            log.info("zakończono share");
        } catch (InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        } finally {
            readersSemaphore.release();
            writersSemaphore.release();
        }
    }
}
