package tn.enicarthage.dtos;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllUsersResponseDTO {
	
	List<UserDTO> userDTOList;
	
	Integer totalPages;
	
	Integer pageNumber;
	
	Long totalElements;


}
