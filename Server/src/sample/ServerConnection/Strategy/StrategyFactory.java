package sample.ServerConnection.Strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategyFactory {
    static Logger log = LoggerFactory.getLogger(StrategyFactory.class.getName());
    static public Strategy createStrategy(String strategy){
        log.info("użyto createStrategy");
        if(strategy.equals("SEND"))
            return new SendStrategy();
        if(strategy.equals("LIST"))
            return new ListStrategy();
        if(strategy.equals("SHARE"))
            return new ShareStrategy();
        if(strategy.equals("DOWN"))
            return new DownStrategy();
        log.info("nieutworzono strategy");
        return null;
    }
}
