package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Main;
import sample.Validators.UserNameValidator;

import java.util.logging.Level;

public class SignInController {

    private MainScreenController mainScreenController;

    @FXML
    TextField filepath;

    @FXML
    TextField nameofuser;

    private Logger log = LoggerFactory.getLogger(getClass());


    @FXML
    void login(){
        log.info("użyto login");
       String userName = nameofuser.getText();
       if(!UserNameValidator.validate(userName)){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Błąd nazwy użytkownika");
           alert.setHeaderText("Nazwa użytkownika nie może zawierać białych znaków");
           alert.showAndWait();
           return;
       }
       log.info("Wczytano informacje uzytkownika");

    }

    public void setMainScreenController(MainScreenController mainScreenController){
        this.mainScreenController = mainScreenController;
        log.info("Ustawiono mainScreen dla SignInScreen");
    }
}
