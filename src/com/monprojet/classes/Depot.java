package com.monprojet.classes;
import java.time.LocalDateTime ;
import java.util.ArrayList;


public class Depot {
	
	/* Attributs */
	
	private static int compteur = 0;
	private int id;
	private LocalDateTime heureDepot;
	private ArrayList<Dechet> listeDechet;
	private int pointsGagnes;
	
	
	
	
	/* Méthodes */
	
	
	public void ajouter(Dechet dech) {
		this.listeDechet.add(dech);
	}
	
	//Setter & getter & toString
	public static int getCompteur() {
		return compteur;
	}
	public static void setCompteur(int compteur) {
		Depot.compteur = compteur;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getHeureDepot() {
		return heureDepot;
	}
	public void setHeureDepot(LocalDateTime heureDepot) {
		this.heureDepot = heureDepot;
	}
	public ArrayList<Dechet> getListeDechet() {
		return listeDechet;
	}
	public Dechet getListeDechet(int i) {
		return listeDechet.get(i);
	}
	public void setListeDechet(ArrayList<Dechet> listeDechet) {
		this.listeDechet = listeDechet;
	}
	public int getPointsGagnes() {
		return pointsGagnes;
	}
	public void setPointsGagnes(int pointsGagnes) {
		this.pointsGagnes = pointsGagnes;
	}
	public String toString(){
		String listeDechet="";
		for (Dechet dech:getListeDechet()) {
			listeDechet=listeDechet+dech.toString()+" , ";
		}
		if (listeDechet.length()<2) {
			listeDechet="VIDE";
		}
		else {
			listeDechet=listeDechet.substring(0, listeDechet.length()-2);
		}
		return "[ Id du Depot : "+getId()+ ", heure du Depot : " +getHeureDepot()+", Points gagnés : "+ getPointsGagnes() +", liste des dechets : "+listeDechet+"]";
	}
	// Constructeurs
	
	
	public Depot() {
		this.id = ++compteur;
		this.heureDepot = LocalDateTime.now(); 		// Par défaut on initialise un dépot avec la date actuelle
		this.listeDechet=new ArrayList<>();
	}
	
	public Depot(LocalDateTime heureDepot) {
		this.id = ++compteur;
		this.heureDepot = heureDepot;
		this.listeDechet=new ArrayList<>();
	}


	
}
