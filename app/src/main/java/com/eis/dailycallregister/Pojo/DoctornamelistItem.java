package com.eis.dailycallregister.Pojo;

public class DoctornamelistItem{
	private String drcd;
	private String drname;
	private String cntcd;



	public void setDrcd(String drcd){
		this.drcd = drcd;
	}

	public String getDrcd(){
		return drcd;
	}

	public void setDrname(String drname){
		this.drname = drname;
	}

	public String getDrname(){
		return drname;
	}

	public void setCntcd(String cntcd){
		this.cntcd = cntcd;
	}

	public String getCntcd(){
		return cntcd;
	}



	@Override
 	public String toString(){
		return 
			"DoctornamelistItem{" + 
			"drcd = '" + drcd + '\'' + 
			",drname = '" + drname + '\'' + 
			",cntcd = '" + cntcd + '\'' + 

			"}";
		}
}
