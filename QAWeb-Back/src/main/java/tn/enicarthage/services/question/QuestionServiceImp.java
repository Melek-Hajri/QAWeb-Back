package tn.enicarthage.services.question;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tn.enicarthage.dtos.AllQuestionsResponseDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.QuestionRepository;
import tn.enicarthage.repositories.UserRepository;

@Service
public class QuestionServiceImp implements QuestionService{

	public static final int SEARCH_RESULT_PER_PAGE = 5;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public QuestionDTO addQuestion(QuestionDTO questionDTO) {

        Optional<User> optionalUser = userRepository.findById(questionDTO.getUserId());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            Question question = new Question();
            question.setTitle(questionDTO.getTitle());
            question.setBody(questionDTO.getBody());
            question.setTags(questionDTO.getTags());
            question.setCreatedDate(new Date());
            question.setUser(user); 

            Question createdQuestion = questionRepository.save(question);

            QuestionDTO createdQuestionDTO = new QuestionDTO();
            createdQuestionDTO.setId(createdQuestion.getId());
            createdQuestionDTO.setTitle(createdQuestion.getTitle());
            createdQuestionDTO.setBody(createdQuestion.getBody());
            createdQuestionDTO.setTags(createdQuestion.getTags());
            createdQuestionDTO.setUserId(createdQuestion.getUser().getId());

            return createdQuestionDTO;
        }
        return null;
    }

	
	@Override
	public AllQuestionsResponseDTO getAllQuestions(int pageNumber) {
		
		Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
		Page<Question> questionPage = questionRepository.findAll(paging);
		
		AllQuestionsResponseDTO allQuestionsResponseDTO = new AllQuestionsResponseDTO();
		allQuestionsResponseDTO.setQuestionDTOList(questionPage.getContent().stream().map(Question::getQuestionDTO).collect(Collectors.toList()));
		allQuestionsResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		allQuestionsResponseDTO.setTotalPages(questionPage.getTotalPages());
		return allQuestionsResponseDTO;
	}


	@Override
	public SingleQuestionDTO getQuestionById(Long questionId) {
		Optional<Question> optionalQuestion = questionRepository.findById(questionId);
		SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
		optionalQuestion.ifPresent(question -> singleQuestionDTO.setQuestionDTO(question.getQuestionDTO()));
		return singleQuestionDTO;
	}

}
