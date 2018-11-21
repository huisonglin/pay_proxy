package com.proxy.pay.proxy_pay.weixin.dto;

public class DecryptUserInfoDTO {
	
	private String encryptedData;
	
	private String iv;
	
	private String session_key;

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	
	

}
