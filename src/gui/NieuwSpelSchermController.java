package gui;

import java.io.IOException;
import java.util.InputMismatchException;

import domein.DomeinController;
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

public class NieuwSpelSchermController extends GridPane {
	@FXML
	private Label lblSpelNaam;
	@FXML
	private TextField txfSpelnaam;
	@FXML
	private Label lblAantalBorden;
	@FXML
	private TextField txfAantalBorden;
	@FXML
	private Button btnVoegToe; 
	
	private DomeinController dc; 
	private Taal taal; 
	
	public NieuwSpelSchermController(DomeinController dc) { 
		this.dc = dc;
		this.taal = dc.getTaal(); 
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("NieuwSpelScherm.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		
			initGUI();
			} 
			catch (IOException ex)
			{
				System.out.println(ex.getMessage());
			}
		btnVoegToe.setOnAction(arg0 -> {
			try {
				btnVoegToeActionEvent(arg0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}); 
	}

	private void initGUI() {
		lblSpelNaam.setText(taal.vertaal("M61"));
		lblAantalBorden.setText(taal.vertaal("M62"));
		btnVoegToe.setText(taal.vertaal("M63"));
		
	}
	public void btnVoegToeActionEvent(ActionEvent event) throws Exception { 
		int aantal;
		String spelnaam;
		
		try {spelnaam = txfSpelnaam.getText(); 
		if (spelnaam == null || spelnaam.isEmpty()) {
			throw new VerplichtVeldException("M65");
		}			
		
		String aantalBorden =  txfAantalBorden.getText(); 
		if (aantalBorden == null || aantalBorden.isEmpty()) { 
			throw new VerplichtVeldException("M65"); 
		}
		aantal = Integer.parseInt(aantalBorden); 
		
		dc.maakSpel(spelnaam, aantal);
		
		NieuwSpelbordSchermController nssc = new NieuwSpelbordSchermController(dc, spelnaam, aantal);
		Scene scene = new Scene(nssc, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		} catch (InputMismatchException in) { 
			System.err.print(taal.vertaal(taal.vertaal("M77")));
		} catch (VerplichtVeldException vve) {
			System.err.print(taal.vertaal(vve.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
}
