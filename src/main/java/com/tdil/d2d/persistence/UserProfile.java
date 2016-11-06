package com.tdil.d2d.persistence;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tdil.d2d.controller.api.request.InstitutionType;

@Entity
@Table(name = "D2D_USERPROFILE")
public class UserProfile implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@ManyToOne
	private User user;
	
	@Column(name = "institutionType")
	@Enumerated(javax.persistence.EnumType.STRING)
	private InstitutionType institutionType;
	
	@ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="D2D_USERPROFILE_TASK", joinColumns=@JoinColumn(name="userprofile_id"), inverseJoinColumns=@JoinColumn(name="task_id"))
	private Set<Task> tasks = new HashSet<Task>();

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

}
