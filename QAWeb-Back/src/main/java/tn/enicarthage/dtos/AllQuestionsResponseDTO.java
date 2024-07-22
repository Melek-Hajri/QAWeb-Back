package tn.enicarthage.dtos;

import java.util.List;

import lombok.Data;

@Data
public class AllQuestionsResponseDTO {

	List<QuestionDTO> questionDTOList;
	
	Integer totalPages;
	
	Integer pageNumber;
	
}
