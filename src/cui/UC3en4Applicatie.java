package cui;

import domein.DomeinController;
import util.Taal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC3en4Applicatie {
	private final DomeinController dc; 
	private Taal taal;
	private int levelVanSpel;
	
	public UC3en4Applicatie(DomeinController dc) { 
		this.dc = dc; 
		taal = dc.getTaal();
		this.levelVanSpel = 1;
	}
	
	public void startUC3()  {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in); // input moet closed worden
		
		// UC3 toon lijst met namen van spelletjes en Speler kiest Spel:
		
		System.out.println(taal.vertaal("M37"));
		
		System.out.println(dc.geefSpellen());
		boolean vlag = true;
		
		do {  
	    	try { 
	    		System.out.println(taal.vertaal("M38"));
	    		int keuze = input.nextInt(); 
	    		
	    		if (keuze < 1 || keuze > 4)
	    		{
                    throw new IllegalArgumentException("M31");
                }
	    		
	    		dc.kiesSpel(keuze);
	    		
	    	
	    		
	    		vlag = false; 
	    	} 
	    	catch (InputMismatchException inputmismatchexception) {
	    		System.out.println(taal.vertaal("M32"));
	    		input.nextLine();	
	    	}
	    	catch (IllegalArgumentException gk) { 
                System.out.println(taal.vertaal(gk.getMessage()));
            }
		 
	    } while (vlag); 	
		
		
		
		// STAP 4 UC3 Systeem toont acties volgens DR_ACTIES_SPEL
		do {
			int keuze2;
			boolean vlag2 = true;
					
			do {  
				try { 
					System.out.println(taal.vertaal("M33")); // M33 = Kies 1 om het volgende spelbord te voltooien. Kies 2 om het spel te verlaten.
					keuze2 = input.nextInt(); 
	    		
					if (keuze2 > 2 || keuze2 < 1)
					{
						throw new IllegalArgumentException("M34"); // M34 = De keuze moet 1 of 2 zijn. Probeer opnieuw.
					}
					
					switch(keuze2) {
					case 1:
						dc.selecteerSpelbord(this.levelVanSpel);
						System.out.println(dc.geefSpelbord()); 
						System.out.println(taal.vertaal("M52")); // legende bij Sokoban
						startUC4(); // hier dit spel uitspelen, voormalig UC4, nu startUC4 in deze CUI Klasse
						break;
					case 2:
						System.exit(0);
						break;
					}
					
					vlag2 = false; 
				} 
	    	
				catch (InputMismatchException inputmismatchexception) {
					System.out.println(taal.vertaal("M32"));
					input.nextLine();	
				}
				catch (IllegalArgumentException getalN) { 
					System.out.println(taal.vertaal(getalN.getMessage()));
				}
		 
			} while (vlag2);

			// STAP 7: Speler voltooit spelbord (UC4)
			
		
			// STAP 8: Systeem toont het aantal voltooide spelborden en het totaal aantal spelborden in dit spel
			System.out.printf("%s%23s%n", taal.vertaal("M39"), taal.vertaal("M40"));
			int[] array = dc.geefVooruitgang();
			System.out.printf("%20d%23d%n", array[0], array[1] );
		}
		 //STAP 9: Zolang niet DR_EINDE_SPEL, ga naar stap 4
		while (!dc.isEindeSpel());
		
		 System.out.println(taal.vertaal("M57"));
	}
	
	public void startUC4() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in); // input moet closed worden
		
		// Stap 3 Systeem toont overzicht mogelijke acties DR_ACTIES SPELBORD
		
		int keuze;
		int richting;
		boolean vlag = true;
	
		do {  
			try { 
				System.out.println(taal.vertaal("M41")); // M41 = kies 1, 2 of 3 voor de volgende acies: 
				System.out.println("1. "+ taal.vertaal("M42")); //M42 = Het mannetje verplaatsen
				System.out.println("2. "+ taal.vertaal("M43")); //M43 = Het spelbord resetten
				System.out.println("3. "+ taal.vertaal("M44")); //M44 = Stoppen met spelen
				keuze = input.nextInt(); 
    		
				if (keuze > 3 || keuze < 1)
				{
					throw new IllegalArgumentException("M45"); // M45 = De keuze moet 1, 2 of 3 zijn. Probeer opnieuw.
				}
				
				switch(keuze) {
				
				// Stap 4a (normaal verloop) Speler wil mannetje verplaatsen, 
				// Systeem vraagt richting wat ingegeven wordt door de Speler
				case 1: //M42 = Het mannetje verplaatsen
					do {
						try {
					System.out.println(taal.vertaal("M46")); // M46 naar welke richting wenst u het mannetje te verplaatsen?
					System.out.println("4. "+ taal.vertaal("M47")); // M47 links
					System.out.println("8. "+ taal.vertaal("M48")); // M48 boven
					System.out.println("6. "+ taal.vertaal("M49")); // M49 rechts
					System.out.println("2. "+ taal.vertaal("M50")); // M50 onder
					richting = input.nextInt();
					dc.verplaatsMannetje(richting);
					vlag = false;
					System.out.println(dc.geefSpelbord());
					
					System.out.println(taal.vertaal("M53"));
					System.out.println(dc.geefAantalVerplaatsingen());
					
					if (richting != 4 && richting != 8 && richting != 6 && richting !=2 ) {
						throw new IllegalArgumentException("M51"); // Geef nummer 4, 8, 6 of 2 in
					}
					
					}
					catch (InputMismatchException inputmismatchexception) {
							System.out.println(taal.vertaal("M32"));
							input.nextLine();	
					}
					catch (IllegalArgumentException getalE) { 
						System.out.println(taal.vertaal(getalE.getMessage()));
					}
					
					} while(vlag); //
					
					
					break;
				// Stap 4b (alternatief) reset van spelbord, 
				// aantal verplaatsingen op 0 en terug naar beginstand
				case 2: //M43 = Het spelbord resetten
					dc.resetSpelbord(); 
					System.out.println(dc.geefSpelbord());
					break;
				// stap 4c (alternatief) Spel wordt opgegeven
				case 3: //M44 = Stoppen met spelen
					break;
				}
			} 
    	
			catch (InputMismatchException inputmismatchexception) {
				System.out.println(taal.vertaal("M32"));
				input.nextLine();	
			}
			catch (IllegalArgumentException getalN) { 
				System.out.println(taal.vertaal(getalN.getMessage()));
			}
	 
		} while (!dc.isVoltooid());
		
			// Stap 7a Systeem meld spelbord voltooid en toont aantal verplaatsingen
			if (dc.isVoltooid()) { 
				System.out.println(taal.vertaal("M55")); 
				System.out.printf("%s = %d%n", taal.vertaal("M53") ,dc.geefAantalVerplaatsingen()); 
				dc.voltooiSpelbord(levelVanSpel);
				levelVanSpel++;	
			}			
			// Stap 7b (niet voltooid) opnieuw naar stap 2	
	}
}
