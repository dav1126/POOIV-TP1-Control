package control;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import model.FabriqueDictionnaire;
import model.GestionDictionnaire;
import model.ImageLocalisee;
import model.Mot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


/**
 * Classe permettant de consulter un mot et de le modifier, au besoin
 * @author 1494442
 *
 */
public class FXMLController_2 implements Initializable, Observer{

	Mot motAConsulter;
	ImageView imgView;
	Stage test;
	String imageDuMotPath;
	
	FXMLController mainController;
	
	@FXML
	private DraggableImageControler customImgViewController;
	
    @FXML
    private Label libelleLabel;

    @FXML
    private TextField libelleTextField;

    @FXML
    private Label definitionTitleLabel;

    @FXML
    private Label definitionTextLabel;

    @FXML
    private TextArea definitionTextTextField;

    @FXML
    private Label dateSaisieLabel;

    @FXML
    private Label dateModifLabel;

    @FXML
    private HBox buttonsAppliquerAnnulerHBox;

    @FXML
    private Button modificationButton;
    
    @FXML
    private VBox conteneurImage;

    @FXML
    private Button chooseImgButton;

    @FXML
    void annulerModifButton(ActionEvent event) {
    	init();
    	empecherModification();
    }

    @FXML
    void appliquerModifButton(ActionEvent event) {
    	
 
    	if(mainController.model.validerDefinition(definitionTextTextField.getText())){
    	FabriqueDictionnaire.getInstance().getDictionnaire().remove(libelleLabel.getText());
    	libelleLabel.setText(libelleTextField.getText());
    	definitionTextLabel.setText(definitionTextTextField.getText());
    	dateModifLabel.setText(LocalDate.now().toString());
    	
    	Mot nouveauMot = new Mot(libelleLabel.getText(), LocalDate.parse(dateSaisieLabel.getText()));
    	nouveauMot.setDefinition(definitionTextLabel.getText());
    	nouveauMot.setDateModif(LocalDate.now());
    	nouveauMot.setImg("file:" +imageDuMotPath);
    	FabriqueDictionnaire.getInstance().getDictionnaire().put(nouveauMot.getLibelle(), nouveauMot);
    	empecherModification();
    	mainController.model.flagModif = true;
    	}
    	else{
    		
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dictionnaire");
			alert.setContentText("La définition dépasse la limite permise de "+ 
			FabriqueDictionnaire.getInstance().getProperties().getProperty("nbMotsDefinition")
			+ "mots.");
			alert.setHeaderText(null);
			alert.showAndWait();
    	}
    	
    }

    @FXML
    void choisirImage(ActionEvent event) 
    {

    	FileChooser pictureChoice = new FileChooser();
		//Allow only image extension files to be chosen
		List<String> imagesExtensionList = new ArrayList<>();
		imagesExtensionList.add("*.jpg");
		imagesExtensionList.add("*.png");
		imagesExtensionList.add("*.gif");
		imagesExtensionList.add("*.bmp");
		pictureChoice.getExtensionFilters().add(new ExtensionFilter("Images", imagesExtensionList));
		
		pictureChoice.setInitialDirectory(new File(System.getProperty("user.dir")));
		File fichier = pictureChoice.showOpenDialog(new Stage());
		
		if (fichier != null)
		{
			imageDuMotPath = fichier.getPath();
			imgView.setImage(new ImageLocalisee("file:" + imageDuMotPath));
		}	
    }

    @FXML
    void fermerButton(ActionEvent event) {
    	fermerLaFenetre();
    	
    }

    /**
     * Active les controles necessaires a la modification et desactive ceux de la consultation
     * @param event
     */
    @FXML
    void modifierMot(ActionEvent event) {
    	
    	definitionTextLabel.setVisible(false);
    	libelleLabel.setVisible(false);
    	definitionTextTextField.setVisible(true);
    	libelleTextField.setVisible(true);	
    	chooseImgButton.setDisable(false);
    	buttonsAppliquerAnnulerHBox.setVisible(true);
    	modificationButton.setVisible(false);
    }
    
    public void initialize(URL location, ResourceBundle resources){
    	
    	imgView = (ImageView)conteneurImage.getChildren().get(0);
    	imgView.setPreserveRatio(true);
    	imgView.setFitWidth(175);
    	imgView.setFitHeight(175);
    	
    	
    }
    
    /**
     * Methode qui initialise la fenetre avec les informations provenant du motSelectionne
     */
    public void init()
    {
    	libelleLabel.setText(motAConsulter.getLibelle());
    	libelleTextField.setText(motAConsulter.getLibelle());
    	
    	if (motAConsulter.getDefinition() != null)
    	{
	    	definitionTextLabel.setText(motAConsulter.getDefinition());
	    	definitionTextTextField.setText(motAConsulter.getDefinition());
    	}
    	else
    	{
    		definitionTextLabel.setText("Aucune definition");
	    	definitionTextTextField.setText("Aucune definition");
    	}
    	
    	dateSaisieLabel.setText(motAConsulter.getDateSaisie().toString());
    	
    	if (motAConsulter.getDateModif() != null)
    		dateModifLabel.setText(motAConsulter.getDateModif().toString());
    	else
    		dateModifLabel.setText("");	
    	
    	customImgViewController.addObserver(this);
    }
    
    /**
     * Desactive les controle servant à la modification du mot, et active ceux
     * relatif à la consultation
     */
    public void empecherModification()
    {
    	definitionTextLabel.setVisible(true);
    	libelleLabel.setVisible(true);
    	definitionTextTextField.setVisible(false);
    	libelleTextField.setVisible(false);	
    	chooseImgButton.setDisable(true);
    	buttonsAppliquerAnnulerHBox.setVisible(false);
    	modificationButton.setVisible(true);
    }
    
    /**
     * Gère la fermeture d'une fenêtre de consultation
     */
    public void fermerLaFenetre()
    {
    	Stage currentStage = (Stage) libelleTextField.getScene().getWindow();
    	mainController.model.supprimerMotEnConsultation(motAConsulter);
    	currentStage.close();
    }  
    
    public void setController(FXMLController c)
    {
    	mainController = c;
    }
    
    public void setMotAConsulter(Mot mot)
    {
    	motAConsulter = mot;
    }
    
    public void setImage()
    {
		if(motAConsulter.getImg().compareTo(Mot.DEFAULT_URL) != 0){
		    		
		    		imageDuMotPath = motAConsulter.getImg();
		    		System.out.println("OH YEAH!: " +imageDuMotPath);
					imgView.setImage(new ImageLocalisee(imageDuMotPath));			
		    	}
    	else{
    		imageDuMotPath = motAConsulter.getDefaultURL();
    		imgView.setImage(new ImageLocalisee(imageDuMotPath));
    	}
	}
    
    @Override
	public void update(Observable arg0, Object arg1) {
		    	
		imageDuMotPath = (String)arg1;
	}
}
