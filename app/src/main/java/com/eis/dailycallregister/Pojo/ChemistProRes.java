package com.eis.dailycallregister.Pojo;

import java.util.List;

public class ChemistProRes{
	private List<ChemistprofilelistItem> chemistprofilelist;
	private boolean error;

	public void setChemistprofilelist(List<ChemistprofilelistItem> chemistprofilelist){
		this.chemistprofilelist = chemistprofilelist;
	}

	public List<ChemistprofilelistItem> getChemistprofilelist(){
		return chemistprofilelist;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ChemistProRes{" + 
			"chemistprofilelist = '" + chemistprofilelist + '\'' +
			",error = '" + error + '\'' + 
			"}";
		}
}
