package gui;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.Taal;

public class SpelbordSchermController extends GridPane {
	private DomeinController dc;
	private Taal taal;
	private int spelLevel;
	@SuppressWarnings("unused")
	private int spelId;
	
	@FXML
	private GridPane controlGrid;
	@FXML
	private ImageView imgOnder;
	@FXML
	private ImageView imgLinks;
	@FXML
	private ImageView imgBoven;
	@FXML
	private ImageView imgRechts;
	@FXML
	private Button btnReset;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLinks;
	@FXML
	private Button btnRechts;
	@FXML
	private Button btnBoven;
	@FXML
	private Button btnOnder;
	@FXML
	private GridPane windowGrid;
	@FXML
	private GridPane sokobanGrid;
	@FXML
	private ImageView ivwArray[][] = new ImageView[10][10];
	@FXML
	private Label lblSpelnaam;
	@FXML
	private Label lblLevel;
	@FXML
	private Label lblAantalZetten;
	@FXML
	private Label lblException; 

	
	public SpelbordSchermController(DomeinController dc, int spelId) throws SQLException {	
		this.dc = dc;
		this.taal = dc.getTaal();
		this.spelId = spelId;
		this.spelLevel = 1;
		
		try {
		FXMLLoader fx9 = new FXMLLoader(getClass().getResource("SpelbordScherm.fxml"));
		fx9.setController(this);
		fx9.setRoot(this);
		fx9.load();
		initGUI(spelId);
		} 
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void initGUI(int spelId) throws SQLException {	
		//initMuziek();
		dc.kiesSpel(spelId);
		dc.selecteerSpelbord(spelLevel); 
				
		int[] vooruitgang = dc.geefVooruitgang();
		lblLevel.setText(taal.vertaal("M39") + ": " + vooruitgang[0] 
				+". "+taal.vertaal("M40") + ": " + vooruitgang[1]);
		lblAantalZetten.setText(taal.vertaal("M53") + ": " + dc.geefAantalVerplaatsingen());
		btnReset.setText("Reset");		
		
		// BUTTONS 
		
		btnExit.setOnAction(this::btnExitActionEvent);
		btnReset.setOnAction(this::btnResetActionEvent);
		btnBoven.setOnAction(this::btnBovenActionEvent);
		btnOnder.setOnAction(this::btnOnderActionEvent);
		btnLinks.setOnAction(this::btnLinksActionEvent);
		btnRechts.setOnAction(this::btnRechtsActionEvent);	
		
		// NUMPAD CONTROLS
		this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
		    if(key.getCode()==KeyCode.NUMPAD8) {	    	 
		    	bovenKeyActionEvent(key);
			}else if(key.getCode()==KeyCode.NUMPAD6){	    	  
				rechterKeyActionEvent(key);
		    }else if(key.getCode()==KeyCode.NUMPAD2){	    	  
		    	onderKeyActionEvent(key);
		    } else if(key.getCode()==KeyCode.NUMPAD4){
				linkerKeyActionEvent(key);
			}
		});			
		toonSpelbord(); // initieel tonen van spelbord	
	

	}	
	
	private void initMuziek() {
		// muziektest
		AudioClip achtergrondMuziek;
		String musicFile = "src/sounds/ds.mp3";  
		Media sound = new Media(Paths.get(musicFile).toUri().toString());
		achtergrondMuziek = new AudioClip(sound.getSource());
		
		achtergrondMuziek.play();
		
	}
	
	private void winGeluidje() {
		AudioClip winGeluidje;
		String musicFile2 = "src/sounds/win.wav";  
		Media sound2 = new Media(Paths.get(musicFile2).toUri().toString());
		winGeluidje = new AudioClip(sound2.getSource());
		winGeluidje.play();
	}
	

	// KEYBOARD ACTION EVENTS
	
	private void linkerKeyActionEvent(KeyEvent key) {
		dc.verplaatsMannetje(4);
		opvolgActie();
	}	
	private void rechterKeyActionEvent(KeyEvent key) {
		dc.verplaatsMannetje(6);
		opvolgActie();
	}	
	private void bovenKeyActionEvent(KeyEvent key){
		dc.verplaatsMannetje(8);
		opvolgActie();
	}	
	private void onderKeyActionEvent(KeyEvent key){
		dc.verplaatsMannetje(2);
		opvolgActie();
	}
	
	// BUTTONS ACTION EVENTS
	@FXML
	private void btnExitActionEvent(ActionEvent arg0){
		SpelMenuSchermController spelmenu = new SpelMenuSchermController(this.dc);
		Scene scene = new Scene(spelmenu, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private void btnResetActionEvent(ActionEvent arg0){
		dc.resetSpelbord();
		opvolgActie();
	}
	@FXML
	private void btnResetNothingActionEvent(ActionEvent arg0) {
		// Doe niets meer
	}
	@FXML
	private void btnOnderActionEvent(ActionEvent event){ 
		dc.verplaatsMannetje(2);
		opvolgActie();
	}
	@FXML
	private void btnLinksActionEvent(ActionEvent event){ 
		dc.verplaatsMannetje(4);
		opvolgActie();
	}
	@FXML
	private void btnRechtsActionEvent(ActionEvent event){ 
		dc.verplaatsMannetje(6);
		opvolgActie();
	}
	@FXML
	private void btnBovenActionEvent(ActionEvent event) {
		dc.verplaatsMannetje(8);
		opvolgActie();
	}
	
	
	private void opvolgActie() {
		this.toonSpelbord();
		this.controleEinde();
	}
	
	@FXML
	private void toonSpelbord() {	
		lblAantalZetten.setText(taal.vertaal("M53") + ": " + dc.geefAantalVerplaatsingen());
		btnReset.setText("Reset");
		btnReset.setTextFill(Color.BLACK);	
		String[] imgArray = new String[]{"gras.PNG","muur.png",
				"doel.png","kist.png","MannetjeGras.PNG","blanco.PNG"};
		
		int velden[][] = dc.geefSpelbordGUI();
		
		for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {              	
            	InputStream inStream = getClass().getResourceAsStream(imgArray[velden[i][j]-1]);
        		Image img = new Image(inStream);
        		ivwArray[i][j] = new ImageView(img);
        		ivwArray[i][j].setFitHeight(51);
            	ivwArray[i][j].setFitWidth(51);	
            	sokobanGrid.add(ivwArray[i][j], j, i);         
            }
            lblSpelnaam.setTextFill(Color.BLACK);
        }	
	}
	
	private void controleEinde(){	
		// controle einde spelbord en einde spel
		if(dc.isVoltooid()) { // einde spelbord
			int[] vooruitgang = dc.geefVooruitgang();
			lblLevel.setText(taal.vertaal("M39") + ": " + vooruitgang[0] 
					+". "+taal.vertaal("M40") + ": " + vooruitgang[1]);
		dc.voltooiSpelbord(spelLevel); // levelVanSpel +1
		
		winGeluidje();
		
		if(dc.isEindeSpel()) { // einde spel
			btnReset.setText("");
			btnReset.setOnAction(arg0 -> {
				btnResetNothingActionEvent(arg0);
				});
			lblSpelnaam.setText(taal.vertaal("M57"));
			lblSpelnaam.setTextFill(Color.GREEN);				
			} else {
			lblSpelnaam.setText(taal.vertaal("M55"));
			lblSpelnaam.setTextFill(Color.BLUE);
			btnReset.setText(taal.vertaal("M58"));
			btnReset.setTextFill(Color.GREEN);
			spelLevel++;
			dc.selecteerSpelbord(spelLevel); 
			}
		}		
	}
}
