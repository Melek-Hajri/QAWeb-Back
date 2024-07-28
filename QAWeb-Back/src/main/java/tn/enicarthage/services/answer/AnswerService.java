package tn.enicarthage.services.answer;

import tn.enicarthage.dtos.AnswerDTO;
import tn.enicarthage.dtos.CommentDTO;

public interface AnswerService {

	AnswerDTO postAnswer(AnswerDTO answerDTO);

	AnswerDTO approveAnswer(Long answerId);

	CommentDTO postCommentToAnswer(CommentDTO commentDTO);

}
