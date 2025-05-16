package com.monprojet.tests;
import java.util.ArrayList;

import com.monprojet.DAO.DechetDAO;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.Dechet;
import com.monprojet.classes.TypeDechet;

public class TestDechet {
	
	
	public static void afficherInfo(Dechet dec){
		System.out.println("Categorie de déchet : "+ dec.getCategorie());
		System.out.println("Numéro de type de déchet : "+ dec.getType());
		System.out.println("Poids du déchet : "+ dec.getPoids());
	}
	
	
	public static void main(String[] args) {
		if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();//inutile si le code qui suit ne crée pas de centre de tri
    	}
		Dechet dec1=new Dechet(0);
		Dechet dec2=new Dechet(TypeDechet.METAL);

		afficherInfo(dec1);
		afficherInfo(dec2);
		System.out.println(dec1.toString());
		
		DechetDAO.read(args);
		
		DechetDAO.delete(dec2);
		DechetDAO.read(args);
		
		
		}
	
}

