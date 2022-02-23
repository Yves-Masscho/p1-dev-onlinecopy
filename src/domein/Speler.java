package domein;

import exceptions.WachtwoordNietCorrect;

public class Speler 
{
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean heeftAdminRechten;
	private String naam;
	private String voornaam;
	
	
	
	/**
	 * UC2 constructor
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @param heeftAdminRechten
	 * @param naam
	 * @param voornaam
	 * @throws WachtwoordNietCorrect
	 * @author Groep89
	 */
	public Speler(String gebruikersnaam, String wachtwoord, boolean heeftAdminRechten, String naam, String voornaam) throws WachtwoordNietCorrect  {
		setNaam(naam);
		setVoornaam(voornaam);
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		this.heeftAdminRechten = heeftAdminRechten;
	}
	
	/**
	 * UC1 setter wachtwoord 
	 * Het wachtwoord wordt gecontroleerd door een hulpmethode.
	 * Indien dit niet voldoet aan de vooropgestelde waarden, 
	 * wordt er een exceptie getoont. 
	 * @param wachtwoord
	 * @throws WachtwoordNietCorrect
	 * @author Groep89
	 */
	public final void setWachtwoord(String wachtwoord) throws WachtwoordNietCorrect  { 
		if(controleerWachtwoord(wachtwoord)) {
			this.wachtwoord = wachtwoord;
		} else {
			throw new WachtwoordNietCorrect(); 
		}
	}
	
	/**
	 * UC1 hulpmethode voor de controle op het wachtwoord
	 * Een wachtwoord moet minstens 8 tekens hebben. 
	 * @param input
	 * @return
	 * @author Groep89
	 */
	public boolean controleerLengte(String input) {
        return input.matches(".{8,}");
	}
	
	/**
	 * UC1 hulpmethode voor de setter van wachtwoord 
	 * Er wordt gecontroleerd of er minstens kleine letter(s), 
	 * hoofdletter(s) en cijfer(s) zijn. 
	 * Daarna wordt de hulpmethode voor de lengte toegepast.
	 * Al deze moeten een true hebben, anders is het wachtwoord niet geldig. 
	 * @param wachtwoord
	 * @return
	 * @author Groep89
	 */
	public boolean controleerWachtwoord(String wachtwoord) {
         if(wachtwoord.matches("(.*)[a-z]+(.*)")) {
        	 if(wachtwoord.matches("(.*)[A-Z]+(.*)")) {
        		 if(wachtwoord.matches("(.*)[0-9]+(.*)")) {
        			 if(controleerLengte(wachtwoord)) { // controleerlengte
        				 return true;
        			 }
        		 }
        	 }
         }
         return false;
	}
	
	/**
	 * UC1 setter gebruikersnaam
	 * @param gebruikersnaam
	 * @author Groep89
	 */
	public final void setGebruikersnaam(String gebruikersnaam)
	{
		if (gebruikersnaam == null || gebruikersnaam.isEmpty())
		{ 
			throw new IllegalArgumentException("M29"); 
		}
		if (gebruikersnaam.length() < 8) 
		{
			throw new IllegalArgumentException("M30");
		}
			
		this.gebruikersnaam = gebruikersnaam;
	}
	
	/**
	 * UC1 setter adminRechten
	 * @param heeftAdminRechten
	 * @author Groep89
	 */
	public final void setAdminRechten(boolean heeftAdminRechten) 
	{
		this.heeftAdminRechten = heeftAdminRechten;
	}
	
	/**
	 * UC2 setter naam 
	 * Een naam is niet verplicht waardoor er geen extra controles nodig zijn. 
	 * @param naam
	 * @author Groep89
	 */
	public final void setNaam(String naam)
	{
		this.naam = naam;
	}
	/**
	 * UC2 setter voornaam 
	 * Een voornaam is niet verplicht waardoor er geen extra controles nodig zijn. 
	 * @param voornaam
	 * @author Groep89
	 */
	public final void setVoornaam(String voornaam)
	{
		this.voornaam = voornaam;
	}
	
	/**
	 * UC1 getter gebruikersnaam 
	 * @return
	 * @author Groep89
	 */
	public final String getGebruikersnaam() 
	{
		return gebruikersnaam;
	}
	
	/**
	 * UC1 getter wachtwoord 
	 * @return
	 * @author Groep89
	 */
	public final String getWachtwoord() {
		return wachtwoord;
	}
	
	/** 
	 * UC1 getter adminRechten 
	 * @return
	 * @author Groep89
	 */
	public final boolean getAdminRechten() 
	{
		return heeftAdminRechten;
	}

	/**
	 * UC2 getter naam 
	 * @return
	 * @author Groep89
	 */
	public final String getNaam() {
		return naam;
	}
	
	/**
	 * UC2 getter voornaam
	 * @return
	 * @author Groep89
	 */
	public final String getVoornaam() {
		return voornaam;
	}
	
	/**
	 * UC1 String methode voor de weergave van de gebruiksernaam nadat het inloggen gelukt is.
	 */
	public final String toString() { 
		return String.format("Gebruikersnaam: %s%n", gebruikersnaam);
	}



}
