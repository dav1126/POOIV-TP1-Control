package control;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import model.GestionDictionnaire;
import model.Mot;

public class Dictionnaire extends Application{
	
	Stage stage;
	
	public static void main(String[] args) {
		
		Application.launch(args);
		/*
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);
		
		GestionDictionnaire model = new GestionDictionnaire();
		ArrayList<Mot> resultats = model.rechMotPartiel(".*abais");
		
		resultats = model.rechAvantDateModif(resultats, yesterday);
		
		
		for(Mot mot : resultats){
			
			System.out.println(mot.getLibelle());
		}
		*/
		
		
	}
	
	public void start(Stage pStage) throws Exception{
		
		HBox root = (HBox) FXMLLoader.load(getClass().getResource("../dictionnaire.fxml"));
		Scene scene = new Scene(root);
		pStage.setScene(scene);
		pStage.setTitle("Dictionnaire");
		pStage.show();
	}
}
