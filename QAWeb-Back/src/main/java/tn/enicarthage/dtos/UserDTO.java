package tn.enicarthage.dtos;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
	
	Long id;
	
	String name;
	
	String email;
	
	Date joinedDate;
	
	boolean isAdmin;
	
	boolean isActive;
}
