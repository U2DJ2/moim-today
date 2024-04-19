package moim_today.presentation.mail;

import moim_today.application.mail.AmazonSesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mails")
public class MailController {

    private final AmazonSesService mailService;

    public MailController(final AmazonSesService mailService) {
        this.mailService = mailService;
    }
}