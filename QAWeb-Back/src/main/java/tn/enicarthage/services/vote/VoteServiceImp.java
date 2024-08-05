package tn.enicarthage.services.vote;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.dtos.VoteDTO;
import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.entities.Vote;
import tn.enicarthage.enums.VoteType;
import tn.enicarthage.repositories.AnswerRepository;
import tn.enicarthage.repositories.QuestionRepository;
import tn.enicarthage.repositories.UserRepository;
import tn.enicarthage.repositories.VoteRepository;

@Service
@RequiredArgsConstructor
public class VoteServiceImp implements VoteService{

	private final VoteRepository voteRepository;

	private final UserRepository userRepository;

	private final QuestionRepository questionRepository;
	
	private final AnswerRepository answerRepository;

	@Override
	public VoteDTO addVote(VoteDTO voteDTO) throws IOException {
		Optional<User> optionalUser = userRepository.findById(voteDTO.getUserId());
		if(optionalUser.isPresent()) {
			if(voteDTO.getQuestionId() != null && voteDTO.getAnswerId() == null) {
				Optional<Question> optionalQuestion = questionRepository.findById(voteDTO.getQuestionId());
				if(optionalQuestion.isPresent()) {
					Vote vote = new Vote();
					vote.setVoteType(voteDTO.getVoteType());
					vote.setUser(optionalUser.get());
					vote.setQuestion(optionalQuestion.get());
					vote.setAnswer(null);
					if(voteDTO.getVoteType() == VoteType.UPVOTE) {
						optionalQuestion.get().setVoteCount(optionalQuestion.get().getVoteCount() + 1);
					} else {
						optionalQuestion.get().setVoteCount(optionalQuestion.get().getVoteCount() - 1);
					}
					questionRepository.save(optionalQuestion.get());
					Vote savedVote = voteRepository.save(vote);
					return savedVote.getVoteDTO();
				} else {
	                throw new IOException("Question not found");
	            }
			} else if(voteDTO.getQuestionId() == null && voteDTO.getAnswerId() != null) {
				Optional<Answer> optionalAnswer = answerRepository.findById(voteDTO.getAnswerId());
				if(optionalAnswer.isPresent()) {
					Vote vote = new Vote();
					vote.setVoteType(voteDTO.getVoteType());
					vote.setUser(optionalUser.get());
					vote.setAnswer(optionalAnswer.get());
					vote.setQuestion(null);
					if(voteDTO.getVoteType() == VoteType.UPVOTE) {
						optionalAnswer.get().setVoteCount(optionalAnswer.get().getVoteCount() + 1);
					} else {
						optionalAnswer.get().setVoteCount(optionalAnswer.get().getVoteCount() - 1);
					}
					answerRepository.save(optionalAnswer.get());
					Vote savedVote = voteRepository.save(vote);
					return savedVote.getVoteDTO();
				} else {
	                throw new IOException("Answer not found");
	            }
			} else {
				throw new IllegalArgumentException("Either answerId or questionId must be provided");
			}
		} else {
            throw new IOException("User not found");
        }
	}

	@Override
	public void cancelQuestionVote(Long userId, Long questionId) {
		
		User user = userRepository.findById(userId).get();
		Question question = questionRepository.findById(questionId).get();
		
		Optional<Vote> vote = voteRepository.findByUserAndQuestion(user, question);
		
		if(vote.isPresent()) {
			if(vote.get().getVoteType() == VoteType.UPVOTE) {
				question.setVoteCount(question.getVoteCount() - 1);
			} else {
				question.setVoteCount(question.getVoteCount() + 1);
			}
			voteRepository.deleteById(vote.get().getId());
			questionRepository.save(question);
		}
		
		
	}

	@Override
	public void cancelAnswerVote(Long userId, Long answerId) {
		
		User user = userRepository.findById(userId).get();
		Answer answer = answerRepository.findById(answerId).get();
		
		Optional<Vote> vote = voteRepository.findByUserAndAnswer(user, answer);
		
		if(vote.isPresent()) {
			if(vote.get().getVoteType() == VoteType.UPVOTE) {
				answer.setVoteCount(answer.getVoteCount() - 1);
			} else {
				answer.setVoteCount(answer.getVoteCount() + 1);
			}
			voteRepository.deleteById(vote.get().getId());
			answerRepository.save(answer);
		}
		
	}
}
