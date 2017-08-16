package com.tdil.d2d.controller.api.dto;

public class GeoLevelDTO {

	private long id;
	private int level;
	private String name;
	
	public GeoLevelDTO() {
	}
	
	public GeoLevelDTO(long id, int level) {
		this.id = id;
		this.level = level;
	}
	
	public GeoLevelDTO(long id, int level,String name) {
		this.id = id;
		this.level = level;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
