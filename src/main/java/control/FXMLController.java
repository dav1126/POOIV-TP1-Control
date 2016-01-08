package control;

import javafx.fxml.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert; 
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.FabriqueDictionnaire;
import model.GestionDictionnaire;
import model.Mot;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import control.FXMLController_2;

public class FXMLController implements Initializable, Observer{
	
	/**
	 * Objet GestionDictionnaire servant à accéder aux méthodes du modèle
	 */
	GestionDictionnaire model;
	
	/**
	 * Pointeur vers le controleur de démarrage du programme
	 */
	Dictionnaire appStartController;
	
	/**
	 * CheckBox pour activer la recherche selon modification avant date
	 */
	@FXML
	private CheckBox checkBoxModifAvant;
	
	/**
	 * CheckBox pour activer la recherche selon modification apres date
	 */
	@FXML
    private CheckBox checkBoxModifApres;
	
	/**
	 * CheckBox pour activer la recherche selon la présence d'image
	 */
	@FXML
    private CheckBox checkBoxImage;
	
	/**
	 * Champ de recherche
	 */
	@FXML
    private TextField motInput;

	/**
	 * Radio button pour la recherche par mot exact
	 */
    @FXML
    private RadioButton radioMotExact;

    /**
     * Group de bouttons radio (mot exact, mot partiel)
     */
    @FXML
    private ToggleGroup mot;

    /**
	 * Radio button pour la recherche par mot partiel
	 */
    @FXML
    private RadioButton radioMotPartiel;

    /**
	 * CheckBox pour activer la recherche la saisie avant telle date
	 */
    @FXML
    private CheckBox checkBoxSaisieAvant;

    /**
     * Control de choix de date
     */
    @FXML
    private DatePicker datePickerSaisieAvant;

    /**
	 * CheckBox pour activer la recherche la saisie apres telle date
	 */
    @FXML
    private CheckBox checkBoxSaisieApres;

    /**
     * Control de choix de date
     */
    @FXML
    private DatePicker datePickerSaisieApres;

    /**
     * Control de choix de date
     */
    @FXML
    private DatePicker datePickerModifAvant;

    /**
     * Control de choix de date
     */
    @FXML
    private DatePicker datePickerModifApres;

    /**
     * Boutton qui active la recherche de mots
     */
    @FXML
    private Button rechercheButton;

    /**
     * Boutton pour ajouter un mot
     */
    @FXML
    private Button ajouterButton;

    /**
     * Liste servant à afficher et a sélectionner les résultats de la recherche
     */
    @FXML
    private ListView<String> listViewResultats;

    /**
     * Boutton pour consulter un mot
     */
    @FXML
    private Button consulterButton;

    /**
     * Boutton pour supprimer un mot
     */
    @FXML
    private Button supprimerButton;
    
    /**
     * Methode qui gere la recherche lorsque le bouton recherche est cliqué
     * @param event
     */
    @FXML
    void activerRecherche(ActionEvent event) {
 
    	if(radioMotExact.isSelected()){
    		
    		if(!motInput.getText().isEmpty() && motInput.getText().length() > 2){
    			
    			model.rechMotExact(motInput.getText());
    			
    			if (checkBoxImage.isSelected())
    			{
    				model.rechSelonImage();
    			}
    			
    			if (checkBoxSaisieApres.isSelected() && datePickerSaisieApres.getValue() != null)
    			{
    				model.rechApresDateSaisie(datePickerSaisieApres.getValue());
    			}
    			
    			if (checkBoxSaisieAvant.isSelected() && datePickerSaisieAvant.getValue() != null)
    			{
    				model.rechAvantDateSaisie(datePickerSaisieAvant.getValue());
    			}
    			
    			if (checkBoxModifApres.isSelected() && datePickerModifApres.getValue() != null)
    			{
    				model.rechApresDateModif(datePickerModifApres.getValue());
    			}
    			
    			if (checkBoxModifAvant.isSelected() && datePickerModifAvant.getValue() != null)
    			{
    				model.rechAvantDateModif(datePickerModifAvant.getValue());
    			}
    			
    			if(model.listeResultats.isEmpty()){
    				
    				Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Dictionnaire");
        			alert.setContentText("Mot introuvable.");
        			alert.setHeaderText(null);
        			alert.showAndWait();
    			}
    			
    			model.notifyObs(model.listeResultats);
    	
    			
    			for(Mot m: model.listeResultats){
    				
    				System.out.println(m.getLibelle());
    			}
    			
    		}
    		
    		else{
    			
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Dictionnaire");
    			alert.setContentText("L'expression doit contenir minimum 3 lettres.");
    			alert.setHeaderText(null);
    			alert.showAndWait();
    		}
    		
    	}else{
    		
    		if(!motInput.getText().isEmpty() && motInput.getText().length() > 2){
    			
    			model.rechMotPartiel(".*" + motInput.getText() + ".*");
    			
    			if (checkBoxImage.isSelected()){
    				
    				model.rechSelonImage();
    			}
    			
    			if (checkBoxSaisieApres.isSelected() && datePickerSaisieApres.getValue() != null){
    				
    				model.rechApresDateSaisie(datePickerSaisieApres.getValue());
    			}
    			
    			if (checkBoxSaisieAvant.isSelected() && datePickerSaisieAvant.getValue() != null){
    				
    				model.rechAvantDateSaisie(datePickerSaisieAvant.getValue());
    			}
    			
    			if (checkBoxModifApres.isSelected() && datePickerModifApres.getValue() != null){
    				
    				model.rechApresDateModif(datePickerModifApres.getValue());
    			}
    			
    			if (checkBoxModifAvant.isSelected() && datePickerModifAvant.getValue() != null){
    				
    				model.rechAvantDateModif(datePickerModifAvant.getValue());
    			}
    			  			

    			if(model.listeResultats.isEmpty()){
    				
    				Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Dictionnaire");
        			alert.setContentText("Mot introuvable.");
        			alert.setHeaderText(null);
        			alert.showAndWait();
    			}
    			
    			model.notifyObs(model.listeResultats);
    			
    			for(Mot m: model.listeResultats){
    				
    				System.out.println(m.getLibelle());
    			}
    		}else{
    			
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Dictionnaire");
    			alert.setContentText("L'expression doit contenir minimum 3 lettres.");
    			alert.setHeaderText(null);
    			alert.showAndWait();
    		}
    	}
    }

