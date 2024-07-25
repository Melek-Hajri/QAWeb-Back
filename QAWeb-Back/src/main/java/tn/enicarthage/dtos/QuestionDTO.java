package tn.enicarthage.dtos;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.entities.Image;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDTO {
	
	Long id;
	
	String title;
	
	String body;
	
	Integer voteCount;
	
	List<String> tags;
	
	Long userId;
	
	String username;
	
	Date createdDate; 
	
	List<Image> files;
}
