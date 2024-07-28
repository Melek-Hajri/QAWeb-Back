package tn.enicarthage.dtos;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.entities.Image;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDTO {
	
	Long id;
	
	String body;
	
	Integer voteCount;
	
	Long questionId;
	
	Long userId;
	
	Date createdDate;
	
	boolean approved;
	
	String username;
	
	List<Image> files;
	
	int voted;
	
	List<CommentDTO> commentDTOList;
}
