package com.monprojet.classes;
import java.time.LocalDate;

import com.monprojet.DAO.ContratPartenariatDAO;

public class ContratPartenariat {

	private static int compteur;
	private int id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String condition;
    private String categoriesProduits;
    private Commerce commerce;  //commerce associé
    private CentreDeTri centreDeTri; //centre de tri lié au contrat
    
   
    
    //Méthodes
    
    
    
    // getters
    public int getId() 
    {
        return id;
    }
    
    public LocalDate getDateDebut() 
    {
        return dateDebut;
    }
    
    public LocalDate getDateFin() 
    {
        return dateFin;
    }
    
    public String getCondition() 
    {
        return condition;
    }
    
    
    public String getCategoriesProduits() 
    {
        return categoriesProduits;
    }
    
    public Commerce getCommerce() 
    {
        return commerce;
    }
    
    public CentreDeTri getCentreDeTri() 
    {
        return centreDeTri;
    }
    //setters

    public void setId(int id) 
    {
        this.id = id;
    }


    public void setDateDebut(LocalDate dateDebut) 
    {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate nouvelleDateFin) {
        this.dateFin = nouvelleDateFin;
        ContratPartenariatDAO.update(this);
    }

    public void setCondition(String condition) 
    {
        this.condition = condition;
        ContratPartenariatDAO.update(this);
    }

    public void setCategoriesProduits(String categoriesProduits) 
    {
        this.categoriesProduits = categoriesProduits;
        ContratPartenariatDAO.update(this);
    }

    public void setCommerce(Commerce commerce) 
    {
        this.commerce = commerce;
    }

    public void setCentreDeTri(CentreDeTri centreDeTri) 
    {
        this.centreDeTri = centreDeTri;
    }

    public String toString() {
        return "ContratPartenariat{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", condition='" + condition + '\'' +
                ", categoriesProduits='" + categoriesProduits + '\'' +
                ", commerce=" + commerce +
                ", centreDeTri=" + centreDeTri +
                '}';
    }
    
    public ContratPartenariat(LocalDate dateDebut, LocalDate dateFin, String condition, String categoriesProduits, Commerce commerce, CentreDeTri centreDeTri) 
    {
    	this.id = ++compteur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.condition = condition;
        this.categoriesProduits = categoriesProduits;
        this.commerce = commerce;
        this.centreDeTri = centreDeTri;
        ContratPartenariatDAO.create(this);
    }
    
}
