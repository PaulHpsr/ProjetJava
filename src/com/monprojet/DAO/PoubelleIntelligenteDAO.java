package com.monprojet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.monprojet.classes.CentreDeTri;
import com.monprojet.classes.PoubelleIntelligente;

public class PoubelleIntelligenteDAO {
	/* Attributs */
	
	private static final String URL = ConnectionDAO.getURL();
    private static final String USER = ConnectionDAO.getUSER();
    private static final String PASSWORD = ConnectionDAO.getPASSWORD();
    
	/* Méthodes */
    
    /* CREATE */
	public static void create(PoubelleIntelligente poubelle) {
		String sql = "INSERT INTO PoubelleIntelligente (id,emplacement,typePoub,etat) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres
            pstmt.setInt(1, poubelle.getId());
            pstmt.setString(2, poubelle.getEmplacement());
            pstmt.setInt(3, poubelle.getTypePoub());
            pstmt.setBoolean(4, poubelle.getEtat());

            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Poubelle insérée avec succès !");                
            }
        } 
        catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	/* READ */
    public static void read(String[] args) {
        
        try {

            // Établir la connexion
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données ! \nAffichage des poubelles : ");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PoubelleIntelligente");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Emplacement: " + rs.getString("emplacement") + ", Type poubelle: "+rs.getInt("typePoub")+", Etat: "+rs.getBoolean("etat")+", Id Centre de Tri: "+rs.getInt("idCentreDeTri"));           }

            // Fermer les ressources
            rs.close();
            stmt.close();
            conn.close();
        } 
         catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	/* UPDATE */ //Utile pour rattacher une poubelle à un centre de tri
	public static void update(PoubelleIntelligente poubelle, CentreDeTri centre) {
	    String sql = "UPDATE PoubelleIntelligente SET idCentreDeTri = ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setInt(1, centre.getId());   //On associe la poubelle à son centre de tri
	        pstmt.setInt(2, poubelle.getId());  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Poubelle mise à jour avec succès !");
	        } else {
	            System.out.println("Aucune poubelle trouvée avec cet ID.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	//Pour actualiser l'emplacement et l'état (plein ou pas) de la poubelle
	public static void update(PoubelleIntelligente poubelle) {
	    String sql = "UPDATE PoubelleIntelligente SET emplacement= ?, etat= ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) { 

	        // Remplissage des paramètres
	        pstmt.setString(1, poubelle.getEmplacement());  
	        pstmt.setBoolean(2, poubelle.getEtat()); 
	        pstmt.setInt(3, poubelle.getId());  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Poubelle mise à jour avec succès !");
	        } else {
	            System.out.println("Aucune poubelle trouvée avec cet ID.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	/* DELETE */
	public static void delete(PoubelleIntelligente poubelle) {
		String sql = "DELETE FROM PoubelleIntelligente WHERE id = ? ";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres
            pstmt.setInt(1, poubelle.getId());
    
            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Poubelle supprimée avec succès !");                
            }
        } 
        catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	
	
	
}