    /**
     * Méthode servant à ajouter un mot au dictionnaire
     * @param event
     */
    @FXML
    void ajouterMot(ActionEvent event) {
    	
    	if(!motInput.getText().isEmpty() && motInput.getText().length() > 2){
    		
    		if(!model.rechMotExiste(motInput.getText())){
    			
    			Mot motAjout = new Mot(motInput.getText(), LocalDate.now());
    			FabriqueDictionnaire.getInstance().getDictionnaire().
    			put(motInput.getText(), motAjout);
    			model.flagModif = true;
    			
    			Alert alert = new Alert(AlertType.CONFIRMATION);
    			alert.setTitle("Dictionnaire");
    			alert.setContentText("Mot ajouté avec succès!");
    			alert.setHeaderText(null);
    			
    			ButtonType ok = new ButtonType("Ok");
        		        		
        		alert.getButtonTypes().setAll(ok);
        		Optional <ButtonType> result = alert.showAndWait();
    		}
    		else{
    			
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Dictionnaire");
    			alert.setContentText("Mot déjà existant.");
    			alert.setHeaderText(null);
    			alert.showAndWait();
    		}
    	}
    	else{
    		
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dictionnaire");
			alert.setContentText("L'expression doit contenir minimum 3 lettres.");
			alert.setHeaderText(null);
			alert.showAndWait();    		
    	}
    }

    /**
     * Methode servant a activer le datePicker lorsque le checkbox recherche
     * après date modif est coché
     * @param event
     */
    @FXML
    void enableDPModifApres(ActionEvent event) {
    	
    	if (datePickerModifApres.isDisabled()){
			
			datePickerModifApres.setDisable(false);
		}
    	else{
    		
    		datePickerModifApres.setDisable(true);
    	}
    }

    /**
     * Methode servant a activer le datePicker lorsque le checkbox recherche
     * avant date modif est coché
     * @param event
     */
    @FXML
    void enableDPModifAvant(ActionEvent event) {

    	if (datePickerModifAvant.isDisabled()){
			
			datePickerModifAvant.setDisable(false);
		}
    	else{
    		
    		datePickerModifAvant.setDisable(true);
    	}
    }

    /**
     * Methode servant a activer le datePicker lorsque le checkbox recherche
     * après date saisie est coché
     * @param event
     */
    @FXML
    void enableDPSaisieApres(ActionEvent event) {
    	
    	if (datePickerSaisieApres.isDisabled()){
			
			datePickerSaisieApres.setDisable(false);
		}
    	else{
    		
    		datePickerSaisieApres.setDisable(true);
    	}
    }
    
    /**
     * Methode servant a activer le datePicker lorsque le checkbox recherche
     * avant date saisie est coché
     * @param event
     */
    @FXML
    void enableDPSaisieAvant(ActionEvent event) {
    	
    	if (datePickerSaisieAvant.isDisabled()){
			
			datePickerSaisieAvant.setDisable(false);
		}
    	else{
    		
    		datePickerSaisieAvant.setDisable(true);
    	}
    }

