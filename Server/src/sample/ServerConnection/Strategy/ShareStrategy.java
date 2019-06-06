package sample.ServerConnection.Strategy;

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


    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
       File csv = Search.getCvsWithFile(pathToServerDir,parameters[0],parameters[1]);
        Scanner scanner = new Scanner(new FileInputStream(csv));
        long position = 0;
        String line = null;
        String line2 = null;
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.contains(parameters[1])&&line.contains(parameters[0])){
                if(line.contains(parameters[2])){
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
        String[] tmp =  csv.getAbsolutePath().split("/");
        int index = Integer.parseInt(tmp[tmp.length-2]);
        Semaphore semaphore = SemaphoreSingleton.get(index);
        try {
            semaphore.acquire();
        } catch (InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(csv.getAbsolutePath(),"rw");
        randomAccessFile.seek(position);
        if(line==null){
            return;
        }
        randomAccessFile.writeUTF(parameters[2]+",\n");
        randomAccessFile.writeUTF(line);
        while(scanner.hasNextLine()){
            randomAccessFile.writeUTF(scanner.nextLine());
        }
        semaphore.release();
    }
}
