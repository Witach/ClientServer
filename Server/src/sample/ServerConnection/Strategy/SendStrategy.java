package sample.ServerConnection.Strategy;

import sample.ServerConnection.SemaphoreSingleton;
import sample.ServerConnection.Session;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SendStrategy implements Strategy {

    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
       byte[] bytesOfFile = session.downloadFile(Integer.parseInt(parameters[2]));
       ArrayList<String> dirNames = new ArrayList<>();
        File serverDir = new File(pathToServerDir);
        for(File file: serverDir.listFiles()){
            if(file.isDirectory()){
                dirNames.add(file.getName());
            }
        }
        ArrayList<Thread> writers = new ArrayList<>();
       ArrayList<Thread> workers = new ArrayList<>();
        for(int i=0;i<dirNames.size();i++){
            workers.add(getThread(Paths.get(pathToServerDir+"/"+dirNames.get(i)+"/info.csv").toString(),
                    parameters[1],
                    parameters[0],
                    i
            ));
            writers.add(writeIntoDirs(
                    pathToServerDir+"/"+dirNames.get(i),
                    parameters[1],
                    bytesOfFile
            ));
        }
        for(Thread thread: writers){
            thread.start();
        }
        while(true){
            for (int i=0 ; i<workers.size() ; i++){
                if(writers.get(i).isInterrupted()&&!workers.get(i).isInterrupted()){
                   workers.get(i).start();
                }
            }
            int flag=0;
            for(Thread thread: workers){
                if(thread.isInterrupted())
                    flag++;
            }
            if (flag>=5)
                break;
            else
                flag=0;
        }
    }

    private Thread getThread(final String filePath,final String fileName,final String username, int semaphoreIndex){
        return new Thread(()->{
            try {
                SemaphoreSingleton.get(semaphoreIndex).acquire();
                PrintWriter writer = new PrintWriter(new FileWriter( Paths.get(filePath).toString(), true));
                writer.write(fileName + ";" + username + ";");
                writer.close();
                SemaphoreSingleton.get(semaphoreIndex).release();
            } catch (InterruptedException|IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

    private Thread writeIntoDirs(final String pathToDir, final String fileName,final byte[] file){
        return new Thread(()->{
            try {
                FileOutputStream fos = new FileOutputStream(Paths.get(pathToDir+"/"+fileName).toString());
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(file, 0, file.length);
                bos.flush();
            }catch (IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}
