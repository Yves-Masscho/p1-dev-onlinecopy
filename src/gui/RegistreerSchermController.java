package gui;

import java.io.IOException;
import domein.DomeinController;
import exceptions.VerplichtVeldException;
import exceptions.WachtwoordNietCorrect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Taal;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class RegistreerSchermController extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblNaam;
	@FXML
	private Label lblVoornaam;
	@FXML
	private TextField txfNaam;
	@FXML
	private Button btnRegistreer;
	@FXML
	private TextField txfVoornaam;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblPasswoord;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private PasswordField pwfPasswoord;
	@FXML
	private Label lblException;
	@FXML 
	private Label lblVerplicht; 
	
	private DomeinController dc;
	private Taal taal;
	
	public RegistreerSchermController(DomeinController dc) {
		
		this.dc = dc;
		this.taal = dc.getTaal();
		
		try {
		FXMLLoader fx3 = new FXMLLoader(getClass().getResource("RegistreerScherm.fxml"));
		fx3.setController(this);
		fx3.setRoot(this);
		fx3.load();
		initGUI();
		
		
		} 
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}

		
	}
	
	public void initGUI() {
		btnRegistreer.setText(taal.vertaal("M36"));
		lblNaam.setText(taal.vertaal("M7"));
		lblVoornaam.setText(taal.vertaal("M8"));
		lblGebruikersnaam.setText(taal.vertaal("M18"));
		lblPasswoord.setText(taal.vertaal("M19"));
		lblVerplicht.setText(taal.vertaal("M56"));
		
		btnRegistreer.setOnAction(arg0 -> {
			try {
				btnRegistreerActionEvent(arg0);
			}
			catch (WachtwoordNietCorrect wnc) { 			
				lblException.setText(taal.vertaal("M6"));
			} 
    		catch (IllegalArgumentException e){
    			lblException.setText(taal.vertaal("M5"));
    		} catch (VerplichtVeldException vve) {
				lblException.setText(taal.vertaal("M65"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
    		}
		});
	}
	
	@FXML
	public void btnRegistreerActionEvent(ActionEvent event) throws Exception {
	 
			String gebruikersnaamString = txfGebruikersnaam.getText();			
			if (gebruikersnaamString == null || gebruikersnaamString.isEmpty()) {
				throw new VerplichtVeldException("M65");
			}			
			String wachtwoordString = pwfPasswoord.getText();
			if (gebruikersnaamString == null || gebruikersnaamString.isEmpty()) {
				throw new VerplichtVeldException("M65");
			}
			String naamString = txfNaam.getText();
			String voornaamString = txfVoornaam.getText();
			dc.registreer(gebruikersnaamString, wachtwoordString, naamString, voornaamString);	
			
			MenuSchermController msc = new MenuSchermController(dc);
			Scene scene = new Scene(msc, 600, 400);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			
			
	}

}
