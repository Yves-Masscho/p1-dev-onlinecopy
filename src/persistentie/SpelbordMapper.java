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

public class SpelbordMapper {

	private static final String INSERT_SPELBORD = "INSERT INTO ID222177_g89.Spelbord "
			+ "(spelbordnaam, spelbord_id, spel_id, level_van_spel, mannetje_x, mannetje_y)" 
			+ "VALUES(?, ?, ?, ?, ?, ?)";
	private static final String INSERT_VELD = "INSERT INTO ID222177_g89.Veld (type_id, spelbord_id, startX, startY, VeldNr) "
			+ "VALUES(?, ?, ?, ?, ?)"; 

	
	/** UC4
	 * Spelbord ophalen uit de databank 
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	public Spelbord geefSpelbord(int spel_id, int level_van_spel) throws SQLException {
		List<Integer> spelbordLijst = new ArrayList<>(); 
		Spelbord spelbord = null;
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement
						("SELECT spelbordnaam, mannetje_x, mannetje_y, type_id, "
								+ "level_van_spel FROM ID222177_g89.Veld v join ID222177_g89.Spelbord sb "
								+ "on v.spelbord_id = sb.spelbord_id where spel_id = ? AND level_van_spel = ?;")){ 
				query.setInt(1, spel_id);
				query.setInt(2, level_van_spel);
				String spelbordNaam = "";
				int levelVanSpel = 0;
				int y = 0, x = 0;
			    try (ResultSet rs = query.executeQuery()) {
			    	while (rs.next()) {
		            	int veldtype = rs.getInt("type_id");
		                spelbordLijst.add(veldtype);  
			    		spelbordNaam = rs.getString("spelbordnaam");
			    		x = rs.getInt("mannetje_x");
			    		y = rs.getInt("mannetje_y");
			    		levelVanSpel = rs.getInt("level_van_spel");
			    		
			    		}
			    	spelbord = new Spelbord(spelbordNaam, spelbordLijst, y, x, levelVanSpel);			    	
	        } catch (SQLException ex) {
	            throw new RuntimeException(ex); 
	        }
	        return spelbord;
		 }
	}
	
	/** UC6
	 * Spelbord toevoegen, enkel spelbordnaam, spelbord_id en level_van_spel zijn verplicht in de databank
	 * de overige mogen null zijn 
	 * @param spelbordNaam
	 * @param spelbordId
	 * @param spelId
	 * @param levelVanSpel
	 * @throws SQLException 
	 */
	public void voegSpelbordToe(Spelbord spelbord, Spel spel, int counter)  { 
		try (
				Connection conn = DriverManager.getConnection(Connectie.JDBC_URL); 
				PreparedStatement query = conn.prepareStatement(INSERT_SPELBORD);) { 
			int array[] = spelbord.getPositieMannetje();
			int x = array[1];
			int y = array[0];
			
			query.setString(1, spelbord.getSpelbordNaam());
			query.setInt(2, geefAantalSpelborden() + 1);
			query.setInt(3, spel.getSpelId());
			query.setInt(4, spelbord.getLevelVanSpel());
			query.setInt(5, x);
			query.setInt(6, y);
			
			query.executeUpdate(); 
		} catch (SQLException ex) { 
			throw new RuntimeException(ex); 
		}
	}
	/**
	 * UC6 
	 * voor de opbouw van een spelbord worden velden in de databank toevoegd
	 * eerst worden de kolommen aangevuld 
	 * daarna wordt pas de volgende rij ingevuld
	 * @param spelbord
	 * @throws SQLException
	 * @throws InterruptedException 
	 */
	public void voegVeldenToe(Spelbord spelbord, int counter) throws SQLException, InterruptedException{ 
		int veldnr = 1; 
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL); 
		PreparedStatement query = conn.prepareStatement(INSERT_VELD);) { //typeId, spelbordId, sX, sY, vNr	
			int spelbordID = geefAantalSpelborden();
			for (int rij = 0; rij < 10; rij++) { 
				for (int kol = 0; kol < 10; kol++) {
			query.setInt(1, spelbord.getVeld(rij, kol)); // type
			query.setInt(2, spelbordID); // spelbord_id
			query.setInt(3, rij); // x
			query.setInt(4, kol); // y
			query.setInt(5, veldnr); 
			veldnr++; 
			query.executeUpdate(); 
			// Thread.sleep(250); // loop trager laten gaan, anders crash
			}  
			} 
			
		} catch (SQLException ex) { 
			throw new RuntimeException(ex); 
		}
		
	} 
	
	public int geefAantalSpelborden() {
		int aantal = 0;
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement("SELECT COUNT(*) AS 'count' from ID222177_g89.Spelbord")) { 
			    try (ResultSet rs = query.executeQuery()) {
			    	while (rs.next()) {
			    		aantal = rs.getInt(1); 
			    	}
			    	}
	        } catch (SQLException ex) {
	            throw new RuntimeException(ex);
	        }
		return aantal;		
	}
	
	
	
}
