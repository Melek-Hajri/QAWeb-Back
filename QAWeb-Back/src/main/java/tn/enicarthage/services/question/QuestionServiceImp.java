package tn.enicarthage.services.question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AllQuestionsResponseDTO;
import tn.enicarthage.dtos.AnswerDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;
import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.AnswerRepository;
import tn.enicarthage.repositories.ImageRepository;
import tn.enicarthage.repositories.QuestionRepository;
import tn.enicarthage.repositories.UserRepository;
import tn.enicarthage.services.answer.AnswerService;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService{

	public static final int SEARCH_RESULT_PER_PAGE = 5;
	
	private final QuestionRepository questionRepository;

	private final UserRepository userRepository;

	private final AnswerRepository answerRepository;

	private final ImageRepository imageRepository;
	
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
		if(optionalQuestion.isPresent()) {
			SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
			List<AnswerDTO> answerDTOList = new ArrayList<>();
			singleQuestionDTO.setQuestionDTO(optionalQuestion.get().getQuestionDTO());
			singleQuestionDTO.getQuestionDTO().setFiles(imageRepository.findAllByQuestionId(questionId));
			List<Answer> answerList = answerRepository.findAllByQuestionId(questionId);
			for(Answer answer : answerList) {
				AnswerDTO answerDTO = answer.getAnswerDTO();
				answerDTO.setFiles(imageRepository.findAllByAnswerId(answerDTO.getId()));
				answerDTOList.add(answerDTO);
			}
			singleQuestionDTO.setAnswerDTOList(answerDTOList);
			return singleQuestionDTO;
		}
		return null;
	}


	@Override
	public AllQuestionsResponseDTO getQuestionsByUserId(Long userId, int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
		Page<Question> questionPage = questionRepository.findAllByUserId(userId, paging);
		
		AllQuestionsResponseDTO allQuestionsResponseDTO = new AllQuestionsResponseDTO();
		allQuestionsResponseDTO.setQuestionDTOList(questionPage.getContent().stream().map(Question::getQuestionDTO).collect(Collectors.toList()));
		allQuestionsResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		allQuestionsResponseDTO.setTotalPages(questionPage.getTotalPages());
		return allQuestionsResponseDTO;
	}
	
	
	

}
