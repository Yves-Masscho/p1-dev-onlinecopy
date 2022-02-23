package persistentie;
import domein.Speler;
import exceptions.WachtwoordNietCorrect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpelerMapper {
	private static final String INSERT_SPELER = "INSERT INTO ID222177_g89.Speler (gebruikersnaam, wachtwoord, adminrechten, naam, voornaam )" 
			+ "VALUES ( ?, ?, ?, ?, ?)";

	public void voegToe(Speler r) { 
	//connectie maken met databank	
		try (
				Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER);) { 
			query.setString(1, r.getGebruikersnaam()); 
			query.setString(2, r.getWachtwoord());
			query.setBoolean(3, r.getAdminRechten());
			query.setString(4, r.getNaam());
			query.setString(5, r.getVoornaam());
			query.executeUpdate(); 			
		} catch (SQLException ex) { 
			throw new RuntimeException(ex); 
		}	
	}
		
	public Speler geefSpeler(String gebruikersNaam) throws WachtwoordNietCorrect { 
		Speler speler = null; 
		
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g89.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersNaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                	String gebruikersnaam_ = rs.getString("gebruikersnaam"); // gebruikersnaam met underscore, anders conflict
                    String wachtwoord = rs.getString("wachtwoord");
                    boolean beheerder = rs.getBoolean(3);
                    String naam = rs.getString("naam");
                    String voornaam = rs.getString("voornaam");
                    speler = new Speler(gebruikersnaam_, wachtwoord, beheerder, naam, voornaam);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return speler;
	}	
	// methode maken voor adminrechten boven te halen 
	

}
