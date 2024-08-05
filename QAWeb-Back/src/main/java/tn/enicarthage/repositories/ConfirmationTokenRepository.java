package tn.enicarthage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.enicarthage.entities.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	ConfirmationToken findByToken(String token);
}
