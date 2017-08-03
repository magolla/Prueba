package com.tdil.d2d.bo.dto;

public class ReportItemDTO {

	private String name;
	private Integer quantity;
	private String color;
	
	public ReportItemDTO(String name, Integer quantity, String color) {
		this.name = name;
		this.quantity = quantity;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
