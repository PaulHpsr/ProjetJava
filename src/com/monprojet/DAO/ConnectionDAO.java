package com.monprojet.DAO;

import java.sql.*;

public class ConnectionDAO {
	
	/* Attributs */
	// à modifier
	private static final String URL = "jdbc:mysql://localhost:3306/GestionTri?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "cytech0001";
    
    
    /* Méthodes */
    public static String getURL() {
    	return URL ;
    }
    public static String getUSER() {
    	return USER ;
    }
    public static String getPASSWORD() {
    	return PASSWORD ;
    }
    
    public static void main(String[] args) {
        // Paramètres de connexion
        String url = getURL(); 
        String user = getUSER(); 
        String password = getPASSWORD(); 

        // Connexion à la base de données
        try {
            // Charger le driver JDBC (non obligatoire avec les versions récentes)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie à la base de données !");

            // Création d'une requête SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM menage");

            // Parcourir les résultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom") + ", CodeAcces: "+rs.getInt("codeacces")+", PointFidel: "+rs.getInt("pointfidel"));
            }

            // Fermer les ressources
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Driver JDBC non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
}

