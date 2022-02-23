package gui;

import java.io.IOException;
import domein.DomeinController;
import exceptions.GebruikersnaamNietCorrect;
import exceptions.VerplichtVeldException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Taal;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class LoginSchermController extends GridPane {

	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblPasswoord;
	@FXML
	private PasswordField pwfPasswoord;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private Button btnLogin;
	@FXML
	private Label lblException;
	
	private DomeinController dc;
	private Taal taal;
	
	public LoginSchermController(DomeinController dc) {
		
		this.dc = dc;
		this.taal = dc.getTaal();
		
		try {
		FXMLLoader fx2 = new FXMLLoader(getClass().getResource("LoginScherm.fxml"));
		fx2.setController(this);
		fx2.setRoot(this);
		fx2.load();
		initGUI();	
		} 
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void initGUI() {
		btnLogin.setText(taal.vertaal("M35"));
		lblGebruikersnaam.setText(taal.vertaal("M18"));
		lblPasswoord.setText(taal.vertaal("M19"));
		btnLogin.setOnAction(arg0 -> {
			try {
				btnLoginActionEvent(arg0);
			} catch(IllegalArgumentException gsg){
				lblException.setText(taal.vertaal("M26"));
	        }	catch (GebruikersnaamNietCorrect gnc) { 
	        	lblException.setText(taal.vertaal("M10"));
	        } 	catch (VerplichtVeldException vve) {
				lblException.setText(taal.vertaal("M65"));
			} 	catch (Exception e) {
				
			}
		});
	}
	
	@FXML
	public void btnLoginActionEvent(ActionEvent event) throws Exception {
		
			String gebruikersnaamString = txfGebruikersnaam.getText();			
		    if (gebruikersnaamString == null || gebruikersnaamString.isEmpty()) {
		    	throw new VerplichtVeldException("M65");
		    }			
			String wachtwoordString = pwfPasswoord.getText();
		    if (gebruikersnaamString == null || gebruikersnaamString.isEmpty()) {
		    	throw new VerplichtVeldException("M65");
		    }		
			dc.meldAan(gebruikersnaamString, wachtwoordString);				
			if (!dc.geefMenuGUI()) {
			MenuSchermController msc = new MenuSchermController(dc);
			Scene scene = new Scene(msc, 800, 600);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			} else {
			MenuSchermAdminController msac = new MenuSchermAdminController(dc);
			Scene scene = new Scene(msac, 800, 600);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.show();	
			}
	}

}
