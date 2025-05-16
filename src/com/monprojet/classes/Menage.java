package com.monprojet.classes;
import java.util.ArrayList;

import com.monprojet.DAO.DechetDAO;
import com.monprojet.DAO.DepotDAO;
import com.monprojet.DAO.MenageDAO;

public class Menage {
	
	/* Attributs */
	
	private static int compteur;
	private int id;
	private String nom;
	private int codeAcces;
	private int pointFidel;
	private ArrayList<BonAchat> listeBonAchat;
	//private ArrayList<Dechet>[] corbeille;	      //Tableau de 4 élément qui sont des sous-corbeilles créées par le menage : Pour tout i∈{0,1,2,3} corbeille[i] est la liste de déchets que le menage prévoit de jeter dans une poubelle de type i.
	private ArrayList<ArrayList<Dechet>> corbeille;	
	private ArrayList<Depot> historiqueDepot;



	
	/* Méthodes */
	
	
	public void creerDepot(int i,PoubelleIntelligente poub){ // l'indice i permet de choisir quelle sous liste on veut mettre dans un nouveau depot
		if (corbeille.get(i).size()==0) {
			throw new IllegalArgumentException("La sous corbeille est vide, impossible de créer un dépot sans déchet !");
		}
		Depot dep= new Depot();
		DepotDAO.create(poub.getId(), this.getId(), dep);
		for (Dechet dech : this.corbeille.get(i)){
			dep.ajouter(dech);
			DechetDAO.update(dech,dep);
		}
		poub.ajouterDepot(dep);
		this.pointFidel=this.pointFidel+poub.attributionPoints(dep);
		DepotDAO.update(dep);
		MenageDAO.update(this);
		this.historiqueDepot.add(dep);
		this.clearCorbeille(i);
	}
	public void remplirCorbeille(Dechet dech,int i){ // l'indice i permet de choisir a quelle sous liste on veut ajouter un dechet
		this.corbeille.get(i).add(dech);
	}

	public void remplirCorbeille(int typeDechet){ //Cette version de remplir corbeille sert pour l'app : on choisit un type de dechet ce déchet est créé et ajouté dans la corbeille de l'utilisateur (ici sous-corbeille 1 mais ça n'a aucune importance on aurait pu tout faire avec la 2. Ainsi les autres sous corbeilles sont inutiles avec ce type d'utilisation) 
		Dechet dech = new Dechet(typeDechet);
		this.corbeille.get(0).add(dech);
	}
	
	public void clearCorbeille(int i) {     //indice de la sous liste qu'on veut vider
		this.corbeille.get(i).clear();
	}
	
	public void convertirPoints(int pointsConvertis,Commerce commerce) { //On choisit le nombre de point que l'on veut convertir et le magasin dans lequel on veut une remise
		if (pointsConvertis > pointFidel) {
			throw new IllegalArgumentException ("Pas assez de point de fidélité !");
		}
		else {
			setPointFidel(getPointFidel()-pointsConvertis);
			BonAchat bon = new BonAchat((float) (pointsConvertis*commerce.getRapportConversion()),this,commerce);
			listeBonAchat.add(bon);
		}
	}
	
		
	// Setter & Getter & toString
	
	
	public int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getCodeAcces() {
		return codeAcces;
	}
	public void setCodeAcces(int codeAcces) {
		this.codeAcces = codeAcces;
	}
	public int getPointFidel() {
		return pointFidel;
	}
	public void setPointFidel(int pointFidel) {
		this.pointFidel = pointFidel;
		MenageDAO.update(this);
	}
	public ArrayList<BonAchat> getListeBonAchat() {
		return listeBonAchat;
	}
	public void setListeBonAchat(ArrayList<BonAchat> listeBonAchat) {
		this.listeBonAchat = listeBonAchat;
	}
	public ArrayList<ArrayList<Dechet>>  getCorbeille() {
		return corbeille;
	}
	public void setCorbeille(ArrayList<ArrayList<Dechet>>  corbeille) {
		this.corbeille = corbeille;
	}
	
	
	
	/*
	public void setCorbeille(int i,ArrayList<Dechet> listeDechet) {	
		
    
		Iterator<ArrayList<Dechet>> it = this.getCorbeille().iterator();
    
		while (it.hasNext()) {
			ArrayList<Dechet> currentListeDechet = it.next();
			if (i == compt) {
				it.set(listeDechet); //jsp si ça marche
				break; // On sort dès qu'on a supprimé l'élément
			}
		}	
	}
	*/
	
