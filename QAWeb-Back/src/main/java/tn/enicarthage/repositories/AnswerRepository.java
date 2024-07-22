package tn.enicarthage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>{

}
