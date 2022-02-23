package persistentie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Spel;
import domein.Spelbord;
import domein.Speler; 

public class SpelMapper {
	private static final String INSERT_SPEL = "INSERT INTO ID222177_g89.Spel (spelnaam, spel_id, aantal, gebruikersnaam)" 
			+ "VALUES ( ?, ?, ?, ?)";
	
	
	/**
	 * spel oproepen uit databank 
	 * @return spelnaam
	 */
	public List<String> geefSpellen() throws SQLException {  // de methode om de lijst te tonen als Strings
			List<String> spellen = new ArrayList<>(); 
		
		// databank connectie maken 
		
		 try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
	                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g89.Spel order by spel_id");
	                ResultSet rs = query.executeQuery()) {

	            while (rs.next()) {
	            	String spelNaam = rs.getString("spelNaam");
	                spellen.add(spelNaam);
	            }
	        } catch (SQLException ex) {
	            throw new RuntimeException(ex);
	        }

	        return spellen;
		
	}
	
	public Spel getSpel(int spelId) throws Exception { //kiesSpel ! 
		Spel spel = null; 
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement("SELECT * from ID222177_g89.Spel where spel_id = ? ")) { 
			query.setInt(1, spelId);
			    try (ResultSet rs = query.executeQuery()) {
			    	if (rs.next()) {
			    		String spelNaam = rs.getString("spelNaam");
			    		spelId = rs.getInt("spel_id"); 
			    		int aantalSpelborden = rs.getInt("aantal");
			    		spel = new Spel(spelNaam, spelId, aantalSpelborden);
			    	}
	        } catch (SQLException ex) {
	            throw new RuntimeException(ex);
	        }

	        
		 
		return spel;
		} } 
		
	
/**
 * UC5, eerst Spel maken
 * @param spelNaam
 * @param spelId
 * @throws SQLException
 */
	public void voegSpelToe(Spel spel, Speler speler)  { 
		try ( 
			Connection conn = DriverManager.getConnection(Connectie.JDBC_URL); 
			PreparedStatement query = conn.prepareStatement(INSERT_SPEL);) { 
			query.setString(1, spel.getSpelnaam());
			query.setInt(2, spel.getSpelId());
			query.setInt(3, spel.getAantalSpelborden());
			query.setString(4, speler.getGebruikersnaam());
			query.executeUpdate(); 
		} catch (SQLException ex) { 
			throw new RuntimeException(ex); 
		}
	}
	
	
	
}
