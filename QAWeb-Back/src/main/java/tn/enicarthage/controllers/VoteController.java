package tn.enicarthage.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.VoteDTO;
import tn.enicarthage.services.answer.AnswerService;
import tn.enicarthage.services.vote.VoteService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoteController {

	private final VoteService voteService;
	
	@PostMapping("/vote")
	public ResponseEntity<?> addVote(@RequestBody VoteDTO voteDTO) {
		try {
			VoteDTO createdVoteDTO = voteService.addVote(voteDTO);
			if(createdVoteDTO == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(createdVoteDTO);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
}
