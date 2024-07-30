package tn.enicarthage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findFirstByEmailAndIsActive(String email, boolean isActive);

}
