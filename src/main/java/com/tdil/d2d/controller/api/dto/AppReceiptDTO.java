package com.tdil.d2d.controller.api.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AppReceiptDTO  {

	 @SerializedName("bundle_id")
     private String bundleId;
	 
     @SerializedName("application_version")
     private String applicationVersion;
     
     @SerializedName("original_application_version")
     private String originalApplicationVersion;
     
     @SerializedName("in_app")
     private List<InAppPurchaseDTO> inApps;

	public String getBundleId() {
		return bundleId;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public String getOriginalApplicationVersion() {
		return originalApplicationVersion;
	}

	public List<InAppPurchaseDTO> getInApps() {
		return inApps;
	}
    
}