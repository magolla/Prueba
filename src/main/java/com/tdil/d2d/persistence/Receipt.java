package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_RECEIPT")
public class Receipt implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@ManyToOne
	private User user;

	@Column(name = "expires_date")
	private long expiresDate;

	@Column(name = "is_trial_period")
	private Boolean isTrialPeriod;

	@Column(name = "order_item_id")
	private String orderItemId;

	@Column(name = "product_id")
	private String productId;

	@Column(name = "purchase_date")
	private long purchaseDate;

	@Column(name = "purchase_date_original")
	private long purchaseDateOriginal;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "transaction_id_original")
	private String transactionIdOriginal;
	
	public Receipt() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getExpiresDate() {
		return expiresDate;
	}

	public void setExpiresDate(long expiresDate) {
		this.expiresDate = expiresDate;
	}

	public Boolean getIsTrialPeriod() {
 		return isTrialPeriod;
	}

	public void setIsTrialPeriod(Boolean isTrialPeriod) {
		this.isTrialPeriod = isTrialPeriod;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public long getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public long getPurchaseDateOriginal() {
		return purchaseDateOriginal;
	}

	public void setPurchaseDateOriginal(long purchaseDateOriginal) {
		this.purchaseDateOriginal = purchaseDateOriginal;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getTransationId() {
		return transactionId;
	}

	public void setTransationId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionIdOriginal() {
		return transactionIdOriginal;
	}

	public void setTransactionIdOriginal(String transactionIdOriginal) {
		this.transactionIdOriginal = transactionIdOriginal;
	}
}
