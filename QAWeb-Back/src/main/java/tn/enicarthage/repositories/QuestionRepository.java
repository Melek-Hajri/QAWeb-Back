package tn.enicarthage.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

	Page<Question> findAllByUserId(Long userId, Pageable paging);
	
	List<Question> findAllByUserId(Long userId);

	Page<Question> findAllByTitleContaining(String title, Pageable pageable);

	Page<Question> findAllByOrderByCreatedDateDesc(Pageable paging);
	
	Page<Question> findAllByOrderByVoteCountDesc(Pageable paging);
	
	@Query("SELECT q FROM Question q WHERE (q.title LIKE %:searchString% OR q.body LIKE %:searchString% OR :searchString MEMBER OF q.tags)")
	Page<Question> findByTitleContainingOrBodyContainingOrTag(@Param("searchString") String searchString, Pageable pageable);

}
