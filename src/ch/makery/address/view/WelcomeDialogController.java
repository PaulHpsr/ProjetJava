package ch.makery.address.view;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class WelcomeDialogController {

    private Stage dialogStage;
    private MainApp mainApp;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleSignIn() {
        if (mainApp != null) {
            dialogStage.close();  // ferme la fenêtre d'accueil
            mainApp.showSignInDialog();  // ouvre la fenêtre de connexion
        } else {
            System.out.println("MainApp is null!");
        }
    }
  
    @FXML
    private void handleSignUp() {
        if (mainApp != null) {
            dialogStage.close();  // ferme la fenêtre d'accueil
            mainApp.showSignUpDialog();  // ouvre la fenêtre de connexion
        } else {
            System.out.println("MainApp is null!");
        }
    }
    
    @FXML
    private void handleCentreTri() {
        boolean loggedIn = mainApp.showSignInCentreDialog();
        if (loggedIn) {
        	dialogStage.close();
        	mainApp.showCentreTriDialog();
        }
    }
}
