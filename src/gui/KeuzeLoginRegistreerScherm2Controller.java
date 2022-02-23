package gui;

import java.io.IOException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Taal;

public class KeuzeLoginRegistreerScherm2Controller extends GridPane {
	@FXML
	private Button btnLogin; 
	@FXML 
	private Button btnRegistreer;
	private DomeinController dc;
	private Taal taal;
	
	public KeuzeLoginRegistreerScherm2Controller(DomeinController dc) { 
		this.dc = dc; 
		this.taal = dc.getTaal();
		
		try
		{
		FXMLLoader fx = new FXMLLoader(getClass().getResource("KeuzeLoginRegistreerScherm.fxml"));
		fx.setController(this);
		fx.setRoot(this);
		fx.load();
		initGUI();
		
		
		btnLogin.setOnAction(this::btnLoginActionEvent);
		btnRegistreer.setOnAction(this::btnRegistreerActionEvent);
	
		} catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	private void initGUI() {
		btnLogin.setText(taal.vertaal("M35"));
		btnRegistreer.setText(taal.vertaal("M36"));	
	}

	@FXML 
    private void btnLoginActionEvent(ActionEvent event) // deze actie wordt hierboven gekoppeld aan de id/referentie
    {
		LoginSchermController lsc = new LoginSchermController(dc);
		Scene scene = new Scene(lsc, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
	
    private void btnRegistreerActionEvent(ActionEvent event) // deze actie wordt hierboven gekoppeld aan de id/referentie
    {
		RegistreerSchermController lsc = new RegistreerSchermController(dc);
		Scene scene = new Scene(lsc, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
		
	
}


