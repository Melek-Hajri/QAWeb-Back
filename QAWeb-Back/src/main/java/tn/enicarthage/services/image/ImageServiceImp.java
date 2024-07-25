package tn.enicarthage.services.image;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Image;
import tn.enicarthage.entities.Question;
import tn.enicarthage.repositories.AnswerRepository;
import tn.enicarthage.repositories.ImageRepository;
import tn.enicarthage.repositories.QuestionRepository;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {
    
    private final ImageRepository imageRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public void storeFile(MultipartFile multipartFile, Long answerId, Long questionId) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Image image = new Image();
        image.setName(fileName);
        image.setType(multipartFile.getContentType());
        image.setData(multipartFile.getBytes());

        if (answerId != null && questionId == null) {
            Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
            if (optionalAnswer.isPresent()) {
                image.setAnswer(optionalAnswer.get());
                image.setQuestion(null); // Ensure Question is null
                imageRepository.save(image);
            } else {
                throw new IOException("Answer not found");
            }
        } else if (questionId != null && answerId == null) {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (optionalQuestion.isPresent()) {
                image.setAnswer(null); // Ensure Answer is null
                image.setQuestion(optionalQuestion.get());
                imageRepository.save(image);
            } else {
                throw new IOException("Question not found");
            }
        } else {
            throw new IllegalArgumentException("Either answerId or questionId must be provided");
        }
    }
}
