package com.eis.dailycallregister.Pojo;

import java.util.List;

public class SetChemistkeyPerRes {
	private List<ChemistdataItem> chemistdata;
	private String errormsg;
	private boolean error;

	public void setChemistdata(List<ChemistdataItem> chemistdata){
		this.chemistdata = chemistdata;
	}

	public List<ChemistdataItem> getChemistdata(){
		return chemistdata;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	@Override
 	public String toString(){
		return 
			"SetChemistkeyPerRes{" +
			"chemistdata = '" + chemistdata + '\'' + 
			",error = '" + error + '\'' + 
			",errormsg = '" + errormsg + '\'' +
			"}";
		}
}