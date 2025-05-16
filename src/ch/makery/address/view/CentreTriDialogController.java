package ch.makery.address.view;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.PoubelleIntelligente;
import ch.makery.address.MainApp;

public class CentreTriDialogController {
	//tableau avec listes de poubelles rattaches au centre associe
    @FXML
    private TableView<PoubelleIntelligente> poubelleTable;
    @FXML
    private TableColumn<PoubelleIntelligente, String> emplacementColumn;
    @FXML
    private TableColumn<PoubelleIntelligente, String> etatColumn;
    @FXML
    private TableColumn<PoubelleIntelligente, Integer> idColumn;
    @FXML
    private TableColumn<PoubelleIntelligente, String> typeColumn;

    @FXML
    private Label idLabel;
    @FXML
    private Label emplacementLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label etatLabel;
    
    @FXML
    private TableView<CentreDeTri> centreTable;
    @FXML
    private TableColumn<CentreDeTri, String> nomCentre;
    
    private CentreDeTri currentCentre;

    private MainApp mainApp;

	@SuppressWarnings("unused")
	private ArrayList<CentreDeTri> centres;
    
    private Stage dialogStage;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	public void setListeCentre(ArrayList<CentreDeTri> centres) {
		this.centres = centres;
		centreTable.getItems().setAll(centres);
	}
   
