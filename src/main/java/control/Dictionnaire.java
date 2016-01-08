package control;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.event.Event;
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
	
	/**
	 * Gestionnaire d'événements utilisé pour gérer la fermeture du stage 
	 * principale
	 */
	EventHandler<WindowEvent> mainWindowEventHandler;
	
	public static void main(String[] args) {
		
		Application.launch(args);		
	}
	
	public void start(Stage pStage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../dictionnaire.fxml"));
		HBox root = (HBox) loader.load();
		FXMLController mainController = (FXMLController)loader.getController();
		mainController.setAppStartController(this);
		
		Scene scene = new Scene(root);
		pStage.setScene(scene);
		pStage.setTitle("Dictionnaire");
		pStage.show();
		
		addMainControllerWindowEventHandler(pStage, mainController);
	}

	/**
	 * Ajoute un gestionnaire de fenêtre au stage principal pour gérer sa 
	 * fermeture
	 * @param stage
	 * @param mainController
	 */
	private void addMainControllerWindowEventHandler(Stage stage, FXMLController mainController)
	{
		mainWindowEventHandler = new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent event)
			{
				event.consume();
				mainController.quitter();	
			}	
		};
		stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, mainWindowEventHandler);
	}
	

}
