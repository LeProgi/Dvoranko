package fer.leprogi.dvoranko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try{
            mailSender.send(message);
        }catch (Exception e){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Error sending mail");
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        }
    }
}
