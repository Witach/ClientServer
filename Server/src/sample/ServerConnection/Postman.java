package sample.ServerConnection;

import sample.ServerConnection.Strategy.Strategy;
import sample.ServerConnection.Strategy.StrategyFactory;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Postman {
    Session session;
    String pathToServerDir;
    private Postman(Socket socket, String pathToServerDir) throws IOException {
        this.session = Session.factory(socket);
        this.pathToServerDir = pathToServerDir;
    }

    public static Postman factory(Socket socket, String pathToServerDir){
        Postman postman = null;
        try{
            postman = new Postman(socket, pathToServerDir);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return postman;
    }
    public String waitForRequest() throws IOException{
       return  session.getMessage();
    }

    public void serve(String request) throws IOException {
        List<String> info = Tokenizer.unTokenize(request);
        Strategy strategy = StrategyFactory.createStrategy(info.get(0).toUpperCase());
        info.remove(info.get(0));
        strategy.reply(session,pathToServerDir,(String[])info.toArray());
    }
}
