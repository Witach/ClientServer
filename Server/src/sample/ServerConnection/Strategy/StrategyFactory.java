package sample.ServerConnection.Strategy;

public class StrategyFactory {

    static public Strategy createStrategy(String strategy){
        if(strategy.equals("SEND"))
            return new SendStrategy();
        if(strategy.equals("LIST"))
            return new ListStrategy();
        if(strategy.equals("SHARE"))
            return new ShareStrategy();
        if(strategy.equals("DOWN"))
            return new DownStrategy();
        return null;
    }
}
