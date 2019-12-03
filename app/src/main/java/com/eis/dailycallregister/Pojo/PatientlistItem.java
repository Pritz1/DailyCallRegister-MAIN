package com.eis.dailycallregister.Pojo;

public class PatientlistItem{
	private String patientname;
	private String gender;
	private String patno;
	private String phoneno;
	private String age;

	public void setPatientname(String patientname){
		this.patientname = patientname;
	}

	public String getPatientname(){
		return patientname;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setPatno(String patno){
		this.patno = patno;
	}

	public String getPatno(){
		return patno;
	}

	public void setPhoneno(String phoneno){
		this.phoneno = phoneno;
	}

	public String getPhoneno(){
		return phoneno;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	@Override
 	public String toString(){
		return 
			"PatientlistItem{" + 
			"patientname = '" + patientname + '\'' + 
			",gender = '" + gender + '\'' + 
			",patno = '" + patno + '\'' + 
			",phoneno = '" + phoneno + '\'' + 
			",age = '" + age + '\'' + 
			"}";
		}
}
