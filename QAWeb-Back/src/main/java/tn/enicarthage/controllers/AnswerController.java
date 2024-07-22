package tn.enicarthage.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.enicarthage.dtos.AnswerDTO;
import tn.enicarthage.services.answer.AnswerService;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

	
	private final AnswerService answerService;
	
	private AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}
	
	@PostMapping
	public ResponseEntity<?> addAnswer(@RequestBody AnswerDTO answerDTO) {
		AnswerDTO createdAnswerDTO = answerService.postAnswer(answerDTO);
		if(createdAnswerDTO == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDTO);
	}
}
