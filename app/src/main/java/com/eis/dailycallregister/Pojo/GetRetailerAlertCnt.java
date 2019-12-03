package com.eis.dailycallregister.Pojo;

import java.util.List;

public class GetRetailerAlertCnt{
	private List<RetailerscntItem> retailerscnt;
	private boolean error;

	public void setRetailerscnt(List<RetailerscntItem> retailerscnt){
		this.retailerscnt = retailerscnt;
	}

	public List<RetailerscntItem> getRetailerscnt(){
		return retailerscnt;
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
			"GetRetailerAlertCnt{" + 
			"retailerscnt = '" + retailerscnt + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}