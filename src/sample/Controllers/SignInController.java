package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Validators.UserNameValidator;

import java.util.logging.Level;

public class SignInController {


    @FXML
    TextField textarea;

    @FXML
    TextField nameofuser;

    private Logger log = LoggerFactory.getLogger(getClass());


    @FXML
    void logIn(){
        log.info("użyto login");
       String userName = nameofuser.getText();
       if(!UserNameValidator.validate(userName))
           System.exit(1);
       log.info("Wczytano informacje uzytkownika");
    }

}
