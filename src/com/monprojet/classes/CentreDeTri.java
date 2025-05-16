package com.monprojet.classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.monprojet.DAO.CentreDeTriDAO;
import com.monprojet.DAO.DepotDAO;
import com.monprojet.DAO.PoubelleIntelligenteDAO;


public class CentreDeTri {
	
	/* Attributs */
	private static int compteur;
	public static ArrayList<CentreDeTri> centres;
	private int id;
	private String nom;
	private String adresse;
	//private int nbPoubelle;
	//private String histroriqueStatistiques;
	private ArrayList<PoubelleIntelligente> listePoubelle;
	private HashMap<Integer,ArrayList<Depot>> historiqueDepot; //L'entier correspond à un id d'une poubelle et la liste de dépot correspond aux dépots faits dans cette poubelle. On a ainsi un historique de dépot par poubelle
	
	/* Méthodes */

	public void ajouter(String emplacement,int type) {
		int[] pasDeDechets=new int[4]; //les poubelles ajoutées au centre de tri sont vide par defaut
		// en fonction du type de la poubelle on specifie les capaMax
		switch (type) {
			case 0:
				int[] capa0= {5,1,1,1};
				PoubelleIntelligente poubelle0=new PoubelleIntelligente(emplacement,type,pasDeDechets,capa0);
				this.listePoubelle.add(poubelle0);
				PoubelleIntelligenteDAO.update(poubelle0, this);
				break;
			case 1:
				int[] capa1= {1,5,1,1};
				PoubelleIntelligente poubelle1=new PoubelleIntelligente(emplacement,type,pasDeDechets,capa1);
				this.listePoubelle.add(poubelle1);
				PoubelleIntelligenteDAO.update(poubelle1, this);
				break;
			case 2:
				int[] capa2= {1,1,5,1};
				PoubelleIntelligente poubelle2=new PoubelleIntelligente(emplacement,type,pasDeDechets,capa2);
				this.listePoubelle.add(poubelle2);
				PoubelleIntelligenteDAO.update(poubelle2, this);
				break;
			case 3:
				int[] capa3= {1,1,1,5};
				PoubelleIntelligente poubelle3=new PoubelleIntelligente(emplacement,type,pasDeDechets,capa3);
				this.listePoubelle.add(poubelle3);
				PoubelleIntelligenteDAO.update(poubelle3, this);
				break;
		// D'après la définition de la classe PoubelleIntelligente on se retrouvera forcément dans l'un des 4 cas ci-dessus car le type de la poubelle appartient à {0,1,2,3}
		}
		
	}
	
	//Autre méthode qui permet d'ajouter une poubelle déjà créée (par exemple si une poubelle est déplacée d'un centre de tri à un autre)
	public void ajouter(PoubelleIntelligente poubelle) {
		this.listePoubelle.add(poubelle);
		PoubelleIntelligenteDAO.update(poubelle, this);
	}
	
	
	public void retirer(int id) {
	    boolean trouve = false;
	    
	    // Utilisation d'un Iterator pour éviter ConcurrentModificationException
	    Iterator<PoubelleIntelligente> it = this.getListePoubelle().iterator();
	    
	    while (it.hasNext()) {
	        PoubelleIntelligente poubelle = it.next();
	        if (poubelle.getId() == id) {
				DepotDAO.updateNull(id);
	            it.remove(); // Suppression sécurisée
	            PoubelleIntelligenteDAO.delete(poubelle);
	            trouve = true;
	            break; // On sort dès qu'on a supprimé l'élément
	        }
	    }
	    
	    if (!trouve) {
	        System.out.println("La poubelle est déjà supprimée ou l'ID ne correspond à aucune poubelle.");
	    }
	}
	/*
	public void collecter(int id) {
		boolean trouve=false;
		for(PoubelleIntelligente poubelle: this.getListePoubelle() ) {
			if(poubelle.getId()==id) {
				poubelle.viderPoubelle();
				trouve=true;
			}
		}
		if(!(trouve)) {System.out.print("la poubelle n'est pas rattachée au centredetri");}
	}
	*/
	public void collecter(int id) {
		boolean trouve=false;
		
		for(PoubelleIntelligente poubelle: this.getListePoubelle() ) {			
			if(poubelle.getId()==id) {
				trouve=true;
				// Créer une nouvelle liste pour stocker les dépôts dans l'historique
            	ArrayList<Depot> arrayListeDepots = new ArrayList<>(poubelle.getListeDepot());
				if(historiqueDepot.containsKey(id)){
						historiqueDepot.get(id).addAll(arrayListeDepots);
				}
				
				else{
					historiqueDepot.put(id, arrayListeDepots);
					/*for (Depot dep: poubelle.getListeDepot()) {
						historiqueDepot.get(id).add(dep);
					}*/
				}
				poubelle.viderListeDepot();
				PoubelleIntelligenteDAO.update(poubelle);
				break;
			}						
		}
		if(!(trouve)) {
			System.out.print("la poubelle n'est pas rattacher au centredetri");
		}
	}
	
