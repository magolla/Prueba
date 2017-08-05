package com.tdil.d2d.bo.dto;

import java.util.List;

public class SubscriptionReportDTO {

	private long countAllUsers;
	private long countUsersB;
	private long countSubscriptions;
	List<ReportItemDTO> list;
	

	
	public long getCountAllUsers() {
		return countAllUsers;
	}
	public void setCountAllUsers(long countAllUsers) {
		this.countAllUsers = countAllUsers;
	}
	public long getCountUsersB() {
		return countUsersB;
	}
	public void setCountUsersB(long countUsersB) {
		this.countUsersB = countUsersB;
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
