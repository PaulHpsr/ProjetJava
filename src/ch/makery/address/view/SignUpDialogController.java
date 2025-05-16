package ch.makery.address.view;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.monprojet.DAO.MenageDAO;
import com.monprojet.classes.Menage;

import ch.makery.address.MainApp;

public class SignUpDialogController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField codeAccesField;

    private MainApp mainApp;
    private Stage dialogStage;

    private boolean okClicked = false;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        String nom = nomField.getText();
        String codeAcces = codeAccesField.getText();

        if (nom.isEmpty() || codeAcces.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        int codeAccesInt;
        try {
            codeAccesInt = Integer.parseInt(codeAcces);
        } catch (NumberFormatException e) {
            showAlert("Le code d'accès doit être un nombre.");
            return;
        }

        // Vérifier si l'utilisateur existe déjà
        if (MenageDAO.doesUserExist(nom, codeAccesInt)) {
            showAlert("Un utilisateur avec ce nom et ce code d'accès existe déjà !");
            return;
        }

        // Créer un nouvel utilisateur 
        @SuppressWarnings("unused")
		Menage newUser = new Menage(nom, codeAccesInt);

        showAlert("Inscription réussie !");
        okClicked = true;
        dialogStage.close();
        mainApp.showWelcomeDialog();
    }

    @FXML
    public void handleCancel() {
        if (dialogStage != null) {
            dialogStage.close();
            mainApp.showWelcomeDialog();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
