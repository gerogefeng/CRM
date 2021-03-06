package de.michaprogs.crm.articlecategory.add;

import de.michaprogs.crm.DeleteAlert;
import de.michaprogs.crm.Main;
import de.michaprogs.crm.articlecategory.DeleteArticleCategory;
import de.michaprogs.crm.articlecategory.InsertArticleCategory;
import de.michaprogs.crm.articlecategory.ModelArticleCategory;
import de.michaprogs.crm.articlecategory.SelectArticleCategory;
import de.michaprogs.crm.articlecategory.UpdateArticleCategory;
import de.michaprogs.crm.barrelsize.ModelBarrelsize;
import de.michaprogs.crm.barrelsize.UpdateBarrelsize;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class ControllerArticleCategoryAdd {

	@FXML private Label lblSubHeadline;
	
	@FXML private TableView<ModelArticleCategory> tvArticleCategory;
	@FXML private TableColumn<ModelArticleCategory, Integer> tcArticleCategoryID;
	@FXML private TableColumn<ModelArticleCategory, String> tcArticleCategory;
	
	@FXML private TextField tfArticleCategory;
	
	@FXML private Button btnAdd;
	@FXML private Button btnDelete;
	
	private Stage stage;
	private Main main;
	
	public ControllerArticleCategoryAdd(){
		
	}
	
	@FXML private void initialize(){
		
		/* BUTTONS */
		initBtnAdd();
		initBtnDelete();
		
		/* TABLES */
		initTableArticleCategory();
		
		refresh();
		
	}
	
	/*
	 * BUTTONS
	 */
	private void initBtnAdd(){
		
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				if(! tfArticleCategory.getText().isEmpty()){					
					new InsertArticleCategory(new ModelArticleCategory(tfArticleCategory.getText()));
					refresh();
					tfArticleCategory.clear();
				}else{
					System.out.println("Bitte Bezeichnung angeben!");
				}
				
			}
		});
		
	}
	
	private void initBtnDelete(){
		
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				deleteArticleCategory();
			}
		});
		
	}
	
	/*
	 * TABLE
	 */
	private void initTableArticleCategory(){
		
		tcArticleCategoryID.setCellValueFactory(new PropertyValueFactory<>("articleCategoryID"));
		tcArticleCategory.setCellValueFactory(new PropertyValueFactory<>("articleCategory"));
		tcArticleCategory.setCellFactory(TextFieldTableCell.forTableColumn());
		tcArticleCategory.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ModelArticleCategory,String>>() {

			@Override
			public void handle(CellEditEvent<ModelArticleCategory, String> event) {
				((ModelArticleCategory)event.getTableView().getItems().get(
					event.getTablePosition().getRow())).setArticleCategory(event.getNewValue());
				
				new UpdateArticleCategory(new ModelArticleCategory(	tcArticleCategoryID.getCellData(event.getTablePosition().getRow()), 
																	tcArticleCategory.getCellData(event.getTablePosition().getRow())));
								
			}
			
		});

		tvArticleCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelArticleCategory>() {

			@Override
			public void changed(ObservableValue<? extends ModelArticleCategory> observable,
					ModelArticleCategory oldValue, ModelArticleCategory newValue) {
				lblSubHeadline.setText(tcArticleCategory.getCellData(tvArticleCategory.getSelectionModel().getSelectedIndex()));				
			}
			
		});
		
		tvArticleCategory.setContextMenu(new ContextMenuTableCategory());
		
	}
	
	/*
	 * DATABASE METHODS
	 */
	private void refresh(){
		tvArticleCategory.setItems(new SelectArticleCategory(new ModelArticleCategory()).getModelArticleCategory().getObsListArticleCategories());
	}
	
	private void deleteArticleCategory(){
		
		if(tvArticleCategory.getSelectionModel().getSelectedItems().size() == 1){
			
			DeleteAlert delete = new DeleteAlert();
			if(delete.getDelete()){
				System.out.println(tcArticleCategoryID.getCellData(tvArticleCategory.getSelectionModel().getSelectedIndex()));
				new DeleteArticleCategory(
					new ModelArticleCategory(
						tcArticleCategoryID.getCellData(tvArticleCategory.getSelectionModel().getSelectedIndex()),
						tcArticleCategory.getCellData(tvArticleCategory.getSelectionModel().getFocusedIndex())
					)
				);
			}
			
			refresh();
			
		}else{
			System.out.println("Bitte 1 Zeile markieren!");
		}
		
	}
	
	/*
	 * GETTER & SETTER
	 */
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	public void setMain(Main main){
		this.main = main;
	}
	
	private class ContextMenuTableCategory extends ContextMenu{
		
		private MenuItem itemDelete = new MenuItem("L�schen");
		
		public ContextMenuTableCategory(){
			
			initItemDelete();
			
			this.getItems().add(itemDelete);
			
		}
		
		/* MENU ITEMS */
		private void initItemDelete(){
			
			itemDelete.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					deleteArticleCategory();
				}
			});
			
		}
		
	}
	
}
