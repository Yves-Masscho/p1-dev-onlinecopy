package domein;

public class Spel {

	private String spelNaam; 
	private int spelId; 
	private int aantalVoltooid;
	private int aantalSpelborden;
	private Spelbord spelbord; // wordt momenteel niet meer gebruikt, moet dit via Spel?
	private String gebruikersNaam;

	/**
	 * UC3 constructor 
	 * Om een spel aan te maken, is een spelnaam, spelId en het aantal spelborden nodig in de databank. 
	 * Het aantal voltooide spelborden staat standaard op 0. 
	 * @param spelNaam
	 * @param spelId
	 * @param aantalSpelborden
	 * @author Groep89 
	 */
	public Spel(String spelNaam, int spelId, int aantalSpelborden) { 
		setSpelNaam(spelNaam);
		setSpelId(spelId);
		this.setAantalVoltooid(0);
		this.setAantalSpelborden(aantalSpelborden);
	}
	
	
	/**
	 * UC5 constructor
	 * Als een speler met adminrechten is ingelogd, en een spel wilt maken, 
	 * wordt de gebruikersnaam ook bijgehouden.
	 * @param spelNaam
	 * @param spelId
	 * @param aantalSpelborden
	 * @param gebruikersnaam
	 * @author Groep89
	 */
	public Spel(String spelNaam, int spelId, int aantalSpelborden, String gebruikersNaam) { 
		setSpelNaam(spelNaam);
		setSpelId(spelId);
		this.setAantalVoltooid(0);
		this.setAantalSpelborden(aantalSpelborden);
		this.setGebruikersNaam(gebruikersNaam);
	}
	

	/**
	 * UC5 setter voor gebruikersnaam 
	 * @param gebruikersNaam
	 * @author Groep89 
	 */
	private void setGebruikersNaam(String gebruikersNaam) {
		this.gebruikersNaam = gebruikersNaam;
	}
	
	/**
	 * UC3 volgens SD geefVooruitgang knowing 
	 * De DC vraagt via deze methode de vooruitgang.
	 * Via een array wordt op de 1e plaats het aantal voltooide spelborden opgehaald.
	 * de 2de plaats geeft het aantal voltooide spelborden. 
	 * @author Groep89 
	 */
	public int [] geefInfoSpel() {
		int info [] = new int [2] ; 
	    info [0] = this.getAantalVoltooid(); 
		info [1] = this.getAantalSpelborden(); 			
		return info; 	
	}
	
	//GETTERS
	/**
	 * UC3 getter voor spelnaam 
	 * @return spelnaam 
	 * @author Groep89
	 */
	public final String getSpelnaam() {
		return spelNaam;
	}
	
	/** 
	 * UC3 getter voor spelId
	 * @return spelId
	 * @author Groep89
	 */
	public final int getSpelId() {
		return spelId;
	}
	
	/**
	 * UC3 getter voor aantalVoltooide spelborden
	 * @return aantalVoltooid 
	 * @author Groep89
	 */
	public final int getAantalVoltooid() {
		return aantalVoltooid;
	}
	
	/**
	 * UC3 getter voor aantalSpelborden
	 * @return aantalSpelborden
	 * @author Groep89
	 */
	public final int getAantalSpelborden() {
		return aantalSpelborden;
	}
	
	// SETTERS
	/**
	 * UC3 setter voor aantalVoltooide spelborden
	 * @param aantalVoltooid
	 * @author Groep89
	 */
	public final void setAantalVoltooid(int aantalVoltooid) {
		this.aantalVoltooid = aantalVoltooid;
	}

	/**
	 * UC3 setter voor het aantal spelborden 
	 * Indien er geen spelborden zijn, wordt er een exceptie geworpen.
	 * Het aantal spelborden moet altijd groter dan 0 zijn. 
	 * @param aantalSpelborden
	 * @author Groep89
	 */
	public final void setAantalSpelborden(int aantalSpelborden) {
		if (aantalSpelborden < 0) {
			throw new IllegalArgumentException();
		}
		this.aantalSpelborden = aantalSpelborden;
	}
	
	/**
	 * UC3 setter spelId 
	 * Het spelId kan nooit kleiner zijn dan 0. 
	 * De methode geeft een exceptie wanneer dit het geval zou zijn.  
	 * @param spelId
	 * @author Groep89
	 */
	public final void setSpelId (int spelId) {
		if (spelId < 0) { 
			throw new IllegalArgumentException();
		}
		this.spelId = spelId;
	}
	
	/**
	 * UC3 setter spelNaam 
	 * Er wordt gecontroleerd dat de spelnaam niet leeg is. 
	 * Indien dit zo zou zijn, volgt er een exceptie. 
	 * @param spelNaam
	 * @author Groep89
	 */
	public final void setSpelNaam(String spelNaam) {
		if (spelNaam == null || spelNaam.isEmpty())
			throw new IllegalArgumentException("M28");
		this.spelNaam = spelNaam;
	}
	
	
}
