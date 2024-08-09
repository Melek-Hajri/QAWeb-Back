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
import tn.enicarthage.dtos.CommentDTO;
import tn.enicarthage.dtos.QuestionDTO;
import tn.enicarthage.dtos.QuestionSearchResponseDTO;
import tn.enicarthage.dtos.SingleQuestionDTO;
import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Comment;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.entities.Vote;
import tn.enicarthage.enums.VoteType;
import tn.enicarthage.repositories.AnswerRepository;
import tn.enicarthage.repositories.CommentRepository;
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
	
	private final CommentRepository commentRepository;
	
	private final SimilarityService similarityService;
	
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
		allQuestionsResponseDTO.setTotalElements(questionPage.getTotalElements());

		return allQuestionsResponseDTO;
	}


	@Override
	public SingleQuestionDTO getQuestionById(Long questionId, Long userId) {
	    Optional<Question> optionalQuestion = questionRepository.findById(questionId);
	    if (optionalQuestion.isPresent()) {
	        Question existingQuestion = optionalQuestion.get();
	        SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
	        List<AnswerDTO> answerDTOList = new ArrayList<>();
	        Optional<Vote> optionalVote = existingQuestion.getVoteList().stream()
	                .filter(vote -> vote.getUser().getId().equals(userId))
	                .findFirst();
	        QuestionDTO questionDTO = existingQuestion.getQuestionDTO();
	        questionDTO.setVoted(0);
	        if (optionalVote.isPresent()) {
	            if (optionalVote.get().getVoteType().equals(VoteType.UPVOTE)) {
	                questionDTO.setVoted(1);
	            } else {
	                questionDTO.setVoted(-1);
	            }
	        }
	        singleQuestionDTO.setQuestionDTO(questionDTO);
	        singleQuestionDTO.getQuestionDTO().setFiles(imageRepository.findAllByQuestionId(questionId));

	        List<CommentDTO> questionCommentDTOList = existingQuestion.getCommentList().stream()
	                .map(Comment::getCommentDTO)
	                .collect(Collectors.toList());
	        singleQuestionDTO.setCommentDTOList(questionCommentDTOList);

	        List<Answer> answerList = answerRepository.findAllByQuestionId(questionId);
	        for (Answer answer : answerList) {
	            AnswerDTO answerDTO = answer.getAnswerDTO();
	            answerDTO.setFiles(imageRepository.findAllByAnswerId(answerDTO.getId()));
	            Optional<Vote> optionalAnswerVote = answer.getVoteList().stream()
	                    .filter(vote -> vote.getUser().getId().equals(userId))
	                    .findFirst();
	            answerDTO.setVoted(0);
	            if (optionalAnswerVote.isPresent()) {
	                if (optionalAnswerVote.get().getVoteType().equals(VoteType.UPVOTE)) {
	                    answerDTO.setVoted(1);
	                } else {
	                    answerDTO.setVoted(-1);
	                }
	            }
	            answerDTOList.add(answerDTO);

	            List<CommentDTO> commentDTOList = answer.getCommentList().stream()
	                    .map(Comment::getCommentDTO)
	                    .collect(Collectors.toList());
	            answerDTO.setCommentDTOList(commentDTOList);
	        }
	        singleQuestionDTO.setAnswerDTOList(answerDTOList);

	        // Get all questions to compare similarity
	        List<QuestionDTO> allQuestions = questionRepository.findAll().stream()
	                .map(Question::getQuestionDTO)
	                .collect(Collectors.toList());

	        // Determine similarity scores
	        List<QuestionDTO> similarQuestions = new ArrayList<>();
	        for (QuestionDTO q : allQuestions) {
	            if (q.getId() != questionId) {
	                Double similarityScore = similarityService.getSimilarityScore(questionDTO.getBody(), q.getBody());
	                if(similarityScore == null) System.out.println("sim is null");
	                if (similarityScore > 0.7) { // Threshold can be adjusted
	                    q.setSimilarityScore(similarityScore); // Ensure `QuestionDTO` has this field
	                    similarQuestions.add(q);
	                }
	            }
	        }

	        // Sort by similarity score (descending order)
	        similarQuestions.sort((q1, q2) -> q2.getSimilarityScore().compareTo(q1.getSimilarityScore()));
	        singleQuestionDTO.setSimilarQuestionsDTOList(similarQuestions);

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
		allQuestionsResponseDTO.setTotalElements(questionPage.getTotalElements());
		return allQuestionsResponseDTO;
	}


	@Override
	public QuestionSearchResponseDTO searchQuestionByTitleAndTagAndBody(String query, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, SEARCH_RESULT_PER_PAGE);
		Page<Question> questionPage;
		if(query == null || query.equals("null") || query.equals("")) {
			questionPage = questionRepository.findAll(pageable);
		} else {
			questionPage = questionRepository.findByTitleContainingOrBodyContainingOrTag(query, pageable);
		}
		QuestionSearchResponseDTO questionSearchResponseDTO = new QuestionSearchResponseDTO();
		questionSearchResponseDTO.setQuestionDTOList(questionPage.stream().map(Question::getQuestionDTO).collect(Collectors.toList()));
		questionSearchResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		questionSearchResponseDTO.setTotalPages(questionPage.getTotalPages());
		questionSearchResponseDTO.setTotalElements(questionPage.getTotalElements());
		return questionSearchResponseDTO;
	}


	@Override
	public QuestionSearchResponseDTO getLatestQuestions(int pageNum) {
		Pageable paging = PageRequest.of(pageNum, SEARCH_RESULT_PER_PAGE);
		Page<Question> questionPage = questionRepository.findAllByOrderByCreatedDateDesc(paging);
		QuestionSearchResponseDTO questionSearchResponseDTO = new QuestionSearchResponseDTO();
		questionSearchResponseDTO.setQuestionDTOList(questionPage.stream().map(Question::getQuestionDTO).collect(Collectors.toList()));
		questionSearchResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		questionSearchResponseDTO.setTotalPages(questionPage.getTotalPages());
		questionSearchResponseDTO.setTotalElements(questionPage.getTotalElements());
		return questionSearchResponseDTO;
	}
	
	@Override
	public QuestionSearchResponseDTO getHighestVotedQuestions(int pageNum) {
		Pageable paging = PageRequest.of(pageNum, SEARCH_RESULT_PER_PAGE);
		Page<Question> questionPage = questionRepository.findAllByOrderByVoteCountDesc(paging);
		QuestionSearchResponseDTO questionSearchResponseDTO = new QuestionSearchResponseDTO();
		questionSearchResponseDTO.setQuestionDTOList(questionPage.stream().map(Question::getQuestionDTO).collect(Collectors.toList()));
		questionSearchResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		questionSearchResponseDTO.setTotalPages(questionPage.getTotalPages());
		questionSearchResponseDTO.setTotalElements(questionPage.getTotalElements());
		return questionSearchResponseDTO;
	}
	
	
	@Override
	public CommentDTO postCommentToQuestion(CommentDTO commentDTO) {
		Optional<Question> optionalQuestion = questionRepository.findById(commentDTO.getQuestionId());
		Optional<User> optionalUser = userRepository.findById(commentDTO.getUserId());
		if(optionalQuestion.isPresent() && optionalUser.isPresent()) {
			Comment comment = new Comment();
			comment.setBody(commentDTO.getBody());
			comment.setCreatedDate(new Date());
			comment.setQuestion(optionalQuestion.get());
			comment.setUser(optionalUser.get());
			
			Comment createdComment = commentRepository.save(comment);
			
			CommentDTO createdCommentDTO = new CommentDTO();
			createdCommentDTO.setId(createdComment.getId());
			return createdCommentDTO;
		}
		
		return null;
	}
	

}
