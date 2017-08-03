package com.tdil.d2d.bo.dto;

import java.util.List;

public class SubscriptionReportDTO {

	private long countUsers;
	private long countSubscriptions;
	List<ReportItemDTO> list;
	

	public long getCountUsers() {
		return countUsers;
	}
	public void setCountUsers(long countUsers) {
		this.countUsers = countUsers;
	}
	public long getCountSubscriptions() {
		return countSubscriptions;
	}
	public void setCountSubscriptions(long countSubscriptions) {
		this.countSubscriptions = countSubscriptions;
	}
	public List<ReportItemDTO> getList() {
		return list;
	}
	public void setList(List<ReportItemDTO> list) {
		this.list = list;
	}
	
	
}
