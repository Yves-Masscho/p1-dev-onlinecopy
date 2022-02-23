package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;

import domein.DomeinController;
import exceptions.VerplichtVeldException;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import util.Taal;

public class NieuwSpelbordSchermController extends GridPane
{
	@FXML
	private Button btnVeld;
	@FXML
	private Button btnMuur;
	@FXML
	private Button btnDoel;
	@FXML
	private Button btnKist;
	@FXML
	private Button btnMannetje;
	@FXML
	private Button btnBlanco;
	@FXML
	private Button btnNieuwSpelbord;
	@FXML
	private Button btnCancelSpelbord;
	@FXML
	private Label lblFoutBoodschap;
	@FXML
	private Label lblVoegToe;
	@FXML
	private Button btnNaamSpelbord;
	@FXML
	private ImageView ivwArray[][] = new ImageView[10][10];
	@FXML
	private TextField txfSpelbordNaam;
	@FXML
	private Button btnVolgende; 
	@FXML 
	private Label lblException;
	
	private int veldtype;
	private DomeinController dc;
	private Taal taal;
	private String spelnaam; 
	private int aantal; 
	private int counter;
	
	
	@FXML
	private GridPane sokobanGrid;
	
	public NieuwSpelbordSchermController(DomeinController dc, String spelnaam, int aantal) {
		this.dc = dc;
		this.taal = dc.getTaal();
		this.spelnaam = spelnaam;
		this.aantal = aantal;
		this.counter = 0;
		try {
			FXMLLoader fx10 = new FXMLLoader(getClass().getResource("NieuwSpelbordScherm.fxml"));
			fx10.setController(this);
			fx10.setRoot(this);
			fx10.load();
			initGui();
			
			} 
			catch (IOException ex)
			{
				System.out.println(ex.getMessage());
			}
		
	}
	
	private void setVeldtype(int veldtype) {
		this.veldtype = veldtype;
	}
	
	private int getVeldtype() {
		return veldtype;
	}
	
