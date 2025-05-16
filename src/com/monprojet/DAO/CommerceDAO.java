package com.monprojet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.monprojet.classes.Commerce;

public class CommerceDAO {
	
	/* Attributs */
	
	private static final String URL = ConnectionDAO.getURL();
    private static final String USER = ConnectionDAO.getUSER();
    private static final String PASSWORD = ConnectionDAO.getPASSWORD();
    
	/* Méthodes */
    
    /* CREATE */
	public static void create(Commerce commerce) {
		String sql = "INSERT INTO Commerce (id,nom) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres
            pstmt.setInt(1, commerce.getId());
            pstmt.setString(2, commerce.getNom());

            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Commerce inséré avec succès !");                
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
            System.out.println("Connexion réussie à la base de données ! \nAffichage des commerces : ");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Commerce");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom"));           }

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
	
    /* UPDATE */
	public static void update(Commerce commerce) {
	    String sql = "UPDATE Commerce SET nom = ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setString(1, commerce.getNom());  
	        pstmt.setInt(2, commerce.getId()); //Condition du WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Commecre mis à jour avec succès !");
	        } else {
	            System.out.println("Commerce NON trouvé !");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
    /* DELETE */
	public static void delete(Commerce commerce) {
	    String sql = "DELETE FROM Commerce WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setInt(1, commerce.getId()); //Condition du WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Commerce supprimmé avec succès !");
	        } else {
	            System.out.println("Commerce NON trouvé !");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	public static Commerce getCommerceById(int id) {
	    String sql = "SELECT * FROM Commerce WHERE id = ?";
	    Commerce commerce = null;

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String nom = rs.getString("nom");
	            double rapport= rs.getDouble("rapport");
	            commerce = new Commerce(id,nom,rapport);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return commerce;
	}

	
}