	public ArrayList<Depot> getHistoriqueDepot() {
		return historiqueDepot;
	}
	public void setHistoriqueDepot(ArrayList<Depot> historiqueDepot) {
		this.historiqueDepot = historiqueDepot;
	}
	
	
	public String toString() {
		String listeBonAchat="";
		String corbeille="";
		String historiqueDepot="";
		
		for (BonAchat b:getListeBonAchat()) {
			listeBonAchat=listeBonAchat + b.getValeur()+"€ chez "+b.getCommerce().getNom() + ", ";
		}
		
		int i=0;
		for (ArrayList<Dechet> listeDechet : getCorbeille()) {
			corbeille=corbeille+"\n";
			corbeille=corbeille+"  Sous Corbeille n° "+ i++ + " : ";
			for (Dechet dech:listeDechet) {
				corbeille=corbeille+dech.toString()+", ";
			}
		}
		
		for (Depot dep:getHistoriqueDepot()) {
			historiqueDepot=historiqueDepot+dep.toString();
		}
				
		return "Menage : (Id : " + id + ", Nom : " + nom + ", Code d'acces : " + codeAcces + ", Point de Fidelité : " + pointFidel
				+ "\n Liste des bons d'achats : " + listeBonAchat + "\n Corbeille : " + corbeille + "\n Historique des dépots : "
				+ historiqueDepot + ")";
	}

	public String toStringListeBonAchat() {
		String listeBonAchat="";
		String corbeille="";
		String historiqueDepot="";
		
		for (BonAchat b:getListeBonAchat()) {
			listeBonAchat=listeBonAchat + b.getValeur()+"€ chez "+b.getCommerce().getNom() + ", ";
		}
		
		int i=0;
		for (ArrayList<Dechet> listeDechet : getCorbeille()) {
			corbeille=corbeille+"\n";
			corbeille=corbeille+"  Sous Corbeille n° "+ i++ + " : ";
			for (Dechet dech:listeDechet) {
				corbeille=corbeille+dech.toString()+", ";
			}
		}
		
		for (Depot dep:getHistoriqueDepot()) {
			historiqueDepot=historiqueDepot+dep.toString();
		}
				
		return  "\n Liste des bons d'achats : " + listeBonAchat;
	}
	
	//Constructeur
	
	public Menage(String nom) {
		this.id=++compteur;
		this.nom=nom;
		this.codeAcces=compteur*71;
		this.listeBonAchat=new ArrayList<>();
		this.corbeille = new ArrayList<>(4);
		for (int i = 0; i < 4; i++) {
			this.corbeille.add(new ArrayList<>());
		}
		 /*
		
		this.corbeille=(ArrayList<Dechet>[]) new ArrayList[4];
		for (int i = 0; i<4; i++) {
			corbeille[i] = new ArrayList<Dechet>();
		}
		 */
		this.historiqueDepot=new ArrayList<>();
		MenageDAO.create(this);
		
	}

	public Menage(String nom, int codeAcces) {
		this.id=++compteur;
		this.nom=nom;
		this.codeAcces=codeAcces;
		this.listeBonAchat=new ArrayList<>();
		this.corbeille = new ArrayList<>(4);
		for (int i = 0; i < 4; i++) {
			this.corbeille.add(new ArrayList<>());
		}
		 /*
		
		this.corbeille=(ArrayList<Dechet>[]) new ArrayList[4];
		for (int i = 0; i<4; i++) {
			corbeille[i] = new ArrayList<Dechet>();
		}
		 */
		this.historiqueDepot=new ArrayList<>();
		MenageDAO.create(this);
		
	}

//Le constructeur de ce ménage est utile uniquement dans l'app quand on crée le ménage à partir d'un ménage enregistré dans la base de donnée
public Menage(int id,String nom, int codeAcces, int pointFidel) {
		this.id=id;
		this.nom=nom;
		this.codeAcces=codeAcces;
		this.pointFidel=pointFidel;
		this.listeBonAchat=new ArrayList<>();
		this.corbeille = new ArrayList<>(4);
		for (int i = 0; i < 4; i++) {
			this.corbeille.add(new ArrayList<>());
		}
		 /*
		
		this.corbeille=(ArrayList<Dechet>[]) new ArrayList[4];
		for (int i = 0; i<4; i++) {
			corbeille[i] = new ArrayList<Dechet>();
		}
		 */
		this.historiqueDepot=new ArrayList<>();
		//On ajoute pas le ménage à la base de donnée ici car quand on utilise ce constructeur le ménage est déjà dans la base de donnée
	}



}
