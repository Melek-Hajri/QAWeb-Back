package tn.enicarthage.services.vote;

import java.io.IOException;

import tn.enicarthage.dtos.VoteDTO;

public interface VoteService {

	VoteDTO addVote(VoteDTO voteDTO) throws IOException;

	void cancelQuestionVote(Long userId, Long questionId);

	void cancelAnswerVote(Long userId, Long answerId);
}
