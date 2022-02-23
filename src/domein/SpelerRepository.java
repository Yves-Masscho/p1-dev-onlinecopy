package domein;
import exceptions.GebruikersnaamNietCorrect;
import exceptions.WachtwoordNietCorrect;
import persistentie.SpelerMapper; 

public class SpelerRepository
{
	private final SpelerMapper mapper;
	
	/**
	 * UC1 constructor 
	 * Via de persistentie wordt de mapper aangemaakt 
	 * @author Groep89
	 */
	public SpelerRepository() {
		mapper = new SpelerMapper(); 
	}
	
	/**
	 * UC1 SD meldAan doing 
	 * In de mapper wordt eerst gecontroleerd of de gebruikersnaam bestaat.
	 * Er wordt een exceptie getoond wanneer de gebruikersnaam niet gekend is. 
	 * Nadat de gebruikersnaam is gekend, wordt een controle op het wachtwoord uitgevoerd.
	 * Ook hier wordt er een exceptie geworpen wanneer het wachtwoord foutief is. 
	 * Indien de ingevoerd gegevens correct zijn, wordt de speler getoond. 
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @return
	 * @throws Exception
	 * @author Groep89
	 */
	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) throws Exception
	{
		Speler s = null;
        
        	s = mapper.geefSpeler(gebruikersnaam);
        	if(s == null) {
        		throw new GebruikersnaamNietCorrect("M10");
        	}
        
        	if(s.getWachtwoord().equals(wachtwoord)) {
        		return s;
        	} else {
        		throw new WachtwoordNietCorrect("M11");
        	}
	}
	
	/**
	 * UC2 SD registreerSpeler doing 
	 * De mapper voegt de speler toe aan de databank 
	 * @param r
	 * @author Groep89
	 */
	public void voegToe(Speler r) { 
		mapper.voegToe(r);	
	}
	
	/**
	 * UC2 SD registreerSpeler doing 
	 * Er wordt gecontrolleerd of de gebruikersnaam al in de databank gekend is. 
	 * Indien de gebruikersnaam gekend is, wordt er een exceptie getoond. 
	 * @param gebruikersnaam
	 * @throws WachtwoordNietCorrect
	 * @author Groep89
	 */
	public void controleerGebruikersnaam(String gebruikersnaam) throws WachtwoordNietCorrect {
	
		if (mapper.geefSpeler(gebruikersnaam)!=null) {
            throw new IllegalArgumentException(); // speler bestaat reeds
       }
	}
	
	

}
