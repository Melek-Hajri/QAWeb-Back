package tn.enicarthage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import tn.enicarthage.dtos.SignupRequest;
import tn.enicarthage.dtos.UserDTO;
import tn.enicarthage.repositories.UserRepository;
import tn.enicarthage.services.User.UserServiceImp;

@RestController
@RequestMapping("/signup")
public class SignUpController {
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest) {
		
//		if(this.userRepo.findByEmail(signupDTO.getEmail()).isPresent()) {
//			String msg = "A user with this email: " + signupDTO.getEmail() + " already exists";
//			return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
//		}
//		if(this.userRepo.findByUsername(signupDTO.getUsername()).isPresent()) {
//			String msg = "A user with this username: " + signupDTO.getUsername() + " already exists";
//			return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
//		}
		
		if(userService.hasUserWithEmail(signupRequest.getEmail())) {
			String msg = "A user with this email: " + signupRequest.getEmail() + " already exists";
			return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
		}
			
		UserDTO createdUser = this.userService.createUser(signupRequest);
		if(createdUser == null)
			return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
}
