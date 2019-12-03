package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class RetailerAndOptions{
	@SerializedName("RetailerAlert")
	private String retailerAlert;

	@SerializedName("PatientProfile")
	private String patientProfile;

	@SerializedName("RetailReachout")
	private String retailReachout;

	public void setRetailerAlert(String retailerAlert){
		this.retailerAlert = retailerAlert;
	}

	public String getRetailerAlert(){
		return retailerAlert;
	}

	public void setPatientProfile(String patientProfile){
		this.patientProfile = patientProfile;
	}

	public String getPatientProfile(){
		return patientProfile;
	}

	public void setRetailReachout(String retailReachout){
		this.retailReachout = retailReachout;
	}

	public String getRetailReachout(){
		return retailReachout;
	}

	@Override
 	public String toString(){
		return 
			"RetailerAndOptions{" + 
			"retailerAlert = '" + retailerAlert + '\'' + 
			",patientProfile = '" + patientProfile + '\'' + 
			",retailReachout = '" + retailReachout + '\'' + 
			"}";
		}
}
