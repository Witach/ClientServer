package sample.ServerConnection;

import java.util.concurrent.Semaphore;

public class SemaphoreSingleton {
     private static SemaphoreSingleton semaphoreSingleton;
     public Semaphore[] writersSemaphore;
     public Semaphore[] readersSemaphore;

     private SemaphoreSingleton(){
        if(writersSemaphore==null){
            writersSemaphore = new Semaphore[5];
            for(int i=0;i<5;i++){
                writersSemaphore[i] = new Semaphore(1);
            }
        }
        if(readersSemaphore==null){
            readersSemaphore = new Semaphore[5];
            for(int i=0;i<5;i++){
                readersSemaphore[i] = new Semaphore(1);
            }
        }
    }
     public static SemaphoreSingleton create(){
        if(semaphoreSingleton == null){
            semaphoreSingleton = new SemaphoreSingleton();
        }
        return semaphoreSingleton;
    }
    public static Semaphore getWritersSemaphore(int index){
        return semaphoreSingleton.writersSemaphore[index];
    }

    public static Semaphore getReadersSemaphore(int index){
        return semaphoreSingleton.readersSemaphore[index];
    }
}