    /**
     * Methode servant à afficher les détails d'un mot sélectionné dans 
     * une autre fenêtre
     * @param event
     */
    @FXML
    void consulterMot() {    	
    	if(listViewResultats.getSelectionModel().getSelectedItem() != null){
    		if(!model.listeMotsEnConsultation.contains(listViewResultats.getSelectionModel().getSelectedItem()))
    		{
    			Stage stageConsultation = null;
					for(Mot m: model.listeResultats){
						
						if(m.getLibelle().compareTo(listViewResultats.getSelectionModel().getSelectedItem()) == 0){
							model.motSelectionne = m;
							model.ajouterMotEnConsultation(m);
						}
					}
					
					
				stageConsultation = loadConsultation();
				model.listeResultats.clear();
		        model.notifyObs(model.listeResultats);	
				stageConsultation.show();
				
    		}
    		else
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Dictionnaire");
    			alert.setContentText("Ce mot est déjà en consultation");
    			alert.setHeaderText(null);
    			alert.showAndWait();
    		}
	    	
	    }
    	else{
    		
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dictionnaire");
			alert.setContentText("Aucune selection");
			alert.setHeaderText(null);
			alert.showAndWait();
    	}
    }
    
    /**
     * Met fin au programme et demande à l'utilisateur s'il veut sauvegarder.
     */
    protected void quitter() {
    	
    	if(model.flagModif)
    	{
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Sauvegarde du dictionnaire");
    		alert.setContentText("Voulez-vous enregistrer les nouvelles modifications au dictionnaire?");
    		alert.setHeaderText(null);
    		
    		ButtonType yes = new ButtonType("Oui");
    		ButtonType no = new ButtonType("Non");
    		
    		alert.getButtonTypes().setAll(yes, no);
    		Optional <ButtonType> result = alert.showAndWait();
    		
    		if(result.get() ==  yes)
    		{
    			try
    			{
    				model.sauvegardeFichier();
    			}
    			catch (IOException e)
    			{
    				e.printStackTrace();
    				Alert alertSave = new Alert(AlertType.ERROR);
    				alertSave.setTitle("IO Error");
    				alertSave.setContentText("Save File error");
    				alertSave.showAndWait();
    			}
    		}
    	}
    		System.exit(0);
    }
    
    /**
     * Supprime un mot sélectionné du dictionnaire
     * @param event
     */
    @FXML
    void supprimerMot(ActionEvent event) {
    	
    	String msg = model.supprimerMot(listViewResultats.getSelectionModel().getSelectedItem());
    	   	
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Dictionnaire");
		alert.setContentText(msg);
		alert.setHeaderText(null);
		alert.showAndWait();
		
		model.flagModif = true;
    }

    public void initialize(URL location, ResourceBundle resources){
    	  	
    	model = new GestionDictionnaire();
    	model.addObserver(this);
		
    	model.loadDictionnaire();			  	
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		
		ObservableList<String> liste = FXCollections.observableArrayList();
    	
		for(Mot m: (ArrayList<Mot>) arg1){
			
			liste.add(m.getLibelle());
		}
		liste.sort(null);
    	listViewResultats.setItems(liste);
	}
	
	/**
	 * Instancie un controleur secondaire pour la fenetre de consultation du mot
	 * à consulter
	 * @return
	 */
	private Stage loadConsultation()
	{
		Stage stageConsultation = null;
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../dictionnaire_mot.fxml"));
		
		VBox rootConsultation = null;
		
		try
		{
			rootConsultation = (VBox) fxmlLoader.load();
			FXMLController_2 subController = (FXMLController_2)fxmlLoader.getController();
			
			subController.setController(this);
			subController.setMotAConsulter(model.motSelectionne);
			subController.setImage();
			subController.init();
			
			Scene sceneConsultation = new Scene(rootConsultation);
			stageConsultation = new Stage();
			stageConsultation.setScene(sceneConsultation);
			stageConsultation.setTitle("Consultation");
			
			setConsultWindowCloseHandler(stageConsultation, subController);
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stageConsultation;	
	}
	
	/**
	 * Make an event handler to remove the mot from the liste de mot en consultation
	 * when the window is closed
	 * @param stageConsult
	 * @param subController
	 */
	private void setConsultWindowCloseHandler(Stage stageConsult, FXMLController_2 subController)
	{
		//Make an event handler to remove the mot from the liste de mot en consultation
	 	//when the stage is closed
		EventHandler<WindowEvent> windowEventHandlerConsult = new EventHandler<WindowEvent>()
 			  {
 				  @Override
 					public void handle(WindowEvent event)
 					{
 					  	event.consume();
 				    	model.supprimerMotEnConsultation(subController.motAConsulter);
 				    	stageConsult.close();
 					}	
 			  };
		
		stageConsult.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEventHandlerConsult);
	}
	
	/**
	 * Défini le controleur passé en paramètre comme controleur de démarrage
	 * @param appStartController
	 */
	public void setAppStartController(Dictionnaire appStartController)
	{
		this.appStartController = appStartController;
	}

}

