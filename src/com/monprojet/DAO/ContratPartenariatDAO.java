package com.monprojet.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.monprojet.classes.ContratPartenariat;

public class ContratPartenariatDAO {
	
	/* Attributs */
	
	private static final String URL = ConnectionDAO.getURL();
    private static final String USER = ConnectionDAO.getUSER();
    private static final String PASSWORD = ConnectionDAO.getPASSWORD();
    
	/* Méthodes */
    
    /* CREATE */
	public static void create(ContratPartenariat contrat) {
		String sql = "INSERT INTO ContratPartenariat (id,idCommerce,idCentreDeTri,dateDebut,dateFin,conditions,categoriesProduits) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres
            pstmt.setInt(1, contrat.getId());
            pstmt.setInt(2, contrat.getCommerce().getId());
            pstmt.setInt(3, contrat.getCentreDeTri().getId());
            pstmt.setDate(4, Date.valueOf(contrat.getDateDebut()));
            pstmt.setDate(5, Date.valueOf(contrat.getDateFin()));
            pstmt.setString(6, contrat.getCondition());
            pstmt.setString(7, contrat.getCategoriesProduits());

            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("ContratPartenariat inséré avec succès !");                
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
            System.out.println("Connexion réussie à la base de données ! \nAffichage des contrats partenariat : ");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ContratPartenariat");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Id commerce: " + rs.getInt("idCommerce") + ", Id centre de tri: "+rs.getInt("idCentreDeTri") + ", Date début: "+rs.getDate("DateDebut") + ", Date fin: "+rs.getDate("DateFin") + ", Conditions: "+rs.getString("conditions") + ", Catégorie produits: "+rs.getString("categoriesProduits"));           
            }

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
    
	public static void update(ContratPartenariat contrat) {
	    String sql = "UPDATE ContratPartenariat SET dateFin = ?, conditions=?, categoriesProduits = ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setDate(1, Date.valueOf(contrat.getDateFin()));   
	        pstmt.setString(2, contrat.getCondition());
            pstmt.setString(3, contrat.getCategoriesProduits());
	        pstmt.setInt(4, contrat.getId());  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("ContratPartenariat mis à jour avec succès !");
	        } else {
	            System.out.println("Aucun ContratPartenariat trouvé avec cet ID.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	/* DELETE */
	public static void delete(ContratPartenariat contrat) {
	    String sql = "DELETE FROM ContratPartenariat WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setInt(1, contrat.getId()); //Condition du WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Contrat supprimmé avec succès !");
	        } else {
	            System.out.println("Contrat NON trouvé !");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
