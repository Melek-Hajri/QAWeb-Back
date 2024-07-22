package tn.enicarthage.dtos;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String jwtToken;

	public AuthenticationResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}
	
}
