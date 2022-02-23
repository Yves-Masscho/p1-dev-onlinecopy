package main;
//dc
import domein.DomeinController;
//gui
import gui.WelkomSchermController;
//JavaFX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StartUpFx extends Application
{
 
    @Override
	public void start(Stage primaryStage) {
    	DomeinController dc = new DomeinController();
		WelkomSchermController root = new WelkomSchermController(dc);
		Scene scene = new Scene(root, 800, 600);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sokoban");	
		primaryStage.show();	
	}
	
	public static void main(String [] args) {
		launch(args);
	}
	

}