package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryVisitor {
    private  String dirPath;
    private  File dir;
    private List<File> fileList;

    private Logger log = LoggerFactory.getLogger(getClass());

    public String getDirPath(){
        return dirPath;
    }

    public DirectoryVisitor( String dirPath){
        log.info("DirectoryVisitor tworzenie");
        this.dirPath = dirPath;
        this.dir =  new File(this.dirPath);
        if (!dir.exists())
            dir.mkdir();
        this.dir =  new File(this.dirPath);
        File[] fileArray =  dir.listFiles();
        fileList = Arrays.stream(fileArray).filter(a->a.isFile()).collect(Collectors.toList());
        log.info("DirectoryVisitor pomyślnie");
    }

    public List<File> getFileList(){
        //log.info("Pobieranie listy plików");
        File[] fileArray =  dir.listFiles();
        fileList = Arrays.stream(fileArray).filter(a->a.isFile()).collect(Collectors.toList());
        //log.info(fileList.toString());
        return fileList;
    }

}
