package com.monprojet.tests;

import java.util.ArrayList;

import com.monprojet.classes.BonAchat;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.Commerce;
import com.monprojet.classes.Menage;

public class TestBonAchat {

	public static void main(String[] args) {
    	if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();//inutile si le code qui suit ne crée pas de centre de tri
    	}
		Menage menage = new Menage("Max");
		Commerce commerce = new Commerce("Leclerc", 0.01);
		
		
		BonAchat bon = new BonAchat(10,menage,commerce);
		
		System.out.println(bon.toString()); //ici c'est normal que le ménage n'ai pas le bon achat dans sa liste car il ne l'a pas ajouté grâce à ses points de fidelité (via la méthode convertirPoints)
	}

}
