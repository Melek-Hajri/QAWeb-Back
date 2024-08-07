package tn.enicarthage.services.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = this.userRepo.findFirstByEmailAndIsActive(email, true);
		if(!optionalUser.isPresent())
			throw new UsernameNotFoundException("Email: " + email + " not found");
		return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(), optionalUser.get().getPassword(), new ArrayList<>());
	}
}
