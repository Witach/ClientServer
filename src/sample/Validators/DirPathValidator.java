package sample.Validators;


        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import java.io.File;

public class DirPathValidator {
    static public boolean validate(String dirPath){
        File dir = new File(dirPath);
        if(!dir.exists()||dir.isFile())
            return false;
        return true;
    }
}
