package com.tdil.d2d.bo.dto;

import java.util.List;

public class JobOfferReportItemDTO {

	private String name;
	private String color;
	private List<Integer> values;
	
	public JobOfferReportItemDTO(String name, String color, List<Integer> values) {
		this.name = name;
		this.color = color;
		this.values = values;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<Integer> getValues() {
		return values;
	}
	public void setValues(List<Integer> values) {
		this.values = values;
	}
	
	public Integer getTotal() {
		Integer sum = 0;
		
		for (Integer integer : values) {
			sum += integer;
		}
		
		return sum;
	}
}
