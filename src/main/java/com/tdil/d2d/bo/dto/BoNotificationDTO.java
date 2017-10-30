package com.tdil.d2d.bo.dto;

import java.util.List;

public class BoNotificationDTO {
	boolean allUser;
	String userIds;
	String userTestIds;
	String titulo;
	String message;
	private List<Long> occupations;
	private List<Long> specialties;
	private List<Long> sponsors; 
	private boolean sendUserA;
	private boolean sendUserB;
	private boolean sendUserBAllSponsor;
	
	
	
	public List<Long> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Long> sponsors) {
		this.sponsors = sponsors;
	}

	public boolean isSendUserBAllSponsor() {
		return sendUserBAllSponsor;
	}

	public void setSendUserBAllSponsor(boolean sendUserBAllSponsor) {
		this.sendUserBAllSponsor = sendUserBAllSponsor;
	}

	public boolean isSendUserA() {
		return sendUserA;
	}

	public void setSendUserA(boolean sendUserA) {
		this.sendUserA = sendUserA;
	}

	public boolean isSendUserB() {
		return sendUserB;
	}

	public void setSendUserB(boolean sendUserB) {
		this.sendUserB = sendUserB;
	}

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

	public String getUserTestIds() {
		return userTestIds;
	}

	public void setUserTestIds(String userTestIds) {
		this.userTestIds = userTestIds;
	}

}
