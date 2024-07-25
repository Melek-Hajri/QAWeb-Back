package tn.enicarthage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

}
