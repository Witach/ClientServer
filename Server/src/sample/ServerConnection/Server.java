package sample.ServerConnection;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;

public class Server extends Thread{

    private File serverDir;
    private  TaskController taskController;
    private ServerSocket serverSocket;
    Logger log = LoggerFactory.getLogger(getClass().getName());
    public Server(String pathToServerDir, int amountOfLightWorkers){
        serverDir = new File(pathToServerDir);
        taskController = new TaskController(amountOfLightWorkers);
        FileControllerSingleton.create(pathToServerDir);
        SemaphoreSingleton.create();
        try {
            serverSocket = new ServerSocket( 65534);
            log.info("utowrzono server socket");
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        while(true){
            listenSock();
        }
    }

    private void listenSock(){
        Socket socket = null;
        try {
            log.info("czekanie na klienta");
            socket = serverSocket.accept();
            log.info("zaakcptowano gniazdo sieciowe");
            Postman postman = Postman.factory(socket,serverDir.getAbsolutePath(),taskController);
            log.info("utworzono postman dla klienta");
            postman.start();
            log.info("uruchomiono wątek klienta");
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

    }

}
