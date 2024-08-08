package tn.enicarthage.entities;

import java.util.Date;
import java.util.List;

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
import tn.enicarthage.dtos.CommentDTO;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String body;
	
	Date createdDate;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	User user;
	
	public CommentDTO getCommentDTO() {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(id);
		commentDTO.setBody(body);
		commentDTO.setCreatedDate(createdDate);
		if(question != null)
			commentDTO.setQuestionId(question.getId());
		if(answer != null)
			commentDTO.setAnswerId(answer.getId());
		commentDTO.setUserId(user.getId());
		commentDTO.setUsername(user.getName());
		
		return commentDTO;
	}
	
}
