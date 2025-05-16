package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignInCentreDialogController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private Stage dialogStage;
    private boolean loginSuccessful = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("Admin".equals(username) && "Admin".equals(password)) {
            loginSuccessful = true;
            dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Erreur d'authentification");
            alert.setHeaderText("Identifiant ou mot de passe incorrect.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
