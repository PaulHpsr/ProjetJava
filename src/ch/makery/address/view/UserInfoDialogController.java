package ch.makery.address.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import com.monprojet.DAO.BonAchatDAO;
import com.monprojet.classes.BonAchat;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.Commerce;
import com.monprojet.classes.Menage;
import com.monprojet.classes.PoubelleIntelligente;
import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserInfoDialogController {
	@FXML
	private Button button1;
	@FXML
	private Button button2;
	@FXML
	private Button button3;
	@FXML
	private Button button4;
	@FXML
	private TextField poubelleIdField;
	@FXML
	private TextField idCommerce;
	@FXML
	private TextField idNbPoints;
	@FXML
	private Button buttonConvertir;
	@FXML
    private TextField commerceIdField;
    @FXML
    private TextField nbPointsField;
	@FXML

	private void handleButton1() {
		incrementButtonValue(button1);
		currentMenage.remplirCorbeille(0); // typeDechet = 0
	}
	
	@FXML
	private void handleButton2() {
		incrementButtonValue(button2);
		currentMenage.remplirCorbeille(1); // typeDechet = 1
	}

	@FXML
	private void handleButton3() {
		incrementButtonValue(button3);
		currentMenage.remplirCorbeille(2); // typeDechet = 2
	}

	@FXML
	private void handleButton4() {
		incrementButtonValue(button4);
		currentMenage.remplirCorbeille(3); // typeDechet = 3
	}
	
	private void incrementButtonValue(Button button) {
		int currentValue = Integer.parseUnsignedInt(button.getText());
		button.setText(String.valueOf(currentValue + 1));
	}

	@FXML
	private void handleDeposerCorbeille() {
		String text = poubelleIdField.getText().trim();

		// Vérifie si le champ est vide
		if (text.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de déposer !");
			alert.setContentText("Il faut saisir l'ID d'une poubelle !");
			alert.showAndWait();
			return;
		}

		int poubelleIdFieldInt;

		try {
			// Tente de parser en entier non signé
			poubelleIdFieldInt = Integer.parseUnsignedInt(text);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Format invalide !");
			alert.setContentText("L'ID doit être un nombre entier positif !");
			alert.showAndWait();
			return;
		}

		if (currentMenage.getCorbeille().get(0).isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de déposer !");
			alert.setContentText("La sous corbeille est vide, impossible de créer un dépot sans déchet !");
			alert.showAndWait();
			return;
		}
		PoubelleIntelligente poubelle = getPoubelle(poubelleIdFieldInt);
		if (poubelle == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de déposer !");
			alert.setContentText("La poubelle n'existe pas!");
			alert.showAndWait();
			return;
		} else {
			currentMenage.creerDepot(0, poubelle);
			System.out.println(poubelle.toString());
			pointsLabel.setText(String.valueOf(currentMenage.getPointFidel()));
			// Remettre les boutons à zéro
			button1.setText("0");
			button2.setText("0");
			button3.setText("0");
			button4.setText("0");
		}

	}
	@FXML
	private void handleConvertir() {
		String text = commerceIdField.getText().trim();
        String text2 = nbPointsField.getText().trim();
		// Vérifie si le champ est vide
		if (text.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de convertir !");
			alert.setContentText("Il faut saisir l'ID d'un commerce !");
			alert.showAndWait();
			return;
		}
        if (text2.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de convertir !");
			alert.setContentText("Il faut saisir un nombre de points !");
			alert.showAndWait();
			return;
		}

		int commerceIdFieldInt;
        int nbPointsFieldInt;

		try {
			// Tente de parser en entier non signé
			commerceIdFieldInt = Integer.parseUnsignedInt(text);
			nbPointsFieldInt = Integer.parseUnsignedInt(text2);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Format invalide !");
			alert.setContentText("L'ID et le nombre de points à convertir doivent être des nombres entier positif !");
			alert.showAndWait();
			return;
		}

		
		Commerce commerce = getCommerce(commerceIdFieldInt);
		if (commerce == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de convertir !");
			alert.setContentText("Le commerce n'existe pas !");
			alert.showAndWait();
			return;
		} 
        if(currentMenage.getPointFidel()<nbPointsFieldInt) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Impossible de convertir !");
			alert.setContentText("Pas assez de points !");
			alert.showAndWait();
			return;
		}
        else {
			currentMenage.convertirPoints(nbPointsFieldInt, commerce);
			afficherBonsAchat(currentMenage);
			System.out.println(currentMenage.toString());
			pointsLabel.setText(String.valueOf(currentMenage.getPointFidel()));
		}
       
	}

	public PoubelleIntelligente getPoubelle(int id) {

		for (CentreDeTri centre : centres) {
			if (centre.getPoubelleById(id) != null) {
				return centre.getPoubelleById(id);
			}
		}
		return null;
	}


	public Commerce getCommerce(int id) {

		for (Commerce commerce : commerces) {
			if (commerce.getId() == id) {
				return commerce;
			}
		}
		return null;
	}
	@FXML
	private TextArea bonAchatTextArea;
	
	public void afficherBonsAchat(Menage currentMenage) {
	    if (currentMenage == null || currentMenage.getListeBonAchat()== null) {
	        bonAchatTextArea.setText("Aucun bon d'achat disponible.");
	        return;
	    }
	    StringBuilder texte = new StringBuilder();
	    texte.append(currentMenage.toStringListeBonAchat()).append("\n");
	    bonAchatTextArea.setText(texte.toString());  // Met à jour la TextArea avec les bons d'achat
	}



	@FXML
	private Label nameLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Label pointsLabel;

	private MainApp mainApp;

	private Stage dialogStage;

	private Menage currentMenage;


    private ArrayList<Commerce> commerces;
	private ArrayList<CentreDeTri> centres;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	// Méthode pour passer les informations à la fenêtre
	public void setUserInfo(String name, int id, int points) {
		nameLabel.setText(name); // Mettre à jour le label du nom
		idLabel.setText(String.valueOf(id)); 
		pointsLabel.setText(String.valueOf(points)); 
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setCurrentMenage(Menage currentMenage) {
		this.currentMenage = currentMenage;
		// Récupérer les bons d'achat depuis la base et les affecter
		ArrayList<BonAchat> bons = BonAchatDAO.getBonsByMenageId(currentMenage.getId());
	    currentMenage.setListeBonAchat(bons);
	    afficherBonsAchat(currentMenage);
		
	}

	public void setListeCentre(ArrayList<CentreDeTri> centres) {
		this.centres = centres;
	}
	public void setListeCommerce(ArrayList<Commerce> commerces) {
		this.commerces = commerces;
	}
	// Méthode pour fermer la fenêtre
	@FXML
	private void handleClose() {
		if (dialogStage != null) {
			dialogStage.close(); 
			mainApp.showWelcomeDialog();
		}
	}

	@FXML
	private void handleAnnuler() {
		if (currentMenage != null) {
			currentMenage.clearCorbeille(0); // vide la corbeille 
		}
		// Remettre les boutons à zéro
		button1.setText("0");
		button2.setText("0");
		button3.setText("0");
		button4.setText("0");

	}

}
