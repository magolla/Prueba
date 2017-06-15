package com.tdil.d2d.controller.api.request;

public class ReceiptSuscriptionRequest extends ApiRequest {

	private String receiptData;
	private String password;
	private Boolean isDev;
	
	public String getReceiptData() {
		return receiptData;
	}
	
	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getIsDev() {
		return isDev;
	}
	
	public void setIsDev(Boolean isDev) {
		this.isDev = isDev;
	}
}
