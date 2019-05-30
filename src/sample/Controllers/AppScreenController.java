package sample.Controllers;
import Postman.Postman;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppScreenController {
    private MainScreenController mainScreenController;
    private Postman postman;

    private Logger log = LoggerFactory.getLogger(getClass());

    @FXML
    Text annoucment;
    @FXML
    ListView files;

    public void setMainScreenController(MainScreenController mainScreenController){
        this.mainScreenController = mainScreenController;
        log.info("Ustawiono mainScreen dla SignInScreen");
    }

    public void setPostman(Postman postman){
        this.postman = postman;
    }

    @FXML
    public void initialize(){
        //annoucment.setText(postman.getDirPath()+postman.getUserName());
    }


}
