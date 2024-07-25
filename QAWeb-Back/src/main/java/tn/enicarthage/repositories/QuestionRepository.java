package tn.enicarthage.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

	Page<Question> findAllByUserId(Long userId, Pageable paging);

}
