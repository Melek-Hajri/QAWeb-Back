package tn.enicarthage.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.enicarthage.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

	List<Image> findAllByAnswerId(Long answerId);

	List<Image> findAllByQuestionId(Long questionId);

}
