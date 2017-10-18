package com.tdil.d2d.bo.dto;

import java.util.List;

public class BoNotificationDTO {
	boolean allUser;
	String userIds;
	String titulo;
	String message;
	private List<Long> occupations;

	private List<Long> specialties; 

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Long> getOccupations() {
		return occupations;
	}

	public void setOccupations(List<Long> occupations) {
		this.occupations = occupations;
	}

	public List<Long> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<Long> specialties) {
		this.specialties = specialties;
	}

	public boolean isAllUser() {
		return allUser;
	}

	public void setAllUser(boolean allUser) {
		this.allUser = allUser;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

}
