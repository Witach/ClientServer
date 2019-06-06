package sample.ServerConnection.Strategy;

import sample.ServerConnection.SemaphoreSingleton;
import sample.ServerConnection.Session;

import java.io.*;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;

public class SendStrategy implements Strategy {

    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
       session.downloadFile(pathToServerDir,Integer.parseInt(parameters[2]));
       try {
           SemaphoreSingleton.get().acquire();
           PrintWriter writer = new PrintWriter(new FileWriter( Paths.get(pathToServerDir + "/info.csv").toString(), true));
           writer.write(parameters[1] + ";" + parameters[0] + ";");
           writer.close();
           SemaphoreSingleton.get().release();
       } catch (InterruptedException e){
           e.printStackTrace();
           System.exit(1);
       }
    }
}
