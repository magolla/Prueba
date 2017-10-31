package com.tdil.d2d.bo.dto;

public class BOSponsorListDTO {
	
	private String name;
	private long id;
	private boolean active;
	
	public BOSponsorListDTO(long id,String name, boolean active) {
		this.id = id;
		this.name = name;
		this.active = active;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

}
