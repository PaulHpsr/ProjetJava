package com.monprojet.classes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.monprojet.DAO.CommerceDAO;


public class Commerce {
	
	/* Attributs */
	private static int compteur;
	private int id;
	private String nom;
	private double rapportConversion; //Liste des rapport pour conv. les pts
	private List<ContratPartenariat> contratsPartenariat;
	
	

	//Méthodes 
	
	public void addPartenariat(LocalDate dateDebut, LocalDate dateFin, String condition, String categoriesProduits, CentreDeTri centreDeTri) 
	{

        for (ContratPartenariat contrat : this.contratsPartenariat) 
        {
            if ((contrat.getDateDebut() == dateDebut) && (contrat.getDateFin() == dateFin) && (contrat.getCondition().equals(condition)) && (contrat.getCategoriesProduits().equals(categoriesProduits)) && (contrat.getCentreDeTri().getId()==centreDeTri.getId())) 
            {
                System.out.println("Le partenariat avec l'ID " + contrat.getId() + " existe déjà.");
                return;  
            }
        }

        ContratPartenariat contrat = new ContratPartenariat(dateDebut, dateFin, condition, categoriesProduits, this, centreDeTri);
        this.contratsPartenariat.add(contrat);
        
        
        System.out.println("Partenariat avec l'ID " + contrat.getId() + " ajouté avec succès.");
    }


    public void supprimerPartenariat(ContratPartenariat contrat) 
    {
        if (this.contratsPartenariat.contains(contrat)) 
        {
            this.contratsPartenariat.remove(contrat);
            
            System.out.println("Le partenariat avec le centre de tri " + contrat.getCentreDeTri().getNom() + " a été supprimé.");
        } else 
        {
            System.out.println("Le partenariat spécifié n'existe pas.");
        }
    }


    public void renouvelerPartenariat(ContratPartenariat contrat, LocalDate nouvelleDateFin) 
    {
        if (this.contratsPartenariat.contains(contrat)) 
        {

            contrat.setDateFin(nouvelleDateFin);
            System.out.println("Le partenariat avec le centre de tri " + contrat.getCentreDeTri().getNom() + " a été renouvelé jusqu'au " + nouvelleDateFin);
        } 
        else 
        {
            System.out.println("Le partenariat spécifié n'existe pas. Impossible de le renouveler.");
        }
    }
	// gertters 
	
	public int getId()
	{
		return id;
	}
	
	public String getNom() 
	{
        return nom;
    }
	
	  public double getRapportConversion() 
	    {
	        return rapportConversion;
	    }
	  
	  
	  
	// setters
	
	
	public void setId(int id)
	{
		this.id = id;
	}

    public void setNom(String nom) 
    {
        this.nom = nom;
        CommerceDAO.update(this);
    }

    public void setRapportConversion(Float rapportConversion) 
    {
        this.rapportConversion = rapportConversion;
    }
    
    // To String
    public String toString() 
    {
        return "Commerce{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", rapportConversion=" + rapportConversion +
                '}';
    }
    
    //Constructeur
	
	public Commerce(String nom, double d)
	{
		this.id = ++compteur;
		this.nom = nom;
		this.rapportConversion = d;
		this.contratsPartenariat = new ArrayList<>();
		CommerceDAO.create(this);
	}
	public Commerce(int id,String nom, double d)
	{
		this.id = ++compteur;
		this.nom = nom;
		this.rapportConversion = d;
		this.contratsPartenariat = new ArrayList<>();
		CommerceDAO.create(this);
	}
}


	
