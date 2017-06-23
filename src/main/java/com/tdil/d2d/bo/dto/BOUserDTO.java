package com.tdil.d2d.bo.dto;

import java.util.ArrayList;
import java.util.List;

public class BOUserDTO {

	private long id;
	
	private String name;

	private String email;
	
	private String password;

	private boolean active;
	
	private List<RoleDTO> roles;
	
	private List<Long> rolesIds;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
		List<Long> rolesIds = new ArrayList<Long>();
		for(RoleDTO role : roles){
			rolesIds.add(role.getId());
		}
		this.rolesIds = rolesIds;
	}
	
	public List<Long> getRolesIds() {
		return rolesIds;
	}
	
	public void setRolesIds(List<Long> rolesIds) {
		this.rolesIds = rolesIds;
	}

	public boolean hasRole(String role){
		for(RoleDTO dto : roles){
			if(dto.getName().equals(role)){
				return true;
			}
		}
		return false;
	}
	
}
