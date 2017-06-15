package com.tdil.d2d.controller.api.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class VerifyReceiptResultDTO  {

	@SerializedName("status")
    private int status;
	
    @SerializedName("environment")
    private String environment;
    
    @SerializedName("receipt")
    private AppReceiptDTO receipt;
    
    @SerializedName("latest_receipt")
    private String latestReceiptB64;
    
    @SerializedName("latest_receipt_info")
    private List<InAppPurchaseDTO> latestPurchases;
    
    private Status statusAsEnum() {
        for (Status statusEnum : Status.values())
            if (statusEnum.getStatus() == status)
                return statusEnum;

        throw new UnsupportedOperationException( "Status not supported: " + status );
    }

    public enum Status {
        /**
         * The receipt is valid.
         */
        SUCCESS( 0 ),
        /**
         * The App Store could not read the JSON object you provided.
         */
        INVALID_REQUEST( 21000 ),
        /**
         * The data in the receipt-data property was malformed.
         */
        INVALID_RECEIPT( 21002 ),
        /**
         * The receipt could not be authenticated.
         */
        UNAUTHORIZED_RECEIPT( 21003 ),
        /**
         * The shared secret you provided does not match the shared secret on file for your account.
         */
        UNAUTHORIZED_REQUEST( 21004 ),
        /**
         * The receipt server is not currently available.
         */
        UNAVAILABLE( 21005 ),
        /**
         * This receipt is valid but the subscription has expired. When this status code is returned to your server, the receipt data
         * is also decoded and returned as part of the response.
         */
        SUBSCRIPTION_EXPIRED( 21006 ),
        /**
         * This receipt is a sandbox receipt, but it was sent to the production service for verification.
         */
        RECEIPT_SANDBOX( 21007 ),
        /**
         * This receipt is a production receipt, but it was sent to the sandbox service for verification.
         */
        RECEIPT_PRODUCTION( 21008 );

        private final int status;

        Status(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

	public int getStatus() {
		return status;
	}

	public String getEnvironment() {
		return environment;
	}

	public AppReceiptDTO getReceipt() {
		return receipt;
	}

	public String getLatestReceiptB64() {
		return latestReceiptB64;
	}

	public List<InAppPurchaseDTO> getLatestPurchases() {
		return latestPurchases;
	}
    
}