package de.michaprogs.crm.article.add;

import de.michaprogs.crm.CreateDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadArticleAdd {

	private AnchorPane root;
	private ControllerArticleAdd controller;
	private Stage stage = new Stage();
	
	public LoadArticleAdd(boolean createDialog){
		
		try{
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewArticleAdd.fxml"));
			root = loader.load();
			controller = loader.getController();
			
			if(createDialog){
				controller.setStage(stage);
				new CreateDialog("", stage, new Scene(root));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public AnchorPane getContent() {
		return root;
	}

	public ControllerArticleAdd getController() {
		return controller;
	}
	
}
