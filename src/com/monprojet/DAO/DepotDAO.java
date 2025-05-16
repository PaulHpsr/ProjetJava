package com.monprojet.DAO;

import com.monprojet.classes.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;


public class DepotDAO {
	
	/* Attributs */
	
	private static final String URL = ConnectionDAO.getURL();
    private static final String USER = ConnectionDAO.getUSER();
    private static final String PASSWORD = ConnectionDAO.getPASSWORD();
    
	/* Méthodes */
    
    /* CREATE */
    
	public static void create(int idPoubelle, int idMenage,Depot depot) {
		String sql = "INSERT INTO Depot (id,idPoubelle, idMenage, heureDepot, pointsGagnes) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Formatage de la date pour SQL
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String heureDepotSQL = depot.getHeureDepot().format(formatter);

            // Remplissage des paramètres
            pstmt.setInt(1, depot.getId());
            pstmt.setInt(2, idPoubelle);
            pstmt.setInt(3, idMenage);
            pstmt.setString(4, heureDepotSQL);
            pstmt.setInt(5, depot.getPointsGagnes());

            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Dépôt inséré avec succès !");
                
                // Récupérer l'ID généré
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        depot.setId(generatedKeys.getInt(1)); // Mise à jour de l'ID de l'objet
                        System.out.println("ID du dépôt généré : " + depot.getId());
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
		
	}
	
	
	/* READ */
    public static void read(String[] args) {
        
        try {

            // Établir la connexion
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données ! \nAffichage des dépôts : ");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Depot");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Id poubelle: " + rs.getInt("idPoubelle") + ", Id ménage: "+rs.getInt("idMenage") + ", Heure de dépôt: "+rs.getString("heureDepot") + ", Points gagnés: "+rs.getInt("pointsGagnes"));           }

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
	public static void update(Depot depot) {
	    String sql = "UPDATE Depot SET pointsGagnes = ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setInt(1, depot.getPointsGagnes());   // Associe la poubelle à son centre de tri
	        pstmt.setInt(2, depot.getId());  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Dépôt mise à jour avec succès !");
	        } else {
	            System.out.println("Aucun dépôt trouvé avec cet ID.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static void updateNull(int idPoubelle) { //Supprime tous les dépots qui ont l'id de la poubelle qu'on veut supprimer (pour éviter les problèmes de foreign key)
	    String sql = "UPDATE Depot SET idPoubelle = null WHERE idPoubelle = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {  

	        // Remplissage des paramètres
	        pstmt.setInt(1, idPoubelle);  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Dépôts mis à jour avec succès !");
	        } else {
	            System.out.println("Aucun dépôt n'a été fait dans cette poubelle, elle peut être supprimée en paix");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	/* DELETE */
	public static void delete(Depot depot) {
		String sql = "DELETE FROM Depot WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Remplissage des paramètres
			pstmt.setInt(1, depot.getId()); // Condition du WHERE

			// Exécution de la requête
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Dépôt supprimmé avec succès !");
			} else {
				System.out.println("Dépôt NON trouvé !");
			}
		} catch (SQLException e) {
			System.err.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
