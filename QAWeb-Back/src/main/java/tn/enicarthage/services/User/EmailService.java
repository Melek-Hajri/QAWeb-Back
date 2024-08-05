package tn.enicarthage.services.User;

public interface EmailService {

	void sendEmail(String to, String subject, String text);
	
}
