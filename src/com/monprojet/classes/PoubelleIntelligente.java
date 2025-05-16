package com.monprojet.classes;
import java.util.ArrayList;

import com.monprojet.DAO.PoubelleIntelligenteDAO;


public class PoubelleIntelligente {
	
/* Attributs */
	
	private static int compteur = 0;
	private int id;
	private String emplacement;
	private int typePoub;
	private ArrayList<Depot> listeDepot; 
	/*
	private int nbDechet0;
	private int nbDechet1;
	private int nbDechet2;
	private int nbDechet3;
	*/
	private boolean etat;  //Booléen pour savoir si la poubelle est pleine ou pas
	private int[] nbDechet;
	
	
	
	public int[] capaMax;
    

/* Méthodes */

/*
	public void identifierUtilisateur(compte CompteUtilisateur) {
		
	}
*/
	
	public boolean natureConforme(Dechet dech) {
		return (dech.getType()==typePoub);
	}

	public int attributionPoints(Depot dep) { 	//Description du système de point choisi : un déchet comforme rapporte 2 points et un déchets non comforme fait perdre 1 point
		int pointGagnes=0;
		for (Dechet dech:dep.getListeDechet()) {
			if (natureConforme(dech)) {
				pointGagnes=pointGagnes+2;
			}
			else {
				pointGagnes=pointGagnes-1;
			}
		}
		dep.setPointsGagnes(pointGagnes);
		return pointGagnes;
	}
	
	public int calcPoids() {
		int res=0;
		for (int i=0;i<4;i++){
			res=res+getNbDechet(i)*TypeDechet.fromType(i).getPoids();
		}
		return (res);
	}
	
		

	public boolean estPlein() {
		return ((nbDechet[0] >= capaMax[0]) || ((nbDechet[1] >= capaMax[1]) || ((nbDechet[2] >= capaMax[2]) || (nbDechet[3] >= capaMax[3]))));
	}
	
	public int notifierPlein() {
		if (estPlein()) {
			System.out.println("Poubelle Pleine ! Elle doit être collectée !");
			setEtat(estPlein());
			PoubelleIntelligenteDAO.update(this);
		}
		return this.getId();
	}
	
	
	public void ajouterDepot(Depot depot){
		this.listeDepot.add(depot); //On ajoute le dépot à la liste
		for (Dechet dech: depot.getListeDechet()) {		//On actualise le nombre de déchets présents dans la poubelle
			
			switch (dech.getType()) {
				case 0:
					nbDechet[0]++;
					break;
				case 1:
					nbDechet[1]++;
					break;
				case 2:
					nbDechet[2]++;
					break;
				case 3:
					nbDechet[3]++;
					break;
			}
		}
		this.notifierPlein();
	}
	
	public void viderListeDepot(){
		this.listeDepot.clear(); //On vide la liste
		for (int i=0;i<4;i++) {  //On actualise le nombre dechet en les mettant à 0
			this.nbDechet[i]=0;
			}
		this.setEtat(false);
	}
	
	//Setter & getter & toString
	
	public int getId() {
		return id;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
		PoubelleIntelligenteDAO.update(this);
	}

	public int getTypePoub() {
		return typePoub;
	}

	public void setTypePoub(int typePoub) {
		if (typePoub < 0 || typePoub > 3) {
			throw new IllegalArgumentException("Type de poubelle invalide : doit être 0, 1, 2 ou 3.");
	    }
	    this.typePoub = typePoub;
	}
	
	public int[] getNbDechet() {
		return nbDechet;
	}
	
	public int getNbDechet(int i) {
		return nbDechet[i];
	}

	public void setNbDechet(int[] nbDechet) {
		this.nbDechet = nbDechet;
	}
	
	public void setNbDechet(int indice, int valeur) {
		this.nbDechet[indice] = valeur;
	}

	public int[] getCapaMax() {
		return capaMax;
	}
	
	public int getCapaMax(int i) {
		return capaMax[i];
	}

	public void setCapaMax(int[] capaMax) {
		this.capaMax = capaMax;
	}
	
	public void setCapaMax(int indice, int valeur) {
		this.capaMax[indice] = valeur;
	}
	public ArrayList<Depot> getListeDepot() {
		return listeDepot;
	}

