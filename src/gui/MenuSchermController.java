package gui;

import java.io.IOException;
import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Taal;

public class MenuSchermController extends GridPane {

	@FXML
	private Label lblSokoban;
	@FXML
	private Button btnSpeelSpel;
	@FXML
	private Button btnSluitSpel;
	@FXML
	private Label lblBericht;
	@FXML
	private Label lblGebruikersnaam;
	private Taal taal;
	private DomeinController dc;
	
	
	
	public MenuSchermController(DomeinController dc) {
		this.dc = dc;
		this.taal = dc.getTaal();
		
		try {
		FXMLLoader fx4 = new FXMLLoader(getClass().getResource("MenuScherm.fxml"));
		fx4.setController(this);
		fx4.setRoot(this);
		fx4.load();
		initGUI();
		
		} 
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		btnSpeelSpel.setOnAction(this::btnSpeelSpelActionEvent);
		btnSluitSpel.setOnAction(this::btnSluitSpelActionEvent);
	}



	private void initGUI() {
		lblGebruikersnaam.setText(dc.geefGebruikersNaam());	
		lblBericht.setText(taal.vertaal("M24"));
		btnSpeelSpel.setText(taal.vertaal("M1"));
		btnSluitSpel.setText(taal.vertaal("M4"));
		}
	
	@FXML
	private void btnSpeelSpelActionEvent(ActionEvent event) {
		//new SpelMenuScherm
		SpelMenuSchermController spelmenu = new SpelMenuSchermController(dc);
		Scene scene = new Scene(spelmenu, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
	}
	@FXML
	private void btnSluitSpelActionEvent(ActionEvent event) { // Afsluiten
		System.exit(0);
	}
}
