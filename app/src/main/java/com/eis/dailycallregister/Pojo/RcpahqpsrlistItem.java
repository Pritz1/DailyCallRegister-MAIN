package com.eis.dailycallregister.Pojo;

public class RcpahqpsrlistItem{
	private String ename;
	private String netid;
	private String level1;
	private String hname;

	public void setEname(String ename){
		this.ename = ename;
	}

	public String getEname(){
		return ename;
	}

	public void setNetid(String netid){
		this.netid = netid;
	}

	public String getNetid(){
		return netid;
	}

	public void setLevel1(String level1){
		this.level1 = level1;
	}

	public String getLevel1(){
		return level1;
	}

	public void setHname(String hname){
		this.hname = hname;
	}

	public String getHname(){
		return hname;
	}

	@Override
 	public String toString(){
		return 
			"RcpahqpsrlistItem{" + 
			"ename = '" + ename + '\'' + 
			",netid = '" + netid + '\'' + 
			",level1 = '" + level1 + '\'' + 
			",hname = '" + hname + '\'' + 
			"}";
		}
}
