package tn.enicarthage.services.question;

import tn.enicarthage.dtos.AllQuestionsResponseDTO;
import tn.enicarthage.dtos.CommentDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.QuestionSearchResponseDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;

public interface QuestionService {

	QuestionDTO addQuestion(QuestionDTO questionDTO);

	AllQuestionsResponseDTO getAllQuestions(int pageNumber);

	AllQuestionsResponseDTO getQuestionsByUserId(Long userId, int pageNumber);

	SingleQuestionDTO getQuestionById(Long questionId, Long userId);

	QuestionSearchResponseDTO getLatestQuestions(int pageNum);

	QuestionSearchResponseDTO getHighestVotedQuestions(int pageNum);

	QuestionSearchResponseDTO searchQuestionByTitleAndTagAndBody(String query, int pageNum);

	CommentDTO postCommentToQuestion(CommentDTO commentDTO);
}
