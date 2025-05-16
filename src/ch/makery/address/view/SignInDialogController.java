package ch.makery.address.view;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.stage.Stage;


import com.monprojet.DAO.MenageDAO;

import ch.makery.address.MainApp;



public class SignInDialogController {

    @FXML
    private TextField firstNameField;
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
    public String getNom() {
        return firstNameField.getText();
    }

    public int getCodeAcces() {
        return Integer.parseInt(codeAccesField.getText());
    }
    @FXML
    private void handleOk() {
        String nom = firstNameField.getText();
        String codeAcces = codeAccesField.getText();

        if (nom.isEmpty() || codeAcces.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Convertir le code d'accès en entier
        int codeAccesInt;
        try {
            codeAccesInt = Integer.parseInt(codeAcces);
        } catch (NumberFormatException e) {
            showAlert("Le code d'accès doit être un nombre.");
            return;
        }

        // Vérifier si l'utilisateur existe dans la base de données
        if (MenageDAO.doesUserExist(nom, codeAccesInt)) {
            showAlert("Connexion réussie !");
            okClicked = true;
            dialogStage.close(); 

            // Récupérer les informations de l'utilisateur dans la base de données
            String userInfo = MenageDAO.getUserInfo(nom, codeAccesInt);
            if (userInfo != null) {
                String[] parts = userInfo.split(",");
                String userName = parts[0];
                int userId = Integer.parseInt(parts[1]);
                int userPoints = Integer.parseInt(parts[2]);
               

                // Appeler la méthode showUserInfoDialog dans le MainApp pour afficher les infos utilisateur
                if (mainApp != null) {
                    mainApp.showUserInfoDialog(userName, userId, userPoints,codeAccesInt);  // Ouvre le UserInfoDialog
                }
            } else {
                System.out.println("Aucun utilisateur trouvé.");
            }

        } else {
            showAlert("Nom ou code d'accès incorrect !");
        }
    }

   
    
    // Méthode de gestion du bouton Annuler
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
