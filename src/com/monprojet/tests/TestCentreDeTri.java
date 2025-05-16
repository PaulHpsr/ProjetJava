package com.monprojet.tests;

import com.monprojet.DAO.CentreDeTriDAO;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.PoubelleIntelligente;
import com.monprojet.classes.Depot;
import com.monprojet.classes.Dechet;
import java.util.ArrayList;
import java.util.HashMap;

public class TestCentreDeTri {
	public static void afficherListePoubelle(CentreDeTri c) {
		System.out.println();
		System.out.print("Liste poubelles id rattachées au centredetri:");
		for (PoubelleIntelligente poubelle : c.getListePoubelle()) {
			System.out.print("    " + poubelle.getId() + " Type :" + poubelle.getTypePoub());
		}
		System.out.println();
	}

	/*
	 * public static void afficherContenancePoubelle(CentreDeTri c,int id) {
	 * for(PoubelleIntelligente poubelle: c.getListePoubelle() ) {
	 * if(id==poubelle.getId()){
	 * System.out.println("liste de dechet de la poubelle : "+poubelle.getNbDechet(0
	 * )+" "+poubelle.getNbDechet(1) +" "+
	 * poubelle.getNbDechet(2)+" "+poubelle.getNbDechet(3)); } }
	 * 
	 * }
	 */
	public static void afficherContenancePoubelle(CentreDeTri c, int id) {
		for (PoubelleIntelligente poubelle : c.getListePoubelle()) {
			if (id == poubelle.getId()) {
				// System.out.println("la liste doit etre vide"+poubelle.getListeDepot());
				for (Depot depot : poubelle.getListeDepot()) {
					TestDepot.afficherInfo(depot);
				}
			}
		}
	}

	public static void testNewCentre(CentreDeTri c) {
		if (c.getNom() != "chacal") {
			System.out.println("Probleme lors de l'initialisation avec l'attibut nom ");
		} else {
			System.out.println("nom du Centre de tri : " + c.getNom());
		}
		if (c.getAdresse() != "paris") {
			System.out.println("probleme lors de l'initialisation avec l'attibut adresse");
		} else {
			System.out.println("adresse du Centre de tri: " + c.getAdresse());
		}
	}

	public static void main(String[] args) {
		if (CentreDeTri.centres == null) {
			CentreDeTri.centres = new ArrayList<CentreDeTri>();
		}
		ArrayList<PoubelleIntelligente> l = new ArrayList<>();
		CentreDeTri centreA = new CentreDeTri("Centre de Cergy", "paris", l);
		testNewCentre(centreA);

		centreA.ajouter("13 rue de general de Gaule", 3);
		centreA.ajouter("13 rue de general de Gaule", 1);
		centreA.ajouter("13 rue de general de Gaule", 2);
		centreA.ajouter("13 rue de general de Gaule", 3);
		centreA.ajouter("13 rue de general de Gaule", 3);
		afficherListePoubelle(centreA);
		/// *

		centreA.retirer(1);
		// on verifie bien qu'on ne peut pas retirer 2 fois la meme poubelle
		centreA.retirer(2);
		centreA.retirer(2);
		afficherListePoubelle(centreA);

		// on collecte la poubelle d'id 4 et on regarde si elle est bien vide apres
		// collect
		/*
		 * int[] nbDechet= {500,50,30,70} ; for(PoubelleIntelligente poubelle:
		 * CentreA.getListePoubelle() ) { if(4==poubelle.getId()){
		 * poubelle.setNbDechet(nbDechet); } }
		 */
		// PoubelleIntelligente poubTest = new PoubelleIntelligente("Paris",1,nbDechet);
		// CentreA.listeDechet.add(poubTest);

		afficherContenancePoubelle(centreA, 4);
		centreA.collecter(4);
		afficherContenancePoubelle(centreA, 4);

		System.out.println(centreA.getPoubelleById(4).toString());

		HashMap<Integer, ArrayList<Depot>> historiqueDepot = new HashMap<Integer, ArrayList<Depot>>();
		ArrayList<Depot> listeDepot = new ArrayList<>();
		Depot dep = new Depot();
		Dechet dech = new Dechet(0);
		dep.ajouter(dech);
		listeDepot.add(dep);
		historiqueDepot.put(1, listeDepot);
		CentreDeTri centreTest = new CentreDeTri(historiqueDepot);
		System.out.println(centreTest.toString());

		CentreDeTriDAO.read(args);

		centreTest.setNom("Centre de Marseille");
		centreTest.setAdresse("Le Vieux Port");
		CentreDeTriDAO.read(args);

		CentreDeTriDAO.delete(centreTest);
		CentreDeTriDAO.read(args);

	}
}
