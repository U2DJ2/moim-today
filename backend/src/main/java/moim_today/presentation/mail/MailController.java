package moim_today.presentation.mail;

import moim_today.application.mail.AmazonSesService;
import moim_today.dto.mail.MailValidRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mails")
public class MailController {

    private final AmazonSesService mailService;

    public MailController(final AmazonSesService mailService) {
        this.mailService = mailService;
    }

    // ToDo: 이메일 인증 API 요청 완성 필요
    @PostMapping("/validation")
    public void mailValidate(final MailValidRequest mailValidRequest){
        mailService.sendValidateMail(mailValidRequest);
    }
}