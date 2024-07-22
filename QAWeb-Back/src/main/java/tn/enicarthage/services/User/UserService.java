package tn.enicarthage.services.User;

import tn.enicarthage.dtos.SignupRequest;
import tn.enicarthage.dtos.UserDTO;

public interface UserService {

	UserDTO createUser(SignupRequest signupDTO);

}
