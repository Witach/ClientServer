package sample.Dialogs;

import javafx.scene.control.Alert;

public class LogInDialog {
    public static void showDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
