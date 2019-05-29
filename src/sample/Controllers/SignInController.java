package sample.Controllers;

import javafx.fxml.FXML;

import java.awt.*;
import sample.Validators.UserNameValidator;

public class SignInController {

    @FXML
    TextField dirPathInput;

    @FXML
    TextField userNameInput;

    @FXML
    void logIn(){
        String userName = userNameInput.getText();
        if(!UserNameValidator.validate(userName))
            throw new Exception("Niepoprawna nazwa użytkownika");
    }
}
