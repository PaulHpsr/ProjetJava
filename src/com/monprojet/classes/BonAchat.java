package com.monprojet.classes;

import java.time.LocalDate;

import com.monprojet.DAO.BonAchatDAO;

public class BonAchat {
	
	/* Attributs */
	
	private static int compteur;
	private int id;
	private float valeur;
	private Menage menage;
	private Commerce commerce;
	private LocalDate dateCreation; //On suppose que la date de création du bon d'achat indique la date limite d'utilisation (par exemple date limite = date de création + 1 an)

	
	/* Méthodes */
	
	//Setters & getters
	public int getId() {
		return id;
	}
	
	public float getValeur() {
		return valeur;
	}


	public void setValeur(int valeur) {
		this.valeur = valeur;
	}


	public Menage getMenage() {
		return menage;
	}


	public void setMenage(Menage menage) {
		this.menage = menage;
	}


	public Commerce getCommerce() {
		return commerce;
	}


	public void setCommerce(Commerce commerce) {
		this.commerce = commerce;
	}
	
	public LocalDate getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDate date) {
	    this.dateCreation = date;
	}

	//On ne met pas de setter pour la date de création car on suppose ici qu'on ne peut pas "remettre à neuf" un bon d'achat déjà créé.

	
	//ToString
	@Override
	public String toString() {
		//return "Bon ddAchat [id=" + id + ", valeur=" + valeur + ", menage=" + menage + ", commerce=" + commerce ", dateCreation=" + dateCreation + "]";
		return "commerce="+commerce;
	}


	//Constructeur
	public BonAchat(float valeur, Menage menage, Commerce commerce) {
		this.id=++compteur;
		this.valeur=valeur;
		this.commerce=commerce;
		this.menage=menage;
		this.dateCreation=LocalDate.now();
		BonAchatDAO.create(this);
	}

	public BonAchat(int id,float valeur, Menage menage, Commerce commerce) { //Utilisé seulement lorsque le bon achat est présent dans la base de donnée mais pas en tant qu'objet java (donc l'id reste bien unique)
		this.id=id;
		this.valeur=valeur;
		this.commerce=commerce;
		this.menage=menage;
		this.dateCreation=LocalDate.now();
	}



}
