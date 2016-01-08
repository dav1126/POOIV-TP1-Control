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
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
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

	/**
	 * Mot en consultation dans ce controleur
	 */
	Mot motAConsulter;
	
	/**
	 * ImageView servant à afficher l'image associée au mot en consultation
	 */
	ImageView imgView;
	
	/**
	 * Stage bidon servant à afficher une fenetre de FileChooser
	 */
	Stage test;
	
	/**
	 * String indiquant le chemin d'accès vers le fichier de l'image
	 * associée au mot
	 */
	String imageDuMotPath;
	
	/**
	 * Gestionnaire d'événement utilisé pour bloquer/permettre les drags
	 */
	EventHandler<DragEvent> dragEventHandler;
	
	/**
	 * Gestionnaire d'événement utilisé pour bloquer/permettre les clics de
	 * souris
	 */
	EventHandler<MouseEvent> mouseEventHandler;
	
	/**
	 * Controleur principal (FXMLController) qui a lancé ce controleur
	 */
	FXMLController mainController;
	
	/**
	 * Controleur de l'imageView contenant l'iamge draggable
	 */
	@FXML
	private DraggableImageControler customImgViewController;
	
	/**
	 * Label servant à afficher le libellé du mot
	 */
    @FXML
    private Label libelleLabel;

    /**
     * TextField servant à modifier le libellé du mot
     */
    @FXML
    private TextField libelleTextField;

    /**
     * Label servant à afficher le mot Definition
     */
    @FXML
    private Label definitionTitleLabel;

    /**
     * Label servant à afficher la définition du mot
     */
    @FXML
    private Label definitionTextLabel;
    
    /**
     * TextArea servant à modifier la definition du mot
     */
    @FXML
    private TextArea definitionTextTextField;

    /**
     * Label servant à afficher la date de saisie
     */
    @FXML
    private Label dateSaisieLabel;

    /**
     * Label servant à afficher la date de modification du mot
     */
    @FXML
    private Label dateModifLabel;

    /**
     * Conteneur renfermant des boutons
     */
    @FXML
    private HBox buttonsAppliquerAnnulerHBox;
    
    /**
     * Boutton servant à modifier le mot
     */
    @FXML
    private Button modificationButton;
    
    /**
     * Conteneur de l'imageView
     */
    @FXML
    private VBox conteneurImage;

    /**
     * Boutton servant choisir une image pour le mot
     */
    @FXML
    private Button chooseImgButton;

    /**
     * Annule les modifications
     * @param event
     */
    @FXML
    void annulerModifButton() {
    	init();
    	empecherModification();
    }

    /**
     * Applique les modifications au mot
     * @param event
     */
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
    	nouveauMot.setImg(imageDuMotPath);
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

    /**
     * Permet à l'utilisateur de choisir une image pour le mot
     * @param event
     */
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

    /**
     * Active les controles necessaires a la modification et desactive ceux 
     * de la consultation
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
    	
    	//Allow mouse and drag events on the draggable image
    	imgView.removeEventFilter(MouseEvent.ANY, mouseEventHandler);
    	imgView.removeEventFilter(DragEvent.ANY, dragEventHandler);
    }
    
    public void initialize(URL location, ResourceBundle resources)
    {
    	
    	imgView = (ImageView)conteneurImage.getChildren().get(0);
    	imgView.setPreserveRatio(true);
    	imgView.setFitWidth(175);
    	imgView.setFitHeight(175);
    	
    	//Make a mouse eventhandler to disable the clicks on the imgView
    	mouseEventHandler = new EventHandler<MouseEvent>()
    	{
			@Override
			public void handle(MouseEvent event)
			{
				event.consume();
			}	
		}
		;
		
		//Make a drag eventhandler to disable the drags on the imgView
		dragEventHandler = new EventHandler<DragEvent>()
		    	{
					@Override
					public void handle(DragEvent event)
					{
						event.consume();
					}	
				}
				;  
    }
    
    /**
     * Methode qui initialise la fenetre avec les informations en fonction du
     * mot en consultation. Défini aussi cet instance en tant qu'observateur et
     * active des gestionnaires d'événements relatifs au drag et au click.
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
    	
    	//Set this instance as an observer of the imgView controller
    	customImgViewController.addObserver(this);
    	
    	//Activate the mouseEventHandler to prevent mouse clicks on the draggable image
		imgView.addEventFilter(MouseEvent.ANY, mouseEventHandler);
		
		//Activate the dragEventHandler to prevent drags on the draggable image
		imgView.addEventFilter(DragEvent.ANY, dragEventHandler);
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
    	
    	//Activate the mouseEventHandler to prevent mouse clicks on the draggable image
    	imgView.addEventFilter(MouseEvent.ANY, mouseEventHandler);
    }
    
    /**
     * Methode permettant d'associer un mainController à ce controleur
     * @param c
     */
    public void setController(FXMLController c)
    {
    	mainController = c;
    }
    
    /**
     * Set le mot passé en paramètre comme mot à consulter
     * @param mot
     */
    public void setMotAConsulter(Mot mot)
    {
    	motAConsulter = mot;
    }
    
    /**
     * Set l'image du mot dans l'iamgeView
     */
    public void setImage()
    {
		if(motAConsulter.getImg().compareTo(Mot.DEFAULT_URL) != 0){
		    		
		    		imageDuMotPath = motAConsulter.getImg();
		    		System.out.println("OH YEAH!: " +imageDuMotPath);
					imgView.setImage(new ImageLocalisee("file:"+imageDuMotPath));			
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
