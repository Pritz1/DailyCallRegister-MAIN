package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class RetailerscntItem{

	@SerializedName("tot_upld")
	private String totUpld;

	@SerializedName("tot")
	private String tot;

	public void setTotUpld(String totUpld){
		this.totUpld = totUpld;
	}

	public String getTotUpld(){
		return totUpld;
	}

	public void setTot(String tot){
		this.tot = tot;
	}

	public String getTot(){
		return tot;
	}

	@Override
 	public String toString(){
		return 
			"RetailerscntItem{" + 
			"tot_upld = '" + totUpld + '\'' + 
			",tot = '" + tot + '\'' + 
			"}";
		}
}
