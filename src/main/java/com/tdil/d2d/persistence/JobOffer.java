package com.tdil.d2d.persistence;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tdil.d2d.controller.api.request.InstitutionType;

@Entity
@Table(name = "D2D_JOBOFFER")
public class JobOffer implements PersistentEntity {
	
	public static String VACANT = "VACANT";
	public static String CLOSED = "CLOSED";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@ManyToOne
	private Occupation occupation;
	
	@ManyToOne
	private Specialty specialty;
	
	@ManyToOne
	private Task task;

	@Column(name = "institutionType")
	@Enumerated(javax.persistence.EnumType.STRING)
	private InstitutionType institutionType;
	
	@Column(name="companyScreenName", length=256)
	private String companyScreenName;
	
	@Column(name = "geoLevelLevel")
	private int geoLevelLevel;
	
	@Column(name = "geoLevelId")
	private long geoLevelId;
	
	@Column(name = "offerDate")
	private Date offerDate;
	
	@Column(name="hour")
	private String hour;
	
	@Column(name="permanent")
	private boolean permanent;

	// Solo si es permanente
	@Column(name="title")
	private String title;
	@Column(name="subtitle")
	private String subtitle;
	
	@Column(name="comment", length=4000)
	private String comment;
	
	@Column(name="vacants")
	private Integer vacants;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne
	private User offerent;
	
	
	private int applications;

	@Column(name="jobApplication_id")
	private Integer jobApplication_id;

	public Integer getJobApplication_id() {
		return jobApplication_id;
	}

	public void setJobApplication_id(Integer jobApplication_id) {
		this.jobApplication_id = jobApplication_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public Date getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getVacants() {
		return vacants;
	}

	public void setVacants(Integer vacants) {
		this.vacants = vacants;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getOfferent() {
		return offerent;
	}

	public void setOfferent(User offerent) {
		this.offerent = offerent;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getGeoLevelLevel() {
		return geoLevelLevel;
	}

	public void setGeoLevelLevel(int geoLevelLevel) {
		this.geoLevelLevel = geoLevelLevel;
	}

	public long getGeoLevelId() {
		return geoLevelId;
	}

	public void setGeoLevelId(long geoLevelId) {
		this.geoLevelId = geoLevelId;
	}

	public Boolean isExpired() {
		// TODO tema hora
		Calendar cal = Calendar.getInstance();
		cal.setTime(getOfferDate());
		int hour = Integer.parseInt(this.getHour().substring(0,2));
		int minutes = Integer.parseInt(this.getHour().substring(2));
		// TODO Auto-generated method stub
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		if (cal.before(Calendar.getInstance())) {
			return true;
		}
		return false;
	}
	
	public void setExpired(Boolean expired){}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public String getCompanyScreenName() {
		return companyScreenName;
	}

	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getApplications() {
		return applications;
	}

	public void setApplications(int applications) {
		this.applications = applications;
	}
}
