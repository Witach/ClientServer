package sample.ServerConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        this.taskController =taskController;
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
        if(info[0].equals("SEND")||info[0].equals("SHARE")){
            taskController.addHeavyTask(new Task(StrategyFactory.createStrategy(info[0]),info,session,pathToServerDir));
        }
        else{
            taskController.addLightTask(new Task(StrategyFactory.createStrategy(info[0]),info,session,pathToServerDir));
        }
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
