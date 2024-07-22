package tn.enicarthage.dtos;

import lombok.Data;

@Data
public class SignupRequest {
	
	Long id;
	
	String name;
	
	String email;
	
	String password;
}
