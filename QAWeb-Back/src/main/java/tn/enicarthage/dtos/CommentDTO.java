package tn.enicarthage.dtos;



import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDTO {
	
	Long id;
	
	String body;
	
	Date createdDate;
	
	Long answerId;
	
	Long userId;
	
	String username;
}
