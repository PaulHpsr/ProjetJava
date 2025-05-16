package com.monprojet.tests;
import com.monprojet.classes.PoubelleIntelligente;
import com.monprojet.classes.Depot;

import java.util.ArrayList;

import com.monprojet.DAO.PoubelleIntelligenteDAO;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.Dechet;


public class TestPoubelleIntelligente {
	
	public static void afficherInfo(PoubelleIntelligente poub){
		
		System.out.println("identifiant : " + poub.getId());
		System.out.println("emplacement : " + poub.getEmplacement());
		System.out.println("type de la poubelle : " + poub.getTypePoub());
		//System.out.println("Nombre déhets de type 0, 1, 2 et 3 : " + poub.getNbDechet0()+ " " + poub.getNbDechet1()+ " " + poub.getNbDechet2()+ " " + poub.getNbDechet3());
		//System.out.println("Capacité max de type 0, 1, 2 et 3 : " + poub.getCapaMax0()+ " " + poub.getCapaMax1()+ " " + poub.getCapaMax2()+ " " + poub.getCapaMax3());
		//System.out.println("Tableau des nb de déchets : ");
		System.out.println("Liste des depots : ");
		for(Depot dep:poub.getListeDepot()) {
			TestDepot.afficherInfo(dep);
		}

	}
	
	
	
	public static void main(String[] args) {
    	if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();//inutile si le code qui suit ne crée pas de centre de tri
    	}
		PoubelleIntelligente p1=new PoubelleIntelligente("NY",0);
		System.out.println(p1.toString());
		afficherInfo(p1);
		//PoubelleIntelligente p2=new PoubelleIntelligente("PARIS",3);
		//afficherInfo(p2);
		//PoubelleIntelligente p3=new PoubelleIntelligente("LONDRES",1);
		//afficherInfo(p3);
		
		System.out.println("");
		
		int[] capaMax={10,100,100,100};
		p1.setCapaMax(capaMax);
		Depot dep = new Depot();
		for (int i=0;i<100;i++) {
			Dechet dech = new Dechet(i%4);
			dep.ajouter(dech);
		}
		
		p1.ajouterDepot(dep);
		System.out.println(p1.toString());
		afficherInfo(p1);
		System.out.println("Nombre déhets de type 0, 1, 2 et 3 : " + p1.getNbDechet(0)+ " " + p1.getNbDechet(1)+ " " + p1.getNbDechet(2)+ " " + p1.getNbDechet(3));
		
		PoubelleIntelligenteDAO.read(args);
		
		
		/*
		int[] capaMax={1,1,1,1};
		int[] nbDechet={1,0,0,0};
		p1.setCapaMax(capaMax);
		p1.setNbDechet(nbDechet);
		Depot dep = new Depot();
		p1.ajouterDepot(dep);
		*/
	}

}
