package ch.makery.address.view;

import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.HashMap;

import com.monprojet.classes.Depot;

public class HistoriqueDepotController {
    @FXML
    private TableView<String> historiqueTable;
    @FXML
    private TableColumn<String, String> depotColumn;

    private HashMap<Integer, ArrayList<Depot>> historiqueDepot;

    // Méthode pour initialiser les données et la TableView
    @FXML
    private void initialize() {
        depotColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()));
    }

    // Méthode pour passer l'historique à la fenêtre d'historique
    public void setHistoriqueDepot(HashMap<Integer, ArrayList<Depot>> historiqueDepot) {
        this.historiqueDepot = historiqueDepot;
        afficherHistorique();
    }

    // Afficher l'historique des dépôts pour le centre sélectionné
    private void afficherHistorique() {
        ArrayList<String> depotStrings = new ArrayList<>();

        if (historiqueDepot != null) {
            // Parcours de chaque entrée du HashMap et ajout de la représentation sous forme de chaîne de chaque dépôt
            for (ArrayList<Depot> depots : historiqueDepot.values()) {
                for (Depot depot : depots) {
                    depotStrings.add(depot.toString());  // Utiliser toString() pour chaque dépôt
                }
            }
        }
        // Mettre à jour la TableView avec les informations des dépôts
        historiqueTable.getItems().setAll(depotStrings);
    }
}
