package tn.enicarthage.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class QuestionDTO {
	
	Long id;
	
	String title;
	
	String body;
	
	List<String> tags;
	
	Long userId;
	
	String username;
	
	Date createdDate; 
}
