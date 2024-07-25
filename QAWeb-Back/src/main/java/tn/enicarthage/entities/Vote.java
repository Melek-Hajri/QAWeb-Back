package tn.enicarthage.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.dtos.VoteDTO;
import tn.enicarthage.enums.VoteType;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	VoteType voteType;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "question_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Question question;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "answer_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Answer answer;

	public VoteDTO getVoteDTO() {
		VoteDTO voteDTO = new VoteDTO();
		voteDTO.setId(id);
		voteDTO.setVoteType(voteType);
		if(answer != null)
			voteDTO.setAnswerId(answer.getId());
		if(question != null)
			voteDTO.setQuestionId(question.getId());
		voteDTO.setUserId(user.getId());
		return voteDTO;
	}
	
}
