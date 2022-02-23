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

public class WelkomSchermController extends GridPane {
	@FXML
	private Button btnNl; 
	@FXML
	private Button btnFr; 
	@FXML
	private Button btnEn; 
	private DomeinController dc; 
	
	
	public WelkomSchermController(DomeinController dc) { 
		
		this.dc = dc; 
	
		try { 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WelkomScherm.fxml")); 
		loader.setController(this);
		loader.setRoot(this);
		loader.load(); 
		} catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		btnNl.setOnAction(this::btnNlActionEvent);
		btnFr.setOnAction(this::btnFrActionEvent);
		btnEn.setOnAction(this::btnEnActionEvent);
	}
	
	private void btnNlActionEvent(ActionEvent event) // deze actie wordt hierboven gekoppeld aan de id/referentie
    {
		Taal taal = new Taal();
		taal.setBundel(1);
		dc.setTaal(taal);	
		KeuzeLoginRegistreerScherm2Controller krsc = new KeuzeLoginRegistreerScherm2Controller(dc);
		Scene scene = new Scene(krsc, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
    
	@FXML
    private void btnFrActionEvent(ActionEvent event) // optie 2 in JavaFX maar in setBundel is dit optie 3
    {
		Taal taal = new Taal();
		taal.setBundel(3);
		dc.setTaal(taal);	
		KeuzeLoginRegistreerScherm2Controller krsc = new KeuzeLoginRegistreerScherm2Controller(dc);
		Scene scene = new Scene(krsc, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
    
	@FXML
    private void btnEnActionEvent(ActionEvent event) // optie 3 in JavaFX maar in setBundel is dit optie 2
    {
		Taal taal = new Taal();
		taal.setBundel(2);
		dc.setTaal(taal);	
		KeuzeLoginRegistreerScherm2Controller krsc = new KeuzeLoginRegistreerScherm2Controller(dc);
		Scene scene = new Scene(krsc, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }


}
