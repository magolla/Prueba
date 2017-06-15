package com.tdil.d2d.controller.api.dto;

import com.google.gson.annotations.SerializedName;

public class InAppPurchaseDTO {

	@SerializedName("quantity")
    private int quantity;
    
    @SerializedName("product_id")
    private String productID;
    
    @SerializedName("transaction_id")
    private String transactionID;
    
    @SerializedName("original_transaction_id")
    private String transactionIDOriginal;
    
    @SerializedName("purchase_date_ms")
    private Long purchaseDate;
    
    @SerializedName("original_purchase_date_ms")
    private Long purchaseDateOriginal;
    
    @SerializedName("expires_date_ms")
    private Long expiresDate;
    
    @SerializedName("cancellation_date_ms")
    private Long cancellationDate;
    
    @SerializedName("app_item_id")
    private String bundleID;
    
    @SerializedName("version_external_identifier")
    private String versionID;
    
    @SerializedName("web_order_line_item_id")
    private String orderItemID;

    @SerializedName("is_trial_period")
    private Boolean isTrialPeriod;
    
	public int getQuantity() {
		return quantity;
	}

	public String getProductID() {
		return productID;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public String getTransactionIDOriginal() {
		return transactionIDOriginal;
	}

	public Long getPurchaseDate() {
		return purchaseDate;
	}

	public Long getPurchaseDateOriginal() {
		return purchaseDateOriginal;
	}

	public Long getExpiresDate() {
		return expiresDate;
	}

	public Long getCancellationDate() {
		return cancellationDate;
	}

	public String getBundleID() {
		return bundleID;
	}

	public String getVersionID() {
		return versionID;
	}

	public String getOrderItemID() {
		return orderItemID;
	}
 
	public Boolean getIsTrialPeriod() {
		return isTrialPeriod;
	}
}