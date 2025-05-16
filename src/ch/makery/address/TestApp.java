package ch.makery.address;

import java.io.IOException;

import ch.makery.address.view.UserInfoDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML pour le UserInfoDialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/view/UserInfoDialog.fxml"));
            AnchorPane page = loader.load();  // Charger le fichier FXML

            // Créer le Stage pour la fenêtre modale
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Informations personnelles");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);  // Utiliser le primaryStage comme owner
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
         // Passer le dialogStage au contrôleur
            UserInfoDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);  // Passer le Stage à la fenêtre modale
            // Afficher la fenêtre
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
