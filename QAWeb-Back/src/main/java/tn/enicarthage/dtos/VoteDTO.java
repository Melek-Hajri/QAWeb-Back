package tn.enicarthage.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.enums.VoteType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoteDTO {
	
	Long id;
	
	VoteType voteType;
	
	Long userId;
	
	Long questionId;
	
	Long answerId;
}