	public void collecteGenerale() {
		for (PoubelleIntelligente poub:getListePoubelle()) {
			if(poub.estPlein()) {
				this.collecter(poub.getId());
			}
		}
	}
	
	public void deplacer(PoubelleIntelligente poubelle, String nouvelleAdresse) {
		poubelle.setEmplacement(nouvelleAdresse);
		PoubelleIntelligenteDAO.update(poubelle);
	}
	
	
	public void calculerStatistiques() {
		//Calcul du nombre moyen de déchet par dépot
		System.out.println("Calcul de quelques données statistiques : ");
		int compteurDepot=0;
		int compteurDechet=0;
		for (ArrayList<Depot> listeDepot:historiqueDepot.values()) {
			for (Depot dep:listeDepot) {
				compteurDepot++;
				compteurDechet=compteurDechet+dep.getListeDechet().size();
			}
		}
		System.out.println("\t Il y a en moyenne " + ((float) compteurDechet/(float) compteurDepot) + " déchets par dépot");
		
		//Calcul du nombre moyen de dépots par poubelle
		System.out.println("\t Il y a en moyenne " + ((float) compteurDepot/(float) historiqueDepot.keySet().size()) + " dépôts par poubelle");
		
	}
	/*
	public String calculerStatistiquesString() {
	    StringBuilder result = new StringBuilder();
	    int compteurDepot = 0;
	    int compteurDechet = 0;

	    for (ArrayList<Depot> listeDepot : historiqueDepot.values()) {
	        for (Depot dep : listeDepot) {
	            compteurDepot++;
	            compteurDechet += dep.getListeDechet().size();
	        }
	    }

	    if (compteurDepot == 0 || historiqueDepot.keySet().size() == 0) {
	        return "Aucune donnée disponible pour les statistiques.";
	    }

	    float moyenneDechetParDepot = (float) compteurDechet / compteurDepot;
	    float moyenneDepotParPoubelle = (float) compteurDepot / historiqueDepot.keySet().size();

	    result.append("Statistiques :\n");
	    result.append(String.format("- %.2f déchets par dépôt.\n", moyenneDechetParDepot));
	    result.append(String.format("- %.2f dépôts par poubelle.\n", moyenneDepotParPoubelle));

	    return result.toString();
	}*/
	
	public String calculerStatistiquesString() {
	    StringBuilder result = new StringBuilder();
	    int compteurDepot = 0;
	    int compteurDechet = 0;
	    
	    // Variables pour suivre les poubelles avec le plus grand nombre de dépôts et de déchets
	    PoubelleIntelligente poubelleMaxDepot = null;
	    PoubelleIntelligente poubelleMaxDechet = null;
	    int maxDepot = 0;
	    int maxDechet = 0;

	    for (ArrayList<Depot> listeDepot : historiqueDepot.values()) {
	        for (Depot dep : listeDepot) {
	            compteurDepot++;
	            compteurDechet += dep.getListeDechet().size();
	            
	            // Mise à jour de la poubelle avec le plus grand nombre de dépôts
	            PoubelleIntelligente poubelle = getPoubelleByDepot(dep);
	            if (poubelle != null) {
	                int poubelleDepotCount = dep.getListeDechet().size();
	                if (poubelleDepotCount > maxDepot) {
	                    poubelleMaxDepot = poubelle;
	                    maxDepot = poubelleDepotCount;
	                }

	                int poubelleDechetCount = dep.getListeDechet().size();
	                if (poubelleDechetCount > maxDechet) {
	                    poubelleMaxDechet = poubelle;
	                    maxDechet = poubelleDechetCount;
	                }
	            }
	        }
	    }

	    if (compteurDepot == 0 || historiqueDepot.keySet().size() == 0) {
	        return "Aucune donnée disponible pour les statistiques.";
	    }

	    // Calculs des moyennes
	    float moyenneDechetParDepot = (float) compteurDechet / compteurDepot;
	    float moyenneDepotParPoubelle = (float) compteurDepot / historiqueDepot.keySet().size();

	    // Ajout des statistiques
	    result.append("Statistiques :\n");
	    result.append(String.format("- %.2f déchets par dépôt.\n", moyenneDechetParDepot));
	    result.append(String.format("- %.2f dépôts par poubelle.\n", moyenneDepotParPoubelle));

	    // Affichage du nombre total de déchets
	    result.append(String.format("- Nombre total de déchets : %d\n", compteurDechet));

	    // Affichage du nombre total de dépôts
	    result.append(String.format("- Nombre total de dépôts : %d\n", compteurDepot));

	    // Affichage du nombre total de poubelles
	    result.append(String.format("- Nombre total de poubelles collectées : %d\n", historiqueDepot.keySet().size()));

	    // Affichage de la poubelle avec le plus grand nombre de dépôts
	    if (poubelleMaxDepot != null) {
	        result.append(String.format("- Poubelle avec le plus grand nombre de dépôts : %s (%d dépôts)\n", 
	            poubelleMaxDepot.toString(), maxDepot));
	    }

	    // Affichage de la poubelle avec le plus grand nombre de déchets
	    if (poubelleMaxDechet != null) {
	        result.append(String.format("- Poubelle avec le plus grand nombre de déchets : %s (%d déchets)\n", 
	            poubelleMaxDechet.toString(), maxDechet));
	    }

	    return result.toString();
	}

