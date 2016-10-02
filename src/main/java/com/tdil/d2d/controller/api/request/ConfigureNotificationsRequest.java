package com.tdil.d2d.controller.api.request;

public class ConfigureNotificationsRequest extends ApiRequest {
	
	private boolean push;
	private boolean notes;
	private boolean congress;
	private boolean promotionsOffers;
	private boolean grantOffers;
	private boolean courses;
	private boolean productAndServices;
	private boolean notifAllDay;
	private boolean notif9to20;
	
	public boolean isPush() {
		return push;
	}
	public void setPush(boolean push) {
		this.push = push;
	}
	public boolean isNotes() {
		return notes;
	}
	public void setNotes(boolean notes) {
		this.notes = notes;
	}
	public boolean isCongress() {
		return congress;
	}
	public void setCongress(boolean congress) {
		this.congress = congress;
	}
	public boolean isPromotionsOffers() {
		return promotionsOffers;
	}
	public void setPromotionsOffers(boolean promotionsOffers) {
		this.promotionsOffers = promotionsOffers;
	}
	public boolean isGrantOffers() {
		return grantOffers;
	}
	public void setGrantOffers(boolean grantOffers) {
		this.grantOffers = grantOffers;
	}
	public boolean isCourses() {
		return courses;
	}
	public void setCourses(boolean courses) {
		this.courses = courses;
	}
	public boolean isProductAndServices() {
		return productAndServices;
	}
	public void setProductAndServices(boolean productAndServices) {
		this.productAndServices = productAndServices;
	}
	public boolean isNotifAllDay() {
		return notifAllDay;
	}
	public void setNotifAllDay(boolean notifAllDay) {
		this.notifAllDay = notifAllDay;
	}
	public boolean isNotif9to20() {
		return notif9to20;
	}
	public void setNotif9to20(boolean notif9to20) {
		this.notif9to20 = notif9to20;
	}
	
}
