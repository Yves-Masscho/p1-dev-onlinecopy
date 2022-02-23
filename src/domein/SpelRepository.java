package domein;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistentie.SpelMapper;
import persistentie.SpelbordMapper; 

public class SpelRepository {
	private final SpelMapper spelMapper;
	private final SpelbordMapper spelbordMapper;

	/**
	 * UC3 constructor spelMapper 
	 * @author Groep89
	 */
	public SpelRepository()  { 
	spelMapper = new SpelMapper();
	spelbordMapper = new SpelbordMapper();
	}
	
	/**
	 * UC3 SD geefSpelbord knowing
	 * DC vraagt aan SpelRepository geefSpelbord() als String
	 * Het spelbord wordt gevraagd aan de databank en getoond a.d.h.v. spelId en levelVanSpel.
	 * SpelRepository geeft spelbord terug. 
	 * @param spelId
	 * @param levelVanSpel
	 * @throws Exception
	 * @author Groep89
	 */
	public Spelbord geefSpelbord(int spelId, int levelVanSpel)   { 
		Spelbord spelbord = null;
		try {
			spelbord = spelbordMapper.geefSpelbord(spelId, levelVanSpel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return spelbord; 
	}
	
	/**
	 * UC3 SD geefSpellen knowing 
	 * De DC vraagt deze lijst aan SpelRepository.
	 * Via de mapper wordt de lijst van spellen getoond. 
	 * @return
	 * @throws Exception 
	 * @author Groep89
	 */
	public List<String> geefSpellen()  { // roept aan om de lijst te tonen
		List<String> spellen = new ArrayList<>(); 
		try {
			spellen = spelMapper.geefSpellen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return spellen;
    }
	
	/**
	 * UC3 SD kiesSpel doing
	 * De SpelRepository vraagt het spel a.d.h.v. spelId aan Spel. 
	 * De databank toont het gevraagde spel. 
	 * @param spelId
	 * @return
	 * @author Groep89
	 */
	public Spel geefSpel(int spelId)  // kiest en maakt spelobject
	{
		Spel spel = null;
        try {
			spel = spelMapper.getSpel(spelId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        return spel;
	}
	
	/**
	 * UC5 SD maakSpel doing 
	 * In de databank wordt gecontroleerd of het spelId bestaat.
	 * Indien het spel niet bestaat wordt er een exceptie getoond. 
	 * @param spelId
	 * @throws Exception 
	 * @author Groep89
	 */
	 public void controleerSpelNaam(int spelId)   { 
			try {
				if (spelMapper.getSpel(spelId) == null) { 
					throw new IllegalArgumentException(); 
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	 /**
	  * UC5 SD registreerSpel doing 
	  * Er wordt een spel toegevoegd aan de databank.
	  * Ook de gebruikersnaam van de speler die het spel maakt, wordt toegevoegd. 
	  * @param spel
	  * @param speler
	  * @author Groep89
	  */
	public void registreerSpel(Spel spel, Speler speler) { 
		//controlleerSpelnaam
		spelMapper.voegSpelToe(spel, speler);
	}
	
	/**
	 * UC5 hulpmethode voor het aanmaken van een spelId.
	 * Het aantal spellen wordt uit de databank gehaald volgens de lengte van de spellenlijst. 
	 * Dat aantal wordt gebruikt voor het volgende spelId aan te maken. 
	 * @return
	 * @author Groep89
	 */
	public int geefAantalSpellen() { 
		int aantal = 1; 
		try {
			aantal = spelMapper.geefSpellen().size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return aantal;
	}

	/**
	 * UC6 SD voegSpelbordToe doing 
	 * Een spelbord toevoegen aan de databank via de persistentie 
	 * Ook de velden worden toegevoegd om het spelbord toe te voegen. // nog niet 
	 * @param spelbord
	 * @author Groep89
	 */
	public void voegSpelbordToe(Spelbord spelbord, Spel spel, int counter) {
		try {
		spelbordMapper.voegSpelbordToe(spelbord, spel, counter);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	/**
	 * UC6 hulpmethode 
	 * De velden worden aan de hand van spelbord object naar de mapper geschreven
	 * @param spelbord
	 * @throws InterruptedException
	 * @author Groep89 
	 */
	public void voegVeldenToe(Spelbord spelbord, int counter) throws InterruptedException { 
		try { 
			spelbordMapper.voegVeldenToe(spelbord, counter);
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
	}
	
	
	
} 
