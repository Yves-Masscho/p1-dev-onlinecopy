package domein;

import java.util.List;

public class Spelbord{
private String spelbordNaam; 
private int velden[][] = new int[10][10]; 
private int aantalVerplaatsingen;
private boolean voltooid;
private int[] positieMannetje = new int[2]; 
private boolean opDoel;
private boolean wegVrij;
private boolean kistOpDoel;
private int aantalKistOpDoel;
private int levelVanSpel;
private String gebruikersnaam; 

/**
 * UC3 Constructor
 * Het spelbord wordt aangemaakt door een spelbordnaam, een lijst van velden (100), 
 * int x en y voor de startpositie van het mannetje op te sporen en het level van het spel.
 * Bij de start staat er nog geen kist op een doel en krijgt dit de waarde vals. 
 * Het aantal verplaatsingen start op 0 en een spelbord kan nog niet voltooid zijn bij de start. 
 * @param spelbordNaam
 * @param deVelden
 * @param y
 * @param x
 * @param levelVanSpel
 * @author Groep89
 */
public Spelbord(String spelbordNaam, List<Integer> deVelden, int y, int x, int levelVanSpel) { 
	setSpelbordNaam(spelbordNaam); 
	setVelden(deVelden); 
	this.opDoel = false;
	this.setPositieMannetje(y, x);
	this.aantalVerplaatsingen = 0;
	this.voltooid = false;
	this.setLevelVanSpel(levelVanSpel);
	this.aantalKistOpDoel = 0;
}

/**
 * UC6 constructor 
 * Deze constructor is minder uitgebreid en heeft enkel een spelbordnaam en de gebruikersnaam van de speler nodig.
 * De setter voor velden is leeg en er staat nog niets op een doel.
 * Het level van het spel wordt ook ingesteld. 
 * @param spelbordnaam
 * @param gebruikersnaam
 * @author Groep89
 */
public Spelbord(String spelbordNaam, int levelVanSpel, String gebruikersnaam) { 
	setSpelbordNaam(spelbordNaam); 
	setVelden(); 
	this.opDoel = false;
	this.setLevelVanSpel(levelVanSpel);
	this.setGebruikersnaam(gebruikersnaam);
}


/**
 * UC3 getter voor spelbordnaam
 * @return spelborNaam
 * @author Groep89
 */
public final String getSpelbordNaam() { 
	return spelbordNaam; 
}

/**
 * UC3 setter voor spelbordNaam
 * Er wordt gecontroleerd of spelbordnaam is ingevuld.
 * Indien dit niet het geval is, wordt er een exceptie geworpen. 
 * @param spelbordNaam
 * @author Groep89
 */
private final void setSpelbordNaam(String spelbordNaam) { 
	if(spelbordNaam == null || spelbordNaam.isEmpty())
		this.spelbordNaam = "test"; // later terug exception, maar throwen en catchen in gui/cui
	/*throw new IllegalArgumentException("Spelbordnaam moet ingevuld zijn.");*/
	this.spelbordNaam = spelbordNaam; 
}

/**
 * UC3 getter voor velden, wordt ook voor UC6 gebruikt
 * @return velden
 * @author Groep89
 */
public final int[][] getVelden() { // return is veldenArray
	return velden;
}

/**
 * UC3 getter voor veld.
 * Deze getter wordt gebruikt om de waarde van 1 veld op te halen.
 * @param rij
 * @param kol
 * @return waarde van het veld
 * @author Groep89
 */
public final int getVeld(int rij, int kol) { 
	return velden[rij][kol];
}

/**
 * UC3 setter velden 
 * Een dubbele for lus wordt gebruikt om de lengte van de array te bepalen.
 * Het aantal kolommen wordt doorlopen, aanvullend met het aantal rijen. 
 * @param deVelden
 * @author Groep89
 */
public final void setVelden(List<Integer> deVelden) {
	int index = 0;
	for (int kol = 0; kol < this.velden.length; kol++){
        for (int rij = 0; rij < this.velden.length; rij++){
             this.velden[kol][rij] = deVelden.get(index);
             index++;
        }
   }
}

/**
 * UC3 setter velden 
 * De array wordt opgevuld met blanco velden.
 * @author Groep89
 */
public void setVelden() {
	for (int kol = 0; kol < this.velden.length; kol++){
        for (int rij = 0; rij < this.velden.length; rij++){
             this.velden[kol][rij] = 6;  // blanco's
        }
   }	
}

/**
 * UC3 setter veld 
 * Door de waarde van Y en X wordt het veldtype bepaald. 
 * @param Y
 * @param X
 * @param veldType
 * @author Groep89
 */
public final void setVeld(int Y, int X, int veldType) { 
	velden[Y][X] = veldType; // Y EN X OMGEWISSELD, dit klopt
}

/**
 * UC4 SD isVoltooid knowing 
 * Elk spelbord heeft 4 kisten die bij een voltooid spelbord op een doel te vinden zijn. 
 * Wanneer de 4 kisten op een doel staan, is het spelbord voltooid en wordt er naar het volgende level gegaan. 
 * Indien nog niet alle kisten op het doel staan, 
 * is het spelbord niet voltooid en blijft het spelbord beschikbaar om uit te spelen.
 * @return voltooid
 * @author Groep89
 */
public final boolean isVoltooid() {
	if (getAantalKistOpDoel() == 4) { 
		voltooid = true;
		this.setLevelVanSpel(this.getLevelVanSpel() + 1);
		
		return voltooid;
	}
	else {
		voltooid = false;
		return voltooid;
	}
}
/**
 * UC4 SD verplaatsMannetje doing
 * Eerst wordt er gecontroleerd of de beweging mogelijk is door de methode controleerBeweging. 
 * Deze doet dit volgens de positie van het mannetje (= methode) en de ingegeven richting. 
 * De richting kan enkel worden uitgevoerd door 2, 4, 6 en 8. Een andere waarde wordt niet aanvaard. 
 * De boolean wegVrij moet true zijn alvorens het mannetje kan verplaatst worden en dan wordt het aantalVerplaatsingen verhoogd. 
 * Er wordt gecontrolleerd of er een kist op een doel staat (boolean kistOpDoel). 
 * Een mannetje kan op een doel staan om verder te bewegen. (Boolean opDoel) 
 * a.d.h.v. X en Y - coördinaten verplaatsen
 * @param richting
 * @author Groep89
 */
public void verplaatsMannetje(int richting)  { //2 - 4 - 6 - 8 
	
	// !!! om te bewegen en alle andere bewerking is het [Y][X]!
	
	this.controleerBeweging(positieMannetje, richting); // controle van verplaating, indien oké wegvrij = true
	if (wegVrij) {
		
		this.setAantalVerplaatsingen(aantalVerplaatsingen + 1);
		
		//verplaatsing van kist die reeds op doel stond
		if (kistOpDoel) {
		this.controleerBewegingKist(positieMannetje, richting);
		}
		
		if (opDoel) { //verplaatsing van mannetje die op een doel staat
		this.setVeld(positieMannetje[0],positieMannetje[1],3);
		} else {
		this.setVeld(positieMannetje[0],positieMannetje[1],1);
		}
	
		
		switch (richting) {
		case 6: // rechts
			this.uitvoeringBewegingR(positieMannetje);
			break;
		case 4: // links
			this.uitvoeringBewegingL(positieMannetje);
			break;
		case 2: // onder
			this.uitvoeringBewegingO(positieMannetje);
			break;
		case 8: // boven
			this.uitvoeringBewegingB(positieMannetje);
			break;
			}	
		} 
	else {
		this.setVeld(positieMannetje[0],positieMannetje[1],5);
	}
}

/**
 * UC4 getter aantalKistOpDoel 
 * @return
 * @author Groep89
 */
public final int getAantalKistOpDoel() {
	return aantalKistOpDoel;
}

/**
 * UC4 setter aantalKistOpDoel 
 * @param aantalKistOpDoel
 * @author Groep89
 */
public final void setAantalKistOpDoel(int aantalKistOpDoel) {
	this.aantalKistOpDoel = aantalKistOpDoel;
}

/**
 * UC4 getter aantalVerplaatsingen 
 * @return
 * @author Groep89
 */
public final int getAantalVerplaatsingen() {
	return aantalVerplaatsingen;
}

/**
 * UC4 setter aantalVerplaatsingen 
 * @param aantalVerplaatsingen
 */
public final void setAantalVerplaatsingen(int aantalVerplaatsingen) {
	this.aantalVerplaatsingen = aantalVerplaatsingen;
}

/**
 * UC4 SD verplaatsMannetje doing 
 * Controleer of het mannetje zich kan verplaatsen 
 * Wanneer er een muur is, is een beweging sowieso niet mogelijk 
 * indien een kist gevolgd wordt door een muur of door nog een kist, is dit ook niet mogelijk 
 * @param positieMannetje
 * @param richting 
 * @return wegVrij 
 * @author Groep89
 */
public boolean controleerBeweging(int[] positieMannetje, int richting) {
	
	switch (richting) {
	
	case 6:
	// methode voor 1tje verder
	if (getVeld(positieMannetje[0], positieMannetje[1]+1) == 2) { 
		wegVrij = false;
		break;}
		else {
    // methode voor 2 verder
			if (getVeld(positieMannetje[0], positieMannetje[1]+1) == 4)  {
				if (getVeld(positieMannetje[0], positieMannetje[1]+2) == 2) {  
				wegVrij = false;
				break;}
				else 
					if (getVeld(positieMannetje[0], positieMannetje[1]+2) == 4) { 
						wegVrij = false; 
						break;}
					else {
						wegVrij = true;
						break;
						}
			}
			else {
				wegVrij = true;
			break;}
		}
	case 4:
		// methode voor 1tje verder
		if (getVeld(positieMannetje[0], positieMannetje[1]-1) == 2) { 
			wegVrij = false;
			break;}
		else {
	    // methode voor 2 verder
		if (getVeld(positieMannetje[0], positieMannetje[1]-1) == 4)  {
				if (getVeld(positieMannetje[0], positieMannetje[1]-2) == 2) { 
					wegVrij = false;
					break;}
				else {
				 if (getVeld(positieMannetje[0], positieMannetje[1]-2) == 4) { 
					wegVrij = false; 
					break;}
				 else {
					 wegVrij = true;
					 break;
				 }
			}
		}
		else wegVrij = true;
		break;
		}	
		
	case 2:
		// methode voor 1tje verder
		if (getVeld(positieMannetje[0] + 1, positieMannetje[1]) == 2) { 
			wegVrij = false;
			break;}
		else {
	    // methode voor 2 verder
		if (getVeld(positieMannetje[0] + 1, positieMannetje[1]) == 4)  {
				if (getVeld(positieMannetje[0] + 2 , positieMannetje[1]) == 2) { 
					wegVrij = false;
					break;}
				else 
					if (getVeld(positieMannetje[0] + 2, positieMannetje[1]) == 4) { 
						wegVrij = false;
						break;}
					else {
						wegVrij = true;
						break;	
					}
			}
			else { wegVrij = true;
			break;
			}
		}
	case 8:
		// methode voor 1tje verder
		if (getVeld(positieMannetje[0]-1, positieMannetje[1]) == 2) { 
			wegVrij = false;
			break;}
			else {
	    // methode voor 2 verder
		if (getVeld(positieMannetje[0]-1, positieMannetje[1]) == 4)  {
				if (getVeld(positieMannetje[0]-2, positieMannetje[1]) == 2) { 
					wegVrij = false;
					break;}
					else 
						if (getVeld(positieMannetje[0]-2, positieMannetje[1]) == 4) { 
							wegVrij = false;
							break;}
						else {
							wegVrij = true;
							break;
							}
				}
				else {
				wegVrij = true;
				break;}
		}
	
	}
	return wegVrij;
}

/**
 * UC4 hulpmethode voor verplaatsMannetje 
 * Een kist kan terug van een doel gehaald worden. 
 * @param positieMannetje
 * @param richting
 * @author Groep89
 */
private void controleerBewegingKist(int[] positieMannetje, int richting) {

	switch(richting) {
	case 6: // rechts
	 if (getVeld(positieMannetje[0], positieMannetje[1]+1) == 4){
		 this.setVeld(positieMannetje[0],positieMannetje[1] + 2, 4);
		 this.setVeld(positieMannetje[0],positieMannetje[1] + 1, 3);
		 kistOpDoel = false;
		 this.setAantalKistOpDoel(aantalKistOpDoel - 1);
		 break;}
	 else {
		 kistOpDoel = false;
		 break;}
	
	case 4: // links
		if (getVeld(positieMannetje[0], positieMannetje[1]-1) == 4){
			this.setVeld(positieMannetje[0],positieMannetje[1] - 2, 4);
			this.setVeld(positieMannetje[0],positieMannetje[1] - 1, 3);
			kistOpDoel = false;
			this.setAantalKistOpDoel(aantalKistOpDoel - 1);
			break;}
		else 
			kistOpDoel = false;
			break;
			
	case 2: //onder
		if (getVeld(positieMannetje[0]+1, positieMannetje[1]) == 4){
			this.setVeld(positieMannetje[0] + 2,positieMannetje[1], 4);
			this.setVeld(positieMannetje[0] + 1,positieMannetje[1], 3);
			kistOpDoel = false;
			this.setAantalKistOpDoel(aantalKistOpDoel - 1);
			break;}
		else 
			kistOpDoel = false;
			break;
			
	case 8: //boven
		if (getVeld(positieMannetje[0]-1, positieMannetje[1]) == 4){
			this.setVeld(positieMannetje[0] - 2,positieMannetje[1] , 4);
			this.setVeld(positieMannetje[0] - 1 ,positieMannetje[1] , 3);
			kistOpDoel = false;
			this.setAantalKistOpDoel(aantalKistOpDoel - 1);
			break;}
		else 
			kistOpDoel = false;
			break;
		}
	}

/**
 * UC4 hulpmethode voor verplaatsmannetje
 * uitvoering van de verplaatsing van het mannetje
 * @param positieMannetje
 * @author Groep89
 */

private void uitvoeringBewegingR(int[] positieMannetje) {// controle of mannetje op een doel komt
	if (getVeld(positieMannetje[0], positieMannetje[1]+1) == 3) {
		opDoel = true;}
	else {
		opDoel = false;}
	
	// controle of mannetje een kist meeneemt 
	if (getVeld(positieMannetje[0], positieMannetje[1]+1) == 4 ) {
		if (getVeld(positieMannetje[0], positieMannetje[1]+ 2) == 3 ) {
		this.setVeld(positieMannetje[0], positieMannetje[1]+2, 4); // verschuiven kist
		kistOpDoel = true;
		this.setAantalKistOpDoel(this.getAantalKistOpDoel() + 1);
		}
		else {
		this.setVeld(positieMannetje[0], positieMannetje[1]+2, 4); // verschuiven kist
		}
	}	
	
	this.setVeld(positieMannetje[0], positieMannetje[1] + 1, 5); // verander nieuw veld naar mannetje
	this.setPositieMannetje(positieMannetje[0], positieMannetje[1] +1 ); // 	
}

/**
 * UC4 hulpmethode voor verplaatsmannetje
 * uitvoering van de verplaatsing van het mannetje
 * @param positieMannetje
 * @author Groep89
 */
private void uitvoeringBewegingL(int[] positieMannetje) {
	// controle of mannetje op een doel komt
		if (getVeld(positieMannetje[0], positieMannetje[1]-1) == 3) {
			opDoel = true;}
		else {
			opDoel = false;}
				
		// controle of mannetje een kist meeneemt 
			if (getVeld(positieMannetje[0], positieMannetje[1]-1) == 4 ) {
			if (getVeld(positieMannetje[0], positieMannetje[1]- 2) == 3 ) {
			this.setVeld(positieMannetje[0], positieMannetje[1] - 2, 4); // verschuiven kist
			kistOpDoel = true;
			this.setAantalKistOpDoel(aantalKistOpDoel + 1);
			}
			else {
			this.setVeld(positieMannetje[0], positieMannetje[1] -2, 4); // verschuiven kist
			}
		}
				
		this.setVeld(positieMannetje[0], positieMannetje[1] - 1, 5); // verander nieuw veld naar mannetje
		this.setPositieMannetje(positieMannetje[0], positieMannetje[1] - 1 ); // verander positiemannetje attribuut	
}

/**
 * UC4 hulpmethode voor verplaatsmannetje
 * uitvoering van de verplaatsing van het mannetje
 * @param positieMannetje
 * @author Groep89
 */
private void uitvoeringBewegingB(int[] positieMannetje) {// controle of mannetje op een doel komt
	if (getVeld(positieMannetje[0]-1, positieMannetje[1]) == 3) {
		opDoel = true;}
	else {
		opDoel = false;}
	
	// controle of mannetje een kist meeneemt 
	if (getVeld(positieMannetje[0] -1 , positieMannetje[1]) == 4 ) {
		if (getVeld(positieMannetje[0] -2, positieMannetje[1]) == 3 ) {
		this.setVeld(positieMannetje[0] -2, positieMannetje[1], 4); // verschuiven kist
		kistOpDoel = true;
		this.setAantalKistOpDoel(aantalKistOpDoel + 1);
		}
		else {
		this.setVeld(positieMannetje[0] - 2, positieMannetje[1], 4); // verschuiven kist
		}
	}
				
	this.setVeld(positieMannetje[0] -1 , positieMannetje[1] , 5); // verander nieuw veld naar mannetje
	this.setPositieMannetje(positieMannetje[0] - 1, positieMannetje[1]); // verander positiemannetje attribuut	
}

/**
 * UC4 hulpmethode voor verplaatsmannetje
 * uitvoering van de verplaatsing van het mannetje
 * @param positieMannetje
 * @author Groep89
 */
private void uitvoeringBewegingO(int[] positieMannetje) {// controle of mannetje op een doel komt
	if (getVeld(positieMannetje[0] + 1, positieMannetje[1]) == 3) {
		opDoel = true;}
	else {
		opDoel = false;}
				
	// controle of mannetje een kist meeneemt 
	if (getVeld(positieMannetje[0] + 1, positieMannetje[1]) == 4 ) {
		if (getVeld(positieMannetje[0] + 2, positieMannetje[1]) == 3 ) {
		this.setVeld(positieMannetje[0] + 2, positieMannetje[1], 4); // verschuiven kist
		kistOpDoel = true;
		this.setAantalKistOpDoel(aantalKistOpDoel + 1);
		}
		else {
		this.setVeld(positieMannetje[0] + 2, positieMannetje[1], 4); // verschuiven kist
		}
	}
				
	this.setVeld(positieMannetje[0] + 1, positieMannetje[1] , 5); // verander nieuw veld naar mannetje
	this.setPositieMannetje(positieMannetje[0] + 1, positieMannetje[1]); // verander positiemannetje attribuut	
}



/**
 * UC3 SD geefSpelbord knowing 
 * Er wordt een String gemaakt om het spelbord te tonen voor de CIU. 
 * Elk veldtype heeft zijn eigen weergave. 
 * Een gewoon veld wordt getoond als spatie, een muur is #, een doel is een 0, 
 * een kist krijgt de weergave * en het mannetje is X. 
 * Velden die buiten de muur vallen en niet speelbaar zijn, krijgt de weergave $. 
 * @author Groep89
 */
@Override
public String toString() { 	
	 String spelbordString = ""; 
		for (int[] array: velden){
	        for (int value : array){
	        	if (value == 1 )
	        	spelbordString += String.format("     ");  
	        	else if (value == 2 )
	            	spelbordString += String.format("  #  "); 
	        	else if (value == 3 )
	            	spelbordString += String.format("  O  "); 
	        	else if (value == 4 )
	            	spelbordString += String.format("  *  "); 
	        	else if (value == 5 )
	                spelbordString += String.format("  X  ");  	
	        	else 
		        	spelbordString += String.format("  $  "); 
	        } 
	        spelbordString += String.format("\n\n");
		}
		return spelbordString; 
	}

/**
 * UC4 getter positieMannetje 
 * @return
 * @author Groep89
 */
public final int[] getPositieMannetje () { 
	return positieMannetje; 
}

/**
 * UC4 setter positieMannetje 
 * @param y
 * @param x
 * @author Groep89
 */
public final void setPositieMannetje(int y, int x) { 
	this.positieMannetje[0] = y;
	this.positieMannetje[1] = x;
}

/**
 * UC3 getter voor levelVanSpel
 * @return
 * @author Groep89
 */
public final int getLevelVanSpel() {
	return levelVanSpel;
}

/**
 * UC3 setter voor levelVanSpel 
 * Methode wordt gebruikt in SD selecteerSpelbord doing
 * @param levelVanSpel
 * @author Groep89
 */
public final void setLevelVanSpel(int levelVanSpel) {
	this.levelVanSpel = levelVanSpel;
}
/**
 * UC6 getter gebruikersnaam 
 * @return
 * @author Groep89
 */
public String getGebruikersnaam() {
	return gebruikersnaam;
}

/**
 * UC6 setter gebruikersnaam 
 * @param gebruikersnaam
 * @author Groep89
 */
public void setGebruikersnaam(String gebruikersnaam) {
	this.gebruikersnaam = gebruikersnaam;
}

}
