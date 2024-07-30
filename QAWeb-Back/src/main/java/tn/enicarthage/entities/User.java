package tn.enicarthage.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.dtos.UserDTO;

@Entity
@Data
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String name;
	
	String email;
	
	String password;
	
	Date joinedDate;
	
	boolean isAdmin = false;
	
	boolean isActive = true;
	
	
	public UserDTO getUserDTO() {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(id);
		userDTO.setEmail(email);
		userDTO.setName(name);
		userDTO.setJoinedDate(joinedDate);
		userDTO.setAdmin(isAdmin);
		userDTO.setActive(isActive);
		return userDTO;
	}
	
}
