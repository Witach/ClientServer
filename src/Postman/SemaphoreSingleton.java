package Postman;

import java.util.concurrent.Semaphore;

public class SemaphoreSingleton {
    private Semaphore listSemaphore;
    private static SemaphoreSingleton semaphoreSingleton;
    private SemaphoreSingleton(Semaphore semaphore){
        listSemaphore = semaphore;
    }
    public static void create(){
        semaphoreSingleton = new SemaphoreSingleton(new Semaphore(1));
    }

    public static Semaphore getListSemaphore() {
        return semaphoreSingleton.listSemaphore;
    }

}
