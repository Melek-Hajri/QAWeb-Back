package tn.enicarthage.dtos;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionSearchResponseDTO {
	
	List<QuestionDTO> questionDTOList;
	
	Integer totalPages;
	
	Integer pageNumber;
	
	long totalElements;
}
