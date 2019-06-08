package sample.ServerConnection;

import sample.ServerConnection.Strategy.Strategy;


public class Task {
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

}
