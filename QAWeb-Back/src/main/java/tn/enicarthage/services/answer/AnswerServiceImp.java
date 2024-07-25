package tn.enicarthage.services.answer;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AnswerDTO;
import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.AnswerRepository;
import tn.enicarthage.repositories.QuestionRepository;
import tn.enicarthage.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AnswerServiceImp implements AnswerService{

	private final UserRepository userRepository;
	
	private final QuestionRepository questionRepository;
	
	private final AnswerRepository answerRepository;
	
	
	@Override
	public AnswerDTO postAnswer(AnswerDTO answerDTO) {

		Optional<User> optionalUser = userRepository.findById(answerDTO.getUserId());
		Optional<Question> optionalQuestion = questionRepository.findById(answerDTO.getQuestionId());
		if(optionalUser.isPresent() && optionalQuestion.isPresent()) {
			Answer answer = new Answer();
			answer.setBody(answerDTO.getBody());
			answer.setCreatedDate(new Date());
			answer.setQuestion(optionalQuestion.get());
			answer.setUser(optionalUser.get());
			Answer createdAnswer = answerRepository.save(answer);
			
			AnswerDTO createdAnswerDTO = new AnswerDTO();
			createdAnswerDTO.setId(createdAnswer.getId());
			
			return createdAnswerDTO;
		}
		return null;
	}

}
