package sample.Controllers;

import Postman.DirectoryVisitor;
import Postman.ListObservable;
import Postman.Postman;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Dialogs.LogInDialog;
import sample.Loader;
import sample.Validators.DirPathValidator;
import sample.Validators.UserNameValidator;

import java.util.concurrent.Executors;


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
        if(!checkUserName(nameofuser.getText()))
            return;
       log.info("Wczytano informacje uzytkownika");
       if(!checkDirPath(filepath.getText()))
           return;
        log.info("Wczytano katalog");
        log.info("Ładowanie AppScreen.fxml");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../Screens/AppScreen.fxml"));
        log.info("Załadowanao  AppScreen.fxml");
        Pane pane = Loader.loadFXMLAsPaneAndLog(loader);
        AppScreenController appScreenController = loader.getController();
        appScreenController.setMainScreenController(mainScreenController);
        DirectoryVisitor directoryVisitor = new DirectoryVisitor(nameofuser.getText(),filepath.getText());
        Postman postman = new Postman(Executors.newFixedThreadPool(5));
        ListObservable listObservable = new ListObservable(directoryVisitor,postman);
        mainScreenController.setScreen(pane);
        appScreenController.setListObservableAndInit(listObservable);
    }

    public void setMainScreenController(MainScreenController mainScreenController){
        this.mainScreenController = mainScreenController;
        log.info("Ustawiono mainScreen dla SignInScreen");
    }

    private boolean checkDirPath(String dirPath){
        if(!DirPathValidator.validate(dirPath)){
            LogInDialog.showDialog("Błąd ścieżki do katalogu","Podany katalog nie istnieje lub  jest plikiem");
            return false;
        }
        return true;

    }

    private boolean checkUserName(String userName){
        if(!UserNameValidator.validate(userName)){
            LogInDialog.showDialog("Błąd nazwy użytkownika","Nazwa użytkownika nie może zawierać białych znaków");
            return false;
        }
        return true;
    }

}
