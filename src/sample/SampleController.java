
package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class SampleController {

    @FXML
    private Button signIn;

    @FXML
    void initialize(){
        signIn.setText("siema");
    }
}
