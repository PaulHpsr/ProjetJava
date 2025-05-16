package com.monprojet.tests;

import java.util.ArrayList;

import com.monprojet.DAO.CommerceDAO;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.Commerce;

public class TestCommerce {

	public static void afficherInfo(Commerce commerce) {
		System.out.println("Id : " + commerce.getId());
		System.out.println("Nom : " + commerce.getNom());
		System.out.println("Liste Rapport : " + commerce.getRapportConversion());
	}

	public static void main(String[] args) {
	        if (CentreDeTri.centres == null) {
			CentreDeTri.centres = new ArrayList<CentreDeTri>();//inutile si le code qui suit ne crée pas de centre de tri
		}
		// créa commerce

		Float rapportConversion = (float) 0.1;
		Commerce commerce = new Commerce("Auchan", rapportConversion);

		afficherInfo(commerce);
		System.out.println(commerce.toString());

		CommerceDAO.read(args);

		Float rapportConversion2 = (float) 0.05;
		Commerce commerce2 = new Commerce("Leclerc", rapportConversion2);
		CommerceDAO.read(args);

		commerce.setNom("Carrefour");
		CommerceDAO.read(args);

		CommerceDAO.delete(commerce2);
		CommerceDAO.read(args);

	}
}
