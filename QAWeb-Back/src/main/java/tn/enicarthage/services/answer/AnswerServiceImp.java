package tn.enicarthage.services.answer;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.AnswerDTO;
import tn.enicarthage.dtos.CommentDTO;
import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Comment;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.AnswerRepository;
import tn.enicarthage.repositories.CommentRepository;
import tn.enicarthage.repositories.QuestionRepository;
import tn.enicarthage.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AnswerServiceImp implements AnswerService{

	private final UserRepository userRepository;
	
	private final QuestionRepository questionRepository;
	
	private final AnswerRepository answerRepository;
	
	private final CommentRepository commentRepository;
	
	
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
	
	
	@Override
	public AnswerDTO approveAnswer(Long answerId) {
		Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
		if(optionalAnswer.isPresent()) {
			Question question = optionalAnswer.get().getQuestion();
			question.setSolved(true);
			questionRepository.save(question);
			Answer answer = optionalAnswer.get();
			answer.setApproved(true);
			Answer updatedAnswer = answerRepository.save(answer);
			AnswerDTO updatedAnswerDTO = new AnswerDTO();
			updatedAnswerDTO.setId(updatedAnswer.getId());
			return updatedAnswerDTO;
		}
		return null;
	}


	@Override
	public CommentDTO postCommentToAnswer(CommentDTO commentDTO) {
		Optional<Answer> optionalAnswer = answerRepository.findById(commentDTO.getAnswerId());
		Optional<User> optionalUser = userRepository.findById(commentDTO.getUserId());
		if(optionalAnswer.isPresent() && optionalUser.isPresent()) {
			Comment comment = new Comment();
			comment.setBody(commentDTO.getBody());
			comment.setCreatedDate(new Date());
			comment.setAnswer(optionalAnswer.get());
			comment.setUser(optionalUser.get());
			
			Comment createdComment = commentRepository.save(comment);
			
			CommentDTO createdCommentDTO = new CommentDTO();
			createdCommentDTO.setId(createdComment.getId());
			return createdCommentDTO;
		}
		
		return null;
	}

}
