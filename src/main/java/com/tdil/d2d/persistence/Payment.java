package com.tdil.d2d.persistence;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_PAYMENT")
public class Payment implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@ManyToOne
	private User user;
	
	@Column(name = "item")
	private String item;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "idPaymentMP")
	private String idPaymentMP;
	
	
	public Payment() {
	}

	public Payment(String idPaymentMP, String item, BigDecimal price, User user) {
		super();
		this.setCreationDate(new Date());
		this.user = user;
		this.idPaymentMP = idPaymentMP;
		this.item = item;
		this.price = price;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getIdPaymentMP() {
		return idPaymentMP;
	}

	public void setIdPaymentMP(String idPaymentMP) {
		this.idPaymentMP = idPaymentMP;
	}
	
}
