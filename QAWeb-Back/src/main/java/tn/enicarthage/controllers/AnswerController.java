package tn.enicarthage.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AnswerDTO;
import tn.enicarthage.dtos.CommentDTO;
import tn.enicarthage.services.answer.AnswerService;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {

	
	private final AnswerService answerService;
	
	@PostMapping
	public ResponseEntity<?> addAnswer(@RequestBody AnswerDTO answerDTO) {
		AnswerDTO createdAnswerDTO = answerService.postAnswer(answerDTO);
		if(createdAnswerDTO == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDTO);
	}
	
	@GetMapping("/{answerId}")
	public ResponseEntity<AnswerDTO> approveAnswer(@PathVariable Long answerId) {
		AnswerDTO approvedAnswerDTO = answerService.approveAnswer(answerId);
		if(approvedAnswerDTO == null) {
			return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(approvedAnswerDTO);
	}
	
	@PostMapping("/comment")
	public ResponseEntity<?> postCommentToAnswer(@RequestBody CommentDTO commentDTO) {
		CommentDTO createdCommentDTO = answerService.postCommentToAnswer(commentDTO);
		if(createdCommentDTO == null) return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);
	}
}
