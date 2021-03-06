package de.michaprogs.crm.position.add;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.michaprogs.crm.AbortAlert;
import de.michaprogs.crm.GraphicButton;
import de.michaprogs.crm.InitCombos;
import de.michaprogs.crm.Validate;
import de.michaprogs.crm.amountunit.ModelAmountUnit;
import de.michaprogs.crm.amountunit.SelectAmountUnit;
import de.michaprogs.crm.article.ModelArticle;
import de.michaprogs.crm.article.SelectArticle;
import de.michaprogs.crm.article.search.LoadArticleSearch;
import de.michaprogs.crm.article.supplier.ModelArticleSupplier;
import de.michaprogs.crm.components.TextFieldDouble;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerAddPosition {

	/* ARTICLE */
	@FXML private TextField tfArticleID;
	@FXML private TextField tfDescription1;
	@FXML private TextField tfDescription2;
	@FXML private TextField tfBarrelsize;
	@FXML private TextField tfBolting;
	
	@FXML private TextFieldDouble tfAmount;
	@FXML private ComboBox<String> cbAmountUnit;
	@FXML private TextFieldDouble tfEk;
	@FXML private ComboBox<String> cbPriceUnitEk;
	@FXML private TextFieldDouble tfVk;
	@FXML private ComboBox<String> cbPriceUnitVk;
	@FXML private ComboBox<String> cbTax;
	
	/* SUPPLIER */
	@FXML private TableView<ModelArticleSupplier> tvArticleSupplier;
	@FXML private TableColumn<ModelArticleSupplier, Integer> tcSupplierID;
	@FXML private TableColumn<ModelArticleSupplier, String> tcSupplierName1;
	@FXML private TableColumn<ModelArticleSupplier, String> tcSupplierArticleID; //SupplierArticleID could be with chars
	@FXML private TableColumn<ModelArticleSupplier, String> tcSupplierDescription1;
	@FXML private TableColumn<ModelArticleSupplier, String> tcSupplierDescription2;
	@FXML private TableColumn<ModelArticleSupplier, BigDecimal> tcSupplierEk;
	@FXML private TableColumn<ModelArticleSupplier, Integer> tcSupplierPriceUnit;
	@FXML private TableColumn<ModelArticleSupplier, String> tcSupplierAmountUnit;
	
	/* BUTTONS */
	@FXML private Button btnArticleSearch;
	@FXML private Button btnArticleAdd;
	@FXML private Button btnAbort;
	
	private Stage stage;
	private ObservableList<ModelArticle> obsListArticle;
	
	public ControllerAddPosition(){}
	
	@FXML private void initialize(){
		
		/* COMBO BOX */
		cbAmountUnit.setItems(new SelectAmountUnit(new ModelAmountUnit()).getModelAmountUnit().getObsListAmountUnitsComboBox());
		new InitCombos().initComboPriceUnit(cbPriceUnitEk);
		new InitCombos().initComboPriceUnit(cbPriceUnitVk);
		new InitCombos().initComboTax(cbTax);
		
		/* BUTTONS */
		initBtnArticleSearch();
		initBtnArticleAdd();
		initBtnAbort();
		
		/* TABLES */
		initTableArticleSupplier();
		
	}
	
	/*
	 * BUTTONS
	 */
	private void initBtnArticleSearch(){
		
		btnArticleSearch.setGraphic(GraphicButton.graphicButton("icon_search_15_white.png", 15, 15));
		btnArticleSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				LoadArticleSearch articleSearch = new LoadArticleSearch(true);
				if(articleSearch.getController().getSelectedArticleID() != 0){
					
					ModelArticle article = new SelectArticle(new ModelArticle(articleSearch.getController().getSelectedArticleID())).getModelArticle();
					
					tfArticleID.setText(String.valueOf(article.getArticleID()));
					tfDescription1.setText(article.getDescription1());
					tfDescription2.setText(article.getDescription2());
					tfBarrelsize.setText(article.getBarrelsize());
					tfBolting.setText(article.getBolting());
					tfEk.setText(String.valueOf(article.getEk()));
					tfVk.setText(String.valueOf(article.getVk()));
					cbPriceUnitEk.getSelectionModel().select(String.valueOf(article.getPriceUnit()));					
					cbPriceUnitVk.getSelectionModel().select(String.valueOf(article.getPriceUnit()));
					cbAmountUnit.getSelectionModel().select(article.getAmountUnit());
					cbTax.getSelectionModel().select(String.valueOf(article.getTax()));
					
					/* SUPPLIER */
					tvArticleSupplier.setItems(article.getObsListArticleSupplier());
					
					//ACTIVATE TEXTFIELDS
					tfAmount.setDisable(false);
					tfEk.setDisable(false);
					tfVk.setDisable(false);
					
					btnArticleAdd.setDisable(false);
					
				}
				
			}
		});
		
	}
	
	private void initBtnArticleAdd(){
		
		btnArticleAdd.setGraphic(new GraphicButton("select_32.png").getGraphicButton());
		btnArticleAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if(! tfArticleID.getText().equals("")){
					
					/* CALULATE TOTAL PRICE */
					if(tfAmount.getText().isEmpty()){
						tfAmount.setText("0.00");
					}
					if(tfEk.getText().isEmpty()){
						tfEk.setText("0.00");
					}
					if(tfVk.getText().isEmpty()){
						tfVk.setText("0.00");
					}
					
					double amount = Double.parseDouble(tfAmount.getText());
					int priceUnit = Integer.parseInt(cbPriceUnitVk.getSelectionModel().getSelectedItem());
					BigDecimal vk = new BigDecimal(tfVk.getText());					
					BigDecimal total = new BigDecimal(String.valueOf(vk.multiply(new BigDecimal(amount)).divide(new BigDecimal(priceUnit))));
					total = total.setScale(2, RoundingMode.CEILING);
					/*----------------------*/
					
					obsListArticle.add(new ModelArticle(
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfArticleID.getText()),
						tfDescription1.getText(),
						tfDescription2.getText(),
						tfBarrelsize.getText(),
						tfBolting.getText(),
						new Validate().new ValidateCurrency().validateCurrency(tfAmount.getText()),
						cbAmountUnit.getSelectionModel().getSelectedItem(),
						new Validate().new ValidateCurrency().validateCurrency(tfVk.getText()),
						new Validate().new ValidateCurrency().validateCurrency(tfEk.getText()),
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(cbPriceUnitVk.getSelectionModel().getSelectedItem()),
						total,
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(cbTax.getSelectionModel().getSelectedItem()),
						""
					));
					
					if(stage != null){
						stage.close();
					}else{
						resetFields();			
						tfAmount.setDisable(true);
						tfEk.setDisable(true);
						tfVk.setDisable(true);
						btnArticleAdd.setDisable(true);
					}
					
				}
				
			}
		});
		
	}
	
	private void initBtnAbort(){
		
		btnAbort.setGraphic(new GraphicButton("cancel_32.png").getGraphicButton());
		btnAbort.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				AbortAlert abort = new AbortAlert();
				if(abort.getAbort()){
					if(stage != null){
						stage.close();
					}else{
						resetFields();
					}
				}
				
			}
		});
		
	}
	
	/*
	 * TABLES
	 */
	private void initTableArticleSupplier(){
		
		tcSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
		tcSupplierName1.setCellValueFactory(new PropertyValueFactory<>("supplierName1"));
		tcSupplierArticleID.setCellValueFactory(new PropertyValueFactory<>("supplierArticleID"));
		tcSupplierDescription1.setCellValueFactory(new PropertyValueFactory<>("supplierDescription1"));
		tcSupplierDescription2.setCellValueFactory(new PropertyValueFactory<>("supplierDescription2"));
		tcSupplierEk.setCellValueFactory(new PropertyValueFactory<>("supplierEk"));
		tcSupplierPriceUnit.setCellValueFactory(new PropertyValueFactory<>("supplierPriceUnit"));
		tcSupplierAmountUnit.setCellValueFactory(new PropertyValueFactory<>("supplierAmountUnit"));
		
	}
	
	/*
	 * UI METHODS
	 */
	private void resetFields(){
		
		tfArticleID.clear();
		tfDescription1.clear();
		tfDescription2.clear();
		tfBarrelsize.clear();
		tfBolting.clear();
		tfAmount.clear();
		cbAmountUnit.getSelectionModel().selectFirst();
		tfEk.clear();
		cbPriceUnitEk.getSelectionModel().selectFirst();
		tfVk.clear();
		cbPriceUnitVk.getSelectionModel().selectFirst();
		cbTax.getSelectionModel().selectFirst();
		
	}
	
	/*
	 * GETTER & SETTER
	 */
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	public ObservableList<ModelArticle> getObsListArticle(){
		return obsListArticle;
	}
	
	public void setObsListArticle(ObservableList<ModelArticle> obsListArticle){
		this.obsListArticle = obsListArticle;
	}
	
}
