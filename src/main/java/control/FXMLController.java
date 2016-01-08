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
	
	GestionDictionnaire model;
		
	@FXML
	private CheckBox checkBoxModifAvant;
	
	@FXML
    private CheckBox checkBoxModifApres;
	
	@FXML
    private CheckBox checkBoxImage;
	
	@FXML
    private TextField motInput;

    @FXML
    private RadioButton radioMotExact;

    @FXML
    private ToggleGroup mot;

    @FXML
    private RadioButton radioMotPartiel;

    @FXML
    private CheckBox checkBoxSaisieAvant;

    @FXML
    private DatePicker datePickerSaisieAvant;

    @FXML
    private CheckBox checkBoxSaisieApres;

    @FXML
    private DatePicker datePickerSaisieApres;

    @FXML
    private DatePicker datePickerModifAvant;

    @FXML
    private DatePicker datePickerModifApres;

    @FXML
    private Button rechercheButton;

    @FXML
    private Button ajouterButton;
    
    @FXML
    private Button quitterButton;

    @FXML
    private ImageView imgMot;

    @FXML
    private ListView<String> listViewResultats;

    @FXML
    private Button consulterButton;

    @FXML
    private Button supprimerButton;
    
    /**
     * Methode qui gere la recherche lorsque le bouton recherche est cliquer
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
    			
    			ButtonType edit = new ButtonType("Consulter");
    			ButtonType ok = new ButtonType("Ok");
        		        		
        		alert.getButtonTypes().setAll(edit, ok);
        		Optional <ButtonType> result = alert.showAndWait();
        		
        		if(result.get() ==  edit){
        		
        			consulterMot();
        		}
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

    @FXML
    void enableDPModifApres(ActionEvent event) {
    	
    	if (datePickerModifApres.isDisabled()){
			
			datePickerModifApres.setDisable(false);
		}
    	else{
    		
    		datePickerModifApres.setDisable(true);
    	}
    }

    @FXML
    void enableDPModifAvant(ActionEvent event) {

    	if (datePickerModifAvant.isDisabled()){
			
			datePickerModifAvant.setDisable(false);
		}
    	else{
    		
    		datePickerModifAvant.setDisable(true);
    	}
    }

    @FXML
    void enableDPSaisieApres(ActionEvent event) {
    	
    	if (datePickerSaisieApres.isDisabled()){
			
			datePickerSaisieApres.setDisable(false);
		}
    	else{
    		
    		datePickerSaisieApres.setDisable(true);
    	}
    }

    @FXML
    void enableDPSaisieAvant(ActionEvent event) {
    	
    	if (datePickerSaisieAvant.isDisabled()){
			
			datePickerSaisieAvant.setDisable(false);
		}
    	else{
    		
    		datePickerSaisieAvant.setDisable(true);
    	}
    }

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
    
    @FXML
    void quitter(ActionEvent event) {
    	
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
    
    @FXML
    void supprimerMot(ActionEvent event) {
    	
    	System.out.println(listViewResultats.getSelectionModel().getSelectedItem());
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
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stageConsultation;	
	}

}

