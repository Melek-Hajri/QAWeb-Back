package tn.enicarthage.services.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AllUsersResponseDTO;
import tn.enicarthage.dtos.AnswerDTO;
import tn.enicarthage.dtos.CommentDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.SignupRequest;
import tn.enicarthage.dtos.SingleUserDTO;
import tn.enicarthage.dtos.UserDTO;
import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Comment;
import tn.enicarthage.entities.ConfirmationToken;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.AnswerRepository;
import tn.enicarthage.repositories.CommentRepository;
import tn.enicarthage.repositories.ConfirmationTokenRepository;
import tn.enicarthage.repositories.ImageRepository;
import tn.enicarthage.repositories.QuestionRepository;
import tn.enicarthage.repositories.UserRepository;
import tn.enicarthage.services.answer.AnswerService;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
	
	public static final int SEARCH_RESULT_PER_PAGE = 5;

    private final UserRepository userRepository;
    
    private final QuestionRepository questionRepository;
    
    private final AnswerRepository answerRepository;
    
    private final CommentRepository commentRepository;
    
    private final ImageRepository imageRepository;
    
    private final ConfirmationTokenRepository confirmationTokenRepository;
    
    private final EmailServiceImp emailService;
	
    @Override
    @Transactional
    public UserDTO createUser(SignupRequest signupDTO) {
        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setName(signupDTO.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        user.setJoinedDate(new Date());
        user.setActive(false); // User is not active until email confirmation

        User createdUser = userRepository.save(user);

        // Create and save confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);
        confirmationTokenRepository.save(confirmationToken);

        // Send confirmation email
        String confirmationLink = "http://localhost:8085/confirm-account?token=" + confirmationToken.getToken();
        emailService.sendEmail(
            user.getEmail(),
            "Email Confirmation",
            "To confirm your account, please click the link: " + confirmationLink
        );

        UserDTO createdUserDTO = new UserDTO();
        createdUserDTO.setId(createdUser.getId());
        return createdUserDTO;
    }

	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmailAndIsActive(email, true).isPresent();
	}
	
	@Override
	public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setActive(false);
        userRepository.save(user);
    }

	@Override
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setActive(true);
        userRepository.save(user);
    }

	@Override
    public AllUsersResponseDTO getAllUsers(int pageNumber, Long userId) {
    	
		User user = userRepository.findById(userId).get();
		
		if(user.isAdmin()) {
			Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
	    	Page<User> userPage = userRepository.findAll(paging);
			
	    	AllUsersResponseDTO allUsersResponseDTO = new AllUsersResponseDTO();
	    	allUsersResponseDTO.setUserDTOList(userPage.getContent().stream().map(User::getUserDTO).collect(Collectors.toList()));
	    	allUsersResponseDTO.setPageNumber(userPage.getPageable().getPageNumber());
	    	allUsersResponseDTO.setTotalPages(userPage.getTotalPages());
	    	allUsersResponseDTO.setTotalElements(userPage.getTotalElements());

			return allUsersResponseDTO;
		} else return null;	
    }

	@Override
    public SingleUserDTO getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
        	SingleUserDTO singleUserDTO = new SingleUserDTO();
        	singleUserDTO.setUserDTO(optionalUser.get().getUserDTO());
        	
        	// Fetch and set questions
            List<QuestionDTO> questionDTOList = questionRepository.findAllByUserId(userId).stream()
                    .map(Question::getQuestionDTO)
                    .collect(Collectors.toList());
            singleUserDTO.setQuestionDTOList(questionDTOList);

            // Fetch and set answers
            List<AnswerDTO> answerDTOList = answerRepository.findAllByUserId(userId).stream()
                    .map(Answer::getAnswerDTO)
                    .collect(Collectors.toList());
            for(AnswerDTO answerDTO : answerDTOList) {
            	answerDTO.setFiles(imageRepository.findAllByAnswerId(answerDTO.getId()));
            }
            singleUserDTO.setAnswerDTOList(answerDTOList);

            // Fetch and set comments
            List<CommentDTO> commentDTOList = commentRepository.findAllByUserId(userId).stream()
                    .map(Comment::getCommentDTO)
                    .collect(Collectors.toList());
            singleUserDTO.setCommentDTOList(commentDTOList);
            

        	return singleUserDTO;
        }
        return null;
    }
    
	@Override
    public void makeAdmin(Long userId) {
    	Optional<User> optionalUser = userRepository.findById(userId);
    	if(optionalUser.isPresent()) {
    		User user = optionalUser.get();
    		user.setAdmin(true);
    		User updatedUser = userRepository.save(user);
    		
    	}
    }

	@Override
	public void revokeAdmin(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
    	if(optionalUser.isPresent()) {
    		User user = optionalUser.get();
    		user.setAdmin(false);
    		User updatedUser = userRepository.save(user);
    		
    	}
		
	}
}
