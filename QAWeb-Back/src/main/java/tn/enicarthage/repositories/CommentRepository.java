package tn.enicarthage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
