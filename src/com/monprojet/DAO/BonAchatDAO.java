package com.monprojet.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.monprojet.classes.BonAchat;
import com.monprojet.classes.Commerce;
import com.monprojet.classes.Menage;

public class BonAchatDAO {
	/* Attributs */
	
	private static final String URL = ConnectionDAO.getURL();
    private static final String USER = ConnectionDAO.getUSER();
    private static final String PASSWORD = ConnectionDAO.getPASSWORD();
    
	/* Méthodes */
    
    /* CREATE */
	public static void create(BonAchat bon) {
		String sql = "INSERT INTO BonAchat (id,idMenage,idCommerce,valeur,dateCreation) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres
            pstmt.setInt(1, bon.getId());
            pstmt.setInt(2, bon.getMenage().getId());
            pstmt.setInt(3, bon.getCommerce().getId());
			pstmt.setFloat(4, bon.getValeur());
            pstmt.setDate(5, Date.valueOf(bon.getDateCreation()));
            
            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Bon d'achat inséré avec succès !");                
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
            System.out.println("Connexion réussie à la base de données ! \nAffichage des bons d'achats : ");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BonAchat");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Id ménage: " + rs.getInt("idMenage") + ", Id commerce: "+rs.getInt("idCommerce") + ", Valeur: " + rs.getFloat("valeur")+", Date de création: "+rs.getDate("dateCreation") );           
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
    //Si on autorise un renouvellement d'un bon la méthode suivante peut être utile
	public static void update(BonAchat bon) {
	    String sql = "UPDATE BonAchat SET dateCreation = ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setDate(1, Date.valueOf(bon.getDateCreation()));
	        pstmt.setInt(2, bon.getId());  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Bon d'achat mis à jour avec succès !");
	        } else {
	            System.out.println("Aucun Bon d'achat trouvé avec cet ID.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	/* DELETE */
	public static void delete(BonAchat bon) {
	    String sql = "DELETE FROM BonAchat WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setInt(1, bon.getId()); //Condition du WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Bon d'achat supprimmé avec succès !");
	        } else {
	            System.out.println("Bon d'achat NON trouvé !");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	public static ArrayList<BonAchat> getBonsByMenageId(int idMenage) {
		ArrayList<BonAchat> bons = new ArrayList<>();
	    String sql = "SELECT * FROM BonAchat WHERE idMenage = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, idMenage);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            float valeur = rs.getFloat("valeur");

	            // récupérer le ménage
	            Menage menage = MenageDAO.getMenageById(idMenage);

	            // récupérer le commerce
	            int idCommerce = rs.getInt("idCommerce");
	            Commerce commerce = CommerceDAO.getCommerceById(idCommerce);

	            // reconstituer le bon
	            BonAchat bon = new BonAchat(id, valeur, menage, commerce);

	            // fixer la date manuellement puisque le constructeur met LocalDate.now()
	            bon.setDateCreation(rs.getDate("dateCreation").toLocalDate());

	            bons.add(bon);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bons;
	}


}