	public void initGui() {
		
		
		try { // SPEL MAKEN, object bestaat nu
			dc.maakSpel(spelnaam, aantal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// vertalingen
		lblVoegToe.setText(taal.vertaal("M66"));
		btnVeld.setText(taal.vertaal("M68"));
		btnMuur.setText(taal.vertaal("M69"));
		btnDoel.setText(taal.vertaal("M70"));
		btnKist.setText(taal.vertaal("M71"));
		btnMannetje.setText(taal.vertaal("M72"));
		btnBlanco.setText(taal.vertaal("M73"));
		btnNieuwSpelbord.setText(taal.vertaal("M67"));
		btnVolgende.setText(taal.vertaal("M74"));
		btnCancelSpelbord.setText(taal.vertaal("M75"));
		

		// actions
		btnVeld.setOnAction(this::btnVeldActionEvent);
		btnMuur.setOnAction(this::btnMuurActionEvent);
		btnMannetje.setOnAction(this::btnMannetjeActionEvent);
		btnBlanco.setOnAction(this::btnBlancoActionEvent);
		btnDoel.setOnAction(this::btnDoelActionEvent);
		btnKist.setOnAction(this::btnKistActionEvent);
		btnVolgende.setOnAction(arg0 -> {
			try{btnVolgendeActionEvent(arg0);}
			catch (IllegalArgumentException iae) {
				lblFoutBoodschap.setText(taal.vertaal(iae.getMessage()));	
			}
		});
		btnCancelSpelbord.setOnAction(this::btnCancelSpelbordActionEvent);
		btnNieuwSpelbord.setOnAction(arg0 -> {
			try {
				btnNieuwSpelbordActionEvent(arg0);
			} catch (VerplichtVeldException e) {
				lblFoutBoodschap.setText(taal.vertaal("M65"));
			}
		});
	
	}
	
	// Kies de naam en maak het object aan, toont het spelbord
	@FXML
	public void btnNieuwSpelbordActionEvent(ActionEvent event) throws VerplichtVeldException {
		String spelbordNaamString = txfSpelbordNaam.getText();			
		if (spelbordNaamString == null || spelbordNaamString.isEmpty()) {
			throw new VerplichtVeldException("M65");
		}			
		dc.maakSpelbord(spelbordNaamString, counter + 1);
		toonSpelbord();
	}
	
	
	// voegt object toe aan lijst in DC maakt het spelbord leeg
	// indien aantal spelborden en counter evenveel zijn, wordt alles weggeschreven
	@FXML
	public void btnVolgendeActionEvent(ActionEvent event) throws IllegalArgumentException{
		try {
			dc.voegToeAanSpelbordLijst();
			
		counter++;

		if (aantal == counter) {
			try {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Info");
				alert1.setHeaderText("Geduld");
				alert1.setContentText("Je spel en spelbord worden weggeschreven, dit kan even duren");
				alert1.showAndWait();
				dc.registreerSpelEnSpelbord();
			} catch (InterruptedException e) {
				lblFoutBoodschap.setText("Crash");
			}
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Je spel en spelbord zijn opgeslagen in de databank");
			alert1.setHeaderText("Je spel en spelbord zijn opgeslagen in de databank");
			alert1.setContentText("Je spel en spelbord zijn opgeslagen in de databank");
			alert1.showAndWait();
			System.exit(0);
			
		} else {
		txfSpelbordNaam.clear();
		lblFoutBoodschap.setText(taal.vertaal("M76"));
		lblFoutBoodschap.setTextFill(Color.BLUE);
		}
		}
		catch (IllegalArgumentException iae) {
			lblFoutBoodschap.setTextFill(Color.RED);
			throw new IllegalArgumentException (iae.getMessage());
		}
		
	}
	
	// Event Listener on Button[#btnVeld].onAction
	@FXML
	public void btnVeldActionEvent(ActionEvent event) {
		setVeldtype(1);
	}
	// Event Listener on Button[#btnMuur].onAction
	@FXML
	public void btnMuurActionEvent(ActionEvent event) {
		setVeldtype(2);
	}
	// Event Listener on Button[#btnDoel].onAction
	@FXML
	public void btnDoelActionEvent(ActionEvent event) {
		setVeldtype(3);
	}
	// Event Listener on Button[#btnKist].onAction
	@FXML
	public void btnKistActionEvent(ActionEvent event) {
		setVeldtype(4);
	}
	// Event Listener on Button[#btnMannetje].onAction
	@FXML
	public void btnMannetjeActionEvent(ActionEvent event) {
		setVeldtype(5);
	}
	// Event Listener on Button[#btnBlanco].onAction
	@FXML
	public void btnBlancoActionEvent(ActionEvent event) {
		setVeldtype(6);
	}
	
	// Event Listener on Button[#btnCancelSpelbord].onAction
	@FXML
	public void btnCancelSpelbordActionEvent(ActionEvent event) {
		System.exit(0);
	}
	
	private void toonSpelbord() {	
		String[] imgArray = new String[]{"gras.PNG","muur.png",
				"doel.png","kist.png","MannetjeGras.PNG","blanco.PNG"};
		
		int velden[][] = dc.geefSpelbordGUI();
		
		for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {              	
            	InputStream inStream = getClass().getResourceAsStream(imgArray[velden[i][j]-1]);
        		Image img = new Image(inStream);
        		ivwArray[i][j] = new ImageView(img);
        		ivwArray[i][j].setFitHeight(49);
            	ivwArray[i][j].setFitWidth(49);	
            	final int ii = i;
            	final int jj = j;
            	ivwArray[i][j].setOnMouseClicked(e -> {
                    dc.veranderVeld(jj, ii, getVeldtype());
                    if (veldtype == 5) {
                    	dc.maakPositieMannetje(jj, ii);
                    }
                    toonSpelbord();
                });
            	sokobanGrid.add(ivwArray[i][j], j, i);     
            	
            }
        }	
	}
	
}
