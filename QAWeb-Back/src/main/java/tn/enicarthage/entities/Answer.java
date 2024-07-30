package tn.enicarthage.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import tn.enicarthage.dtos.AnswerDTO;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Lob
	@Column(name = "body", length = 512)
	String body;
	
	Date createdDate;
	
	boolean approved = false;
	
	Integer voteCount = 0;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Question question;
	
	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Vote> voteList;
	
	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Comment> commentList;

	public AnswerDTO getAnswerDTO() {
		AnswerDTO answerDTO = new AnswerDTO();
		answerDTO.setId(id);
		answerDTO.setBody(body);
		answerDTO.setCreatedDate(createdDate);
		answerDTO.setApproved(approved);
		answerDTO.setVoteCount(voteCount);
		answerDTO.setUserId(user.getId());
		answerDTO.setQuestionId(question.getId());
		answerDTO.setQuestionTitle(question.getTitle());
		answerDTO.setUsername(user.getName());
		return answerDTO;
	}
}
