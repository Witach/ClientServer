package Postman;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Postman{
    private ExecutorService executor;

    public Postman(ExecutorService executor){
        this.executor = executor;
    }

    public void sendFile(){

        //SEND
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {

                }catch (IOException e)
            }
        });


    }

    public void updateDir(){

    }

    public void shareFile(){

    }

}
