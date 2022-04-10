package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseEntity{
	@Enumerated(EnumType.STRING)
	@Column(length = 20,name="name")
	private UserRoles userRole;

	public Role(UserRoles userRole) {
		super();
		this.userRole = userRole;
	}

	public Role() {
		super();
	}

	public UserRoles getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "Role [userRole=" + userRole + "]";
	}

	
}
