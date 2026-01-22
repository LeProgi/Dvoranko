package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.service.MailService;
import fer.leprogi.dvoranko.service.MailgunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/mail")
@RequiredArgsConstructor
public class MailTestController {

    private final MailgunService mailgunService;
    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity sendMail(){
//            mailgunService.sendEmail("vid.martin.lulic@gmail.com", "Probni mail","pozdav pusa");
        try {
            mailService.sendMail("vid.martin.lulic@gmail.com", "Test mail", "Testiram mailer");
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.ok("Kume strgo se mail");
        }
    }
}
