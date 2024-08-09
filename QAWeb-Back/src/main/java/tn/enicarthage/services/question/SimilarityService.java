package tn.enicarthage.services.question;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class SimilarityService {

    private final WebClient webClient;

    public SimilarityService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public Double getSimilarityScore(String question1, String question2) {
        String url = "http://localhost:8000/predict/";
        QuestionPair questionPair = new QuestionPair(question1, question2);
        return webClient.post().uri(url)
                .bodyValue(questionPair)
                .retrieve()
                .bodyToMono(String.class)
                .map(responseBody -> {
                    try {
                        // Parse the response body to extract the similarity score
                        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
                        return jsonNode.get("similarity_score").asDouble();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error parsing JSON response", e);
                    }
                })
                .block();
    }
}

class QuestionPair {
	private String text1;
    private String text2;
    

	public QuestionPair(String text1, String text2) {
		super();
		this.text1 = text1;
		this.text2 = text2;
	}
	
    public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
}

class SimilarityResponse {
    private Double similarityScore;

	public Double getSimilarityScore() {
		return similarityScore;
	}

	public void setSimilarityScore(Double similarityScore) {
		this.similarityScore = similarityScore;
	}
	
}
