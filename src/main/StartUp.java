package main;
import java.util.InputMismatchException;
import java.util.Scanner;

import cui.UC1Applicatie;
import cui.UC2Applicatie;
import domein.DomeinController;
import exceptions.GebruikersnaamNietCorrect;
import exceptions.WachtwoordNietCorrect;
import util.Taal; 


public class StartUp {
	
public static void main(String [] args) throws Exception {
		
		Taal taal = new Taal();
		Scanner s = new Scanner(System.in); 
		int taalKeuze;
		DomeinController dc = new DomeinController();
		dc.setTaal(taal); 
		String antwoord; 
		
		boolean keuze = true; 
		
		  System.out.println("Taal wijzigen: kies 1 voor Nederlands");
		  System.out.println("Change language: choose 2 for English");
		  System.out.println("Changer la langue: choisis 3 pour Français");	
		    
	    do { 
	    	try { 
	  
	    		taalKeuze = s.nextInt(); 
	    		taal.setBundel(taalKeuze);
	    		
	    		if (taalKeuze > 3 || taalKeuze < 1)
	    		{
                    throw new IllegalArgumentException("De keuze moet in het interval [1,3] liggen. Probeer opnieuw.\n"
                    		+ "The selection must be between 1 and 3. Please try again\n"
                    		+ "La sélection doit se faire dans l'intervalle [1,3]. Essayez à nouveau ");
                }
	    		keuze = false; 
	    	} 
	    	
	    	catch (InputMismatchException inputmismatchexception) {
	    		s.nextLine();
	    		System.out.println("Probeer opnieuw! Taal wijzigen: kies 1 voor Nederlands");
	  		  	System.out.println("Try Again ! Change language: choose 2 for English");
	  		  	System.out.println("Essayer a nouveau ! Changer la langue: choisis 3 pour Français");
	    	} 
	    	
	    	catch (IllegalArgumentException outofbounds) { 
	    		 {
	                 System.out.println(outofbounds.getMessage());
	                 s.nextLine();
	             }
	    		
	    	}
		 
	    } while (keuze); 
	    
	    System.out.println(taal.vertaal("M16") +  taal.vertaal("M21") +"/" + taal.vertaal("M22") ); //wilt u inloggen? Ja/neen  
	  
	    try {antwoord = s.next().toUpperCase(); 
	    if (antwoord.equals(taal.vertaal("M21"))) { 
	    	new UC1Applicatie(dc).start(); 
	    } else 
	    		new UC2Applicatie(dc).start();
	      
				} catch (GebruikersnaamNietCorrect gnc) { 
					System.err.print(gnc);
				} catch (WachtwoordNietCorrect wnc) { 
					System.err.print(wnc);
				} 
	    s.close();
	} 
	
}