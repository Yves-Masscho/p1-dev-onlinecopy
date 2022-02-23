package gui;

import java.io.IOException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Taal;

public class MenuSchermAdminController extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Button btnSpeelSpel;
	@FXML
	private Button btnMaakNieuwSpel;
	@FXML
	private Button btnWijzigSpel;
	@FXML
	private Button btnSluitAf;
	@FXML
	private Label lblBericht;
	@FXML
	private Label lblGebruikersnaam;
	
	private Taal taal;
	private DomeinController dc;
	
	public MenuSchermAdminController(DomeinController dc) {
		this.dc = dc;
		this.taal = dc.getTaal();
		
		try {
		FXMLLoader fx5 = new FXMLLoader(getClass().getResource("MenuSchermAdmin.fxml"));
		fx5.setController(this);
		fx5.setRoot(this);
		fx5.load();
		initGUI();
		
		} 
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		btnSpeelSpel.setOnAction(this::btnSpeelSpelActionEvent);
		btnMaakNieuwSpel.setOnAction(this::btnMaakNieuwSpelActionEvent);
		btnWijzigSpel.setOnAction(this::btnWijzigSpelActionEvent);
		btnSluitAf.setOnAction(this::btnSluitAfActionEvent);
	}
	
	private void initGUI() {
		lblGebruikersnaam.setText(dc.geefGebruikersNaam());	
		lblBericht.setText(taal.vertaal("M24"));
		btnSpeelSpel.setText(taal.vertaal("M1"));
		btnMaakNieuwSpel.setText(taal.vertaal("M2"));
		btnWijzigSpel.setText(taal.vertaal("M3"));
		btnSluitAf.setText(taal.vertaal("M4"));
	
		}
	
	@FXML
	private void btnSpeelSpelActionEvent(ActionEvent event) {
		SpelMenuSchermController spelmenu = new SpelMenuSchermController(dc);
		Scene scene = new Scene(spelmenu, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private void btnMaakNieuwSpelActionEvent(ActionEvent event) { // Maak nieuw spel
		NieuwSpelSchermController nieuwSpel = new NieuwSpelSchermController(dc); 
		Scene scene = new Scene(nieuwSpel, 800, 600); 
		Stage stage = (Stage) this.getScene().getWindow(); 
		stage.setScene(scene);
		stage.show(); 
	}
	@FXML
	private void btnWijzigSpelActionEvent(ActionEvent event) { // Wijzig een spel
		Alert button3Alert = new Alert(AlertType.INFORMATION);
		button3Alert.setContentText(taal.vertaal("M53"));
		button3Alert.showAndWait();	
	}
	@FXML
	private void btnSluitAfActionEvent(ActionEvent event) { // Afsluiten
		System.exit(0);
	}

}
