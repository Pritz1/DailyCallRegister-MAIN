package com.eis.dailycallregister.Pojo;

import java.util.List;

public class PatientListRes{
	private boolean error;
	private String errormsg;
	private List<PatientlistItem> patientlist;

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setPatientlist(List<PatientlistItem> patientlist){
		this.patientlist = patientlist;
	}

	public List<PatientlistItem> getPatientlist(){
		return patientlist;
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
			"PatientListRes{" + 
			"error = '" + error + '\'' + 
			",patientlist = '" + patientlist + '\'' + 
			"}";
		}
}
