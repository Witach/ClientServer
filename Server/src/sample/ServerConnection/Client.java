package sample.ServerConnection;

import javafx.geometry.Pos;

import java.io.IOException;

public class Client extends Thread {

    private Postman postman;

    public Client(Postman postman){
        this.postman = postman;
    }
    @Override
    public void run() {
        try{
            String request = postman.waitForRequest();
            postman.serve(request);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
