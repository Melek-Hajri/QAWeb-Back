package tn.enicarthage.services.question;

import tn.enicarthage.dtos.AllQuestionsResponseDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.QuestionSearchResponseDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;

public interface QuestionService {

	QuestionDTO addQuestion(QuestionDTO questionDTO);

	AllQuestionsResponseDTO getAllQuestions(int pageNumber);

	AllQuestionsResponseDTO getQuestionsByUserId(Long userId, int pageNumber);

	SingleQuestionDTO getQuestionById(Long questionId, Long userId);

	QuestionSearchResponseDTO searchQuestionByTitle(String title, int pageNum);

	QuestionSearchResponseDTO getLatestQuestions(int pageNum);

	QuestionSearchResponseDTO getHighestVotedQuestions(int pageNum);
}
