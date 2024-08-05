package tn.enicarthage.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AllQuestionsResponseDTO;
import tn.enicarthage.dtos.AllUsersResponseDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;
import tn.enicarthage.dtos.SingleUserDTO;
import tn.enicarthage.services.User.UserService;
import tn.enicarthage.services.question.QuestionService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/users/{pageNumber}/{userId}")
    public ResponseEntity<?> getAllUsers(@PathVariable int pageNumber, @PathVariable Long userId) {
        AllUsersResponseDTO allUsersResponseDTO = userService.getAllUsers(pageNumber, userId);
        
        if(allUsersResponseDTO == null) {
    		return ResponseEntity.badRequest().body("You have no access privileges!");
    	} 
    	return ResponseEntity.ok(allUsersResponseDTO);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
    	SingleUserDTO singleUserDTO = userService.getUserById(userId);
    	if(singleUserDTO == null) {
    		return ResponseEntity.notFound().build();
    	} 
    	return ResponseEntity.ok(singleUserDTO);
    }
    
    @PostMapping("/users/deactivate/{userId}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/activate/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
        userService.activateUser(userId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/users/make_admin/{userId}") 
    public ResponseEntity<?> makeAdmin(@PathVariable Long userId) {
        userService.makeAdmin(userId);
        return ResponseEntity.ok().build();
    }

}
