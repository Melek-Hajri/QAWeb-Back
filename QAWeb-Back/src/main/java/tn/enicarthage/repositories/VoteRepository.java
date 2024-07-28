package tn.enicarthage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Answer;
import tn.enicarthage.entities.Question;
import tn.enicarthage.entities.User;
import tn.enicarthage.entities.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

	Optional<Vote> findByUserAndQuestion(User user, Question question);

	Optional<Vote> findByUserAndAnswer(User user, Answer answer);

}
