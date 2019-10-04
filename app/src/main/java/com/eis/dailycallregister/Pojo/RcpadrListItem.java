package com.eis.dailycallregister.Pojo;

public class RcpadrListItem{
	private String drcd;
	private String netid;
	private String drname;
	private String cntcd;

	public void setDrcd(String drcd){
		this.drcd = drcd;
	}
	public String getDrcd(){
		return drcd;
	}
	public void setNetid(String netid){
		this.netid = netid;
	}
	public String getNetid(){
		return netid;
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
			"RcpadrListItem{" + 
			"drcd = '" + drcd + '\'' + 
			",netid = '" + netid + '\'' + 
			",drname = '" + drname + '\'' + 
			",cntcd = '" + cntcd + '\'' + 
			"}";
		}
}
