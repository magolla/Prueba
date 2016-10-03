package com.tdil.d2d.controller.api.dto;

public class ActivityLogDTO {

	private long id;
	private String creationDate;
	private String log;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActivityLogDTO [id=");
		builder.append(id);
		builder.append(", ");
		if (creationDate != null) {
			builder.append("creationDate=");
			builder.append(creationDate);
			builder.append(", ");
		}
		if (log != null) {
			builder.append("log=");
			builder.append(log);
		}
		builder.append("]");
		return builder.toString();
	}
	
	

}
