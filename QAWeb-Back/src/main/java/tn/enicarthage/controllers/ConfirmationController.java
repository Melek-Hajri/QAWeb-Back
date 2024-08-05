package tn.enicarthage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.enicarthage.entities.ConfirmationToken;
import tn.enicarthage.entities.User;
import tn.enicarthage.repositories.ConfirmationTokenRepository;
import tn.enicarthage.repositories.UserRepository;

@RestController
public class ConfirmationController {

	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findById(token.getUser().getId()).get();
            user.setActive(true);
            userRepository.save(user);
            return new ResponseEntity<>("Account verified successfully, you can now login", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }
}
