package cui;
import java.util.InputMismatchException;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.WachtwoordNietCorrect;
import util.Taal; 

public class UC2Applicatie {
	private final DomeinController dc; 
	private Taal taal;
	
	public UC2Applicatie(DomeinController dc) { 
		this.dc = dc; 
		taal = dc.getTaal();
	}
	public void start() throws Exception { 
		registreer(); 
	}
	
	public void registreer() throws Exception {
		
		String naam, voornaam, gebruikersnaam, wachtwoord;
		boolean vlag = true;
		Scanner input = new Scanner(System.in);
		
		do { 
	    	try { 
	  
	    		System.out.print(taal.vertaal("M7")); 
	    		naam = input.nextLine(); 
	    		System.out.println(taal.vertaal("M8")); 
	    		voornaam = input.nextLine(); 
	    		System.out.println(taal.vertaal("M5"));
	    		gebruikersnaam = input.nextLine(); 
	    		System.out.println(taal.vertaal("M6")); 
	    		wachtwoord = input.nextLine(); 
	    		dc.registreer(gebruikersnaam, wachtwoord, naam, voornaam);	
	    		
	    		vlag = false; 
	    	} 
	    	
				catch (WachtwoordNietCorrect wnc) { 
				System.err.print(taal.vertaal("M11"));
				} 
	    		
	    		catch (IllegalArgumentException e){
                System.out.println(taal.vertaal(e.getMessage()));
            } 
	    	
	    	
	    			 
	    } while (vlag); 
		
		System.out.println(dc.geefGebruikersNaam());
		System.out.println(dc.geefMenu()); 
		
		int keuzeMenu = input.nextInt();
		
		boolean keuze = true; 
		    
	    do { 
	    	try { 
	    		
	    		if (keuzeMenu > 4 || keuzeMenu < 1)
	    		{
                  throw new IllegalArgumentException("M31"); //De keuze moet 1, 2, 3 of 4 zijn. Probeer opnieuw. 
              }
	    		keuze = false; 
	    	} 
	    	
	    	catch (IllegalArgumentException gk) { 
	                 System.out.println(taal.vertaal(gk.getMessage()));
	             }
	    	catch (InputMismatchException inputmismatchexception) {//keuze moet een getal zijn. Probeer opnieuw
	    		System.out.println(taal.vertaal("M32"));
	    		input.nextLine();
	    	} 
	    	
		 
	    } while (keuze); 
	    
	    switch(keuzeMenu) { // admincontrole nog toevoegen
	    case 1:
	    	new UC3en4Applicatie(dc).startUC3();
	    	break;
	    case 2:
	    	//new UC5Applicatie(dc).start();
	    	break;
	    case 3:
	    	//new UC7Applicatie(dc).start(); // gaan we nooit toe komen
	    	break;
	    case 4:
	    	System.exit(0);
	    	break;
	  }
		
	 input.close();
	}
}
