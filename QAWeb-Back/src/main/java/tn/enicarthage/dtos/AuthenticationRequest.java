package tn.enicarthage.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {

	String email;
	
	String password;
}
