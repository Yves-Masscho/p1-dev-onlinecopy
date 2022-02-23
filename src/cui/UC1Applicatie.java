package cui;
import java.util.Scanner;
import domein.DomeinController;
import exceptions.GebruikersnaamNietCorrect;
import util.Taal;

import java.util.InputMismatchException;

public class UC1Applicatie {
	private final DomeinController dc; 
	private Taal taal;
	
	
	public UC1Applicatie(DomeinController dc) { 
		this.dc = dc; 
		taal = dc.getTaal(); 
	}
	
	public void start() throws Exception { 
	
		Scanner input = new Scanner(System.in); 
		String gebruikersnaam, wachtwoord; 
		boolean vlag = true;
		
		do {
			try {
			System.out.print(taal.vertaal("M18"));  
			gebruikersnaam = input.nextLine();
			System.out.print(taal.vertaal("M19"));
			wachtwoord = input.nextLine();
			dc.meldAan(gebruikersnaam, wachtwoord);
			vlag = false;
			}
				catch(IllegalArgumentException gsg){
	            System.out.println(taal.vertaal(gsg.getMessage()));
	        }	catch (GebruikersnaamNietCorrect gnc) { 
	        	System.err.print(taal.vertaal("M10"));
	        }
		} 
		while (vlag);

		System.out.println(dc.geefGebruikersNaam());
		System.out.println(dc.geefMenu()); 
		
		int keuzeMenu;
		
		boolean keuze = true; 
		    
	    do { 
	    	try { 
	    		keuzeMenu = input.nextInt();
	    		if (keuzeMenu > 4 || keuzeMenu < 1)
	    		{
                  throw new IllegalArgumentException("M31"); //De keuze moet 1, 2, 3 of 4 zijn. Probeer opnieuw. 
                }
	    		keuze = false;
	    		
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
	    		 
	    	} 
	    	
	    	catch (IllegalArgumentException gk) { 
	                 System.out.println(taal.vertaal(gk.getMessage()));
	             }
	    	catch (InputMismatchException inputmismatchexception) {//keuze moet een getal zijn. Probeer opnieuw
	    		System.out.println(taal.vertaal("M32"));
	    		input.nextLine();
	    	} 
	    		 
	    } while (keuze); 
		
	    input.close();
	}
	
}



