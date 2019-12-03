package com.eis.dailycallregister.Pojo;

public class ChemistprofilelistItem{
	private String stcd;
	private String cntcd;
	private String sttype;
	private String stname;
	private String status;

	public void setStcd(String stcd){
		this.stcd = stcd;
	}

	public String getStcd(){
		return stcd;
	}

	public void setCntcd(String cntcd){
		this.cntcd = cntcd;
	}

	public String getCntcd(){
		return cntcd;
	}

	public void setSttype(String sttype){
		this.sttype = sttype;
	}

	public String getSttype(){
		return sttype;
	}

	public void setStname(String stname){
		this.stname = stname;
	}

	public String getStname(){
		return stname;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ChemistprofilelistItem{" + 
			"stcd = '" + stcd + '\'' + 
			",cntcd = '" + cntcd + '\'' + 
			",sttype = '" + sttype + '\'' + 
			",stname = '" + stname + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
