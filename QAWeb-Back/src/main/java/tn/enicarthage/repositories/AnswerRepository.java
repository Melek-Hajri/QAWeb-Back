package tn.enicarthage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>{

	List<Answer> findAllByQuestionId(Long questionId);

}
