package sample.ServerConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ServerConnection.Strategy.Strategy;
import sample.ServerConnection.Strategy.StrategyFactory;

import java.io.IOException;
import java.net.Socket;

public class Postman extends Thread{
    Session session;
    String pathToServerDir;
    TaskController taskController;
    static Logger log = LoggerFactory.getLogger(Postman.class.getName());
    private Postman(Socket socket, String pathToServerDir, TaskController taskController) throws IOException {
        this.session = Session.factory(socket);
        this.pathToServerDir = pathToServerDir;
        this.taskController = taskController;
    }

    public static Postman factory(Socket socket, String pathToServerDir, TaskController taskController){
        log.info("tworzenie postman");
        Postman postman = null;
        try{
            postman = new Postman(socket, pathToServerDir, taskController);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        log.info("utworzono postman");
        return postman;
    }
    public String waitForRequest() throws IOException{
       return  session.getMessage();
    }

    public void serve(String request){
        log.info("rozpoczęcie obsługi");
        String[] info = Tokenizer.unTokenize(request);
        Strategy strategy = StrategyFactory.createStrategy(info[0]);
        taskController.addTask(new Task(strategy, info, session,pathToServerDir));
    }
    @Override
    public void run(){
        try {
            String request =  waitForRequest();
            serve(request);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
