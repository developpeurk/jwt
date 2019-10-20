package org.sid.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppRole {
	@Id @GeneratedValue
private Long id;
private String roleName;

//pas besoin d'une association bi directionnel 
//parve que je n'ai pas besoin d'un role de savoir les utilisateurs
}
