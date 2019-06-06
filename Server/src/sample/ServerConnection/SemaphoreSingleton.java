package sample.ServerConnection;

import java.util.concurrent.Semaphore;

public class SemaphoreSingleton {
    static public Semaphore[] semaphore;

    public static Semaphore get(int index){
        if(semaphore==null){
            semaphore = new Semaphore[5];
            for(int i=0;i<5;i++){
                semaphore[i] = new Semaphore(1);
            }
        }
        return semaphore[index];
    }
}
