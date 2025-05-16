package com.monprojet.classes;


public enum TypeDechet {
	
	PLASTIQUE(0,2), VERRE(1,4), CARTON(2,3), METAL(3,5); //Écrit sour la forme NOMCATEGORIE(numéroDeType,Poids)

	private final int type;
    private final int poids;

  
    // Getter pour le type
    public int getType() {
        return type;
    }

    // Getter pour le poids
    public int getPoids() {
        return poids;
    }


	// Constructeur de l'énumération
    TypeDechet(int type, int poids) {
    	this.type = type;
    	this.poids = poids;
    }
    
    // Méthode pour obtenir l'enum à partir de sa valeur de type
    public static TypeDechet fromType(int type) {
        for (TypeDechet nom : TypeDechet.values()) {
            if (nom.getType() == type) {
                return nom;
            }
        }
        throw new IllegalArgumentException("Type invalide : " + type);
    }
    public static void main(String[] args){
    	for (TypeDechet type : TypeDechet.values())
    		System.out.println(type);
    	TypeDechet t= fromType(1);
    	System.out.println(t);
    }
}

