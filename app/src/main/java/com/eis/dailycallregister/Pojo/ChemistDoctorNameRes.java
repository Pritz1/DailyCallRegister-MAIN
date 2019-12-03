package com.eis.dailycallregister.Pojo;

import java.util.List;

public class ChemistDoctorNameRes{
	private boolean error;
	private List<DoctornamelistItem> doctornamelist;

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setDoctornamelist(List<DoctornamelistItem> doctornamelist){
		this.doctornamelist = doctornamelist;
	}

	public List<DoctornamelistItem> getDoctornamelist(){
		return doctornamelist;
	}

	@Override
 	public String toString(){
		return 
			"ChemistDoctorNameRes{" + 
			"error = '" + error + '\'' + 
			",doctornamelist = '" + doctornamelist + '\'' + 
			"}";
		}
}