package com.monprojet.tests;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.monprojet.classes.Depot;
import com.monprojet.DAO.DepotDAO;
import com.monprojet.classes.CentreDeTri;
//import com.monprojet.classes.TypeDechet;
import com.monprojet.classes.Dechet;
//import java.util.ArrayList;
//import com.monprojet.tests.TestDechet;

public class TestDepot {
	
	public static void afficherInfo(Depot dep){
		System.out.println("id du dépot : "+ dep.getId());
		System.out.println("heure de dépot : "+ dep.getHeureDepot());
		System.out.println("Liste de déchets qui composent le dépot : ");
		for (Dechet dech:dep.getListeDechet()) {
			TestDechet.afficherInfo(dech);
		}
	}
	
	
	public static void main(String[] args) {
		if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();//inutile si le code qui suit ne crée pas de centre de tri
    	}
		Depot dep1=new Depot();
		LocalDateTime d = LocalDateTime.of(1990, 1, 1, 8, 50);
		Depot dep2=new Depot(d);

		
		Dechet dech=new Dechet(1);
		Dechet dech2=new Dechet(3);
		dep1.ajouter(dech);
		dep1.ajouter(dech2);
		
		afficherInfo(dep1);
		
		System.out.println(" ");
		afficherInfo(dep2);
		System.out.println(dep1.toString());
		
		DepotDAO.read(args); //Normal que ce soit vide car un dépôt n'est ajouté à la base que via la méthode creerDepot de la classe ménage
		
	}
}
