package de.michaprogs.crm.customer.data;

import java.time.LocalDate;

import de.michaprogs.crm.DeleteAlert;
import de.michaprogs.crm.GraphicButton;
import de.michaprogs.crm.InitCombos;
import de.michaprogs.crm.Validate;
import de.michaprogs.crm.customer.ModelCustomer;
import de.michaprogs.crm.customer.add.LoadCustomerAdd;
import de.michaprogs.crm.customer.search.LoadCustomerSearch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerCustomerData {

	@FXML private Label lblSubHeadline;
	
	@FXML private TextField tfCustomerID;
	@FXML private ComboBox<String> cbSalutation;
	@FXML private TextField tfName1;
	@FXML private TextField tfName2;
	@FXML private TextField tfStreet;
	@FXML private ComboBox<String> cbLand;
	@FXML private TextField tfZip;
	@FXML private TextField tfLocation;
	
	@FXML private TextField tfPhone;
	@FXML private TextField tfMobile;
	@FXML private TextField tfFax;
	@FXML private TextField tfEmail;
	@FXML private TextField tfWeb;
	@FXML private TextField tfContact;
	@FXML private TextField tfUstID;
	
	@FXML private ComboBox<String> cbPayment;
	@FXML private TextField tfIBAN;
	@FXML private TextField tfBIC;
	@FXML private TextField tfBank;
	@FXML private TextField tfPaymentSkonto;
	@FXML private TextField tfPaymentNetto;
	@FXML private TextField tfSkonto;
	
	@FXML private TextField tfCustomerIDBilling;
	@FXML private ComboBox<String> cbSalutationBilling;
	@FXML private TextField tfName1Billing;
	@FXML private TextField tfName2Billing;
	@FXML private TextField tfStreetBilling;
	@FXML private ComboBox<String> cbLandBilling;
	@FXML private TextField tfZipBilling;
	@FXML private TextField tfLocationBilling;
	
	@FXML private TextField tfPhoneBilling;
	@FXML private TextField tfMobileBilling;
	@FXML private TextField tfFaxBilling;
	@FXML private TextField tfEmailBilling;
	@FXML private TextField tfWebBilling;
	@FXML private TextField tfContactBilling;
	@FXML private TextField tfUstIDBilling;
	
	@FXML private ComboBox<String> cbPaymentBilling;
	@FXML private TextField tfIBANBilling;
	@FXML private TextField tfBICBilling;
	@FXML private TextField tfBankBilling;
	@FXML private TextField tfPaymentSkontoBilling;
	@FXML private TextField tfSkontoBilling;
	@FXML private TextField tfPaymentNettoBilling;
	
	@FXML private TextArea taNotes;
	@FXML private Label lblLastChange;
	
	@FXML private Button btnSearch;
	@FXML private Button btnNew;
	@FXML private Button btnEdit;
	      private Button btnEditSave = new Button("Speichern");
	      private Button btnEditAbort = new Button("Abbrechen");
	@FXML private Button btnDelete;
	
	@FXML private Button btnBillingAdd;
	@FXML private Button btnBillingDelete;
	
	@FXML private HBox hboxBtnTopbar;
	
	private Stage stage;
	
	public ControllerCustomerData(){}
	
	@FXML private void initialize(){
		
		new InitCombos().initComboLand(cbLand);
		new InitCombos().initComboPayment(cbPayment);
		new InitCombos().initComboSalutation(cbSalutation);
		
		new InitCombos().initComboLand(cbLandBilling);
		new InitCombos().initComboPayment(cbPaymentBilling);
		new InitCombos().initComboSalutation(cbSalutationBilling);
		
		/* BUTTONS */
		initBtnSearch();
		initBtnNew();
		initBtnEdit();
		initBtnEditSave();
		initBtnEditAbort();
		initBtnDelete();
		
		initBtnBillingAdd();
		initBtnBillingDelete();
		
		setButtonState();
		
	}
	
	/*
	 * BUTTONS
	 */
	private void initBtnSearch(){
		
		btnSearch.setGraphic(new GraphicButton("file:resources/search_32.png").getGraphicButton());
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				LoadCustomerSearch customerSearch = new LoadCustomerSearch(true);
				if(customerSearch.getController().getSelectedCustomerID() != 0){
					selectCustomer(customerSearch.getController().getSelectedCustomerID());
				}
				
			}
		});
		
	}
	
	private void initBtnNew(){
		
		btnNew.setGraphic(new GraphicButton("file:resources/new_32.png").getGraphicButton());
		btnNew.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				LoadCustomerAdd customerAdd = new LoadCustomerAdd(true);
				if(customerAdd.getController().getCreatedCustomerID() != 0){
					selectCustomer(customerAdd.getController().getCreatedCustomerID());
				}
				
			}
		});
		
	}
	
	private void initBtnEdit(){
		
		btnEdit.setGraphic(new GraphicButton("file:resources/edit_32.png").getGraphicButton());
		btnEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			
				hboxBtnTopbar.getChildren().add(hboxBtnTopbar.getChildren().indexOf(btnEdit) + 1, btnEditSave);
				hboxBtnTopbar.getChildren().add(hboxBtnTopbar.getChildren().indexOf(btnEdit) + 2, btnEditAbort);
				
				enableFields();				
				setButtonState();
				
			}
		});		
		
	}
	
	private void initBtnEditSave(){
		
		btnEditSave.getStyleClass().add("btnTopbar");
		btnEditSave.setGraphic(new GraphicButton("file:resources/save_32.png").getGraphicButton());
		btnEditSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				/* UPDATE CUSTOMER */
				ModelCustomer customer = new ModelCustomer();				
				if(customer.validate(new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfCustomerID.getText()), 
									tfName1.getText())){
					
					customer.updateCustomer(
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfCustomerID.getText()),
						cbSalutation.getSelectionModel().getSelectedItem(),
						tfName1.getText(), 
						tfName2.getText(),
						tfStreet.getText(), 
						cbLand.getSelectionModel().getSelectedItem(), 
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfZip.getText()), 
						tfLocation.getText(), 
						
						tfPhone.getText(), 
						tfMobile.getText(), 
						tfFax.getText(), 
						tfEmail.getText(),
						tfWeb.getText(), 
						tfContact.getText(), 
						tfUstID.getText(), 
						
						cbPayment.getSelectionModel().getSelectedItem(), 
						tfIBAN.getText(), 
						tfBIC.getText(), 
						tfBank.getText(), 
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfPaymentSkonto.getText()),
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfPaymentNetto.getText()), 
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfSkonto.getText()), 
						
						String.valueOf(LocalDate.now()), 
						taNotes.getText(),
						
						new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfCustomerIDBilling.getText())
					);
					
					hboxBtnTopbar.getChildren().remove(btnEditAbort);
					hboxBtnTopbar.getChildren().remove(btnEditSave);
					
					disableFields();
					setButtonState();
					
					//Reload ArticleData
					if(stage != null){
						stage.close();
					}else{
						selectCustomer(new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfCustomerID.getText()));
					}
					
				}
			}
			
		});		
	}
	
	private void initBtnEditAbort(){
		
		btnEditAbort.getStyleClass().add("btnTopbar");
		btnEditAbort.setGraphic(new GraphicButton("file:resources/cancel_32.png").getGraphicButton());
		btnEditAbort.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				hboxBtnTopbar.getChildren().remove(btnEditAbort);
				hboxBtnTopbar.getChildren().remove(btnEditSave);
				
				disableFields();
				setButtonState();

				//Reload ArticleData
				selectCustomer(new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfCustomerID.getText()));				
				
			}
		});
		
	}
	
	private void initBtnDelete(){
		
		btnDelete.setGraphic(new GraphicButton("file:resources/delete_32.png").getGraphicButton());
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				DeleteAlert delete = new DeleteAlert();
				if(delete.getDelete()){
					
					ModelCustomer customer = new ModelCustomer();
					customer.deleteCustomer(new Validate().new ValidateOnlyInteger().validateOnlyInteger(tfCustomerID.getText()), tfName1.getText());
					
					resetFields();
					setButtonState();
					
				}
				
			}
		});
		
	}
	
	private void initBtnBillingAdd(){
		
		btnBillingAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				LoadCustomerSearch customerSearch = new LoadCustomerSearch(true);
				if(customerSearch.getController().getSelectedCustomerID() != 0){
					
					ModelCustomer customer = new ModelCustomer();
					customer.selectCustomer(customerSearch.getController().getSelectedCustomerID());
					
					tfCustomerIDBilling.setText(String.valueOf(customer.getCustomerID()));
					tfName1Billing.setText(customer.getName1());
					tfName2Billing.setText(customer.getName2());
					tfStreetBilling.setText(customer.getStreet());
					cbLandBilling.getSelectionModel().select(customer.getLand());
					tfZipBilling.setText(String.valueOf(customer.getZip()));
					tfLocationBilling.setText(customer.getLocation());
					
					tfPhoneBilling.setText(customer.getPhone());
					tfMobileBilling.setText(customer.getMobile());
					tfFaxBilling.setText(customer.getFax());
					tfEmailBilling.setText(customer.getEmail());
					tfWebBilling.setText(customer.getWeb());
					tfContactBilling.setText(customer.getContact());
					tfUstIDBilling.setText(customer.getUstID());
					
					cbPaymentBilling.getSelectionModel().select(customer.getPayment());
					tfIBANBilling.setText(customer.getIBAN());
					tfBICBilling.setText(customer.getBIC());
					tfBankBilling.setText(customer.getBank());
					tfPaymentNettoBilling.setText(String.valueOf(customer.getPaymentNetto()));
					tfPaymentSkontoBilling.setText(String.valueOf(customer.getPaymentSkonto()));
					tfSkontoBilling.setText(String.valueOf(customer.getSkonto()));
					
				}
				
			}
		});
		
	}
	
	private void initBtnBillingDelete(){
		
		btnBillingDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				DeleteAlert delete = new DeleteAlert();
				if(delete.getDelete()){
					resetFieldsBilling();
				}
				
			}
		});
		
	}
	
	/*
	 * DATABASE METHODS
	 */
	private void selectCustomer(int _customerID){
		
		ModelCustomer customer = new ModelCustomer();
		customer.selectCustomer(_customerID);
		
		if(! customer.getName1().equals("")){
			
			tfCustomerID.setText(String.valueOf(customer.getCustomerID()));
			cbSalutation.getSelectionModel().select(customer.getSalutation());
			tfName1.setText(customer.getName1());
			tfName2.setText(customer.getName2());
			tfStreet.setText(customer.getStreet());
			cbLand.getSelectionModel().select(customer.getLand());
			tfZip.setText(String.valueOf(customer.getZip()));
			tfLocation.setText(customer.getLocation());
			
			tfPhone.setText(customer.getPhone());
			tfMobile.setText(customer.getMobile());
			tfFax.setText(customer.getFax());
			tfEmail.setText(customer.getEmail());
			tfWeb.setText(customer.getWeb());
			tfContact.setText(customer.getContact());
			tfUstID.setText(customer.getUstID());
			
			cbPayment.getSelectionModel().select(customer.getPayment());
			tfBank.setText(customer.getBank());
			tfIBAN.setText(customer.getIBAN());
			tfBIC.setText(customer.getBIC());
			tfBank.setText(customer.getBank());
			tfPaymentSkonto.setText(String.valueOf(customer.getPaymentSkonto()));
			tfPaymentNetto.setText(String.valueOf(customer.getPaymentNetto()));
			tfSkonto.setText(String.valueOf(customer.getSkonto()));
			
			taNotes.setText(customer.getNotes());
			lblLastChange.setText(customer.getLastChange());
			
			lblSubHeadline.setText(" - " + customer.getCustomerID() + " " + customer.getName1() + ", " + customer.getZip() + " " + customer.getLocation());
			
			//ALWAYS LAST - OTHERWISE THE DATA IN THE MODEL WOULD BE OVERWRITTEN
			if(customer.getBillingID() != 0){
				
				customer.selectCustomer(customer.getBillingID());
				
				tfCustomerIDBilling.setText(String.valueOf(customer.getCustomerID()));
				cbSalutationBilling.getSelectionModel().select(customer.getSalutation());
				tfName1Billing.setText(customer.getName1());
				tfName2Billing.setText(customer.getName2());
				tfStreetBilling.setText(customer.getStreet());
				cbLandBilling.getSelectionModel().select(customer.getLand());
				tfZipBilling.setText(String.valueOf(customer.getZip()));
				tfLocationBilling.setText(customer.getLocation());
				
				tfPhoneBilling.setText(customer.getPhone());
				tfMobileBilling.setText(customer.getMobile());
				tfFaxBilling.setText(customer.getFax());
				tfEmailBilling.setText(customer.getEmail());
				tfWebBilling.setText(customer.getWeb());
				tfContactBilling.setText(customer.getContact());
				tfUstIDBilling.setText(customer.getUstID());
				
				cbPaymentBilling.getSelectionModel().select(customer.getPayment());
				tfBankBilling.setText(customer.getBank());
				tfIBANBilling.setText(customer.getIBAN());
				tfBICBilling.setText(customer.getBIC());
				tfBankBilling.setText(customer.getBank());
				tfPaymentSkontoBilling.setText(String.valueOf(customer.getPaymentSkonto()));
				tfPaymentNettoBilling.setText(String.valueOf(customer.getPaymentNetto()));
				tfSkontoBilling.setText(String.valueOf(customer.getSkonto()));
				
			}else{				
				resetFieldsBilling();				
			}
			
		}else{
			resetFields();
			System.out.println("Keine Daten gefunden!");
		}
		
		setButtonState();
		
	}
	
	/*
	 * UI METHODS
	 */
	private void enableFields(){
		
		tfCustomerID.setDisable(false);
		cbSalutation.setDisable(false);
		tfName1.setDisable(false);
		tfName2.setDisable(false);
		tfStreet.setDisable(false);
		cbLand.setDisable(false);
		tfZip.setDisable(false);
		tfLocation.setDisable(false);
		
		tfPhone.setDisable(false);
		tfMobile.setDisable(false);
		tfFax.setDisable(false);
		tfEmail.setDisable(false);
		tfWeb.setDisable(false);
		tfContact.setDisable(false);
		tfUstID.setDisable(false);
		
		cbPayment.setDisable(false);
		tfBank.setDisable(false);
		tfIBAN.setDisable(false);
		tfBIC.setDisable(false);
		tfBank.setDisable(false);
		tfPaymentSkonto.setDisable(false);
		tfPaymentNetto.setDisable(false);
		tfSkonto.setDisable(false);
		
		taNotes.setDisable(false);
		
	}
	
	private void disableFields(){
		
		tfCustomerID.setDisable(true);
		cbSalutation.setDisable(true);
		tfName1.setDisable(true);
		tfName2.setDisable(true);
		tfStreet.setDisable(true);
		cbLand.setDisable(true);
		tfZip.setDisable(true);
		tfLocation.setDisable(true);
		
		tfPhone.setDisable(true);
		tfMobile.setDisable(true);
		tfFax.setDisable(true);
		tfEmail.setDisable(true);
		tfWeb.setDisable(true);
		tfContact.setDisable(true);
		tfUstID.setDisable(true);
		
		cbPayment.setDisable(true);
		tfBank.setDisable(true);
		tfIBAN.setDisable(true);
		tfBIC.setDisable(true);
		tfBank.setDisable(true);
		tfPaymentSkonto.setDisable(true);
		tfPaymentNetto.setDisable(true);
		tfSkonto.setDisable(true);
		
		taNotes.setDisable(true);
		
	}
	
	private void resetFields(){
		
		tfCustomerID.clear();
		cbSalutation.getSelectionModel().selectFirst();
		tfName1.clear();
		tfName2.clear();
		tfStreet.clear();
		cbLand.getSelectionModel().selectFirst();
		tfZip.clear();
		tfLocation.clear();
		
		tfPhone.clear();
		tfMobile.clear();
		tfFax.clear();
		tfEmail.clear();
		tfWeb.clear();
		tfContact.clear();
		tfUstID.clear();
		
		cbPayment.getSelectionModel().selectFirst();
		tfBank.clear();
		tfIBAN.clear();
		tfBIC.clear();
		tfBank.clear();
		tfPaymentSkonto.clear();
		tfPaymentNetto.clear();
		tfSkonto.clear();
		
		resetFieldsBilling();
		
		taNotes.clear();
		lblLastChange.setText("Letzte �nderung: "); //Same is in the FXML-File
		
		lblSubHeadline.setText("");
		
	}
	
	private void resetFieldsBilling(){
		
		tfCustomerIDBilling.clear();
		cbSalutationBilling.getSelectionModel().selectFirst();
		tfName1Billing.clear();
		tfName2Billing.clear();
		tfStreetBilling.clear();
		cbLandBilling.getSelectionModel().selectFirst();
		tfZipBilling.clear();
		tfLocationBilling.clear();
		
		tfPhoneBilling.clear();
		tfMobileBilling.clear();
		tfFaxBilling.clear();
		tfEmailBilling.clear();
		tfWebBilling.clear();
		tfContactBilling.clear();
		tfUstIDBilling.clear();
		
		cbPaymentBilling.getSelectionModel().selectFirst();
		tfBankBilling.clear();
		tfIBANBilling.clear();
		tfBICBilling.clear();
		tfBankBilling.clear();
		tfPaymentSkontoBilling.clear();
		tfPaymentNettoBilling.clear();
		tfSkontoBilling.clear();
		
	}
	
	private void setButtonState(){
		
		if(tfCustomerID.getText().equals("")){
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);
			
		}else{
			
			btnEdit.setDisable(false);
			btnDelete.setDisable(false);
			
			//if the hboxBtnTopbar contains btnEditAbort and btnEditSave it means btnEdit was pressed 
			if(hboxBtnTopbar.getChildren().contains(btnEditAbort) && hboxBtnTopbar.getChildren().contains(btnEditSave)){
				
				btnDelete.setDisable(true);
				btnNew.setDisable(true);
				btnSearch.setDisable(true);
				btnEdit.setDisable(true);
				
				btnBillingAdd.setDisable(false);
				btnBillingDelete.setDisable(false);
								
			}else{
				
				btnSearch.setDisable(false);
				btnNew.setDisable(false);
				btnEdit.setDisable(false);
				btnDelete.setDisable(false);
				
				btnBillingAdd.setDisable(true);
				btnBillingDelete.setDisable(true);
				
			}
		}
	}
	
	/*
	 * GETTER & SETTER
	 */
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
}