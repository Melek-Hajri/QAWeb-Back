package tn.enicarthage.dtos;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.entities.Image;
import tn.enicarthage.entities.Question;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDTO {
	
	Long id;
	
	String title;
	
	String body;
	
	Integer voteCount;
	
	boolean solved;
	
	List<String> tags;
	
	Long userId;
	
	String username;
	
	Date createdDate; 
	
	List<Image> files;
	
	int voted;
	
	Double similarityScore; 
}
