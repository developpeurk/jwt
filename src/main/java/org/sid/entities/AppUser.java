package org.sid.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// @Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String userName;
	//@JsonIgnore
	private String password;
	// chaque fois je charge un utilisateur je charge les roles aussi
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<AppRole> roles = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonSetter

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "AppUser [id=" + id + ", userName=" + userName + ", password=" + password + ", roles=" + roles + "]";
	}

	public AppUser(Long id, String userName, String password, Collection<AppRole> roles) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.roles = roles;
	}

	public AppUser() {
		super();
	}

}
