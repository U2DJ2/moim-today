package moim_today.presentation.mail;

import moim_today.application.mail.AmazonSesService;
import moim_today.dto.mail.MailSendRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MailController {

    private final AmazonSesService mailService;

    public MailController(final AmazonSesService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/mails/send")
    public void sendMail(@RequestBody final MailSendRequest mailSendRequest){
        mailService.send(mailSendRequest);
    }
}
