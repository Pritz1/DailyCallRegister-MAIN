package com.eis.dailycallregister.Pojo;

public class ChemistdataItem{
	private String keyp;
	private String mobileno;
	private String img_url;

	public void setKeyp(String keyp){
		this.keyp = keyp;
	}

	public String getKeyp(){
		return keyp;
	}

	public void setMobileno(String mobileno){
		this.mobileno = mobileno;
	}

	public String getMobileno(){
		return mobileno;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	@Override
 	public String toString(){
		return 
			"ChemistdataItem{" + 
			"keyp = '" + keyp + '\'' + 
			",mobileno = '" + mobileno + '\'' +
					",img_url = '" + img_url + '\'' +
			"}";
		}
}
