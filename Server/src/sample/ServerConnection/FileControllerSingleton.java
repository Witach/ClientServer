package sample.ServerConnection;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FileControllerSingleton {
    List<File> listOfDirs;
    public List<File> listOfCsv;
    public AtomicInteger amountOfFilesInDirs;
    public String pathToServerDir;
    static FileControllerSingleton fileControllerSingleton;

    public static void create(String pathToServerDir){
        if(fileControllerSingleton == null) {
            fileControllerSingleton = new FileControllerSingleton();
            File dir = new File(pathToServerDir);
            File[] dirs = dir.listFiles();
            fileControllerSingleton.listOfDirs = Arrays.asList(dirs);
            fileControllerSingleton.listOfCsv = fileControllerSingleton.listOfDirs.stream()
                    .map(file -> Paths.get(file.getAbsolutePath() + "/info.csv").toFile())
                    .collect(Collectors.toList());
            fileControllerSingleton.amountOfFilesInDirs = new AtomicInteger();
            fileControllerSingleton.listOfDirs.stream()
                    .forEach(
                            files ->
                            fileControllerSingleton.amountOfFilesInDirs.addAndGet(files.listFiles().length)
                    );
            fileControllerSingleton.pathToServerDir = pathToServerDir;
        }
    }
    private  FileControllerSingleton(){}
    public  static FileControllerSingleton  getInstance(){
        return fileControllerSingleton;
    }



}
