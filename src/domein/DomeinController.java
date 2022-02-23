package domein;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Taal;

public class DomeinController
{
	private final SpelerRepository spelerRepository; /**UC1*/
	private Speler speler; /**UC1*/
	private Taal taal; /**UC1*/
	private Spel spel; /**UC3*/
	private Spelbord spelbord;  /**UC4*/
	private final SpelRepository spelRepository; /**UC3*/
	private List<Spelbord> spelbordLijst = new ArrayList<>();
	
	// constructor
	
	/**UC1 constructor 
	 * De constructor maakt een nieuwe SpelerRepository.
	 * Daarna wordt een nieuwe spelRepository aangemaakt.
	 * @author Groep89
	 * */
	public DomeinController() { 
		spelerRepository = new SpelerRepository(); 
		spelRepository = new SpelRepository();
	}
	
	// methodes
	
	/** UC1, volgens SD meldAan doing
	 * De speler wordt aanmeld aan de hand van de geefSpeler methode in de spelerRepository 
	 * De gebruikersnaam en het wachtwoord zijn nodig om de speler te kunnen aanmelden.
	 * Indien de gegevens niet correct zijn, wordt er een exceptie geworpen 'speler ongekend'
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @throws Exception
	 * @author Groep89
	 */
	public void meldAan(String gebruikersnaam, String wachtwoord) throws Exception {
		
		try { 
		speler = spelerRepository.geefSpeler(gebruikersnaam, wachtwoord);
		} catch (IllegalArgumentException e)
         {
        	throw new IllegalArgumentException("M26");
        } 
    }
	
	
	/** UC2, volgens SD registreerSpeler doing
	 * De gebruikersnaam wordt gecontroleerd alvorens de registratie kan uitgevoerd worden.
	 * Registreert nieuwe speler met 4 attributen. Indien geen exceptions wordt zowel een
	 * object speler gemaakt als een nieuw item in de databank via SQL-commands in de mapper toegevoegd
	 * Nieuwe spelers krijgen automatisch false bij adminrechten
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @param naam
	 * @param voornaam
	 * @throws Exception
	 * @author Groep89
	 */
	public void registreer(String gebruikersnaam, String wachtwoord, String naam, String voornaam) throws Exception {
		
		spelerRepository.controleerGebruikersnaam(gebruikersnaam);
		this.speler = new Speler(gebruikersnaam, wachtwoord, false, naam, voornaam); // standaard false bij registeren
		spelerRepository.voegToe(speler);	
	}
	
	
	/**UC1, volgens SD geefGebruikersnaam knowing
	* Haalt de gebruikersnaam op van de huidige speler door de getter aan te roepen
	* van de klasse Speler.
	* Er wordt met een vertaling gewerkt om de gebruiker te ontvangen in het spel.
	* @author Groep89 
	*/
	public String geefGebruikersNaam() { 
		String gebruikersnaam = speler.getGebruikersnaam(); 
		String uitvoer = ""; 
		uitvoer += taal.vertaal("M20") + "\n" + taal.vertaal("M23") + gebruikersnaam; 
		
		return String.format(uitvoer);  
	}
	
	/**UC1, volgens SD geefMenu knowing
	 * Geeft menu weer na het inloggen of registreren rekening houdende met de reeds gekozen taal
	 * Getoond: 'Speel spel'
	 * Als de speler adminrechten heeft (via getter) krijgt de speler een uitgebreider menu te zien
	 * Extra getoond: 'Maak nieuw spel' (UC5) + 'Wijzig een spel' (UC7) + 'Sluit af'
	 * @author Groep89
	 */
	public String geefMenu() { /**UC1*/ 
		String menu = "" + taal.vertaal("M24") + "\n"; 
		menu += "1. " + taal.vertaal("M1");
		menu += "\n";
		if (!speler.getAdminRechten()) {
			menu += "2. " + taal.vertaal("M4") + "\n"; 
			
		} else { 
			menu += "2. " + taal.vertaal("M2") + "\n"; 
			menu += "3. "+ taal.vertaal("M3") + "\n";
			menu += "4. "+ taal.vertaal("M4") + "\n"; 
		}
		return menu; 
	}
	
	/**UC1 hulpmethode
	 * Aangepaste geefMenu voor GUI. 
	 * Op basis van adminrechten worden bepaalde labers/buttons verborgen
	 * @author Groep89
	 */
	
	public boolean geefMenuGUI(){
		return speler.getAdminRechten();
	}
	
	/**UC3, volgens SD geefSpellen knowing
	 * Systeem moet lijst tonen van mogelijke spelen
	 * @return lijst van gekende spellen
	 * @throws SQLException 
	 * @author Groep89
	 */
	public String geefSpellen()  { 
		List<String> spellen = new ArrayList<>(); 
		spellen = spelRepository.geefSpellen();
		int lengte = spellen.size();
		String lijst = "";
		for(int i = 0; i < lengte; i++) {
			lijst += i+1 + "." + spellen.get(i) + " ";
		}
		return lijst;	
		}

