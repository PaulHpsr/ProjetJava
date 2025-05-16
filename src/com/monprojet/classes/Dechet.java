package com.monprojet.classes;

import com.monprojet.DAO.DechetDAO;
public class Dechet {
	
	/* Attributs */
	private static int compteur;
	private int id;
	private TypeDechet categorie;
	
	
	/* Méthodes */
	
	//Getter & Setter & toString
	
	public int getId() {
		return id;
	}
	
	public TypeDechet getCategorie() {
		return categorie;
	}

	public void setCategorie(TypeDechet categorie) {
		this.categorie = categorie;
	}
	
	public int getType() {
		return this.categorie.getType();
	}
	public int getPoids() {
		return this.categorie.getPoids();
	}
	public String toString(){
		return "{"+getCategorie()+ ", Type : " +categorie.getType()+", Poids : "+getPoids()+"}";
	}
	
	//Constructeurs
	
	public Dechet(TypeDechet categorie) {
		this.id=++compteur;
		this.categorie = categorie;
		//InsertionDechet.insererDechet(this.getType(), this.getCategorie(), this.getPoids());
		DechetDAO.create(this);

	}

	public Dechet(int type) {
		this.id=++compteur;
		this.categorie = TypeDechet.fromType(type);
		//InsertionDechet.insererDechet(this.getType(), this.getCategorie(), this.getPoids());
		DechetDAO.create(this);
	}

	
}
