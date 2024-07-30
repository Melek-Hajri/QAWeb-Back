package tn.enicarthage.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AuthenticationRequest;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.UserRepository;
import tn.enicarthage.services.answer.AnswerService;
import tn.enicarthage.services.utils.JwtUtil;



@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final JwtUtil jwtUtil;

	private final UserRepository userRepo;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";
	
	@PostMapping("/login")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect email or password");
		} catch(DisabledException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
			return;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		
		Optional<User> optionalUser = this.userRepo.findFirstByEmailAndIsActive(userDetails.getUsername(), true);
		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		if(optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject()
					.put("userId", optionalUser.get().getId())
					.put("isAdmin", optionalUser.get().isAdmin())
					.toString());
		}
		
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Access-Control-Expose-Headers", "Authorization, X-PINGOTHER, X-Requested-With, Content-Type, Accept, X-Custom-Header");
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

	}
}
