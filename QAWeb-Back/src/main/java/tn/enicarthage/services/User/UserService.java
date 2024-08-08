package tn.enicarthage.services.User;

import tn.enicarthage.dtos.AllUsersResponseDTO;
import tn.enicarthage.dtos.SignupRequest;
import tn.enicarthage.dtos.SingleUserDTO;
import tn.enicarthage.dtos.UserDTO;

public interface UserService {

	UserDTO createUser(SignupRequest signupDTO);

	AllUsersResponseDTO getAllUsers(int pageNumber, Long userId);

	SingleUserDTO getUserById(Long userId);

	void makeAdmin(Long userId);

	void deactivateUser(Long userId);

	void activateUser(Long userId);

	void revokeAdmin(Long userId);

}