    @FXML
    private void initialize() {
        // Colonne nom centre
        nomCentre.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getNom()));
        
        typeColumn.setCellValueFactory(cellData -> 
        new SimpleStringProperty(typeToString(cellData.getValue().getTypePoub())));


        // Colonnes poubelles
        idColumn.setCellValueFactory(cellData -> 
            //new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
            new SimpleObjectProperty<>(cellData.getValue().getId()));            
        emplacementColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEmplacement()));

        etatColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEtat() ? "Pleine" : "Disponible"));
        

	     etatColumn.setCellFactory(column -> new TableCell<PoubelleIntelligente, String>() {
	         @Override
	         protected void updateItem(String item, boolean empty) {
	             super.updateItem(item, empty);
	
	             if (empty || item == null) {
	                 setText(null);
	                 setStyle("");  
	             } else {
	                 setText(item);
	                 if (item.equals("Pleine")) {
	                     setStyle("-fx-background-color: #ffcccc; -fx-text-fill: red; -fx-font-weight: bold;");
	                 } else {
	                     setStyle("-fx-background-color: #ccffcc; -fx-text-fill: green;");
	                 }
	             }
	         }
	     });


        centreTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldCenter, newCentre) -> {
                currentCentre = newCentre;          
                afficherPoubellesDuCentre(currentCentre);
            }
        );
    }

    
    private void afficherPoubellesDuCentre(CentreDeTri centre) {
        if (centre != null) {
            poubelleTable.getItems().setAll(centre.getListePoubelle());
        } else {
            poubelleTable.getItems().clear();
        }
    }



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    
  
   
    @FXML
    private void handleDeletePoubelle() {
        // On récupère la poubelle sélectionnée
        PoubelleIntelligente selected = poubelleTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            if (currentCentre != null) {
                // Appelle ta méthode retirier pour retirer la poubelle du centre
                currentCentre.retirer(selected.getId());

                // Actualise la table : on repasse la nouvelle liste
                poubelleTable.getItems().setAll(currentCentre.getListePoubelle());

                
            }
        } else {
            // Aucune poubelle sélectionnée : on affiche une alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucune poubelle sélectionnée");
            alert.setContentText("Veuillez sélectionner une poubelle à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPoubelle() {
        // Vérifier si un centre est sélectionné
        if (currentCentre != null) {
            // Demande à l'utilisateur les informations nécessaires pour créer la poubelle
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nouvelle Poubelle");
            dialog.setHeaderText("Ajouter une nouvelle poubelle");
            dialog.setContentText("Entrez l'emplacement de la poubelle:");

            // Attends la réponse de l'utilisateur
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent() && !result.get().isEmpty()) {
                String emplacement = result.get();

                TextInputDialog typeDialog = new TextInputDialog();
                typeDialog.setTitle("Type de la Poubelle");
                typeDialog.setHeaderText("Sélectionner le type de la poubelle");
                typeDialog.setContentText("Entrez le type (0 = Plastique, 1 = Verre, 2 = Carton, 3 = Metal):");

                Optional<String> typeResult = typeDialog.showAndWait();
                if (typeResult.isPresent()) {
                    try {
                        int type = Integer.parseInt(typeResult.get());

                        // Vérifier que le type est valide
                        if (type >= 0 && type <= 3) {
                            // Ajouter la poubelle au centre sélectionné
                            currentCentre.ajouter(emplacement, type);

                            // Actualiser la liste des poubelles affichées
                            poubelleTable.getItems().setAll(currentCentre.getListePoubelle());
                        } else {
                            // Si le type n'est pas valide, on affiche une alerte
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Type invalide");
                            alert.setHeaderText("Le type de la poubelle n'est pas valide.");
                            alert.setContentText("Le type doit être compris entre 0 et 3.");
                            alert.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                        // Si l'utilisateur entre un nombre incorrect pour le type, on affiche une alerte
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Entrée invalide");
                        alert.setHeaderText("Le type doit être un nombre entier.");
                        alert.setContentText("Veuillez entrer un nombre entier pour le type.");
                        alert.showAndWait();
                    }
                }
            }
        } else {
            // Si aucun centre n'est sélectionné, on affiche une alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun centre sélectionné");
            alert.setHeaderText("Veuillez sélectionner un centre de tri.");
            alert.setContentText("Pour ajouter une poubelle, vous devez d'abord sélectionner un centre de tri.");
            alert.showAndWait();
        }
    }

    @FXML
	private void handleClose() {
		if (dialogStage != null) {
			dialogStage.close(); 
			mainApp.showWelcomeDialog();
		}
	}
    @FXML
    private void handleHistorique() {
        if (currentCentre != null) {
            // Nouvelle fenêtre pour afficher l'historique
            Stage historiqueStage = new Stage();
            historiqueStage.setTitle("Historique des Dépôts");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("HistoriqueDepotDialog.fxml"));

            try {
                // Créer l'interface et la scène
                AnchorPane historiqueLayout = loader.load();
                Scene scene = new Scene(historiqueLayout);
                historiqueStage.setScene(scene);

                HistoriqueDepotController historiqueController = loader.getController();
                
                historiqueController.setHistoriqueDepot(currentCentre.getHistoriqueDepot());

                // Afficher la fenêtre
                historiqueStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Si aucun centre n'est sélectionné on affiche une alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun centre sélectionné");
            alert.setHeaderText("Veuillez sélectionner un centre de tri.");
            alert.setContentText("Pour afficher l'historique, vous devez d'abord sélectionner un centre de tri.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleCollecterPoubelle() {
        PoubelleIntelligente selectedPoubelle = poubelleTable.getSelectionModel().getSelectedItem();

        if (selectedPoubelle != null) {
            if (currentCentre != null) {
                // Fenêtre de confirmation
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmer la collecte");
                confirm.setHeaderText("Vider la poubelle n°" + selectedPoubelle.getId());
                confirm.setContentText("Confirmez-vous la collecte de cette poubelle ?");

                Optional<ButtonType> result = confirm.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Si on appuie sur OK : on vide la poubelle
                    currentCentre.collecter(selectedPoubelle.getId());

                    // Actualiser la liste des poubelles
                    poubelleTable.getItems().setAll(currentCentre.getListePoubelle());

                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucune poubelle sélectionnée");
            alert.setContentText("Veuillez sélectionner une poubelle à vider.");
            alert.showAndWait();
        }
    }


    @FXML
    private void handleCollecteGenerale() {
        // Vérifier si un centre est sélectionné
        if (currentCentre != null) {
            // on fait la collecteGenerale sur le centre sélectionné
            currentCentre.collecteGenerale();
            poubelleTable.getItems().setAll(currentCentre.getListePoubelle());

            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Collecte Générale");
            alert.setHeaderText("Collecte générale effectuée");
            alert.setContentText("La collecte générale a été réalisée avec succès.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun centre sélectionné");
            alert.setHeaderText("Veuillez sélectionner un centre de tri.");
            alert.setContentText("Pour effectuer une collecte générale, vous devez d'abord sélectionner un centre.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleStatistiques() {
        if (currentCentre != null) {
            String stats = currentCentre.calculerStatistiquesString();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Statistiques du centre");
            alert.setHeaderText("Statistiques pour le centre : " + currentCentre.getNom());
            alert.setContentText(stats);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun centre sélectionné");
            alert.setHeaderText("Veuillez sélectionner un centre pour afficher ses statistiques.");
            alert.showAndWait();
        }
    }


    /**
     * La méthode suivante nous sert pour convertir l'entier type de poubelle en texte lisible.
     */
    private String typeToString(int type) {
        switch (type) {
            case 0: return "Ordures ménagères";
            case 1: return "Plastique";
            case 2: return "Papier";
            case 3: return "Verre";
            default: return "Inconnu";
        }
    }
}
