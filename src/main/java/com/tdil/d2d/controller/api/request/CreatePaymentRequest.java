package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.NotEmpty;

public class CreatePaymentRequest extends ApiRequest {	
	
	@NotEmpty
	private String item;
	
	@NotEmpty
	private String price;
	
	@NotEmpty
	private String idPaymentMP;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIdPaymentMP() {
		return idPaymentMP;
	}

	public void setIdPaymentMP(String idPaymentMP) {
		this.idPaymentMP = idPaymentMP;
	}

}
