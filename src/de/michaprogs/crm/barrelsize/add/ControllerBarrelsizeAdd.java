package de.michaprogs.crm.barrelsize.add;

import de.michaprogs.crm.DeleteAlert;
import de.michaprogs.crm.barrelsize.DeleteBarrelsize;
import de.michaprogs.crm.barrelsize.ModelBarrelsize;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerBarrelsizeAdd {

	//Tables & Columns
	@FXML private TableView<ModelBarrelsize> tvBarrelsize;
	@FXML private TableColumn<ModelBarrelsize, Integer> tcBarrelsizeID;
	@FXML private TableColumn<ModelBarrelsize, String> tcBarrelsize;
	
	@FXML private Label lblSubHeadline;
	
	//TextFields
	@FXML private TextField tfBarrelsize;
	
	//Buttons
	@FXML private Button btnAdd;
	@FXML private Button btnDelete;
	
	private Stage stage;
	
	public ControllerBarrelsizeAdd(){}
	
	@FXML private void initialize(){
		
		/* BUTTONS */
		initBtnAdd();
		initBtnDelete();
		
		/* TABLES */
		initTableBarrelsize();
		
		//Load all barrelsize from Database and show
		refreshTable();
		
	}
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	/*
	 * BUTTONS
	 */
	private void initBtnDelete(){
		
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				deleteBarrelsize();
			}
		});	
	}
	
	private void initBtnAdd(){
		
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				new ModelBarrelsize().insertbarrelsize(tfBarrelsize.getText());
				refreshTable();
				tfBarrelsize.clear();
				
			}
		});
		
	}
	
	/*
	 * TABLES
	 */
	private void initTableBarrelsize(){
		
		tcBarrelsizeID.setCellValueFactory(new PropertyValueFactory<>("barrelsizeID"));
		tcBarrelsize.setCellValueFactory(new PropertyValueFactory<>("barrelsize"));	
		
		tvBarrelsize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelBarrelsize>() {

			@Override
			public void changed(ObservableValue<? extends ModelBarrelsize> observable, ModelBarrelsize oldValue,
					ModelBarrelsize newValue) {
				lblSubHeadline.setText(tcBarrelsize.getCellData(tvBarrelsize.getSelectionModel().getSelectedIndex()));				
			}
			
		});
		
		tvBarrelsize.setContextMenu(new ContextMenuTable());
		
	}
	
	/*
	 * DATABASE METHODS
	 */
	private void deleteBarrelsize(){
		
		if(tvBarrelsize.getSelectionModel().getSelectedItems().size() == 1){
			if(new DeleteAlert().getDelete()){
				new DeleteBarrelsize(tcBarrelsizeID.getCellData(tvBarrelsize.getSelectionModel().getSelectedIndex()));
				refreshTable();
			}
		}else{
			System.out.println("Bitte 1 Zeile markieren!");
		}
		
	}

	private void refreshTable(){		
		ModelBarrelsize barrelsize = new ModelBarrelsize();
		barrelsize.selectBarrelsizes();
		tvBarrelsize.setItems(barrelsize.getObsListBarrelsizes());	
	}
	
	private class ContextMenuTable extends ContextMenu{
		
		private MenuItem itemDelete = new MenuItem("L�schen");
		
		public ContextMenuTable(){
			
			initItemDelete();
			
			this.getItems().add(itemDelete);
			
		}
		
		private void initItemDelete(){
			
			itemDelete.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					deleteBarrelsize();					
				}
			});
			
		}
		
	}
	
}
