package com.monprojet.lancementPrincipal;

import java.util.ArrayList;

import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.Dechet;
import com.monprojet.classes.Menage;
import com.monprojet.classes.PoubelleIntelligente;
import com.monprojet.classes.TypeDechet;

public class Main {
	public static void main(String[] args) {
    	if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();
    	}
		// On crée un centre de tri
		CentreDeTri centreA = new CentreDeTri("CentreA", "Paris");

		// On lui ajoute des poubelles
		centreA.ajouter("3 avenue des Champs-Elysées", 0);
		centreA.ajouter("13 rue de general de Gaule", 1);
		centreA.ajouter("10 rue Bonaparte", 2);
		centreA.ajouter("23 rue Paul Vaillant Couturier", 3);

		// On enregistre ces poubelles pour les réutiliser plus tard
		PoubelleIntelligente poub1 = centreA.getPoubelleById(1);
		PoubelleIntelligente poub2 = centreA.getPoubelleById(2);
		PoubelleIntelligente poub3 = centreA.getPoubelleById(3);
		PoubelleIntelligente poub4 = centreA.getPoubelleById(4);

		// On affiche les infos du centre de tri pour vérifier que les poubelles ont
		// bien été ajoutées
		System.out.println(centreA.toString() + "\n \n");

		// On crée des ménages
		Menage dupont = new Menage("Dupont");
		Menage legrand = new Menage("Legrand");

		// Les déchets des Dupont
		Dechet dechet1 = new Dechet(TypeDechet.VERRE);
		Dechet dechet2 = new Dechet(TypeDechet.METAL);
		Dechet dechet3 = new Dechet(TypeDechet.METAL);
		Dechet dechet4 = new Dechet(TypeDechet.PLASTIQUE);
		Dechet dechet5 = new Dechet(TypeDechet.VERRE);

		// Les déchets des Legrand
		Dechet dechet6 = new Dechet(TypeDechet.METAL);
		Dechet dechet7 = new Dechet(TypeDechet.VERRE);
		Dechet dechet8 = new Dechet(TypeDechet.CARTON);
		Dechet dechet9 = new Dechet(TypeDechet.PLASTIQUE);
		Dechet dechet10 = new Dechet(TypeDechet.METAL);

		// Les ménages déposent des déchets dans leur propre corbeille
		dupont.remplirCorbeille(dechet6, 2);// le deuxième paramètre permet de choisir à quelle sous liste on veut
											// ajouter un dechet
		dupont.remplirCorbeille(dechet7, 0);
		dupont.remplirCorbeille(dechet8, 3);
		dupont.remplirCorbeille(dechet9, 3);
		dupont.remplirCorbeille(dechet10, 3);

		legrand.remplirCorbeille(dechet1, 1);
		legrand.remplirCorbeille(dechet2, 1);
		legrand.remplirCorbeille(dechet3, 3);
		legrand.remplirCorbeille(dechet4, 3);
		legrand.remplirCorbeille(dechet5, 2);

		// On affiche les infos des ménages pour vérifier que les corbeilles ont bien
		// été remplies
		System.out.println(dupont.toString() + "\n");
		System.out.println(legrand.toString() + "\n \n");

		// Chaque ménage fait plusieurs dépôts
		dupont.creerDepot(0, poub4);// le premier paramètre permet de choisir quelle sous liste on veut mettre dans
									// un nouveau depot
		dupont.creerDepot(3, poub4);

		legrand.creerDepot(2, poub1);
		legrand.creerDepot(3, poub2);
		legrand.creerDepot(1, poub3);

		// On affiche les informations du centre de tri
		System.out.println("\n" + centreA.toString() + "\n \n");

		System.out.println(dupont.toString() + "\n");
		System.out.println(legrand.toString() + "\n");

		// On réalise une collecte d'une poubelle dans notre centre de tri
		centreA.collecter(1);
		System.out.println("\n" + centreA.toString() + "\n \n");

		// On réalise une collecte des poubelles pleines dans notre centre de tri
		// centreA.collecteGenerale();
		System.out.println("\n" + centreA.toString() + "\n \n");

		// On calcule certaines statistiques sur le centre de tri
		centreA.calculerStatistiques();

		// On recalcule ces statistiques après avoir collecter les poubelles pleines
		centreA.collecteGenerale();
		System.out.println("\n" + centreA.toString() + "\n \n");
		centreA.calculerStatistiques();
		
				
	}

	/*
	 * //ArrayList<PoubelleIntelligente> l = new ArrayList<>(); CentreDeTri
	 * CentreA=new CentreDeTri("CentreA","Paris");
	 * //TestCentreDeTri.testNewCentre(CentreA);
	 * CentreA.ajouter("13 rue de general de Gaule",3);
	 * TestCentreDeTri.afficherListePoubelle(CentreA); for(PoubelleIntelligente
	 * poubelle: CentreA.getListePoubelle() ) { if(1==poubelle.getId()){ /* Depot
	 * depot=new Depot(); Depot depot1=new Depot(); Depot depot2=new Depot(); Depot
	 * depot3=new Depot(); Depot depot4=new Depot(); Dechet dechet6=new
	 * Dechet(TypeDechet.VERRE); Dechet dechet1=new Dechet(TypeDechet.METAL); Dechet
	 * dechet2=new Dechet(TypeDechet.METAL); Dechet dechet3=new
	 * Dechet(TypeDechet.METAL); Dechet dechet4=new Dechet(TypeDechet.METAL); Dechet
	 * dechet5=new Dechet(TypeDechet.METAL);
	 * 
	 * 
	 * 
	 * depot.ajouter(dechet1); depot1.ajouter(dechet2); depot2.ajouter(dechet3);
	 * depot3.ajouter(dechet4); depot4.ajouter(dechet5); depot.ajouter(dechet6);
	 * System.out.println("################"); poubelle.ajouterDepot(depot);
	 * System.out.println("################"); poubelle.ajouterDepot(depot1);
	 * System.out.println("################"); poubelle.ajouterDepot(depot2);
	 * System.out.println("################"); poubelle.ajouterDepot(depot3);
	 * System.out.println("################"); poubelle.ajouterDepot(depot4);
	 * System.out.println(poubelle.toString());
	 * 
	 * }
	 * 
	 * } System.out.println(CentreA.toString());
	 * //TestCentreDeTri.afficherContenancePoubelle(CentreA,1);
	 * 
	 * //CentreA.collecter(1);
	 * //TestCentreDeTri.afficherContenancePoubelle(CentreA,4); }
	 */
}