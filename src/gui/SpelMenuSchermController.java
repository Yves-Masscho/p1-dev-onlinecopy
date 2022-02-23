package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

import domein.DomeinController;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Taal;
import javafx.scene.control.ChoiceBox;

public class SpelMenuSchermController extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private ChoiceBox<String> cbKeuzeSpel;
	@FXML
	private Label lblKeuzeSpel;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnKiesSpel;
	
	private DomeinController dc; 
	private Taal taal; 
	public SpelMenuSchermController(DomeinController dc) { 
		this.dc = dc;
		this.taal = dc.getTaal(); 
		try { 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SpelMenuScherm.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load(); 
			initGUI(); 
			
		} catch(IOException ex) { 
			System.out.println(ex.getMessage()); 
		}
	}

	private void initGUI() {
		lblKeuzeSpel.setText(taal.vertaal("M38"));
		cbKeuzeSpel.getItems().addAll(dc.geefSpellen().split(" ")); //geeft alle items in de lijst 
		cbKeuzeSpel.setValue("");
		btnKiesSpel.setText(taal.vertaal("M1"));
		btnKiesSpel.setOnAction(arg0 -> getChoice(cbKeuzeSpel));
		btnKiesSpel.setOnAction(this::btnKiesSpelAction);	
	}


	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void btnCancelActionEvent(ActionEvent event) {
		System.exit(0);
		
	}
	// Event Listener on Button[#btnKiesSpel].onAction
	@FXML
	public void btnKiesSpelAction(ActionEvent event) {	
		SpelbordSchermController ssc;
		try {
			ssc = new SpelbordSchermController(dc, getChoice(cbKeuzeSpel));
			Scene scene = new Scene(ssc, 800, 600);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// in de methode geefSpellen in de DC worden de spellen getoond met een nr vooraan
	// Door de volgorde in de SQL statement zal de spelId telkens overeenkomen met het getal vooraan de string
	// Deze methode haalt het eerste element uit die string en geeft die door als int
	
	private int getChoice(ChoiceBox<String> cbKeuzeSpel) { 
		String str = cbKeuzeSpel.getValue();
		int spelId = Character.getNumericValue(str.charAt(0));
		return spelId;
	}
}
