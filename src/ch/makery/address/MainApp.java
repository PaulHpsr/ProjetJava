package ch.makery.address;

import ch.makery.address.view.CentreTriDialogController;

import ch.makery.address.view.SignInCentreDialogController;
import ch.makery.address.view.SignInDialogController;
import ch.makery.address.view.SignUpDialogController;
import ch.makery.address.view.UserInfoDialogController;
import ch.makery.address.view.WelcomeDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import com.monprojet.classes.Menage;
import com.monprojet.DAO.MenageDAO;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.Commerce;
import com.monprojet.classes.Dechet;
import com.monprojet.classes.TypeDechet;
import java.util.ArrayList;

public class MainApp extends Application {

	private Stage primaryStage;
	private static ArrayList<CentreDeTri> centres;
	private static ArrayList<Commerce> commerces;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Accueil");

		this.primaryStage.getIcons().add(new Image("file:resources/images/logoApp.png"));

		showWelcomeDialog();
		// showSignInDialog();
	}

	public void showWelcomeDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/WelcomeDialog.fxml"));
			AnchorPane page = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Bienvenue");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			WelcomeDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showSignInDialog() {
		try {
			// Charger le fichier FXML et créer une nouvelle scène
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SignInDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Créer le Stage pour la fenêtre modale
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Sign In");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Passer le dialogStage au contrôleur
			SignInDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage); 
			controller.setMainApp(this);
			// Afficher le dialogStage
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showSignUpDialog() {
		try {
			// Charger le fichier FXML et créer une nouvelle scène
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SignUpDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Créer le Stage pour la fenêtre modale
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Sign Up");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Passer le dialogStage au contrôleur
			SignUpDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage); 
			controller.setMainApp(this);
			// Afficher le dialogStage
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showUserInfoDialog(String nom, int id, int points, int codeAcces) {
		try {
			// Charger le fichier FXML pour le UserInfoDialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/ch/makery/address/view/UserInfoDialog.fxml"));
			AnchorPane page = loader.load();

			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Informations personnelles");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			UserInfoDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);
			controller.setUserInfo(nom, id, points); 
			Menage currentUser =  MenageDAO.getMenageById(id);
			if (currentUser == null) {
			    System.out.println("Menage non trouvé !");
			    return;
			}
			// PoubelleIntelligente poubelle1 = centres.get(0).getPoubelleById(1);
			// controller.setPoubelle(poubelle1);
			controller.setListeCentre(centres);
			controller.setListeCommerce(commerces);
			controller.setCurrentMenage(currentUser);

			// Afficher la fenêtre UserInfoDialog
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showCentreTriDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CentreTriDialog.fxml"));
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Centre de Tri");

			Scene scene = new Scene(loader.load());
			dialogStage.setScene(scene);

			CentreTriDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);
			controller.setListeCentre(centres);

			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean showSignInCentreDialog() {
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/SignInCentreDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Connexion");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        SignInCentreDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);

	        dialogStage.showAndWait();

	        return controller.isLoginSuccessful();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	public static void main(String[] args) {
		if (CentreDeTri.centres == null) {
			CentreDeTri.centres = new ArrayList<CentreDeTri>();
		}

        centres = new ArrayList<CentreDeTri>();
		commerces = new ArrayList<Commerce>();

		// Création de centres de tri
        CentreDeTri centreA = new CentreDeTri("CentreA", "Paris");
        centres.add(centreA);
        CentreDeTri centreB = new CentreDeTri("CentreB", "Paris");
        centres.add(centreB);
        CentreDeTri centreC = new CentreDeTri("CentreC", "Lyon");
        centres.add(centreC);
        CentreDeTri centreD = new CentreDeTri("CentreD", "Marseille");
        centres.add(centreD);
        CentreDeTri centreE = new CentreDeTri("CentreE", "Nice");
        centres.add(centreE);
        CentreDeTri centreF = new CentreDeTri("CentreF", "Toulouse");
        centres.add(centreF);

        // Ajout de poubelles aux centres
        // CentreA : Paris
        centreA.ajouter("3 avenue des Champs-Elysées", 0); // Ordure ménagères
        centreA.ajouter("13 rue de général de Gaulle", 1); // Plastique
        centreA.ajouter("10 rue Bonaparte", 2); // Papier
        centreA.ajouter("23 rue Paul Vaillant Couturier", 3); // Verre

        // CentreB : Paris
        centreB.ajouter("5 boulevard Montparnasse", 0); // Ordure ménagères
        centreB.ajouter("25 avenue de la République", 1); // Plastique
        centreB.ajouter("4 rue de la Liberté", 2); // Papier
        centreB.ajouter("9 rue du Faubourg Saint-Antoine", 3); // Verre

        // CentreC : Lyon
        centreC.ajouter("12 rue de la Croix-Rousse", 0); // Ordure ménagères
        centreC.ajouter("8 rue Victor Hugo", 1); // Plastique
        centreC.ajouter("10 place Bellecour", 2); // Papier
        centreC.ajouter("6 avenue Jean Jaurès", 3); // Verre

        // CentreD : Marseille
        centreD.ajouter("20 rue Saint-Ferréol", 0); // Ordure ménagères
        centreD.ajouter("15 rue du Prado", 1); // Plastique
        centreD.ajouter("3 avenue des Chartreux", 2); // Papier
        centreD.ajouter("2 rue des Alpes", 3); // Verre

        // CentreE : Nice
        centreE.ajouter("7 rue Jean Médecin", 0); // Ordure ménagères
        centreE.ajouter("21 avenue de la Gare", 1); // Plastique
        centreE.ajouter("12 rue de l'Observatoire", 2); // Papier
        centreE.ajouter("5 avenue du Mercantour", 3); // Verre

        // CentreF : Toulouse
        centreF.ajouter("10 rue Alsace Lorraine", 0); // Ordure ménagères
        centreF.ajouter("14 rue de la Trinité", 1); // Plastique
        centreF.ajouter("16 avenue des Minimes", 2); // Papier
        centreF.ajouter("18 rue de la Pomme", 3); // Verre

        // Création de ménages pour chaque centre
        Menage dupont = new Menage("Dupont");
        Menage legrand = new Menage("Legrand");
        Menage martin = new Menage("Martin");
        Menage durand = new Menage("Durand");
        Menage bernard = new Menage("Bernard");
        Menage fabre = new Menage("Fabre");

        // Déchets de chaque famille
        Dechet dechet1 = new Dechet(TypeDechet.VERRE);
        Dechet dechet2 = new Dechet(TypeDechet.METAL);
        Dechet dechet3 = new Dechet(TypeDechet.CARTON);
        Dechet dechet4 = new Dechet(TypeDechet.PLASTIQUE);

        // Remplissage des corbeilles des ménages
        dupont.remplirCorbeille(dechet1, 0);
        dupont.remplirCorbeille(dechet2, 1);
        dupont.remplirCorbeille(dechet3, 2);
        dupont.remplirCorbeille(dechet4, 3);
        dupont.remplirCorbeille(dechet1, 0);
        dupont.remplirCorbeille(dechet2, 1);
        dupont.remplirCorbeille(dechet3, 2);
        dupont.remplirCorbeille(dechet4, 3);

        legrand.remplirCorbeille(dechet2, 0);
        legrand.remplirCorbeille(dechet1, 1);
        legrand.remplirCorbeille(dechet3, 2);
        legrand.remplirCorbeille(dechet4, 3);
        legrand.remplirCorbeille(dechet2, 0);
        legrand.remplirCorbeille(dechet1, 1);
        legrand.remplirCorbeille(dechet3, 2);
        legrand.remplirCorbeille(dechet4, 3);

        martin.remplirCorbeille(dechet4, 0);
        martin.remplirCorbeille(dechet2, 3);
        martin.remplirCorbeille(dechet3, 3);
        martin.remplirCorbeille(dechet2, 3);

        durand.remplirCorbeille(dechet1, 0);
        durand.remplirCorbeille(dechet4, 1);
        durand.remplirCorbeille(dechet3, 2);
        durand.remplirCorbeille(dechet4, 3);
        durand.remplirCorbeille(dechet3, 2);
        durand.remplirCorbeille(dechet4, 3);


        bernard.remplirCorbeille(dechet1, 0);
        bernard.remplirCorbeille(dechet1, 1);
        bernard.remplirCorbeille(dechet4, 2);
        bernard.remplirCorbeille(dechet3, 3);
        bernard.remplirCorbeille(dechet4, 0);
        bernard.remplirCorbeille(dechet3, 0);


        fabre.remplirCorbeille(dechet2, 0);
        fabre.remplirCorbeille(dechet3, 1);
        fabre.remplirCorbeille(dechet4, 2);
        fabre.remplirCorbeille(dechet4, 3);
        fabre.remplirCorbeille(dechet3, 2);
        fabre.remplirCorbeille(dechet4, 2);
        fabre.remplirCorbeille(dechet4, 1);


        // Création des dépôts pour chaque famille
        dupont.creerDepot(1, centreA.getPoubelleById(1));
        dupont.creerDepot(3, centreA.getPoubelleById(2));
        dupont.creerDepot(2, centreB.getPoubelleById(5));
        dupont.creerDepot(0, centreC.getPoubelleById(10));
     
        legrand.creerDepot(2, centreB.getPoubelleById(5));
        legrand.creerDepot(3, centreB.getPoubelleById(6));
        legrand.creerDepot(0, centreA.getPoubelleById(3));
        legrand.creerDepot(1, centreC.getPoubelleById(12));
        
        martin.creerDepot(0, centreC.getPoubelleById(9));
        martin.creerDepot(3, centreC.getPoubelleById(11));
        
        durand.creerDepot(2, centreD.getPoubelleById(14));
        durand.creerDepot(3, centreD.getPoubelleById(15));
       
        bernard.creerDepot(0, centreE.getPoubelleById(18));
        bernard.creerDepot(1, centreE.getPoubelleById(17));
        
        fabre.creerDepot(2, centreF.getPoubelleById(22));
        fabre.creerDepot(3, centreF.getPoubelleById(23)); /**/

        // Affichage des informations des centres
        System.out.println("\n" + centreA.toString());
        System.out.println("\n" + centreB.toString());
        System.out.println("\n" + centreC.toString());
        System.out.println("\n" + centreD.toString());
        System.out.println("\n" + centreE.toString());
        System.out.println("\n" + centreF.toString());

        // Collecte des poubelles pour chaque centre
        /*
        centreA.collecteGenerale();
        centreB.collecteGenerale();
        centreC.collecteGenerale();
        centreD.collecteGenerale();
        centreE.collecteGenerale();
        centreF.collecteGenerale();
		*/
     


		// On ajoute des commerces
		Commerce auchan = new Commerce("Auchan", 0.1);
		commerces.add(auchan);
		Commerce leclerc = new Commerce("Leclerc", 0.2);
		commerces.add(leclerc);

		launch(args);
	}
}
