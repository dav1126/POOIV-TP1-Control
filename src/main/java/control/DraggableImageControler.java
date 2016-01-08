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

    @FXML
    private ImageView imageView;

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

    @FXML
    void dragDone(DragEvent event) {
    	//if (event.getDragboard().getTransferModes().contains(TransferMode.MOVE))
    	if(event.getTransferMode() == TransferMode.MOVE)
    	{
    		((ImageView)event.getSource()).setImage(null);
    	}
    }

    @FXML
    void dragDropped(DragEvent event) 
    {
    	Dragboard db = event.getDragboard();
    	boolean success = false;
    	if (db.hasImage() || db.hasString())
    	{
	    	ImageView target = (ImageView)event.getTarget();
	    	target.setImage(db.getImage());
	    	success = true;
    	}
    	event.setDropCompleted(success);
    	setChanged();
    	notifyObservers(db.getUrl());
    	System.out.println("IMAGE STRING = " +db.getUrl());
    }

    @FXML
    void dragEntered(DragEvent event) {
    	Dragboard db = event.getDragboard();
    	if (db.hasImage())
    	{
    		((ImageView)event.getTarget()).setEffect(new Glow(0.8));
    	}
    }

    @FXML
    void dragExited(DragEvent event) {
    	Dragboard db = event.getDragboard();
    	if (db.hasImage())
    	{
    		((ImageView)event.getTarget()).setEffect(null);
    	}
    }

    @FXML
    void dragOver(DragEvent event) {
    	ImageView target = (ImageView)event.getTarget();
    	
    	if (event.getGestureSource() != target && event.getDragboard().hasImage())
    	{
    		event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
    	}
    }
}
