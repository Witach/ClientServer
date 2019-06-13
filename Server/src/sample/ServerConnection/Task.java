package sample.ServerConnection;

import sample.ServerConnection.Strategy.Strategy;

import java.io.IOException;


public class Task implements  Runnable{
    Strategy strategy;
    String[] paramteres;
    Session session;
    String dirPath;

    public Task(Strategy strategy, String[] paramteres, Session session, String dirPath){
        this.strategy = strategy;
        this.paramteres = paramteres;
        this.session = session;
        this.dirPath = dirPath;
    }

    @Override
    public void run(){
        try {
            strategy.reply(session,dirPath,paramteres);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
