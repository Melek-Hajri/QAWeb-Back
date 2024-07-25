package tn.enicarthage.services.question;

import tn.enicarthage.dtos.AllQuestionsResponseDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;

public interface QuestionService {

	QuestionDTO addQuestion(QuestionDTO questionDTO);

	AllQuestionsResponseDTO getAllQuestions(int pageNumber);

	SingleQuestionDTO getQuestionById(Long questionId);

	AllQuestionsResponseDTO getQuestionsByUserId(Long userId, int pageNumber);

}