	/**UC3, volgens SD kiesSpel doing
	* DC vraagt via de spelRepository aan Spel 
	* een Spel aan de hand van een spelId.  
	* @param spelId
	* @author Groep89
	*/
	public void kiesSpel(int spelId)  { 
		this.spel = spelRepository.geefSpel(spelId);	
	}	
		
		
	/**UC3, volgens SD selecteerSpelbord doing
	 * DC vraagt aan de SpelRepository om een spelbord te selecteren.
	 * De spelrepository heeft hiervoor ook het spelId nodig en haalt deze eerst op.
	 * Daarna selecteert de spelrepository het spelbord uit de databank en geeft dit een level
	 * @param levelVanSpel 
	 * @author Groep89 
	 */
	public void selecteerSpelbord(int levelVanSpel)  {
		int spelId = spel.getSpelId();
		spelbord = spelRepository.geefSpelbord(spelId, levelVanSpel);
		spelbord.setLevelVanSpel(levelVanSpel);
	}

	
	/** UC3, volgens SD geefVooruitgang knowing
	 * DC vraagt aan spel de vooruitgang. 
	 * De vooruitgang wordt in spel bekeken door het aantal voltooide spelborden 
	 * en ook het aantal spelborden worden opgehaald.
	 * De vooruitgang keert dan via int terug naar DC 
	 * @return vooruitgang
	 * @author Groep89
	 */
	public int[] geefVooruitgang(){ 
		int [] vooruitgang = new int [2]; 
		vooruitgang = spel.geefInfoSpel();
		return vooruitgang;
	}
	
	
	/**UC3, volgens SD isEindeSpel knowing
	 * De DC vraagt aan spel het aantal voltooide spelborden en aantal spelborden. 
	 * Als het aantal voltooide spellen gelijk is aan het aantal spelborden die het spel bevat
	 * wordt een true waarde getoond en is het spel ten einde. 
	 * @author Groep89
	 */ 
	public boolean isEindeSpel() { 
		if (spel.getAantalVoltooid() == spel.getAantalSpelborden()) {
			return true;
		}
		return false;
	}
	
	/**UC4, volgens SD resetSpelbord doing
	 * De DC vraagt aan de SpelRepository een spelbord aan de hand van spelId en levelVanSpel.
	 * De SpelRepository vraagt dit spelbord op en zet dit terug in originele staat.
	 * @author Groep89
	 */
	public void resetSpelbord() {
		spelbord = spelRepository.geefSpelbord(spel.getSpelId(), spelbord.getLevelVanSpel());
	}
	
	/**UC4, volgens SD verplaatsMannetje doing
	 * In spelbord wordt een controle uitgevoerd voor de richting. 
	 * Het spelbord voert de richting uit die de DC doorgeeft.
	 * @param richting
	 * @author Groep89
	 * */
	public void verplaatsMannetje(int richting) {
		spelbord.verplaatsMannetje(richting);
		}
	
	/**UC4, volgens SD geefAantalVerplaatsingen knowing 
	 * Het spelbord houdt elke verplaatsing bij die wordt uitgevoerd 
	 * en toont deze waarde via een int
	 * @author Groep89
	 * */
	public int geefAantalVerplaatsingen() {
		return spelbord.getAantalVerplaatsingen();
	}
	
	/**UC4, volgens SD isVoltooid knowing
	 * Wanneer alle kisten op een doel staan, is het spel voltooid.
	 * Het spelbord geeft een true of false terug. 
	 * @author Groep89
	 * */
	public boolean isVoltooid() {
		if (spelbord.isVoltooid()) {
		int i = 0;
			spel.setAantalVoltooid(i + 1);
		}
		return spelbord.isVoltooid();
	}
	
	/**UC3, volgens SD geefSpelbord knowing
	 * Via een String wordt het gevraagde spelbord weergegeven. 
	 * De string wordt gevraagd in de klasse Spelbord. 
	 * @author Groep89
	 * */
	public String geefSpelbord() {
		String spelbordString = spelbord.toString();
		return spelbordString;
	}
	
	/**
	 * UC3-4
	 * Extra hulpmethode voor de GUI
	 * Wordt opnieuw gebruikt in UC6
	 * @author Groep89
	 */
	public int[][] geefSpelbordGUI(){
		return spelbord.getVelden();
	}

	/** UC4 hulpmethode 
	 * Via Spel wordt het aantal voltooide spelborden ingesteld door de parameter
	 * @param aantalVoltooid
	 * @author Groep89
	 */
	public void voltooiSpelbord(int aantalVoltooid) {
		spel.setAantalVoltooid(aantalVoltooid);	
	}
	