	public boolean getEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public String toString(){
		String listeDepot="";
		if (!getListeDepot().isEmpty()) {
			for (Depot depot:getListeDepot()) {
				listeDepot=listeDepot+depot.toString()+"\n";
			}
			return "/ Id de la poubelle : "+getId()+ ", Emplacement poubelle : " +getEmplacement()+", Type poubelle: "+getTypePoub()+", Poubelle pleine ? : "+getEtat()+", \n Liste des depots : "+listeDepot.substring(0, listeDepot.length() - 2)+"/";
		 }else {
   	 			return "/ Id de la poubelle : "+getId()+ ", Emplacement poubelle : " +getEmplacement()+", Type poubelle: "+getTypePoub()+", Poubelle pleine ? : "+getEtat()+", \n Liste des dépôts : VIDE."+"/"; // Message si la liste est vide
		}
	}

	/*
	public int getNbDechet0() {
	 
		return nbDechet0;
	}

	public void setNbDechet0(int nbDechet0) {
		this.nbDechet0 = nbDechet0;
	}

	public int getNbDechet1() {
		return nbDechet1;
	}

	public void setNbDechet1(int nbDechet1) {
		this.nbDechet1 = nbDechet1;
	}

	public int getNbDechet2() {
		return nbDechet2;
	}

	public void setNbDechet2(int nbDechet2) {
		this.nbDechet2 = nbDechet2;
	}

	public int getNbDechet3() {
		return nbDechet3;
	}

	public void setNbDechet3(int nbDechet3) {
		this.nbDechet3 = nbDechet3;
	}

	public int getCapaMax0() {
		return capaMax0;
	}

	public void setCapaMax0(int capaMax0) {
		this.capaMax0 = capaMax0;
	}

	public int getCapaMax1() {
		return capaMax1;
	}

	public void setCapaMax1(int capaMax1) {
		this.capaMax1 = capaMax1;
	}

	public int getCapaMax2() {
		return capaMax2;
	}

	public void setCapaMax2(int capaMax2) {
		this.capaMax2 = capaMax2;
	}

	public int getCapaMax3() {
		return capaMax3;
	}

	public void setCapaMax3(int capaMax3) {
		this.capaMax3 = capaMax3;
	}
*/	
	// Constructeurs
	

	public PoubelleIntelligente(String emplacement, int typePoub) {
		this.id=++compteur;
		this.setEmplacement(emplacement);
		this.setTypePoub(typePoub);
		this.nbDechet=new int[4];
		this.capaMax=new int[4];
		this.listeDepot = new ArrayList<Depot>();
		this.etat=false;
		PoubelleIntelligenteDAO.create(this);
	}

	/*
	public PoubelleIntelligente(int  id, String emplacement, int typePoub) {
		
		this.setId(id);
		this.setEmplacement(emplacement);
		this.setTypePoub(typePoub);
		this.nbDechet=new int[4];
		this.capaMax=new int[4];
	}
	*/
	
	public PoubelleIntelligente(String emplacement, int typePoub, int[] nbDechet) {
		
		if (nbDechet.length != 4) {
			System.out.println("ERREUR : Liste nbDechet doit avoir exactement 4 éléments");
		}
		this.id=++compteur;
		this.setEmplacement(emplacement);
		this.setTypePoub(typePoub);
		this.nbDechet=nbDechet;
		this.capaMax=new int[4];
		this.listeDepot = new ArrayList<Depot>();
		this.etat=estPlein();
		PoubelleIntelligenteDAO.create(this);

		/*
		this.setNbDechet0(NbDechet0);
		this.setNbDechet0(NbDechet1);
		this.setNbDechet0(NbDechet2);
		this.setNbDechet0(NbDechet3);
		*/
	}
	
	public PoubelleIntelligente(String emplacement, int typePoub, int[] nbDechet, int[] capaMax) {
		
		if (nbDechet.length != 4) {
			System.out.println("ERREUR : Liste nbDechet doit avoir exactement 4 éléments");
		}
		if (capaMax.length != 4) {
			System.out.println("ERREUR : Liste capaMax doit avoir exactement 4 éléments");
		}
		
		this.id=++compteur;
		this.setEmplacement(emplacement);
		this.setTypePoub(typePoub);
		this.nbDechet=nbDechet;
		this.capaMax=capaMax;
		this.listeDepot = new ArrayList<Depot>();
		this.etat=estPlein();
		PoubelleIntelligenteDAO.create(this);
		
		/*
		this.setNbDechet0(NbDechet0);
		this.setNbDechet1(NbDechet1);
		this.setNbDechet2(NbDechet2);
		this.setNbDechet3(NbDechet3);
		this.setCapaMax0(capaMax0);
		this.setCapaMax1(capaMax1);
		this.setCapaMax2(capaMax2);
		this.setCapaMax3(capaMax3);
		*/
	}

}
