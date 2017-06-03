package com.tdil.d2d.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_BOUSER")
public class BOUser implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "lastLoginDate")
	private Date lastLoginDate;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "email")
	private String email;
	
	@Column(name="pass")
	private String password;

	@Column(name = "enabled")
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "D2D_USER_ROLE", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",
					nullable = false, updatable = false)})
	private Set<Role> roles = new HashSet<Role>(0);
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getSalt() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(this.getCreationDate());
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> userRole) {
		this.roles = userRole;
	}

	
	
}
