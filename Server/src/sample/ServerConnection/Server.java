package sample.ServerConnection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private File serverDir;
    private ServerSocket serverSocket;

    private Server(String pathToServerDir){
        serverDir = new File(pathToServerDir);
        try {
            serverSocket = new ServerSocket(600023);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void listenSock(){
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

    }
}
