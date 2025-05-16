package com.monprojet.tests;
import com.monprojet.DAO.MenageDAO;
import com.monprojet.classes.*;
import java.util.ArrayList;

public class TestMenage {
	public static void afficherCorbeille(Menage menage){
		int num=0;
		System.out.println("Contenu de la corbeille : ");
		for (ArrayList<Dechet> listeDechet : menage.getCorbeille()){
			System.out.println("Déchets dans la sous corbeille numéro " + num + " : ");
			for(Dechet dech : listeDechet){
				TestDechet.afficherInfo(dech);
			}
			num++;
		}
	}	
	
	
	public static void main(String[] args) {
		if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();//inutile si le code qui suit ne crée pas de centre de tri
    	}
		//Creation de la poubelleIntelligente de type 0
		int typePoubelle=0;
		int[] capa= {10,10,10,10};
		int[] nbDechet= {0,0,0,0};
		
		PoubelleIntelligente poub= new PoubelleIntelligente("ParisTest",typePoubelle,nbDechet,capa);
		
		//Creation d'un menage
		Menage menage=new Menage("Dupont");
		
		//On crée des déchets
		Dechet dechet1=new Dechet(TypeDechet.VERRE);
		Dechet dechet2=new Dechet(TypeDechet.METAL);
		Dechet dechet3=new Dechet(TypeDechet.METAL);
		
		// On remplit la corbeille du ménage
		menage.remplirCorbeille(dechet1,1);
		menage.remplirCorbeille(dechet2,1);
		menage.remplirCorbeille(dechet3,3);
		
		
		System.out.println("On affiche le ménage avant qu'il fasse un dépot : \n");

		System.out.println(menage.toString());
		
		//afficherCorbeille(menage);
		
		System.out.println("\n On crée un dépot dans cette poubelle : \n" + poub.toString());
		menage.creerDepot(1,poub);
		
		//afficherCorbeille(menage);
		
		/*
		menage.remplirCorbeille(dechet2,2);
		menage.remplirCorbeille(dechet3,3);
		afficherCorbeille(menage);
		menage.creerDepot(2);
		menage.creerDepot(1);
		afficherCorbeille(menage);
		 */
		System.out.println("\n On affiche le ménage qui vient de faire son dépot \n");

		System.out.println(menage.toString());
		
		MenageDAO.read(args);
		
		Commerce commerce=new Commerce("Auchan",0.01);
		menage.setPointFidel(100);
		menage.convertirPoints(100, commerce);
		System.out.println(menage.toString());
		}
	
}
