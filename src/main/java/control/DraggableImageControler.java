package control;

import java.util.Observable;

import model.ImageLocalisee;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DraggableImageControler extends Observable{

	/**
	 * ImageView de l'image draggable
	 */
    @FXML
    private ImageView imageView;

    /**
     * Methode déclenchée lorsqu'un drag est détecté
     * @param event
     */
    @FXML
    void dragDetected(MouseEvent event) 
    {
    	Dragboard db = ((ImageView)event.getSource()).startDragAndDrop(TransferMode.COPY);
    	
    	//Créer le contenu disponible dans le dragboard
    	ClipboardContent content = new ClipboardContent();
    	ImageLocalisee imgLocalisee  = (ImageLocalisee)imageView.getImage();
    	content.putImage(imgLocalisee);
    	content.putString("imageMot");
    	content.putUrl(imgLocalisee.getURL());
    	
    	//Ajoute le contenu au dragboard
    	db.setContent(content);
    	
    	//Consummer l'événement
    	event.consume();
    }

    /**
     * Methode déclenchée lorsqu'un drag est terminé
     * @param event
     */
    @FXML
    void dragDone(DragEvent event) {
    	//if (event.getDragboard().getTransferModes().contains(TransferMode.MOVE))
    	if(event.getTransferMode() == TransferMode.MOVE)
    	{
    		((ImageView)event.getSource()).setImage(null);
    	}
    }

    /**
     * Methode déclenchée lorsqu'un drag est relaché
     * @param event
     */
    @FXML
    void dragDropped(DragEvent event) 
    {
    	System.out.println("TESTESET");
    	Dragboard db = event.getDragboard();
    	boolean success = false;
    	if (db.hasUrl())
    	{
	    	ImageView target = (ImageView)event.getTarget();
	    	target.setImage(new ImageLocalisee(db.getUrl()));
	    	success = true;
    	}
    	event.setDropCompleted(success);
    	String formattedURL = db.getUrl().substring(5);//To remove the "file:" substing at the beginning of the Url
    	setChanged();
    	notifyObservers(formattedURL);
    	System.out.println("IMAGE STRING = " +db.getUrl());
    }

    /**
     * Methode déclenchée lorsqu'un événement de drag entre dans la zone
     * @param event
     */
    @FXML
    void dragEntered(DragEvent event) {
    	Dragboard db = event.getDragboard();
    	if (db.hasUrl())
    	{
    		((ImageView)event.getTarget()).setEffect(new Glow(0.8));
    	}
    }

    /**
     * Methode déclenchée lorsqu'un événement de drag sort de la zone
     * @param event
     */
    @FXML
    void dragExited(DragEvent event) {
    	Dragboard db = event.getDragboard();
    	if (db.hasUrl())
    	{
    		((ImageView)event.getTarget()).setEffect(null);
    	}
    }

    /**
     * Methode déclenchée lorsqu'un événement de drag superpose la zone
     * @param event
     */
    @FXML
    void dragOver(DragEvent event) {
    	ImageView target = (ImageView)event.getTarget();
    	
    	if (event.getGestureSource() != target && event.getDragboard().hasUrl())
    	{
    		event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
    	}
    }
}
