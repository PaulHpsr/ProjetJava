package com.monprojet.DAO;

import java.sql.*;

import com.monprojet.classes.Menage;

public class MenageDAO {
	
	/* Attributs */
	
	private static final String URL = ConnectionDAO.getURL();
    private static final String USER = ConnectionDAO.getUSER();
    private static final String PASSWORD = ConnectionDAO.getPASSWORD();
    
	/* Méthodes */
    
    /* CREATE */
    
	public static void create(Menage menage) {
		String sql = "INSERT INTO Menage (id,nom,codeAcces,pointFidel) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Remplissage des paramètres
            pstmt.setInt(1, menage.getId());
            pstmt.setString(2, menage.getNom());
            pstmt.setInt(3, menage.getCodeAcces());
            pstmt.setInt(4, menage.getPointFidel());

            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ménage inséré avec succès !");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
	public static void create(int id,String nom,int pointFidel,int codeAcces) {
		String sql = "INSERT INTO Menage (id,nom,codeAcces,pointFidel) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Remplissage des paramètres
            pstmt.setInt(1,id);
            pstmt.setString(2, nom);
            pstmt.setInt(3, codeAcces);
            pstmt.setInt(4, pointFidel);

            // Exécution de la requête
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ménage inséré avec succès !");
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
            System.out.println("Connexion réussie à la base de données ! \nAffichage des ménages : ");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Menage");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom") + ", Code d'acces: "+rs.getInt("codeAcces") + ", Points de fidélité: "+rs.getInt("pointFidel"));           }

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
    public static void update(Menage menage) {
	    String sql = "UPDATE Menage SET pointFidel = ? WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) { 

	        // Remplissage des paramètres
	        pstmt.setInt(1, menage.getPointFidel());  
	        pstmt.setInt(2, menage.getId());  // Condition WHERE

	        // Exécution de la requête
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Ménage mise à jour avec succès !");
	        } else {
	            System.out.println("Aucun Ménage trouvée avec cet ID.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
    
    /* DELETE */
	public static void delete(Menage menage) {
		String sql = "DELETE FROM Menage WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Remplissage des paramètres
			pstmt.setInt(1, menage.getId()); // Condition du WHERE

			// Exécution de la requête
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Ménage supprimmé avec succès !");
			} else {
				System.out.println("Ménage NON trouvé !");
			}
		} catch (SQLException e) {
			System.err.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
	public static boolean doesUserExist(String nom, int codeAcces) {
	    String sql = "SELECT 1 FROM Menage WHERE nom = ? AND codeAcces = ? LIMIT 1";
	    
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, nom);
	        pstmt.setInt(2, codeAcces);

	        ResultSet rs = pstmt.executeQuery();

	        return rs.next();  // Si on trouve une ligne, l'utilisateur existe
	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	        return false;  // En cas d'erreur, on renvoie false
	    }
	}
	public static String getUserInfo(String nom, int codeAcces) {
	    String sql = "SELECT nom, id, pointFidel FROM Menage WHERE nom = ? AND codeAcces = ?";
	    
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, nom);
	        pstmt.setInt(2, codeAcces);

	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // Retourner une chaîne de caractères avec les informations utilisateur
	            return rs.getString("nom") + "," + rs.getInt("id") + "," + rs.getInt("pointFidel");
	        } else {
	            return null; // Aucun utilisateur trouvé
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null; // En cas d'erreur, retourner null
	    }
	}
	public static Menage getMenageById(int id) {
	    String sql = "SELECT * FROM Menage WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            // Créer et retourner le ménage trouvé
	            String nom = rs.getString("nom");
	            int codeAcces = rs.getInt("codeAcces");
	            int pointFidel = rs.getInt("pointFidel");

	            return new Menage(id, nom, codeAcces, pointFidel);
	        } else {
	            // Aucun ménage trouvé
	            return null;
	        }

	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}


    
}
