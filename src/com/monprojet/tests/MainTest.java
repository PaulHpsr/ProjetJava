package com.monprojet.tests;

import java.util.ArrayList;

import com.monprojet.classes.CentreDeTri;

public class MainTest {

	public MainTest() {
	}

	public static void main(String[] args) {
    	if (CentreDeTri.centres==null) {
    		CentreDeTri.centres = new ArrayList<CentreDeTri>();
    	}		
		TestCentreDeTri.main(args);
		TestPoubelleIntelligente.main(args);
		TestMenage.main(args);
		TestDepot.main(args);
		TestDechet.main(args);
		TestCommerce.main(args);
		TestContratPartenariat.main(args);
				
	}
}

