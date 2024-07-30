package tn.enicarthage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findAllByUserId(Long userId);

}