	public PoubelleIntelligente getPoubelleByDepot(Depot depot) {
	    
		for (PoubelleIntelligente poubelle : listePoubelle) {
	        for (Depot d : poubelle.getListeDepot()) { // Supposons que chaque poubelle ait une liste de dépôts
	            if (d.equals(depot)) {
	                return poubelle;
	            }
	        }
	    }
	    return null;
	}

	
	public void fairePrediction() {}


	// Setter & getter

	public static ArrayList<CentreDeTri> getCentres() {
    	return centres;
	}
	public HashMap<Integer, ArrayList<Depot>> getHistoriqueDepot() {
		return historiqueDepot;
	}
	public void setHistoriqueDepot(HashMap<Integer, ArrayList<Depot>> historiqueDepot) {
		this.historiqueDepot = historiqueDepot;
	}
	public void setNom(String nom) {
		this.nom = nom;
		CentreDeTriDAO.update(this);
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
		CentreDeTriDAO.update(this);
	}
	public void setListePoubelle(ArrayList<PoubelleIntelligente> listePoubelle) {
		this.listePoubelle = listePoubelle;
	}
	public	int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public ArrayList<PoubelleIntelligente> getListePoubelle() {
		return listePoubelle;
	}
	public String toString(){
		String listePoubelle="";
		if (!getListePoubelle().isEmpty()) {
			for (PoubelleIntelligente poubelle:getListePoubelle()) {
				listePoubelle=listePoubelle+poubelle.toString()+" , \n";
			}
			return "( Id du Centre de Tri : "+getId()+ ", Nom du Centre de Tri : " +getNom()+", Adresse du centre de Tri : "+getAdresse()+",\n Liste des poubelles : \n"+listePoubelle.substring(0, listePoubelle.length() - 2)+"\n Historique des depots : \n"+toStringHistoriqueDepot()+")";
		}else{
			return "( Id du Centre de Tri : "+getId()+ ", Nom du Centre de Tri : " +getNom()+", Adresse du centre de Tri : "+getAdresse()+", Liste des poubelles : VIDE"+"\n Historique des depots : \n"+toStringHistoriqueDepot()+")";
		}
	}
	
	public String toStringHistoriqueDepot() {
		String hist = "";
		int i=0;
		for(Integer key : historiqueDepot.keySet()){
			if (i==0) {
				hist = hist +"\t id Poubelle : "+key + ", Dépots pour cette poubelle :";
				i++;
			}
			else {
				hist = hist +"\n\t id Poubelle : "+key + ", Dépots pour cette poubelle :";
			}
			for(Depot dep:historiqueDepot.get(key)) {
				hist=hist + dep.toString()+",";
			}
		}
		return hist;
	}
	
	public PoubelleIntelligente getPoubelleById(int id) {
		
		for(PoubelleIntelligente poub: this.getListePoubelle() ) {
			if(id==poub.getId()){
				return poub;
			}
		}
		System.out.println("La poubelle d'id "+id+" n'existe pas");
		return null;
		//throw new IllegalArgumentException("La poubelle d'id "+id+" n'existe pas");
	}

	// Constructeurs
	
	public CentreDeTri(String nom,String adresse) {
		this.id=++compteur;
		this.nom=nom;
		this.adresse=adresse;
		this.listePoubelle=new ArrayList<>();
		this.historiqueDepot = new HashMap<Integer,ArrayList<Depot>>();
		centres.add(this);
		CentreDeTriDAO.create(this);
	}
	
	public CentreDeTri(String nom,String adresse,ArrayList<PoubelleIntelligente> l) {
		this.id=++compteur;
		this.nom=nom;
		this.adresse=adresse;
		this.listePoubelle=l;
		this.historiqueDepot = new HashMap<Integer,ArrayList<Depot>>();
		centres.add(this);
		CentreDeTriDAO.create(this);
	}
	public CentreDeTri(HashMap<Integer, ArrayList<Depot>> historiqueDepot) {
		this.id=++compteur;
		this.nom="Test";
		this.adresse="Test";
		this.listePoubelle=new ArrayList<>();
		this.historiqueDepot = historiqueDepot;
		centres.add(this);
		CentreDeTriDAO.create(this);
	}


} 
	

