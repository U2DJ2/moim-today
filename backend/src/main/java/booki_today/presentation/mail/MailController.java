package booki_today.presentation.mail;

import booki_today.application.mail.MailService;
import booki_today.dto.mail.MailSendRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;

    public MailController(final MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/mails/send")
    public String sendMail(@RequestBody MailSendRequest mailSendRequest){
        mailService.send(mailSendRequest);
        return "메일 전송 완료";
    }
}
