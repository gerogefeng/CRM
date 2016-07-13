package de.michaprogs.crm;

import javafx.scene.control.ComboBox;

public class InitCombos {

	public InitCombos(){}
	
	public void initComboPriceUnit(ComboBox<String> cbPriceUnit){
		
		cbPriceUnit.getItems().addAll(
			"1",
			"10",
			"100",
			"1000"
		);
		
		cbPriceUnit.getSelectionModel().select(0);
		
	}
	
	public void initComboAmountUnit(ComboBox<String> cbAmountUnit){
		
		cbAmountUnit.getItems().addAll(
			"STK",
			"LTR",
			"METER",
			"KG",
			"TO"
		);
		
		cbAmountUnit.getSelectionModel().select(0);
		
	}
	
	public void initComboTax(ComboBox<String> cbTax){
		
		cbTax.getItems().addAll(
			"7",
			"19"
		);
		
		cbTax.getSelectionModel().select(1);
		
	}
	
	public void initComboCategory(ComboBox<String> cbCategory){
		
		cbCategory.getItems().addAll(
			"",
			"Gas",
			"Gas-Zubeh�r",
			"Heiz�l",
			"Diesel",
			"Schmierstoffe"
		);
		
		cbCategory.getSelectionModel().select(0);
		
	}
	
	public void initComboOfferEnd(ComboBox<String> cbOfferEnd){
		
		cbOfferEnd.getItems().addAll(
			"Standart",
			"Fl�ssiggas",
			"Beh�lter",
			"Beh�lter-Tausch",
			"Heizmobil"
		);
		
		cbOfferEnd.getSelectionModel().select(0);
		
	}
	
}