package tn.enicarthage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

}
