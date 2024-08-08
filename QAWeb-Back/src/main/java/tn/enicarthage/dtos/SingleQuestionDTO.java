package tn.enicarthage.dtos;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.entities.Question;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SingleQuestionDTO {
	
	QuestionDTO questionDTO;
	
	List<AnswerDTO> answerDTOList;
	
	List<CommentDTO> commentDTOList;
	
	List<QuestionDTO> similarQuestionsDTOList;
}
