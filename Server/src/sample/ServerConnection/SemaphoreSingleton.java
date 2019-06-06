package sample.ServerConnection;

import java.util.concurrent.Semaphore;

public class SemaphoreSingleton {
    static Semaphore semaphore;

    private void setSemaphore(Semaphore semaphore){
        SemaphoreSingleton.semaphore = semaphore;
    }

    public static Semaphore get(){
        if(semaphore==null){
            semaphore = new Semaphore(1);
        }
        return semaphore;
    }
}
