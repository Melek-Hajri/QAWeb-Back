package tn.enicarthage.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import tn.enicarthage.dtos.QuestionDTO;

@Entity
@Data
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String title;
	
	@Lob
	@Column(name = "body", length = 512)
	String body;
	
	Date createdDate;
	
	@ElementCollection
	@CollectionTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "tag")
	List<String> tags;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	User user;
	
	
	public QuestionDTO getQuestionDTO() {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setId(id);
		questionDTO.setTags(tags);
		questionDTO.setTitle(title);
		questionDTO.setBody(body);
		questionDTO.setUserId(user.getId());
		questionDTO.setUsername(user.getName());
		questionDTO.setCreatedDate(createdDate);
		return questionDTO;
	}
}
