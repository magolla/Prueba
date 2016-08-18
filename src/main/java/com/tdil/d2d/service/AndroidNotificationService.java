package com.tdil.d2d.service;

import java.math.BigDecimal;

public interface AndroidNotificationService {

	public void sendPaymentRequest(long paymentId, long paymentDestinationId, String paymentDestinationDescription, Integer installments, 
			BigDecimal amount, String regId);
}
