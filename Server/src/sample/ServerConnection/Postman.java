package sample.ServerConnection;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Postman {
    Session session;
    private Postman(Socket socket) throws IOException {
        this.session = Session.factory(socket);
    }

    public Postman factory(Socket socket){
        Postman postman = null;
        try{
            postman = new Postman(socket);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return postman;
    }
    public String waitForRequest(){
       return  session.getMessage();
    }

    private void serve(String request){
        String[] info = request.split(Tokenizer.getSeparator());

    }
}
