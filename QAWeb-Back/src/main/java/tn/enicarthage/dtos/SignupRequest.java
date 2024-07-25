package tn.enicarthage.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
	
	Long id;
	
	String name;
	
	String email;
	
	String password;
}
