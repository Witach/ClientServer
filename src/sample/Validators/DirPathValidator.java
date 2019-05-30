package sample.Validators;


import java.io.File;

public class DirPathValidator {
    static public boolean validate(String dirPath){
        File dir = new File(dirPath);
        if(!dir.exists()||dir.isFile())
            return false;
        return true;
    }
}
