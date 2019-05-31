package sample.Dialogs;

import javafx.scene.control.Alert;

public class ErrorDialog {
    public static void showDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Problem z wcztniem fxml lub controller");
        alert.showAndWait();
    }
    public static void showDialog(String className){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Problem z "+className);
        alert.showAndWait();
    }
}
