
package com.tdil.d2d.controller.api.response;

public class UserReceiptResponse extends ApiResponse {
	
	private Long expiresDate;
	private Long purchaseDate;
	private Boolean isTrialPeriod;
	
	public Long getExpiresDate() {
		return expiresDate;
	}
	
	public void setExpiresDate(Long expiresDate) {
		this.expiresDate = expiresDate;
	}
	
	public Long getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(Long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public Boolean getIsTrialPeriod() {
		return isTrialPeriod;
	}
	
	public void setIsTrialPeriod(Boolean isTrialPeriod) {
		this.isTrialPeriod = isTrialPeriod;
	}
}
