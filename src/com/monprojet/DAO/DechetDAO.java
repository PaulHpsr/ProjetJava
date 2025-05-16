package com.monprojet.DAO;

import com.monprojet.classes.*;
import java.sql.*;


public class DechetDAO {
	
	/* Attributs */
	
	private static final String URL = ConnectionDAO.getURL();
    private static final String USER = ConnectionDAO.getUSER();
    private static final String PASSWORD = ConnectionDAO.getPASSWORD();
    
	/* Méthodes */
	
    /* CREATE */
	public static void create(Dechet dechet) {
		String sql = "INSERT INTO Dechet (typeDechet,categorie,poids) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres
            pstmt.setInt(1, dechet.getType());
            pstmt.setString(2, dechet.getCategorie().toString());
            pstmt.setInt(3, dechet.getPoids());

            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Déchet inséré avec succès !");                
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
            System.out.println("Connexion réussie à la base de données ! \nAffichage des déchets : ");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Dechet");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Id dépôt: " + rs.getInt("idDepot") + ", Type de déchet: "+rs.getInt("typeDechet") + ", Categorie: "+rs.getString("categorie") + ", Poids: "+rs.getInt("poids"));           }

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
    
	
	/* UPDATE */ //Permet d'ajouter l'id du dépot à un déchet une fois que le déchet à été placé dans un dépot
	public static void update(Dechet dechet, Depot depot) {
	    String sql = "UPDATE Dechet SET idDepot = ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Pas besoin de RETURN_GENERATED_KEYS

	        // Remplissage des paramètres
	        pstmt.setInt(1, depot.getId());   // Précise que le déchet a été fait dans un certain dépôt
	        pstmt.setInt(2, dechet.getId());  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Déchet mis à jour avec succès !");
	        } else {
	            System.out.println("Aucun déchet trouvé avec cet ID.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	 
	/* DELETE */
	public static void delete(Dechet dechet) {
		String sql = "DELETE FROM Dechet WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Remplissage des paramètres
			pstmt.setInt(1, dechet.getId()); // Condition du WHERE

			// Exécution de la requête
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Déchet supprimmé avec succès !");
			} else {
				System.out.println("Déchet NON trouvé !");
			}
		} catch (SQLException e) {
			System.err.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}