package tn.enicarthage.services.User;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.SignupRequest;
import tn.enicarthage.dtos.UserDTO;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.UserRepository;
import tn.enicarthage.services.answer.AnswerService;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepo;
	
	@Override
	@Transactional
	public UserDTO createUser(SignupRequest signupDTO) {
		User user = new User();
		user.setEmail(signupDTO.getEmail());
		user.setName(signupDTO.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
		//user.setRole(RoleType.SimpleUser);
		User createdUser = userRepo.save(user);
		UserDTO createdUserDTO = new UserDTO();
		createdUserDTO.setId(createdUser.getId());
		return createdUserDTO;
	}

	public boolean hasUserWithEmail(String email) {
		return userRepo.findFirstByEmail(email).isPresent();
	}
}
