package tn.enicarthage.dtos;

import lombok.Data;

@Data
public class AnswerDTO {
	
	Long id;
	
	String body;
	
	Long questionId;
	
	Long userId;
}
