package com.monprojet.tests;

import com.monprojet.classes.Commerce;
import com.monprojet.classes.ContratPartenariat;
import com.monprojet.DAO.ContratPartenariatDAO;
import com.monprojet.classes.CentreDeTri;
import java.time.LocalDate;
import java.util.ArrayList;  



public class TestContratPartenariat {
    public static void main(String[] args) {
        if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();
    	}
        // Création du centre de tri
        CentreDeTri centreDeTri = new CentreDeTri("Centre de Tri A", null);

        
        
        // Créa commerce fictif
        Commerce commerce = new Commerce("SuperMarché", 0.0);

        // Création des dates
        LocalDate dateDebut = LocalDate.of(2023, 4, 1); 
        LocalDate dateFin = LocalDate.of(2025, 4, 1);    

        // Création du contrat de partenariat
        ContratPartenariat contrat = new ContratPartenariat(dateDebut, dateFin, "Partenariat pour la gestion des déchets", "Alimentaire, Hygiène", commerce, centreDeTri);
        
        // Affichage du contrat avec toString
        System.out.println("Contrat créé : " + contrat.toString());
        
        // Affichage des getters
        System.out.println("ID du contrat : " + contrat.getId());
        System.out.println("Date de début : " + contrat.getDateDebut());
        System.out.println("Date de fin : " + contrat.getDateFin());
        System.out.println("Condition : " + contrat.getCondition());
        System.out.println("Catégories de produits : " + contrat.getCategoriesProduits());
        System.out.println("Commerce associé : " + contrat.getCommerce().getNom());  // Utilisation du getter pour obtenir le nom du commerce
        System.out.println("Centre de tri associé : " + contrat.getCentreDeTri().getNom());  // Utilisation du getter pour obtenir le nom du centre de tri
        
        ContratPartenariatDAO.read(args); //test affichage de la BDD
        
        // Test des setters
        contrat.setDateFin(LocalDate.of(2026, 4, 1));  
        
        
        contrat.setCondition("Partenariat étendu pour la gestion des déchets");
        contrat.setCategoriesProduits("Alimentaire, Hygiène, Electronique");
        
        // Affiche les modifs
        System.out.println("Contrat modifié : " + contrat.toString());

        // Vérif les nouvelles valeurs
        System.out.println("Nouvelle date de fin : " + contrat.getDateFin());
        System.out.println("Nouvelle condition : " + contrat.getCondition());
        System.out.println("Nouvelles catégories de produits : " + contrat.getCategoriesProduits());
        
        
        ContratPartenariatDAO.read(args); //affichage après avoir fait des modifications (ContratPartenariatDAO.update est appelé dans les setters donc cet affichage nous permet de vérifier que cette méthode fonctionne bien)
        
        ContratPartenariatDAO.delete(contrat); //Supression du contrat de la base de donnée
        ContratPartenariatDAO.read(args); //Normal si c'est vide ! (on vient de supprimer le seul contrat de la table)
        
    }
}