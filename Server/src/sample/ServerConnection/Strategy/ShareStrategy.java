package sample.ServerConnection.Strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
       File csv = Search.getCvsWithFile(pathToServerDir,parameters[0],parameters[1]);
        Scanner scanner = new Scanner(new FileInputStream(csv));
        long position = 0;
        String line = null;
        String line2 = null;
        log.info("użyto stream do csv");
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.contains(parameters[2])&&line.contains(parameters[1])){
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
        log.info("znaleziono pozycje");
        String[] tmp =  csv.getAbsolutePath().split("/");
        int index = Integer.parseInt(tmp[tmp.length-2]);
        Semaphore writersSemaphore = SemaphoreSingleton.getWritersSemaphore(index);
        Semaphore readersSemaphore = SemaphoreSingleton.getReadersSemaphore(index);
        try {
            readersSemaphore.acquire();
            writersSemaphore.acquire();
            RandomAccessFile randomAccessFile = new RandomAccessFile(csv.getAbsolutePath(),"rw");
            randomAccessFile.seek(position);
            if(line==null){
                return;
            }
            log.info("nadpisano pozwolenia");
            randomAccessFile.writeUTF(parameters[3]+",\n");
            randomAccessFile.writeUTF(line);
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
