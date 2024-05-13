package moim_today.presentation.mail;

import moim_today.application.mail.AmazonSesService;
import moim_today.implement.mail.MailScheduler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mails")
public class MailController {

    private final AmazonSesService mailService;
    private final MailScheduler mailScheduler;

    public MailController(final AmazonSesService mailService, final MailScheduler mailScheduler) {
        this.mailService = mailService;
        this.mailScheduler = mailScheduler;
    }

    @PostMapping
    public void test() {
        mailScheduler.sendInvitationMail();
    }
}