package tn.enicarthage.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AllQuestionsResponseDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;
import tn.enicarthage.services.answer.AnswerService;
import tn.enicarthage.services.question.QuestionService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {
    
    private final QuestionService questionService;
    
    @PostMapping("/question")
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDTO) {
        QuestionDTO createdQuestionDTO = questionService.addQuestion(questionDTO);
        if (createdQuestionDTO == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
    }
    
    @GetMapping("/questions/{pageNumber}")
    public ResponseEntity<AllQuestionsResponseDTO> getAllQuestions(@PathVariable int pageNumber) {
        AllQuestionsResponseDTO allQuestionsResponseDTO = questionService.getAllQuestions(pageNumber);
        return ResponseEntity.ok(allQuestionsResponseDTO);
    }
    
    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId) {
    	SingleQuestionDTO singleQuestionDTO = questionService.getQuestionById(questionId);
    	if(singleQuestionDTO == null) {
    		return ResponseEntity.notFound().build();
    	} 
    	return ResponseEntity.ok(singleQuestionDTO);
    }
    
    @GetMapping("/questions/{userId}/{pageNumber}")
    public ResponseEntity<AllQuestionsResponseDTO> getQuestionsByUserId(@PathVariable Long userId, @PathVariable int pageNumber) {
        AllQuestionsResponseDTO allQuestionsResponseDTO = questionService.getQuestionsByUserId(userId, pageNumber);
        return ResponseEntity.ok(allQuestionsResponseDTO);
    }
}
