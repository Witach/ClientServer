package sample.ServerConnection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private File serverDir;
    private ServerSocket serverSocket;

    public Server(String pathToServerDir){
        serverDir = new File(pathToServerDir);
        try {
            serverSocket = new ServerSocket(600023);
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
            socket = serverSocket.accept();
            Postman postman = Postman.factory(socket,serverDir.getAbsolutePath());
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

    }
}