	/**
	 * UC5, volgens SD maakSpel doing
	 * De SpelRepository controleert de spelnaam a.d.h.v. spelId 
	 * Het spelId haalt de SpelRepository op via de methode geefAantalSpellen.
	 * Na de controle van het spelId wordt dit met 1 verhoogd om een nieuw spelId in te stellen. 
	 * De gebruikersnaam van de speler wordt opgehaald en toegevoegd bij het aanmaken van het spel. 
	 * Om het spel aan te maken, worden de parameters spelNaam, spelId, aantalSpelborden en de gebruikersnaam meegegeven. 
	 * @param spelNaam
	 * @param aantalSpelborden
	 * @author Groep89
	 */
	public void maakSpel(String spelNaam, int aantalSpelborden) throws Exception { 
		int spelId = spelRepository.geefAantalSpellen() + 1;
		//spelRepository.controleerSpelNaam(spelId);	
		String gebruikersnaam = speler.getGebruikersnaam(); 
		this.spel = new Spel(spelNaam, spelId, aantalSpelborden, gebruikersnaam);
	}
	
	/**
	 * UC6, volgens SD maakSpelbord doing
	 * Een nieuw spelbord wordt aangemaakt na het aanmaken van een spel. 
	 * De spelbordnaam, level en de gebruikersnaam van de speler worden ingevoerd. 
	 * @param spelbordNaam
	 * @author Groep89
	 */
	public void maakSpelbord(String spelbordNaam, int levelVanSpel) { 
		this.spelbord = new Spelbord(spelbordNaam, levelVanSpel, speler.getGebruikersnaam());	
	}

	/**
	 * UC6, volgens SD veranderVeld doing
	 * Via de setter van Veld in Spelbord worden de posities ingesteld. 
	 * Ook het veldtype wordt via een int ingevoerd. 
	 * @param positieX
	 * @param positieY
	 * @param veldtype
	 * @author Groep89
	 */
	public void veranderVeld(int positieX, int positieY,  int veldType) { 
		spelbord.setVeld(positieY, positieX, veldType); 
	}
	
	/**
	 * UC6 SD geefSpelbordGui knowing
	 * De DC vraagt aan spelbord de velden voor het spelbord
	 * @return
	 * @author Groep89
	 */
	public int [][] geefVelden() { 
		return spelbord.getVelden(); 
	}
	
	/**
	 * UC6 hulpmethode 
	 * Spelbord toevoegen aan een lijst
	 * @author Groep89
	 */
	public void voegToeAanSpelbordLijst() throws IllegalArgumentException {
		int aantalMannetjes = 0;
		int aantalDoelen = 0;
		int aantalKisten = 0;
		for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
             if (spelbord.getVeld(i, j) == 5) {
            	  aantalMannetjes = aantalMannetjes + 1;}
             if (spelbord.getVeld(i, j) == 3) {
            	  aantalDoelen = aantalDoelen + 1;}
             if (spelbord.getVeld(i,j) == 4) {
            	  aantalKisten = aantalKisten + 1;}
             }
            }
		if (aantalMannetjes != 1) {
			throw new IllegalArgumentException("M78");
		}
		if (aantalDoelen != aantalKisten) {
			throw new IllegalArgumentException("M79");	
		}
		spelbordLijst.add(this.spelbord);
	}
	
	/**
	 * UC6 hulpmethode 
	 * Het spel wordt geregistreerd via de SpelRepository naar de mapper 
	 * Via een lus worden daarna de spelborden ingevoerd 
	 * @throws InterruptedException
	 * @author Groep89 
	 */
	public void registreerSpelEnSpelbord() throws InterruptedException {
		spelRepository.registreerSpel(spel, speler);
		int counter = 0;
		for (Spelbord sb : spelbordLijst) {
			spelRepository.voegSpelbordToe(sb, spel, counter);
			spelRepository.voegVeldenToe(sb, counter);
			counter++;
		}
	}
	
	
	/**
	 * UC6 hulpmethode 
	 * de startpositie van het mannetje wordt uit de databank opgehaald 
	 * @param x
	 * @param y
	 * @author Groep89
	 */
	public void maakPositieMannetje(int x, int y) {
		spelbord.setPositieMannetje(y, x);
	}
	
	/**setter voor taal
	 * Stelt het taalobject in voor de domeincontroller
	 * @param taal
	 * @author Groep89
	 */
	public void setTaal(Taal taal) { 
	 this.taal = taal;	 
	}
	
	/**getter voor taal  
	 * haalt de taal op voor de applicaties en de gui-controllers
	 * @author Groep89
	 * */
	public Taal getTaal() { 
		return taal;
	}
}


